package qtriptest.pages;

import java.util.List;
import java.util.concurrent.TimeoutException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdventurePage 
{
    static RemoteWebDriver driver;

    public AdventurePage(RemoteWebDriver driver)
    {
        this.driver=driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }
    
    @FindBy(xpath = "//select[@name='duration']")
    private  WebElement duration;
    @FindBy(xpath = "(//div[@class='ms-3'])[1]")
    private WebElement clearbtnDuration;
    @FindBy(xpath = "//select[@id='category-select']")
    private WebElement category;
    @FindBy(xpath = "(//div[@class='ms-3'])[2]")
    private WebElement clearbtnCategory;
    @FindBy(xpath = "//div[@class='activity-card']")
    private List<WebElement> allAdventureList;
    @FindBy(xpath = "//div[@class='category-filter']/div")
    private WebElement textCategory;
    // @FindBy(id = "search-adventures")
    // WebElement searchAdventure;

   
    public void selectDuration(String hours) throws InterruptedException
    {
        Thread.sleep(3000);
        Actions action=new Actions(driver);
        action.moveToElement(duration).click().perform();
        Select select=new Select(duration);
        select.selectByVisibleText(hours);

    }

    public String getTexthours()
    {
        Select select=new Select(duration);
        WebElement texthours = select.getFirstSelectedOption();
        return texthours.getText();
    }

    public  void selectCategory(String cat) throws InterruptedException
    {
        Actions action=new Actions(driver);
        action.moveToElement(category).click().perform();
        category.click();
        Thread.sleep(5000);
        Select select=new Select(category);
        select.selectByVisibleText(cat);
    }

    public String getCategory() 
    {
        return textCategory.getText();
    }

    public int getFilterResultCount() throws InterruptedException
    {
        Thread.sleep(2000);
        return allAdventureList.size();
    }

    public void clearDurationfilter() throws InterruptedException
    {
        Thread.sleep(3000);
        Actions action=new Actions(driver);
        action.moveToElement(clearbtnDuration).click().build().perform();
    }

    public  void clearCategoryfilter() throws InterruptedException
    {
        Thread.sleep(3000);
        Actions action=new Actions(driver);
        action.moveToElement(clearbtnCategory).click().build().perform();
    }

    public int getAllResultCount() throws InterruptedException
    {
        Thread.sleep(5000);
        return allAdventureList.size();
    }
}