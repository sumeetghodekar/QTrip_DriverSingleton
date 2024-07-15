package qtriptest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverSingleton {

    private static RemoteWebDriver driver = null;
    private static final int pageload=20;
		private DriverSingleton(){}

		public static RemoteWebDriver getDriver(String browser) throws MalformedURLException{
			if(driver == null){
                switch(browser){
                   case "chrome":
                            ChromeOptions options = new ChromeOptions();
                            options.addArguments("--no-sandbox");
                            options.addArguments("--disable-gpu");
                            driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), options);
                            break;

                   case "firefox":
                            FirefoxOptions ff = new FirefoxOptions();
                            //ff.setBrowserVersion("92");
                            driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), ff);
                            break;
                }
				
                driver.manage().window().maximize();
                driver.manage().timeouts().pageLoadTimeout(pageload, TimeUnit.SECONDS);
			}
			return driver;
        
        }

        public static void launchApplication(String browser,String URL)
        {
            try 
            {
                getDriver(browser).get(URL);    
            } catch (Exception e) {
                //TODO: handle exception
                e.printStackTrace();
            }
        }
    }
