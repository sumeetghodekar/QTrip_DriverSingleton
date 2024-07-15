package qtriptest.pages;

import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage 
{
    RemoteWebDriver driver;

    public LoginPage(RemoteWebDriver driver)
    {
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }  

    private static String loginendpoint="/pages/login/";

    @FindBy(xpath = "//input[@name='email']")
    private WebElement usrname;
    @FindBy(xpath = "//input[@name='password']")
    private WebElement pass;
    @FindBy(xpath = "//button[text()='Login to QTrip']")
    private WebElement loginButton;
    @FindBy(xpath = "//h2[text()='Login']")
    private WebElement logintext;

    public boolean isLoginPageNavigate() throws InterruptedException
    {
        Thread.sleep(3000);
        // return driver.getCurrentUrl().contains(loginendpoint);
        // String LoginURL=driver.getCurrentUrl();
        // if(LoginURL.contains(loginendpoint))
        // {
        //     return true;
        // }
        // return false;
        return this.logintext.getText().equals("Login");
    }

    public void loginUser(String username,String password)
    {
        System.out.println("Perform Login");
        Actions action=new Actions(driver);
        
        action.moveToElement(this.usrname).sendKeys(this.usrname,username).build().perform();

        action.moveToElement(this.pass).sendKeys(this.pass,password).build().perform();

        action.moveToElement(this.loginButton).click(this.loginButton).build().perform();
    }


}
