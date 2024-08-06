/*
 * Copyright 2010-2024 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlinx.atomicfu.incremental;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.JUnit3RunnerWithInners;
import org.jetbrains.kotlin.test.KotlinTestUtils;
import org.jetbrains.kotlin.test.util.KtTestUtil;
import org.jetbrains.kotlin.test.TargetBackend;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.generators.tests.GenerateTestsKt}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("plugins/atomicfu/atomicfu-compiler/testData/projects")
@TestDataPath("$PROJECT_ROOT")
@RunWith(JUnit3RunnerWithInners.class)
public class IncrementalK2JVMWithAtomicfuRunnerTestGenerated extends AbstractIncrementalK2JVMWithAtomicfuRunnerTest {
  private void runTest(String testDataFilePath) {
    KotlinTestUtils.runTest(this::doTest, TargetBackend.JVM_IR, testDataFilePath);
  }

  public void testAllFilesPresentInProjects() {
    KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("plugins/atomicfu/atomicfu-compiler/testData/projects"), Pattern.compile("^([^\\.]+)$"), null, TargetBackend.JVM_IR, false);
  }

  @TestMetadata("atomicExtension")
  public void testAtomicExtension() {
    runTest("plugins/atomicfu/atomicfu-compiler/testData/projects/atomicExtension/");
  }

  @TestMetadata("atomicHandlers")
  public void testAtomicHandlers() {
    runTest("plugins/atomicfu/atomicfu-compiler/testData/projects/atomicHandlers/");
  }
}
