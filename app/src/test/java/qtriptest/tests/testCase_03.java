package qtriptest.tests;

import qtriptest.DP;
import qtriptest.ExternalDataProvider;
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


public class testCase_03 
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

    @Test(description = "Verify that booking and cancellation functionalities work correctly", priority = 3, groups = {"Booking and Cancellation Flow"},dataProvider = "qtripData3", dataProviderClass = ExternalDataProvider.class,enabled = true)
    public void TestCase03(String username,String password,String searchCity,String adventurename,String guestName,String date,String count) throws InterruptedException
    {
        logStatus("Page test", "Verify adventure booking and cancellation", "started");
        softassert = new SoftAssert();
        String[] usertext = { username };
        
        for (String str : usertext) 
        {
            //navigate to resgister
            home.navigatetoRegister();

            //verify for register page
            softassert.assertTrue(register.isRegisterPagesucceed(),"Register page navigation failed");
            
            //register the user
            String uniqueemail=register.generateUniqueEmail(str);
            register.registerUser(uniqueemail, password, password);

            //navigate to login page
            softassert.assertTrue(login.isLoginPageNavigate(),"Login page navigation failed");

            //perform login 
            login.loginUser(register.uniqueUsername, password);

            //search bar
            home.searchBar(searchCity);

            //search city
            boolean isCity = home.isCityDisplayed(searchCity);
            softassert.assertTrue(isCity,"No City displayed in autocomplete");

            //click on adevnture and search
            adp.searchAdventure(adventurename);

            //select the adventure
            adp.clickonAdventure();

            //enter name 
            adp.enterName(guestName);

            //enter date
            adp.enterDate(date);

            //enter count
            adp.entercount(count);

            //reserve button
            adp.submitButton();

            //check for successfull
            softassert.assertTrue(adp.greetingMessage(),"Successfull message not found");

            //reservation page
            his.reservationPage();

            // List<String> transactionIDs = his.getAllTransactionIDs();
            // softassert.assertFalse(transactionIDs.isEmpty(), "No transaction IDs found");
    
            // for (String transactionID : transactionIDs) {
            //     his.cancelReservationForTransactionID(transactionID);
            //     softassert.assertFalse(his.isTransactionIDPresent(transactionID),
            //             "Transaction ID should be removed after cancellation");
            // }
                
            his.cancelButton();
            his.reservationCancell();

            home.performLogout();

            softassert.assertAll();
        }

    }


}
