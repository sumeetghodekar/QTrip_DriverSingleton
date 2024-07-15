package qtriptest.pages;

import java.util.List;
import java.util.concurrent.TimeoutException;
import org.apache.commons.math3.analysis.function.Exp;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdventureDetailsPage 
{
    RemoteWebDriver driver;
    WebDriverWait wait;
    public AdventureDetailsPage(RemoteWebDriver driver)
    {
        this.driver=driver;
        wait=new WebDriverWait(driver, 20);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//input[@id='search-adventures']")
    private WebElement searchAdv;
    @FindBy(xpath = "//img[@class='img-responsive']")
    private WebElement imgClick;
    @FindBy(xpath = "//input[@type='text']")
    private WebElement nameField;
    @FindBy(xpath = "//input[@type='date']")
    private WebElement datefield;
    @FindBy(xpath = "//input[@type='number']")
    private WebElement countfield;
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement subBtn;
    @FindBy(xpath = "//div[@id='reserved-banner']")
    private WebElement message;
    

    public void searchAdventure(String adventureName) throws InterruptedException
    {
        // wait.until(ExpectedConditions.visibilityOf(searchAdv));
        Thread.sleep(3000);
        Actions action=new Actions(driver);
        action.moveToElement(this.searchAdv).click().sendKeys(this.searchAdv,adventureName).build().perform();
    }

    public void clickonAdventure() throws InterruptedException
    {
        Thread.sleep(3000);
        Actions action=new Actions(driver);
        action.moveToElement(this.imgClick).click().build().perform();
    }

    public void enterName(String nameText)
    {
        wait.until(ExpectedConditions.visibilityOf(nameField));
        Actions action=new Actions(driver);
        action.moveToElement(this.nameField).click().sendKeys(this.nameField,nameText).build().perform();
    }

    public void enterDate(String datetext) throws InterruptedException
    {
        Thread.sleep(3000);
        Actions action=new Actions(driver);
        action.moveToElement(this.datefield).click().sendKeys(this.datefield,datetext).build().perform();
    }

    public void entercount(String counttext)
    {
        wait.until(ExpectedConditions.visibilityOf(countfield));
        Actions action=new Actions(driver);
        action.moveToElement(this.countfield).click().sendKeys(this.countfield,counttext).build().perform();
    }

    public void submitButton()
    {
        Actions action=new Actions(driver);
        action.moveToElement(this.subBtn).click().build().perform();
    }

    public boolean greetingMessage()
    {
        wait.until(ExpectedConditions.visibilityOf(message));
        String text = message.getText();
        if(text.contains("adventure is successful"))
        {
            return true;
        }
        return false;
    }

}