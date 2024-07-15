
package qtriptest.pages;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HistoryPage 
{
    RemoteWebDriver driver;
    WebDriverWait wait;

    public HistoryPage(RemoteWebDriver driver)
    {
        this.driver=driver;
        wait=new WebDriverWait(driver, 10);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//a[text()='Reservations']")
    private WebElement reservation;
    @FindBy(xpath = "//tbody/tr/th")
    private List<WebElement> transactionId;
    // @FindBy(xpath = "//tbody/tr/th")
    // private WebElement transcationID;
    @FindBy(xpath = "//button[@class='cancel-button']")
    private WebElement cancelBtn;
    @FindBy(xpath = "//div[@id='no-reservation-banner']")
    private WebElement message;
    @FindBy(xpath = "//tbody/tr/th")
    private List<WebElement> listTranscationID;


    public void reservationPage()
    {
        wait.until(ExpectedConditions.visibilityOf(reservation));
        Actions action=new Actions(driver);
        action.moveToElement(this.reservation).click().build().perform();
    }


    public List<String> getAllTransactionIDs() {
        wait.until(ExpectedConditions.visibilityOfAllElements(transactionId));
        return transactionId.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public boolean isTransactionIDPresent(String transactionID) {
        for (WebElement element : transactionId) {
            if (element.getText().equals(transactionID)) {
                return true;
            }
        }
        return false;
    }

    public void cancelReservationForTransactionID(String transactionID) {
        wait.until(ExpectedConditions.visibilityOfAllElements(transactionId));
        for (WebElement element : transactionId) {
            if (element.getText().equals(transactionID)) {
                Actions action = new Actions(driver);
                action.moveToElement(element).click(cancelBtn).perform();
                break;
            }
        }
    }

    public boolean reservationCancell() throws InterruptedException
    {
        Thread.sleep(3000);
        String text = message.getText();
        if(text.contains("Oops! You have not made any reservations yet!"))
        {
            return true;
        }
        return false;
    }

    public void cancelButton() throws InterruptedException
    {
        Thread.sleep(3000);
        Actions action =new Actions(driver);
        action.moveToElement(this.cancelBtn).click().build().perform();
    }

    public boolean listTransaction()
    {
        int count = listTranscationID.size();
        if(count == 9)
        {
            return true;
        }
        return false;
    }

}