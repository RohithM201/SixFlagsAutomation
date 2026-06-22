package Libraries;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class CommonFunctions {
    public WebDriver driver;
    public WebDriverWait wait;




    public void browserDriver (WebDriver driver)
    {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));



    }

    public CommonFunctions ()
    {
        super();


    }

    public void clickWait(WebElement a)
    {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(a));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        try {
            element.click();
        } catch (ElementNotInteractableException e) {
            js.executeScript("arguments[0].click();", element);
        }
    }

    public void clickWait(By locator)
    {
        for (int attempt = 0; attempt < 3; attempt++) {
            try {
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
                try {
                    element.click();
                } catch (ElementNotInteractableException e) {
                    js.executeScript("arguments[0].click();", element);
                }
                return;
            } catch (StaleElementReferenceException e) {
                if (attempt == 2) {
                    throw e;
                }
            }
        }
    }

    public void clickIfPresent(By locator)
    {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        } catch (TimeoutException ignored) {
        }
    }

    public void switchToNewestWindow()
    {
        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
        }
    }

    public void switchToDefaultContent()
    {
        driver.switchTo().defaultContent();
    }

    public void switchToFrameContaining(By locator)
    {
        wait.until(currentDriver -> {
            driver.switchTo().defaultContent();

            if (!driver.findElements(locator).isEmpty()) {
                return true;
            }

            for (WebElement frame : driver.findElements(By.tagName("iframe"))) {
                driver.switchTo().defaultContent();
                driver.switchTo().frame(frame);

                if (!driver.findElements(locator).isEmpty()) {
                    return true;
                }
            }

            driver.switchTo().defaultContent();
            return false;
        });
    }

    public void scroll (WebElement a) {
        wait.until(ExpectedConditions.visibilityOf(a));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", a);
    }

     public void ticketValidation(WebElement a, int b)
     {
         wait.until(ExpectedConditions.attributeToBe(a, "value", String.valueOf(b)));
         String t = a.getAttribute("value");
         int actualTicketAmount = Integer.parseInt(t);

         Assert.assertEquals(actualTicketAmount, b);





}

     public void ticketValidation(By a, int b)
     {
         WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(a));
         wait.until(ExpectedConditions.attributeToBe(element, "value", String.valueOf(b)));
         String t = element.getAttribute("value");
         int actualTicketAmount = Integer.parseInt(t);

         Assert.assertEquals(actualTicketAmount, b);





}}
