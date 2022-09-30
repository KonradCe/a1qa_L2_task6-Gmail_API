import base.EuronewsSubscriptionBase;
import forms.MainPage;
import forms.NewslettersPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EuronewsSubscriptionTest extends EuronewsSubscriptionBase {

    @Test
    public void euronewsSubscriptionTest() {
        logger.debug("STEP 1");
        browser.goTo(urls.getValue("/euronewsMainPage").toString());
        MainPage mainPage = new MainPage();
        Assert.assertTrue(mainPage.state().waitForDisplayed(), "home page was not displayed");

        logger.debug("STEP 2");
        mainPage.acceptCookies();
        mainPage.goToNewslettersPage();
        NewslettersPage newslettersPage = new NewslettersPage();
        Assert.assertTrue(newslettersPage.state().waitForDisplayed(), "newsletter page was not displayed");

        logger.debug("STEP 3");
        newslettersPage.selectRandomNewsletter();
        Assert.assertTrue(newslettersPage.isEmailSubscriptionFormDisplayed(),
                "email subscription form glued to the bottom of the page was not displayed");

        logger.debug("STEP 4");
        newslettersPage.enterEmailIntoSubscriptionForm(testData.getValue("/email").toString());
        newslettersPage.clickSubmitButton();
    }
}
