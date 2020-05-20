/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.j2k;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.JUnit3RunnerWithInners;
import org.jetbrains.kotlin.test.KotlinTestUtils;
import org.jetbrains.kotlin.test.TestMetadata;
import org.jetbrains.kotlin.test.TestRoot;
import org.junit.runner.RunWith;

/*
 * This class is generated by {@link org.jetbrains.kotlin.generators.tests.TestsPackage}.
 * DO NOT MODIFY MANUALLY.
 */
@SuppressWarnings("all")
@TestRoot("j2k/old")
@TestDataPath("$CONTENT_ROOT")
@RunWith(JUnit3RunnerWithInners.class)
@TestMetadata("testData/multiFile")
public class JavaToKotlinConverterMultiFileTestGenerated extends AbstractJavaToKotlinConverterMultiFileTest {
    private void runTest(String testDataFilePath) throws Exception {
        KotlinTestUtils.runTest(this::doTest, this, testDataFilePath);
    }

    @TestMetadata("AnnotationWithArrayParameter")
    public void testAnnotationWithArrayParameter() throws Exception {
        runTest("testData/multiFile/AnnotationWithArrayParameter/");
    }

    @TestMetadata("FieldToProperty")
    public void testFieldToProperty() throws Exception {
        runTest("testData/multiFile/FieldToProperty/");
    }

    @TestMetadata("FunctionalInterfaceAcceptor")
    public void testFunctionalInterfaceAcceptor() throws Exception {
        runTest("testData/multiFile/FunctionalInterfaceAcceptor/");
    }

    @TestMetadata("GetterAndSetterUsages")
    public void testGetterAndSetterUsages() throws Exception {
        runTest("testData/multiFile/GetterAndSetterUsages/");
    }

    @TestMetadata("KT11952")
    public void testKT11952() throws Exception {
        runTest("testData/multiFile/KT11952/");
    }

    @TestMetadata("NullabilityByDFa")
    public void testNullabilityByDFa() throws Exception {
        runTest("testData/multiFile/NullabilityByDFa/");
    }

    @TestMetadata("ProtectedVisibility")
    public void testProtectedVisibility() throws Exception {
        runTest("testData/multiFile/ProtectedVisibility/");
    }

    @TestMetadata("ToCompanionObject")
    public void testToCompanionObject() throws Exception {
        runTest("testData/multiFile/ToCompanionObject/");
    }

    @TestMetadata("ToObject")
    public void testToObject() throws Exception {
        runTest("testData/multiFile/ToObject/");
    }
}
