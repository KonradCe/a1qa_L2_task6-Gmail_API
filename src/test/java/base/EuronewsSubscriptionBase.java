package base;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import io.restassured.RestAssured;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import utils.GmailUtils;

import javax.naming.AuthenticationException;

public class EuronewsSubscriptionBase {

    protected static Logger logger = null;
    protected static Browser browser = AqualityServices.getBrowser();
    protected static ISettingsFile urls = new JsonSettingsFile("Urls.json");
    protected static ISettingsFile testData = new JsonSettingsFile("TestData.json");
    protected static ISettingsFile apiConfig = new JsonSettingsFile("GmailApiConfig.json");

    @BeforeSuite
    protected static void suiteSetUp() {
        Configurator.setRootLevel(Level.DEBUG);
        logger = Logger.getInstance();
        RestAssured.baseURI = apiConfig.getValue("/gmailApiBaseUri").toString();

        try {
            Integer emailsMarkedAsRead = GmailUtils.markAllEmailsAsRead();
            logger.info("Number of emails marked as 'read' before testing begins: " + emailsMarkedAsRead);
        } catch (AuthenticationException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

        browser.maximize();
    }

    @AfterMethod
    protected static void tearDown() {
        browser.quit();
    }
}
