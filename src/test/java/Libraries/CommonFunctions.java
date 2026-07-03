package Libraries;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

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

    public void clickWaitAndSwitchToFrame(By clickLocator, By nextLocator)
    {
        printCheckoutState("before checkout click", clickLocator);
        switchToFrameContaining(clickLocator);
        clickWait(clickLocator);
        switchToNewestWindow();
        printCheckoutState("after normal checkout click", clickLocator);

        try {
            switchToFrameContaining(nextLocator);
        } catch (TimeoutException e) {
            printInsuranceDebug();
            switchToFrameContaining(clickLocator);
            clickWithJavaScript(clickLocator);
            switchToNewestWindow();
            printCheckoutState("after javascript checkout click", clickLocator);
            try {
                switchToFrameContaining(nextLocator);
            } catch (TimeoutException finalTimeout) {
                printInsuranceDebug();
                throw finalTimeout;
            }
        }
    }

    public void clickWithJavaScript(By locator)
    {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        js.executeScript("""
                const element = arguments[0];
                const clickable = element.shadowRoot
                    ? element.shadowRoot.querySelector('button, [role="button"], a')
                    : null;
                (clickable || element).click();
                """, element);
    }

    public void clickIfPresent(By locator)
    {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        } catch (TimeoutException ignored) {
        }
    }

    public void clickIfPresentInAnyFrame(By locator)
    {
        try {
            wait.until(currentDriver -> {
                driver.switchTo().defaultContent();

                if (clickFirstMatch(locator)) {
                    return true;
                }

                for (WebElement frame : driver.findElements(By.tagName("iframe"))) {
                    driver.switchTo().defaultContent();
                    driver.switchTo().frame(frame);

                    if (clickFirstMatch(locator)) {
                        return true;
                    }
                }

                driver.switchTo().defaultContent();
                return false;
            });
        } catch (TimeoutException ignored) {
            driver.switchTo().defaultContent();
        }
    }

    private boolean clickFirstMatch(By locator)
    {
        for (WebElement element : driver.findElements(locator)) {
            if (element.isDisplayed() && element.isEnabled()) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
                try {
                    element.click();
                } catch (ElementNotInteractableException e) {
                    js.executeScript("arguments[0].click();", element);
                }
                return true;
            }
        }

        return false;
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

    public void switchToFrame(By locator)
    {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
    }

    public void switchToFrameContaining(By locator)
    {
        wait.until(currentDriver -> {
            driver.switchTo().defaultContent();

            boolean found = switchToFrameContainingRecursive(locator);
            if (!found) {
                driver.switchTo().defaultContent();
            }

            return found;
        });
    }

    private boolean switchToFrameContainingRecursive(By locator)
    {
        if (!driver.findElements(locator).isEmpty()) {
            return true;
        }

        int frameCount = driver.findElements(By.tagName("iframe")).size();

        for (int i = 0; i < frameCount; i++) {
            try {
                driver.switchTo().frame(i);

                if (switchToFrameContainingRecursive(locator)) {
                    return true;
                }

                driver.switchTo().parentFrame();
            } catch (NoSuchFrameException | StaleElementReferenceException ignored) {
                driver.switchTo().defaultContent();
                return false;
            }
        }

        return false;
    }

    public void printInsuranceDebug()
    {
        driver.switchTo().defaultContent();
        System.out.println("Insurance debug URL: " + driver.getCurrentUrl());
        printInsuranceCandidates("default content");

        int frameIndex = 0;
        for (WebElement frame : driver.findElements(By.tagName("iframe"))) {
            try {
                driver.switchTo().defaultContent();
                String frameId = frame.getAttribute("id");
                String frameName = frame.getAttribute("name");
                String frameSrc = frame.getAttribute("src");
                driver.switchTo().frame(frame);
                System.out.println("Insurance debug iframe " + frameIndex
                        + " id=[" + frameId + "]"
                        + " name=[" + frameName + "]"
                        + " src=[" + frameSrc + "]"
                        + " text=[" + textSnippet(driver.findElement(By.tagName("body")).getText()) + "]");
                printInsuranceCandidates("iframe " + frameIndex);
            } catch (NoSuchElementException | StaleElementReferenceException ignored) {
                System.out.println("Insurance debug iframe " + frameIndex + " became stale while printing");
            }
            frameIndex++;
        }

        driver.switchTo().defaultContent();
    }

    private String textSnippet(String text)
    {
        if (text == null) {
            return "";
        }

        String normalized = text.replaceAll("\\s+", " ").trim();

        if (normalized.length() <= 1000) {
            return normalized;
        }

        return normalized.substring(0, 1000) + "...";
    }

    public void printCheckoutState(String label, By checkoutLocator)
    {
        driver.switchTo().defaultContent();
        System.out.println("Checkout debug " + label + " URL: " + driver.getCurrentUrl());
        System.out.println("Checkout debug " + label + " title: " + driver.getTitle());
        System.out.println("Checkout debug " + label + " windows: " + driver.getWindowHandles().size());
        System.out.println("Checkout debug " + label + " default iframe count: " + driver.findElements(By.tagName("iframe")).size());
        printLocatorDebug("checkout in default content", checkoutLocator);

        int frameIndex = 0;
        for (WebElement frame : driver.findElements(By.tagName("iframe"))) {
            try {
                driver.switchTo().defaultContent();
                String frameId = frame.getAttribute("id");
                String frameName = frame.getAttribute("name");
                String frameSrc = frame.getAttribute("src");
                driver.switchTo().frame(frame);
                System.out.println("Checkout debug " + label
                        + " iframe " + frameIndex
                        + " id=[" + frameId + "]"
                        + " name=[" + frameName + "]"
                        + " src=[" + frameSrc + "]");
                printLocatorDebug("checkout in iframe " + frameIndex, checkoutLocator);
            } catch (NoSuchElementException | StaleElementReferenceException ignored) {
                System.out.println("Checkout debug " + label + " iframe " + frameIndex + " became stale while printing");
            }
            frameIndex++;
        }

        driver.switchTo().defaultContent();
    }

    public void printLocatorDebug(String name, By locator)
    {
        System.out.println("Debug " + name + " count: " + driver.findElements(locator).size());

        for (WebElement element : driver.findElements(locator)) {
            System.out.println("Debug " + name
                    + " tag=[" + element.getTagName() + "]"
                    + " displayed=[" + element.isDisplayed() + "]"
                    + " enabled=[" + element.isEnabled() + "]"
                    + " id=[" + element.getAttribute("id") + "]"
                    + " class=[" + element.getAttribute("class") + "]"
                    + " disabled=[" + element.getAttribute("disabled") + "]"
                    + " aria-disabled=[" + element.getAttribute("aria-disabled") + "]"
                    + " checked=[" + element.getAttribute("checked") + "]"
                    + " aria-checked=[" + element.getAttribute("aria-checked") + "]"
                    + " text=[" + element.getText() + "]");
        }
    }

    private void printInsuranceCandidates(String context)
    {
        By candidates = By.xpath("//*[contains(translate(@id,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'insur') or contains(translate(@id,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'opt-') or contains(translate(@class,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'insur') or contains(translate(@class,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'protect') or contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'insurance') or contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'protection') or contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'protect')]");

        for (WebElement element : driver.findElements(candidates)) {
            String text = element.getText();
            String id = element.getAttribute("id");
            String className = element.getAttribute("class");
            String tagName = element.getTagName();

            if ((text != null && !text.isBlank()) || (id != null && !id.isBlank())) {
                System.out.println(context + " tag=[" + tagName + "] id=[" + id + "] class=[" + className + "] text=[" + text + "]");
            }
        }
    }

    public void scroll (WebElement a) {
        wait.until(ExpectedConditions.visibilityOf(a));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", a);
    }



     public void ticketValidation(By a, int b) {
         WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(a));
         wait.until(ExpectedConditions.attributeToBe(element, "value", String.valueOf(b)));
         String t = element.getAttribute("value");
         int actualTicketAmount = Integer.parseInt(t);

         Assert.assertEquals(actualTicketAmount, b);
     }

     public BigDecimal calculateCartItemsTotal(By cartItemPrices)
     {
         wait.until(ExpectedConditions.visibilityOfElementLocated(cartItemPrices));
         List<WebElement> prices = driver.findElements(cartItemPrices);
         BigDecimal total = BigDecimal.ZERO;

         for (WebElement price : prices) {
             if (price.isDisplayed()) {
                 total = total.add(parseCurrency(price.getText()));
             }
         }

         return total;
     }

     public void validateCartTotal(By cartItemPrices, By displayedTotal)
     {
         BigDecimal calculatedTotal = calculateCartItemsTotal(cartItemPrices);
         WebElement totalElement = wait.until(ExpectedConditions.visibilityOfElementLocated(displayedTotal));
         BigDecimal actualTotal = parseCurrency(totalElement.getText());

         Assert.assertEquals(actualTotal, calculatedTotal);
     }

     private BigDecimal parseCurrency(String text)
     {
         String amount = text.replaceAll("[^0-9.]", "");

         if (amount.isBlank()) {
             return BigDecimal.ZERO;
         }

         return new BigDecimal(amount);
     }

     public int maxAmount(By increaseButton)
     {
         int clicks = 0;

         while (clicks < 20) {
             WebElement button = firstClickableElement(increaseButton);

             if (button == null || isDisabled(button)) {
                 return clicks;
             }

             JavascriptExecutor js = (JavascriptExecutor) driver;
             js.executeScript("arguments[0].scrollIntoView({block: 'center'});", button);

             try {
                 button.click();
             } catch (ElementNotInteractableException e) {
                 js.executeScript("arguments[0].click();", button);
             }

             clicks++;
         }

         return clicks;
     }

     public void maxAmountForEach(By increaseButtons)
     {
         while (true) {
             WebElement button = firstClickableElement(increaseButtons);

             if (button == null || isDisabled(button)) {
                 return;
             }

             maxAmount(increaseButtons);
         }
     }

     private WebElement firstClickableElement(By locator)
     {
         for (WebElement element : driver.findElements(locator)) {
             if (element.isDisplayed() && element.isEnabled()) {
                 return element;
             }
         }

         return null;
     }

     private boolean isDisabled(WebElement element)
     {
         String disabled = element.getAttribute("disabled");
         String ariaDisabled = element.getAttribute("aria-disabled");
         String className = element.getAttribute("class");

         return disabled != null
                 || "true".equalsIgnoreCase(ariaDisabled)
                 || (className != null && className.toLowerCase().contains("disabled"));
     }
}
