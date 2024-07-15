package qtriptest.pages;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class RegisterPage 
{

    RemoteWebDriver driver;
    public  String uniqueUsername;
    public RegisterPage(RemoteWebDriver driver)
    {
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }    
    
    private static String  registerendpoint="/pages/register/";
    //private static int count=1;

    @FindBy(xpath="//input[@name='email']")
    private WebElement useremail;
    @FindBy(xpath="//input[@name='password']")
    private WebElement pass;
    @FindBy(xpath="//input[@name='confirmpassword']")
    private WebElement confirmpass;
    @FindBy(xpath="//button[text()='Register Now']")
    private WebElement registerbtn;


    public boolean isRegisterPagesucceed() throws InterruptedException
    {
        //Thread.sleep(3000);
        return driver.getCurrentUrl().contains(registerendpoint);
    }

    public String generateUniqueEmail(String baseEmail) {
        String uniqueEmail = baseEmail.split("@")[0] + System.currentTimeMillis() + "@" + baseEmail.split("@")[1];
        uniqueUsername = uniqueEmail;
        return uniqueEmail;
    }

    public void registerUser(String email,String password,String confirmPassword)
    {
        System.out.println("User is getting Register");

      
        Actions action = new Actions(driver);

        action.moveToElement(this.useremail).click().sendKeys(this.useremail,email).build().perform();

        action.moveToElement(this.pass).click().sendKeys(this.pass,password).build().perform();

        action.moveToElement(this.confirmpass).click().sendKeys(this.confirmpass,confirmPassword).build().perform();

        action.moveToElement(this.registerbtn).click(this.registerbtn).build().perform();

    }

}
