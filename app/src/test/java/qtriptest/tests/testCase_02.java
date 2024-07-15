package qtriptest.tests;


import qtriptest.DP;
import qtriptest.ExternalDataProvider;
import qtriptest.pages.AdventurePage;
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

public class testCase_02 
{
    static RemoteWebDriver driver;
    static HomePage home;
    static RegisterPage register;
    static LoginPage login;
    static AdventurePage adv;
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

    }
    //dataProvider = "qtripData", dataProviderClass = ExternalDataProvider.class,enabled = true
    @Test(description = "Verify that search and filter functionalities work correctly", priority = 2, groups = {"Search and Filter Flow"},dataProvider = "qtripData2", dataProviderClass = ExternalDataProvider.class,enabled = true)
    public void TestCase02(String cityname,String categoryfilter,String duration,String expectedfilterResult,String expectedunfilteredresult) throws InterruptedException
    {
        logStatus("Page test", "Verify City Search and Filter", "started");
        softassert = new SoftAssert();

        //search for city with no result
        home.searchBar("mum");

        //no city found 
        String notFound = home.noResultText();
        softassert.assertEquals(notFound,"No City found","No City found message not displayed");

        //search for city with result
        String[] cities = { cityname };
        for(String city:cities)
        {
            home.searchBar(city);

        //String cityName="Ben";
        //city found
        boolean isCity = home.isCityDisplayed(city);
        softassert.assertTrue(isCity,"No City displayed in autocomplete");


        //select duration
        adv.selectDuration(duration);
        String durationresult = adv.getTexthours();
        Thread.sleep(2000);
        softassert.assertEquals(durationresult,duration,"Duration Filtered result count mismatch");
        // System.out.println(durationresult);

        adv.selectCategory(categoryfilter);
        String categoryresult = adv.getCategory();
        Thread.sleep(2000);
        //softassert.assertEquals(categoryresult, categoryfilter,"Category Filtered result count mismatch");
        // System.out.println(categoryresult);
        //softassert.assertTrue(categoryresult.contains(categoryfilter),"Category Filtered result count mismatch");
        if(categoryresult.contains(categoryfilter))
        {
           System.out.println("Category found"+categoryresult);
        }

        adv.clearDurationfilter();
        adv.clearCategoryfilter();

        Thread.sleep(3000);
        int unfilterResult=adv.getAllResultCount();
        int expfilterresult = Integer.parseInt(expectedunfilteredresult);
        softassert.assertEquals(unfilterResult,expfilterresult,"Filtered result count mismatch");
        // System.out.println(unfilterResult);
        // System.out.println(expectedfilterResult);
        
        driver.navigate().back();
        Thread.sleep(2000);
        }
        softassert.assertAll();
    }

}
