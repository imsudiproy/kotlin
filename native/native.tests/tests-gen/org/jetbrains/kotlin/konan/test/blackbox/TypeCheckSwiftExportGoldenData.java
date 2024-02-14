/*
 * Copyright 2010-2024 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.konan.test.blackbox;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.util.KtTestUtil;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.generators.tests.GenerateNativeTestsKt}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("native/swift/swift-export-standalone/testData")
@TestDataPath("$PROJECT_ROOT")
public class TypeCheckSwiftExportGoldenData extends SwiftTypeCheckBaseTest {
  @Test
  public void testAllFilesPresentInTestData() {
    KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("native/swift/swift-export-standalone/testData"), Pattern.compile("^(.+)\\.swift$"), null, true);
  }

  @Nested
  @TestMetadata("native/swift/swift-export-standalone/testData/class_no_package")
  @TestDataPath("$PROJECT_ROOT")
  public class Class_no_package {
    @Test
    public void testAllFilesPresentInClass_no_package() {
      KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("native/swift/swift-export-standalone/testData/class_no_package"), Pattern.compile("^(.+)\\.swift$"), null, true);
    }

    @Nested
    @TestMetadata("native/swift/swift-export-standalone/testData/class_no_package/golden_result")
    @TestDataPath("$PROJECT_ROOT")
    public class Golden_result {
      @Test
      public void testAllFilesPresentInGolden_result() {
        KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("native/swift/swift-export-standalone/testData/class_no_package/golden_result"), Pattern.compile("^(.+)\\.swift$"), null, true);
      }

      @Test
      @TestMetadata("result.swift")
      public void testResult() {
        runTest("native/swift/swift-export-standalone/testData/class_no_package/golden_result/result.swift");
      }
    }

    @Nested
    @TestMetadata("native/swift/swift-export-standalone/testData/class_no_package/input_root")
    @TestDataPath("$PROJECT_ROOT")
    public class Input_root {
      @Test
      public void testAllFilesPresentInInput_root() {
        KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("native/swift/swift-export-standalone/testData/class_no_package/input_root"), Pattern.compile("^(.+)\\.swift$"), null, true);
      }
    }
  }

  @Nested
  @TestMetadata("native/swift/swift-export-standalone/testData/documentation")
  @TestDataPath("$PROJECT_ROOT")
  public class Documentation {
    @Test
    public void testAllFilesPresentInDocumentation() {
      KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("native/swift/swift-export-standalone/testData/documentation"), Pattern.compile("^(.+)\\.swift$"), null, true);
    }

    @Nested
    @TestMetadata("native/swift/swift-export-standalone/testData/documentation/golden_result")
    @TestDataPath("$PROJECT_ROOT")
    public class Golden_result {
      @Test
      public void testAllFilesPresentInGolden_result() {
        KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("native/swift/swift-export-standalone/testData/documentation/golden_result"), Pattern.compile("^(.+)\\.swift$"), null, true);
      }

      @Test
      @TestMetadata("result.swift")
      public void testResult() {
        runTest("native/swift/swift-export-standalone/testData/documentation/golden_result/result.swift");
      }
    }

    @Nested
    @TestMetadata("native/swift/swift-export-standalone/testData/documentation/input_root")
    @TestDataPath("$PROJECT_ROOT")
    public class Input_root {
      @Test
      public void testAllFilesPresentInInput_root() {
        KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("native/swift/swift-export-standalone/testData/documentation/input_root"), Pattern.compile("^(.+)\\.swift$"), null, true);
      }
    }
  }

  @Nested
  @TestMetadata("native/swift/swift-export-standalone/testData/functions")
  @TestDataPath("$PROJECT_ROOT")
  public class Functions {
    @Test
    public void testAllFilesPresentInFunctions() {
      KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("native/swift/swift-export-standalone/testData/functions"), Pattern.compile("^(.+)\\.swift$"), null, true);
    }

    @Nested
    @TestMetadata("native/swift/swift-export-standalone/testData/functions/golden_result")
    @TestDataPath("$PROJECT_ROOT")
    public class Golden_result {
      @Test
      public void testAllFilesPresentInGolden_result() {
        KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("native/swift/swift-export-standalone/testData/functions/golden_result"), Pattern.compile("^(.+)\\.swift$"), null, true);
      }

      @Test
      @TestMetadata("result.swift")
      public void testResult() {
        runTest("native/swift/swift-export-standalone/testData/functions/golden_result/result.swift");
      }
    }

    @Nested
    @TestMetadata("native/swift/swift-export-standalone/testData/functions/input_root")
    @TestDataPath("$PROJECT_ROOT")
    public class Input_root {
      @Test
      public void testAllFilesPresentInInput_root() {
        KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("native/swift/swift-export-standalone/testData/functions/input_root"), Pattern.compile("^(.+)\\.swift$"), null, true);
      }
    }
  }

  @Nested
  @TestMetadata("native/swift/swift-export-standalone/testData/no_package")
  @TestDataPath("$PROJECT_ROOT")
  public class No_package {
    @Test
    public void testAllFilesPresentInNo_package() {
      KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("native/swift/swift-export-standalone/testData/no_package"), Pattern.compile("^(.+)\\.swift$"), null, true);
    }

    @Nested
    @TestMetadata("native/swift/swift-export-standalone/testData/no_package/golden_result")
    @TestDataPath("$PROJECT_ROOT")
    public class Golden_result {
      @Test
      public void testAllFilesPresentInGolden_result() {
        KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("native/swift/swift-export-standalone/testData/no_package/golden_result"), Pattern.compile("^(.+)\\.swift$"), null, true);
      }

      @Test
      @TestMetadata("result.swift")
      public void testResult() {
        runTest("native/swift/swift-export-standalone/testData/no_package/golden_result/result.swift");
      }
    }

    @Nested
    @TestMetadata("native/swift/swift-export-standalone/testData/no_package/input_root")
    @TestDataPath("$PROJECT_ROOT")
    public class Input_root {
      @Test
      public void testAllFilesPresentInInput_root() {
        KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("native/swift/swift-export-standalone/testData/no_package/input_root"), Pattern.compile("^(.+)\\.swift$"), null, true);
      }
    }
  }

  @Nested
  @TestMetadata("native/swift/swift-export-standalone/testData/variables")
  @TestDataPath("$PROJECT_ROOT")
  public class Variables {
    @Test
    public void testAllFilesPresentInVariables() {
      KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("native/swift/swift-export-standalone/testData/variables"), Pattern.compile("^(.+)\\.swift$"), null, true);
    }

    @Nested
    @TestMetadata("native/swift/swift-export-standalone/testData/variables/golden_result")
    @TestDataPath("$PROJECT_ROOT")
    public class Golden_result {
      @Test
      public void testAllFilesPresentInGolden_result() {
        KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("native/swift/swift-export-standalone/testData/variables/golden_result"), Pattern.compile("^(.+)\\.swift$"), null, true);
      }

      @Test
      @TestMetadata("result.swift")
      public void testResult() {
        runTest("native/swift/swift-export-standalone/testData/variables/golden_result/result.swift");
      }
    }

    @Nested
    @TestMetadata("native/swift/swift-export-standalone/testData/variables/input_root")
    @TestDataPath("$PROJECT_ROOT")
    public class Input_root {
      @Test
      public void testAllFilesPresentInInput_root() {
        KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("native/swift/swift-export-standalone/testData/variables/input_root"), Pattern.compile("^(.+)\\.swift$"), null, true);
      }
    }
  }
}
