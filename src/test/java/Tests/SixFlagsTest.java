package Tests;

import Libraries.Browser;
import Methods.SixFlagsMagicMountain;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class SixFlagsTest extends Browser {
    SixFlagsMagicMountain sf;


    @BeforeMethod

    public void getBrowser() {
        browsertype();
        sf = new SixFlagsMagicMountain(driver);
    }

    @Test
    public void runner() {
        sf.Runner();

    }

//    @AfterMethod
//    public void closeBrowser()
//    {
//        if (driver != null) {
//            driver.quit();
//        }
//    }

}