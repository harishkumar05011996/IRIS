package com.IRIS.testRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

public class TestRunner {

    public static void main(String[] args) {

        String suitePath = System.getProperty("user.dir")
                + File.separator + "src"
                + File.separator + "main"
                + File.separator + "resources"
                + File.separator + "Suite"
                + File.separator + "execute_Default.xml";

        System.out.println("Suite path = " + suitePath);

        // 1) Validate suite file exists
        File suiteFile = new File(suitePath);
        if (!suiteFile.exists()) {
            System.out.println("❌ Suite file NOT found: " + suiteFile.getAbsolutePath());
            return;
        }

        // 2) Run TestNG
        TestNG testNg = new TestNG();
        List<String> suites = new ArrayList<>();
        suites.add(suiteFile.getAbsolutePath());

        testNg.setTestSuites(suites);

        // Optional debugging
        testNg.setVerbose(2);
        testNg.setUseDefaultListeners(true); // generates test-output by default

        System.out.println("✅ Starting TestNG...");
        testNg.run();   // ✅ THIS WAS MISSING
        System.out.println("✅ TestNG execution finished.");
    }
}
