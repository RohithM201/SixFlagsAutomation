package Methods;

import Libraries.CommonFunctions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SixFlagsMagicMountain extends CommonFunctions {

    public SixFlagsMagicMountain(WebDriver driver) {
        super();
        browserDriver(driver);
        PageFactory.initElements(driver,this);
    }

    By privacyDialogButton = By.xpath("//button[normalize-space()='Save' or contains(normalize-space(), 'Accept')]");

    @FindBy(xpath = "//*[self::a or self::button][.//*[normalize-space()='Six Flags Magic Mountain'] or contains(@aria-label,'Six Flags Magic Mountain')]")
    WebElement magicMountain;
    @FindBy(xpath = "//button[normalize-space()='Buy Now' and contains(translate(@data-accesso-keyword, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'snap')]")
    WebElement parkTicket;
    By ticketamount = By.xpath("//*[@aria-label='Increase']/ancestor::*[.//input[@slot='input']][1]//input[@slot='input']");
    By waterPark = By.xpath("//*[self::a or self::button][normalize-space()='Waterpark Tickets']");
    By parkTicketBuyNow = By.xpath("(//*[normalize-space()='Park Ticket']/following::*[self::a or self::button][normalize-space()='Buy Now'])[1]");
    By accessoParkTicketBuyNow = By.xpath("//*[@aria-label='Buy Now Park Ticket']");
    By nextAvailableDate = By.xpath("(//*[self::button or @role='button'][contains(@class, 'acso-cal__btn') and contains(@class, '--active') and not(contains(@class, '--selected'))])[1]");
    By ticketSelector = By.xpath("//*[@aria-label='Increase' and not(@disabled)]");






    public void Runner()
    {
        clickIfPresent(privacyDialogButton);
        scroll(magicMountain);
        clickWait(magicMountain);
        clickWait(parkTicket);
        clickWait(parkTicketBuyNow);
        switchToNewestWindow();

        switchToFrameContaining(accessoParkTicketBuyNow);
        clickWait(accessoParkTicketBuyNow);
        switchToNewestWindow();
        switchToFrameContaining(nextAvailableDate);
        clickWait(nextAvailableDate);
        switchToFrameContaining(ticketSelector);
        clickWait(ticketSelector);
//        switchToDefaultContent();
        ticketValidation(ticketamount,2);



    }

}
