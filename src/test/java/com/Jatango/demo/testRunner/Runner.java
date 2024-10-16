package com.Jatango.demo.testRunner;


import demo.pageObject.EmailUtil;
import demo.pageObject.xls_Reader;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.junit.AfterClass;
import javax.mail.MessagingException;

@RunWith(Cucumber.class)
@CucumberOptions(features = "./src/test/resources/features",
        glue = {"demo/stepdefs"},
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" },
        tags="@Jatango_Login",
        monochrome = true)

public class Runner {
    @AfterClass
    public static void tearDown() {
         xls_Reader reader = new xls_Reader("./src/test/resources/Data.xlsx");
         String dirPath = System.getProperty("user.dir");
        String[] attachFiles = new String[3];
        attachFiles[0] = dirPath + "" + "\\Reports\\HTMLReports\\Jatango.html";
        attachFiles[1] = dirPath + "" + "\\Reports\\PdfReport\\Jatango.pdf";
        attachFiles[2] = dirPath + "" + "\\Reports.zip";
        try {
            EmailUtil.sendEmailWithAttachments(
                    "smtp-mail.outlook.com", // SMTP server
                    "587",                          // SMTP port
                    reader.getCellData("SendMail","EmailID",2),       // Your email
                    reader.getCellData("SendMail","Password",2),                // Your password
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
