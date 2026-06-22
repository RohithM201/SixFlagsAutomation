package Libraries;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

public class Browser extends CommonFunctions implements UI {



    public void browsertype ()
    {
        switch (browser)
        {
            case "chrome":
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless=new");
                driver = new ChromeDriver();
                break;

            case "firefox":
                driver = new FirefoxDriver();
                break;

            case "edge":
                driver = new EdgeDriver();
                break;

            case "safari":
                driver = new SafariDriver();
                break;

            default: System.out.println("Browser invalid, Expected Browser Chrome");
            break;


        }

        driver.get(link);
        System.out.println("Loaded page: " + driver.getTitle() + " - " + driver.getCurrentUrl());


    }

}
