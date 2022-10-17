import base.EuronewsSubscriptionBaseTest;
import forms.MainPage;
import forms.NewslettersPage;
import forms.SubscriptionConfirmedPage;
import forms.UnsubscribePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.GmailUtils;

import javax.naming.AuthenticationException;

public class EuronewsSubscriptionTest extends EuronewsSubscriptionBaseTest {

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
        String selectedNewsletter = newslettersPage.selectRandomNewsletter();
        Assert.assertTrue(newslettersPage.isEmailSubscriptionFormDisplayed(),
                "email subscription form glued to the bottom of the page was not displayed");

        logger.debug("STEP 4");
        newslettersPage.enterEmailIntoSubscriptionForm(testData.getValue("/email").toString());
        newslettersPage.clickSubmitButton();
        try {
            Assert.assertTrue(GmailUtils.isNewMailExists(), "no new email with a request to confirm subscription");
        } catch (InterruptedException e) {
            logger.error("waiting for new mail interrupted");
            throw new RuntimeException(e);
        }

        logger.debug("STEP 5");
        try {
            String urlToConfirmSubscription = GmailUtils.getUrlToConfirmSubscription();
            browser.goTo(urlToConfirmSubscription);
        } catch (AuthenticationException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        SubscriptionConfirmedPage subscriptionConfirmedPage = new SubscriptionConfirmedPage();
        Assert.assertTrue(subscriptionConfirmedPage.state().waitForDisplayed(), "page with subscription confirmation was not displayed");

        logger.info("Marking all euronews new emails as read so they will not be interpreted as new email in the final step");
        try {
            GmailUtils.markAllEmailsAsRead();
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }

        logger.debug("STEP 6");
        subscriptionConfirmedPage.clickOnBackToTheSiteButton();
        Assert.assertTrue(mainPage.state().waitForDisplayed(), "home page was not displayed");

        logger.debug("STEP 7");
        mainPage.goToNewslettersPage();
        newslettersPage.openPreviewOfNewsletter(selectedNewsletter);
        Assert.assertTrue(newslettersPage.previewForNewsletterIsOpen(selectedNewsletter), "a preview of selected newsletter is not open");

        logger.debug("STEP 8");
        browser.goTo(newslettersPage.getUnsubscribeUrlFromNewsletterPreview());
        UnsubscribePage unsubscribePage = new UnsubscribePage();
        Assert.assertTrue(unsubscribePage.state().waitForDisplayed(), "unsubscribe page was not displayed");

        logger.debug("STEP 9");
        unsubscribePage.enterEmailToUnsubscribe(testData.getValue("/email").toString());
        unsubscribePage.clickUnsubscribeButton();
        Assert.assertTrue(unsubscribePage.isUnsubscribeMessageExists(), "unsubscribe message has not appeared");

        logger.debug("STEP 10");
        try {
            Assert.assertFalse(GmailUtils.isNewMailExists());
        } catch (InterruptedException e) {
            logger.error("waiting for new mail interrupted");
            throw new RuntimeException(e);
        }
    }
}
