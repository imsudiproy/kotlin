plugins {
    kotlin("jvm")
    id("jps-compatible")
    id("compiler-tests-convention")
}

dependencies {
    testImplementation(projectTests(":compiler:tests-common-new"))
    testImplementation(projectTests(":compiler:fir:fir2ir"))
    testRuntimeOnly(projectTests(":compiler"))

    testImplementation(libs.junit4)
    testImplementation(kotlinStdlib())
    testImplementation(project(":libraries:tools:abi-comparator"))

    testApi(platform(libs.junit.bom))
    testImplementation(libs.junit.platform.suite)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testRuntimeOnly(libs.junit.vintage.engine)

    testImplementation(intellijCore())
}

sourceSets {
    "main" {}
    "test" { projectDefault() }
}

compilerTests {
    testData(project(":compiler").isolated, "testData/codegen")
    testData(project(":compiler").isolated, "testData/klib")
    withScriptRuntime()
    withScriptingPlugin()
    withAnnotations()
    withMockJdkRuntime()
    withTestJar()
}

fun Project.codegenTest(
    target: Int,
    jdk: JdkMajorVersion,
    jvm: String = jdk.majorVersion.toString(),
    targetInTestClass: String = "$target",
    body: Test.() -> Unit = {}
): TaskProvider<Test> = projectTest(
    taskName = "codegenTarget${targetInTestClass}Jvm${jvm}Test",
    jUnitMode = JUnitMode.JUnit5,
    maxMetaspaceSizeMb = 1024,
    defineJDKEnvVariables = listOf(jdk)
) {
    useJUnitPlatform()
    inputs.file(File(rootDir, "compiler/testData/mockJDKModified/rt.jar")).withNormalizer(ClasspathNormalizer::class)
    systemProperty(
        "org.jetbrains.kotlin.test.mockJDKModifiedRuntime",
        File(rootDir, "compiler/testData/mockJDKModified/rt.jar").absolutePath
    )
    inputs.file(File(rootDir, "compiler/testData/mockJDK/jre/lib/annotations.jar")).withNormalizer(ClasspathNormalizer::class)
    systemProperty(
        "org.jetbrains.kotlin.test.mockJdkAnnotationsJar",
        File(rootDir, "compiler/testData/mockJDK/jre/lib/annotations.jar").absolutePath
    )
    inputs.dir(File(rootDir, "third-party/annotations")).withPathSensitivity(PathSensitivity.RELATIVE)
    systemProperty(
        "third-party/annotations",
        File(rootDir, "third-party/annotations").absolutePath
    )
    inputs.dir(File(rootDir, "third-party/jsr305")).withPathSensitivity(PathSensitivity.RELATIVE)
    systemProperty(
        "third-party/jsr305",
        File(rootDir, "third-party/jsr305").absolutePath
    )
    inputs.files(files(File(rootDir, "libraries/stdlib/")).asFileTree.matching {
        include("**.kt")
    }).withPathSensitivity(PathSensitivity.RELATIVE) //TODO only kt files
    systemProperty(
        "stdlib.path",
        File(rootDir, "libraries/stdlib/").absolutePath
    )

    val testName = "JvmTarget${targetInTestClass}OnJvm${jvm}"
    filter.includeTestsMatching("org.jetbrains.kotlin.codegen.jdk.$testName")

    javaLauncher.set(project.getToolchainLauncherFor(jdk))

    systemProperty("kotlin.test.default.jvm.target", "${if (target <= 8) "1." else ""}$target")
    body()
    doFirst {
        logger.warn("Running tests with $target target and ${javaLauncher.get().metadata.installationPath.asFile}")
    }
    group = "verification"
}

//JDK 8
// This is default one and is executed in default build configuration
codegenTest(target = 8, jdk = JdkMajorVersion.JDK_1_8)

//JDK 11
codegenTest(target = 8, jdk = JdkMajorVersion.JDK_11_0)

codegenTest(target = 11, jdk = JdkMajorVersion.JDK_11_0)

//JDK 17
codegenTest(target = 8, jdk = JdkMajorVersion.JDK_17_0)

codegenTest(target = 17, jdk = JdkMajorVersion.JDK_17_0) {
    systemProperty("kotlin.test.box.d8.disable", true)
}

//..also add this two tasks to build after adding fresh jdks to build agents
val mostRecentJdk = JdkMajorVersion.values().last()

//LAST JDK from JdkMajorVersion available on machine
codegenTest(target = 8, jvm = "Last", jdk = mostRecentJdk)

codegenTest(
    target = mostRecentJdk.majorVersion,
    targetInTestClass = "Last",
    jvm = "Last",
    jdk = mostRecentJdk
) {
    systemProperty("kotlin.test.box.d8.disable", true)
}

testsJar()
