package Tests;

import Libraries.Browser;
import Methods.SixFlagsMagicMountain;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SixFlagsTest extends Browser {
    SixFlagsMagicMountain sf = new SixFlagsMagicMountain();
    @BeforeMethod
    public void getBrowser ()
    {
        browsertype();
    }

    @Test
    public void runner ()
    {
        sf.Runner();

    }

}
