package base;

import aquality.selenium.core.logging.Logger;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.testng.annotations.BeforeSuite;
import utils.RequestUtils;

public class EuronewsSubscriptionBase {

    protected static Logger logger = null;

    @BeforeSuite
    protected static void suiteSetUp() {
        Configurator.setRootLevel(Level.DEBUG);
        logger = Logger.getInstance();

        RequestUtils.getAuthorizationUrl();
    }
}
