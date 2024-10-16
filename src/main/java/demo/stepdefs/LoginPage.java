package demo.stepdefs;

import demo.DriverManager;
import demo.TestBase;
import demo.pageObject.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import java.io.File;
import javax.swing.*;



import static demo.pageObject.projectAllXpath.*;
import static org.sikuli.script.Sikulix.print;


public class LoginPage extends DriverManager {
    static projectAllXpath dashBoardXpath = new projectAllXpath(driver);
    static xls_Reader reader = new xls_Reader("./src/test/resources/Data.xlsx");
    SoftAssert softAssert = new SoftAssert();
    static String MyShopsValue = reader.getCellData("Login", "MyShopsDashBoard", 2);
    static String UserNameValue = reader.getCellData("Login", "USERNAME", 2);
    static String PasswordValue = reader.getCellData("Login", "PASSWORD", 2);
    static String CaptchaValue = reader.getCellData("Login", "Captcha", 2);

    // TestCase:-1
    @Given("Enter application URL in address bar")
    public void enter_application_url_in_address_bar() throws Exception {
        try {
            ScreenRecorderUtil.startRecord("URL open");
            driver.get(prop.getPropValues(TestBase.URL));
            System.out.println("Open URL is : " + TestBase.URL);
            Thread.sleep(8000);
        }catch (Exception e){
            System.out.println("URL not open");
            screenshot_File.Jatango(driver, "Open URL");
        }
    }

    @When("Enter Username")
    public void enter_username() throws Exception {
        dashBoardXpath.moveToElementAndCLikOn(Log_In);
        enterValue(Username, UserNameValue);
        Thread.sleep(500);
        System.out.println("   Enter UserName is:  " + UserNameValue);
        try {
            File src1 = driver.findElement(By.xpath("//*[@alt='captcha']")).getScreenshotAs(OutputType.FILE);
            String path = "./Screenshots/capture.png";
            FileHandler.copy(src1, new File(path));
            ITesseract image1 = new Tesseract();
            image1.setTessVariable("user_defined_dpi", "71");
            Thread.sleep(500);
            String imageText = image1.doOCR(new File(path));
            System.out.println("imageText: " + imageText);
            Thread.sleep(500);
            String finalText = imageText.replaceAll("[^a-zA-z0-9]", "");
            System.out.println("finalText: " + finalText);
            Thread.sleep(500);

            String abc = JOptionPane.showInputDialog("Enter The capture");
            System.out.println("Test Image: " + abc);
            reader.setCellData("Login", "Captcha", 2, abc);
            Thread.sleep(500);
            String xyz = reader.getCellData("Login", "Captcha", 2);
            enterValue(CaptchaTextBox, xyz);
            System.out.println("Captch value is: " + xyz);
            Thread.sleep(500);
            clickOn(Continue);
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("CaptchaTextBox not view: " + e.getMessage());
            screenshot_File.Jatango(driver, "CaptchaTextBox not view");
        }

    }

    @Then("Enter Password")
    public void enter_password() throws Exception {
        if (Sing_In.isDisplayed()) {
            clickOnAfterElementIsVisible(Sing_In);
            Thread.sleep(500);
            try {
                File src1 = driver.findElement(By.xpath("//*[@alt='captcha']")).getScreenshotAs(OutputType.FILE);
                String path = "./Screenshots/capture.png";
                FileHandler.copy(src1, new File(path));
                ITesseract image1 = new Tesseract();
                image1.setTessVariable("user_defined_dpi", "71");
                Thread.sleep(500);
                String imageText = image1.doOCR(new File(path));
                System.out.println("imageText: " + imageText);
                Thread.sleep(500);
                String finalText = imageText.replaceAll("[^a-zA-z0-9]", "");
                System.out.println("finalText: " + finalText);
                Thread.sleep(500);

                String abc = JOptionPane.showInputDialog("Enter The capture");
                System.out.println("Test Image: " + abc);
                reader.setCellData("Login", "Captcha", 2, abc);
                Thread.sleep(500);

                enterValue(CaptchaTextBox, CaptchaValue);
                System.out.println("Captch value is: " + CaptchaValue);
                Thread.sleep(500);
                clickOn(Continue);
                System.out.println("Continue button click");
                Thread.sleep(500);

                enterValue(Password, PasswordValue);
                System.out.println(" Enter The Password is: " + PasswordValue);
                Thread.sleep(500);


            } catch (Exception e) {
                enterValue(Password, PasswordValue);
                Thread.sleep(500);
                System.out.println(" Enter The Password is: " + PasswordValue);
            }
        } else {
            screenshot_File.Jatango(driver, "Password is captch not showing");
            enterValue(Password, PasswordValue);
            System.out.println(" Enter The Password is captch not showing: " + PasswordValue);
            Thread.sleep(500);

        }
    }

    @Then("Click Sing_In")
    public void click_sing_in() throws Exception {
        Thread.sleep(500);
        WebElement SingIn = Sing_In;
        try {
            if (SingIn.isDisplayed()) {
                clickOn(SingIn);
                System.out.println("************Click The Sing_In Button************");
                Thread.sleep(500);
            } else {
                System.out.println("************Click The Sing_In Button Related Issue************");
                Thread.sleep(500);
            }
        } catch (Exception e) {
            System.out.println("Sign_in button not view");
            screenshot_File.Jatango(driver, "Sign_in button not view");
            Assert.assertTrue(true, "Sign_in button not view");
            Thread.sleep(500);
        }

        Thread.sleep(500);
    }

    @Then("User should be redirected to the homepage")
    public void user_should_be_redirected_to_the_homepage() throws Throwable {
        String CurrentURL = driver.getCurrentUrl();
        String LoginURL = TestBase.URL;
        softAssert.assertEquals(CurrentURL, LoginURL);
        System.out.println("Login Successful");
        System.out.println("Title Verify: " + driver.getTitle());
        Thread.sleep(1000);

        try {
            for (int i = 0; i < MyShopsList.size(); i++) {
                getDropDownValue(MyShopsList);
                iterateWebElementListAndSelectValue(MyShopsList, MyShopsValue);
                System.out.println("MyShopsValue is: " + MyShopsValue);
            }
        } catch (Exception e) {
            System.out.println("User should not be redirected: " + e.getMessage());
        }
    }

}