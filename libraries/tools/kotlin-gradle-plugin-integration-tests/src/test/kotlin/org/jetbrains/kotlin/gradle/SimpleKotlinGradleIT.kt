package org.jetbrains.kotlin.gradle

import org.gradle.api.logging.LogLevel
import org.gradle.util.GradleVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilerExecutionStrategy
import org.jetbrains.kotlin.gradle.testbase.*
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.plugin.diagnostics.KotlinToolingDiagnostics
import org.jetbrains.kotlin.test.TestMetadata
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import java.util.zip.ZipFile
import kotlin.io.path.*

@JvmGradlePluginTests
@DisplayName("KGP simple tests")
class SimpleKotlinGradleIT : KGPBaseTest() {

    @GradleTest
    @DisplayName("On second run common tasks should be up-to-date")
    fun testSimpleCompile(gradleVersion: GradleVersion) {
        project(
            projectName = "simpleProject",
            gradleVersion = gradleVersion,
            buildOptions = defaultBuildOptions.copy(logLevel = LogLevel.DEBUG),
        ) {
            build("compileDeployKotlin", "build") {
                assertOutputContains("Finished executing kotlin compiler using ${KotlinCompilerExecutionStrategy.DAEMON} strategy")
                assertFileInProjectExists("build/reports/tests/test/classes/demo.TestSource.html")
                assertTasksExecuted(":compileKotlin", ":compileTestKotlin", ":compileDeployKotlin")
            }

            build("compileDeployKotlin", "build") {
                assertTasksUpToDate(
                    ":compileKotlin",
                    ":compileTestKotlin",
                    ":compileDeployKotlin",
                    ":compileJava"
                )
            }
        }
    }

    @GradleTest
    @DisplayName("Plugin allows to suppress all warnings")
    fun testSuppressWarnings(gradleVersion: GradleVersion) {
        project("suppressWarnings", gradleVersion) {
            build("build") {
                assertTasksExecuted(":compileKotlin")
                assertOutputDoesNotContain("""w: [^\r\n]*?\.kt""".toRegex())
            }
        }
    }

    @GradleTest
    @DisplayName("Plugin should allow to add custom Kotlin directory")
    fun testKotlinCustomDirectory(gradleVersion: GradleVersion) {
        project("customSrcDir", gradleVersion) {
            build("build")
        }
    }

    @GradleTest
    @DisplayName("Plugin should correctly handle additional java source directories")
    fun testKotlinExtraJavaSrc(gradleVersion: GradleVersion) {
        project("additionalJavaSrc", gradleVersion) {
            build("build")
        }
    }

    @GradleTest
    @DisplayName("Using newer language features with older api level should fail the build")
    fun testLanguageVersion(gradleVersion: GradleVersion) {
        project("languageVersion", gradleVersion) {
            buildAndFail("build") {
                assertOutputContains("The feature \"break continue in inline lambdas\" is only available since language version 2.2")
            }
        }
    }

    @GradleTest
    @DisplayName("Compilation should fail on unknown JVM target")
    fun testJvmTarget(gradleVersion: GradleVersion) {
        project("jvmTarget", gradleVersion) {
            buildAndFail("build") {
                assertOutputContains("Unknown Kotlin JVM target: 1.7")
            }
        }
    }

    @GradleTest
    @DisplayName("Should produce '.kotlin_module' file with specified name")
    fun testModuleName(gradleVersion: GradleVersion) {
        project("moduleName", gradleVersion) {
            build("build") {
                assertFileInProjectExists("build/classes/kotlin/main/META-INF/FLAG.kotlin_module")
                assertFileInProjectNotExists("build/classes/kotlin/main/META-INF/moduleName.kotlin_module")
                assertOutputDoesNotContain("Argument -module-name is passed multiple times")
            }
        }
    }

    @GradleTest
    @DisplayName("Compile task destination dir should be configured on configuration phase")
    fun testDestinationDirReferencedDuringEvaluation(gradleVersion: GradleVersion) {
        project("destinationDirReferencedDuringEvaluation", gradleVersion) {
            build("build") {
                assertOutputContains("GreeterTest > testHelloWorld PASSED")
            }
        }
    }

    @GradleTest
    @DisplayName("Plugin correctly handle redefined build dir location")
    fun testBuildDirLazyEvaluation(gradleVersion: GradleVersion) {
        project("kotlinProject", gradleVersion) {
            // Change the build directory in the end of the build script:
            val customBuildDirName = "customBuild"
            buildGradle.append(
                "buildDir = '$customBuildDirName'"
            )

            build("build") {
                assertDirectoryInProjectExists("$customBuildDirName/classes")
                assertFileInProjectNotExists("build")
            }
        }
    }

    @GradleTest
    @DisplayName("Should correctly work with Groovy lang modules")
    fun testGroovyInterop(gradleVersion: GradleVersion) {
        project("groovyInterop", gradleVersion) {
            build("build") {
                assertTasksExecuted(":test")
                assertOutputContains("GroovyInteropTest > parametersInInnerClassConstructor PASSED")
                assertOutputContains("GroovyInteropTest > classWithReferenceToInner PASSED")
                assertOutputContains("GroovyInteropTest > groovyTraitAccessor PASSED")
                assertOutputContains("GroovyInteropTest > parametersInEnumConstructor PASSED")
            }
        }
    }

    //Proguard corrupts RuntimeInvisibleParameterAnnotations/RuntimeVisibleParameterAnnotations tables:
    // https://sourceforge.net/p/proguard/bugs/735/
    // Gradle 7 compatibility issue: https://github.com/Guardsquare/proguard/issues/136
    @GradleTest
    @DisplayName("Should correctly interop with ProGuard")
    @TestMetadata("interopWithProguarded")
    fun testInteropWithProguarded(gradleVersion: GradleVersion) {
        project(
            "interopWithProguarded",
            gradleVersion,
        ) {
            build("build") {
                assertTasksExecuted(":test")
                assertOutputContains("InteropWithProguardedTest > parametersInInnerKotlinClassConstructor PASSED")
                assertOutputContains("InteropWithProguardedTest > parametersInInnerJavaClassConstructor PASSED")
                assertOutputContains("InteropWithProguardedTest > parametersInJavaEnumConstructor PASSED")
                assertOutputContains("InteropWithProguardedTest > parametersInKotlinEnumConstructor PASSED")
            }
        }
    }

    @GradleTest
    @DisplayName("Should correctly work with Scala lang modules")
    fun testScalaInterop(gradleVersion: GradleVersion) {
        project("scalaInterop", gradleVersion) {
            build("build") {
                assertTasksExecuted(":test")
                assertOutputContains("ScalaInteropTest > parametersInInnerClassConstructor PASSED")
            }
        }
    }

    @GradleTest
    @DisplayName("Should not produce kotlin-stdlib version conflict on Kotlin files compilation in 'buildSrc' module")
    internal fun testKotlinDslStdlibVersionConflict(gradleVersion: GradleVersion) {
        project(
            projectName = "buildSrcUsingKotlinCompilationAndKotlinPlugin",
            gradleVersion,
        ) {
            listOf(
                "compileClasspath",
                "compileOnly",
                "runtimeClasspath"
            ).forEach { configuration ->
                build("-p", "buildSrc", "dependencies", "--configuration", configuration) {
                    listOf(
                        "org.jetbrains.kotlin:kotlin-stdlib:${buildOptions.kotlinVersion}",
                        "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${buildOptions.kotlinVersion}",
                        "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${buildOptions.kotlinVersion}",
                        "org.jetbrains.kotlin:kotlin-stdlib-common:${buildOptions.kotlinVersion}",
                        "org.jetbrains.kotlin:kotlin-reflect:${buildOptions.kotlinVersion}",
                        "org.jetbrains.kotlin:kotlin-script-runtime:${buildOptions.kotlinVersion}"
                    ).forEach { assertOutputDoesNotContain(it) }
                }
            }

            build("assemble")
        }
    }

    @DisplayName("Changing compile task destination directory does not break test compilation")
    @GradleTest
    internal fun customDestinationDir(gradleVersion: GradleVersion) {
        project("simpleProject", gradleVersion) {
            //language=Groovy
            buildGradle.appendText(
                """
                |
                |def compileKotlinTask = tasks.named("compileKotlin", org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile.class)
                |
                |compileKotlinTask.configure {
                |    it.destinationDirectory.set(project.layout.buildDirectory.dir("banana"))
                |}
                |
                |def compileKotlinTaskOutput = compileKotlinTask.flatMap { it.destinationDirectory }
                |sourceSets.test.compileClasspath.from(compileKotlinTaskOutput)
                |sourceSets.test.runtimeClasspath.from(compileKotlinTaskOutput)
                |
                """.trimMargin()
            )

            build("build") {
                assertFileInProjectExists("build/banana/demo/KotlinGreetingJoiner.class")
                assertFileInProjectExists("build/libs/simpleProject.jar")
                ZipFile(projectPath.resolve("build/libs/simpleProject.jar").toFile()).use { jar ->
                    assert(jar.entries().asSequence().count { it.name == "demo/KotlinGreetingJoiner.class" } == 1) {
                        "The jar should contain one entry `demo/KotlinGreetingJoiner.class` with no duplicates\n" +
                                jar.entries().asSequence().map { it.name }.joinToString()
                    }
                }
            }
        }
    }

    @DisplayName("Default jar content should not contain duplicates")
    @GradleTest
    internal fun defaultJarContent(gradleVersion: GradleVersion) {
        project("simpleProject", gradleVersion) {
            build("build") {
                assertFileInProjectExists("build/libs/simpleProject.jar")
                ZipFile(projectPath.resolve("build/libs/simpleProject.jar").toFile()).use { jar ->
                    assert(jar.entries().asSequence().count { it.name == "demo/KotlinGreetingJoiner.class" } == 1) {
                        "The jar should contain one entry `demo/KotlinGreetingJoiner.class` with no duplicates\n" +
                                jar.entries().asSequence().map { it.name }.joinToString()
                    }
                }
            }
        }
    }

    @Disabled("KT-58223: Currently is not used and we should start using it after working on followup issues")
    @DisplayName("Possible to override kotlin.user.home location")
    @GradleTest
    fun overrideKotlinUserHome(
        gradleVersion: GradleVersion,
        @TempDir tempDir: Path,
    ) {
        project(
            projectName = "simpleProject",
            gradleVersion = gradleVersion,
            buildOptions = defaultBuildOptions.copy(kotlinUserHome = null)
        ) {
            gradleProperties.appendText(
                """
                |
                |kotlin.user.home=${tempDir.resolve("kotlin-cache").absolutePathString().normalizePath()}
                """.trimMargin()
            )

            build("compileKotlin") {
                assertTasksExecuted(":compileKotlin")

                val baseProjectsDir = tempDir.resolve("kotlin-cache")
                assertDirectoryExists(baseProjectsDir)
            }
        }
    }

    @DisplayName("KT-63499: source sets conventions are not registered since Gradle 8.2")
    @GradleTestVersions(minVersion = TestVersions.Gradle.G_8_2)
    @GradleTest
    fun sourceSetsConventionsAreNotRegistered(gradleVersion: GradleVersion) {
        project("simpleProject", gradleVersion) {
            // project's buildscript has to be in Groovy
            buildGradle.append(
                //language=Gradle
                """
                sourceSets {
                    main {
                        customSourceFilesExtensions // try using a property of KotlinSourceSet through the source set convention
                    }
                }
                """.trimIndent()
            )
            KotlinSourceSet::customSourceFilesExtensions // ensure the accessed property is available on KotlinSourceSet
            buildAndFail("help") {
                assertOutputContains("Could not get unknown property 'customSourceFilesExtensions' for source set 'main' ")
            }
        }
    }

    @GradleTest
    fun testKotlinCompilerEmbeddable(gradleVersion: GradleVersion) {
        project("kotlinProjectWithBuildSrc", gradleVersion) {
            build {
                assertNoDiagnostic(KotlinToolingDiagnostics.KotlinCompilerEmbeddableIsPresentInClasspath)
            }
            subProject("buildSrc").buildScriptInjection {
                project.dependencies.add("runtimeOnly", "org.jetbrains.kotlin:kotlin-compiler-embeddable:1.7.10")
            }
            buildAndFail {
                // example of incompatibility caused by the problem
                // if it started to fail, feel free to remove/adjust this assertion
                assertOutputContains("class org.jetbrains.kotlin.build.report.metrics.GradleBuildTime can not implement org.jetbrains.kotlin.build.report.metrics.BuildTime, because it is not an interface")
                assertHasDiagnostic(KotlinToolingDiagnostics.KotlinCompilerEmbeddableIsPresentInClasspath)
            }
        }
    }
}
