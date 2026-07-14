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
    By addtoCart = By.xpath("//*[normalize-space()='Add to cart' and @role='button']");
    By noThanks = By.xpath("//*[contains(@class, 'upsell__cancel-button') or normalize-space()='No Thanks' or normalize-space()='No thanks' or normalize-space()='no thanks']");
    By fastlanePassAmount = By.xpath("//*[@id='gap-14']");
    By fastLaneUltimate = By.xpath("//button[normalize-space()='Increase Fast Lane Ultimate quantity' or contains(translate(@gap-method,'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'increase')]");
    By fastLaneReserve = By.xpath("//*[@id='Fast Lane Reserve']/gap-card/gap-card-content/div/div[3]/div[2]/div[2]/quantity/gap-quantity/button[2]");
    By fastLaneAdd = By.xpath("//gap-button[@data-cy='fLane_addBtn']");
    By singleMeal = By.xpath("//*[@id='application-view']/div/div/div/div[2]/package-display[1]/ng-include/div/gap-card/gap-card-content/div/div[2]/quantity/gap-quantity/button[2]");
    By allDayDining = By.xpath("//*[@id='application-view']/div/div/div/div[2]/package-display[2]/ng-include/div/gap-card/gap-card-content/div/div[2]/quantity/gap-quantity/button[2]");
    By photoPackage = By.xpath("//*[@id='application-view']/div/div/div/div[2]/package-display[3]/ng-include/div/gap-card/gap-card-content/div/div[2]/quantity/gap-quantity/button[2]");
    By addToCart2 = By.xpath("//*[contains(@id,'application-view')]/div/div/div/div[3]/gap-button");
    By checkBox1 = By.xpath("//*[@id='cart-view']/div[2]/div[2]/cart-summary/gap-card/gap-card-content[2]/div/div[2]/gap-checkbox/gap-iconography");
    By checkBox2 = By.xpath("//*[@id='cart-view']/div[2]/div[2]/cart-summary/gap-card/gap-card-content[2]/cart-optins/div[2]/gap-checkbox/gap-iconography");
    By cartItemPrices = By.xpath("//div[contains(@class,'cart-item__pricing-price')]");
    By checkoutTotal = By.xpath("(//*[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'total')]/following::*[contains(text(),'$')])[1]");
    By checkOutBtn = By.xpath("//*[@id='cart-view']//gap-button[contains(normalize-space(.), 'Checkout') or contains(normalize-space(.), 'Check Out')]");
    By insurance = By.xpath("//*[@id='tg-selection-input-placeholder-opt-in']/ancestor::*[self::label or @role='radio' or @role='button' or contains(@class,'tg-selection')][1]");
    By noInsurance = By.xpath("//*[@id='tg-selection-input-placeholder-opt-out']/ancestor::*[self::label or @role='radio' or @role='button' or contains(@class,'tg-selection')][1]");
    By continueBtn = By.xpath("//*[@id='ap-application']/div/sc-payment-scene/div/main/div/ap-page-section[3]/div/div/ap-nav-button/div/ap-button/gap-button");
    By continuteBtn2 = By.xpath("//*[@id='ap-application']/div/sc-payment-scene/div/main/div/ap-page-section[3]/div/div/ap-nav-button/div/ap-button/gap-button//div");








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
        maxAmount(ticketSelector);
//        switchToDefaultContent();
        ticketValidation(ticketamount,14);
        clickWait(addtoCart);
        clickIfPresentInAnyFrame(noThanks);
        clickIfPresentInAnyFrame(fastLaneUltimate);
        maxAmount(fastLaneUltimate);
        ticketValidation(fastlanePassAmount, 14);
        maxAmount(fastLaneReserve);
        clickWait(fastLaneAdd);

        //Meals
        switchToFrameContaining(singleMeal);
       maxAmount(singleMeal);
       maxAmount(allDayDining);
       maxAmount(photoPackage);
       clickWait(addToCart2);
       clickWait(checkBox1);
       clickWait(checkBox2);
       clickWait(checkOutBtn);
       switchToFrameContaining(continuteBtn2);
        clickWait(continuteBtn2);

//       clickWait(insurance)
//       clickWait(noInsurance);
//       clickWait(insurance);
//       clickWait(continueBtn);







    }

}
