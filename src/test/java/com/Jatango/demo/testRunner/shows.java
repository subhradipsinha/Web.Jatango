/**
 * Created By Subhradip Sinha
 * Date: 9/12/2024
 * Project Name: jatango
 */

package com.Jatango.demo.testRunner;

import demo.pageObject.EmailUtil;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

import javax.mail.MessagingException;

@RunWith(Cucumber.class)
@CucumberOptions(features = "./src/test/resources/features/shows.feature",
        glue = {"demo/stepdefs"},
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" },
        tags="@Jatango_ShowsPage",
        monochrome = true)

public class shows {

    @AfterClass
    public static void tearDown() {
        String dirPath = System.getProperty("user.dir");
        String[] attachFiles = new String[3];
        attachFiles[0] = dirPath + "" + "\\Reports\\HTMLReports\\Jatango.html";
        attachFiles[1] = dirPath + "" + "\\Reports\\PdfReport\\Jatango.pdf";
        attachFiles[2] = dirPath + "" + "\\Reports.zip";
        try {
            EmailUtil.sendEmailWithAttachments(
                    "smtp-mail.outlook.com", // SMTP server
                    "587",                          // SMTP port
                    "subhradip.sinha@ncompasbusiness.com",       // Your email
                    "IceCream@2024",                // Your password
                    "subhradip.sinha@gmail.com",        // Recipient's email
                    "Test Report",                  // Email subject
                    "Current Data Please find attached the test reports.", // Email message
                    attachFiles                     // Files to attach
            );
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
}
