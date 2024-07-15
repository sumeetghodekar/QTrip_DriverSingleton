package qtriptest.tests;

import qtriptest.DP;
import qtriptest.ExternalDataProvider;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.core.util.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;
import static qtriptest.DriverSingleton.*;
public class testCase_01 
{
    static RemoteWebDriver driver;
    static HomePage home;
    static RegisterPage register;
    static LoginPage login;
    SoftAssert softassert;
    private static final int pageload=20;
    public static void logStatus(String type, String message, String status) {
		System.out.println(String.format("%s |  %s  |  %s | %s",
				String.valueOf(java.time.LocalDateTime.now()), type, message, status));
	}

    @BeforeClass(alwaysRun = true, enabled = true)
	public static void createDriver() throws MalformedURLException {
        launchApplication("chrome","https://qtripdynamic-qa-frontend.vercel.app/");
        driver = getDriver("chrome");
        // ChromeOptions options=new ChromeOptions();
        // options.addArguments("--no-sandbox");
        // options.addArguments("--disable-gpu");
		logStatus("driver", "Initializing driver", "Started");
		// final DesiredCapabilities capabilities = new DesiredCapabilities();
		// capabilities.setBrowserName(BrowserType.CHROME);
		// driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);
        // driver.manage().window().maximize();
        // driver.manage().deleteAllCookies();
        // driver.get("https://qtripdynamic-qa-frontend.vercel.app/");
        // driver.manage().timeouts().pageLoadTimeout(pageload, TimeUnit.SECONDS);

		logStatus("driver", "Initializing driver", "Success");

        //Initialize 
        home = new HomePage(driver);
        register = new RegisterPage(driver);
        login = new LoginPage(driver);
	}

    @Test(description = "Verify that user can log in",dataProvider = "qtripData", dataProviderClass = ExternalDataProvider.class,enabled = true,priority = 1, groups = {"Login Flow"})
    public void TestCase01(String username,String password) throws InterruptedException
    {
        WebDriverWait wait=new WebDriverWait(driver,20);
        softassert = new SoftAssert();

        //Check for register button
        softassert.assertTrue(home.isRegitserVisible(),"Register button on home not found");

        //navigate to register page
        home.navigatetoRegister();

        //check user is navigated to regsiter page
        softassert.assertTrue(register.isRegisterPagesucceed(),"Register page navigation failed");

        //register the user
        String uniqueemail=register.generateUniqueEmail(username);
        register.registerUser(uniqueemail, password, password);

        //register.registerUser(email, password, confirmpassword);

        //wait.until(ExpectedConditions.urlToBe("https://qtripdynamic-qa-frontend.vercel.app/pages/login"));
        //check user is navigated to login page
        softassert.assertTrue(login.isLoginPageNavigate(),"Login page navigation failed");
        //System.out.println(driver.getCurrentUrl());
        //perform login 
        login.loginUser(register.uniqueUsername, password);

        //verify logout 
        softassert.assertTrue(home.navBarOption("logout"),"Logout button did not found");

        //perform logout
        home.performLogout();

        softassert.assertAll();

    }

}
