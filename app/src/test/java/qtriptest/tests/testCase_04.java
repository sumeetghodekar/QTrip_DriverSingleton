package qtriptest.tests;

import qtriptest.DP;
import qtriptest.ExternalDataProvider;
//import qtriptest.ExternalDataProvider;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HistoryPage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
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


public class testCase_04 
{
    static RemoteWebDriver driver;
    static HomePage home;
    static RegisterPage register;
    static LoginPage login;
    static AdventurePage adv;
    static HistoryPage his;
    static AdventureDetailsPage adp;
    SoftAssert softassert;
    private static final int pageload=20;
    public static void logStatus(String type, String message, String status) 
    {
		System.out.println(String.format("%s |  %s  |  %s | %s",
		String.valueOf(java.time.LocalDateTime.now()), type, message, status));
	}

    @BeforeClass(alwaysRun = true, enabled = true)
	public static void createDriver() throws MalformedURLException 
    {
        launchApplication("chrome","https://qtripdynamic-qa-frontend.vercel.app/");
        driver = getDriver("chrome");
		logStatus("driver", "Initializing driver", "Started");
		logStatus("driver", "Initializing driver", "Success");



        //Initialize 
        home = new HomePage(driver);
        register = new RegisterPage(driver);
        login = new LoginPage(driver);
        adv = new AdventurePage(driver);
        his = new HistoryPage(driver);
        adp = new AdventureDetailsPage(driver);

    }

    @Test(description = "Verify that booking history can be viewed", priority = 4, groups = {"Reliability Flow"},dataProvider = "qtripData4", dataProviderClass = ExternalDataProvider.class,enabled = true)
    public void TestCase04(String username,String password,String dataset1,String dataset2,String dataset3) throws InterruptedException
    {
        logStatus("Page test", "Verify that booking history can be viewed", "started");
        softassert = new SoftAssert();

        String[][] allDatasets = {
            dataset1.split(";"),
            dataset2.split(";"),
            dataset3.split(";")
        };

        // String[] userdata = { dataset1,dataset2,dataset3 };
        for (String[] dataset: allDatasets) 
        {
            String cityname = dataset[0];
            String adevnturename = dataset[1];
            String guestname = dataset[2];
            String adevnturedate = dataset[3];
            String adevnturecount = dataset[4];
            //navigate to resgister
            home.navigatetoRegister();
            //verify for register page
            softassert.assertTrue(register.isRegisterPagesucceed(),"Register page navigation failed");
            //register the user
            String uniqueemail=register.generateUniqueEmail(username);
            register.registerUser(uniqueemail, password, password);
            //navigate to login page
            softassert.assertTrue(login.isLoginPageNavigate(),"Login page navigation failed");
            //perform login 
            login.loginUser(register.uniqueUsername, password);
            //search bar
            home.searchBar(cityname);
            //search city
            boolean isCity = home.isCityDisplayed(cityname);
            softassert.assertTrue(isCity,"No City displayed in autocomplete");
            //click on adevnture and search
            adp.searchAdventure(adevnturename);
            //select the adventure
            adp.clickonAdventure();
            //enter name 
            adp.enterName(guestname);
            //enter date
            adp.enterDate(adevnturedate);
            //enter count
            adp.entercount(adevnturecount);
            //reserve button
            adp.submitButton();
            //check for successfull
            softassert.assertTrue(adp.greetingMessage(),"Successfull message not found");
            //
            his.reservationPage();
            //logout 
            home.performLogout();
        }
        his.reservationPage();
        his.listTransaction();

    }

    @AfterClass(enabled = true)
	public static void quitDriver() throws MalformedURLException {
		driver.close();
		driver.quit();
		logStatus("driver", "Quitting driver", "Success");
	}
}
