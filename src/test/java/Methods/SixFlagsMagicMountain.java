package Methods;

import Libraries.CommonFunctions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SixFlagsMagicMountain extends CommonFunctions {

    public SixFlagsMagicMountain() {
        super();
        browserDriver(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//button[@id='e4357edc-2280-4eef-937c-272df6038c85']/div[2]/button[1]")
    WebElement saveBtn;
    @FindBy(xpath = "//button[contains(@aria-label,'Visit Six Flags Magic Mountain')]")
    WebElement magicMountain;



    public void Runner()
    {
        clickWait(saveBtn);
        scroll(magicMountain);
        clickWait(magicMountain);
    }

}

