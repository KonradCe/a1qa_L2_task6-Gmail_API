package base;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.testng.annotations.BeforeSuite;

public class EuronewsSubscriptionBase {

    protected static Logger logger = null;
    protected static Browser browser = AqualityServices.getBrowser();
    protected static ISettingsFile urls = new JsonSettingsFile("Urls.json");
    protected static ISettingsFile testData = new JsonSettingsFile("TestData.json");

    @BeforeSuite
    protected static void suiteSetUp() {
        Configurator.setRootLevel(Level.DEBUG);
        logger = Logger.getInstance();
        browser.maximize();
    }
}
