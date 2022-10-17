package forms;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;
import utils.RandomUtils;
import utils.StringUtils;

import java.util.List;

public class NewslettersPage extends Form {

    private final Logger logger = Logger.getInstance();

    private ITextBox emailForm = getElementFactory().getTextBox(
            By.xpath("//section[contains(@class, 'sticky')]"), "subscription form at the bottom of the page");
    private ITextBox emailInputBox = getElementFactory().getTextBox(
            By.xpath("//input[@class='w-full']"), "email input box");
    private IButton submitButton = getElementFactory().getButton(
            By.xpath("//input[@type='submit' and not(@disabled)]"), "submit to newsletter button");
    private ITextBox openNewsletterPreview = getElementFactory().getTextBox(
            By.xpath("//div[contains(@id, '_previews') and contains(@style, 'opacity: 1') and not(contains(@style, 'display: none'))]"), "currently open newsletter preview");
    private List<ITextBox> newsletterBoxList = getElementFactory().findElements(
            By.xpath("//div[contains(@class, 'bg-white')]"), ITextBox.class);

    public NewslettersPage() {
        super(By.xpath("//span[contains(@class, 'h1') and contains(., 'newsletters')]"), "newsletters page");
    }

    public String selectRandomNewsletter() {
        ITextBox randomNewsletter = newsletterBoxList.get(RandomUtils.getRandomIntInRange(newsletterBoxList.size()));
        logger.info("Subscribing to: " + randomNewsletter.findChildElement(By.xpath("//h2"), ITextBox.class).getText());
        randomNewsletter.findChildElement(
                By.xpath("//label[contains(@class, 'btn-tertiary') and not(contains(@class, 'hidden'))]"),
                "select newsletter button",
                IButton.class).click();

        return randomNewsletter.findChildElement(By.xpath("//h2"), ITextBox.class).getText();
    }

    public boolean isEmailSubscriptionFormDisplayed() {
        return emailForm.state().waitForDisplayed();
    }

    public void enterEmailIntoSubscriptionForm(String email) {
        emailInputBox.type(email);
    }

    public void clickSubmitButton() {
        submitButton.click();
    }

    public void openPreviewOfNewsletter(String selectedNewsletter) {
        String xpath = "//div[contains(@class, 'bg-white')]//h2[contains(text(), '%s')]//following-sibling::a";
        IButton newsletterPreviewLink = getElementFactory().getButton(
                By.xpath(String.format(xpath, selectedNewsletter)), "newsletter preview link");

        newsletterPreviewLink.click();
    }

    public boolean previewForNewsletterIsOpen(String newsletterName) {
        if (!openNewsletterPreview.state().waitForDisplayed()) {
            return false;
        }
        return StringUtils.idContainsNewsletterName(newsletterName, openNewsletterPreview.getAttribute("id"));
    }

    public String getUnsubscribeUrlFromNewsletterPreview() {
        String parentId = openNewsletterPreview.getAttribute("id");
        String xpath = "//div[@id='%s']//iframe";
        IButton newsletterPreviewIframe = getElementFactory().getButton(By.xpath(String.format(xpath, parentId)), "iframe inside newsletter preview");

        AqualityServices.getBrowser().getDriver().switchTo().frame(newsletterPreviewIframe.getElement());
        ILink unsubscribeLink = getElementFactory().getLink(By.xpath("//a[contains(text(), 'unsubscribe')]"), "unsubscribe link");
        String unsubscribeUrl = unsubscribeLink.getHref();
        AqualityServices.getBrowser().getDriver().switchTo().defaultContent();

        return unsubscribeUrl;
    }
}
