package Libraries;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CommonFunctions {
    public WebDriver driver;
    public WebDriverWait wait;




    public void browserDriver (WebDriver driver)
    {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));


    }

    public CommonFunctions ()
    {
        super();


    }

    public void clickWait(WebElement a)
    {
        wait.until(ExpectedConditions.visibilityOf(a)).click();
    }

    public void scroll (WebElement a)
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", a);




}}
