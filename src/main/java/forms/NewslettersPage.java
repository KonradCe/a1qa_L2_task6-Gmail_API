package forms;

import aquality.selenium.core.logging.Logger;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;
import utils.RandomUtils;

import java.util.List;

public class NewslettersPage extends Form {

    private final Logger logger = Logger.getInstance();

    private ITextBox emailForm = getElementFactory().getTextBox(
            By.xpath("//section[contains(@class, 'sticky ')]"), "subscription form at the bottom of the page");
    private ITextBox emailInputBox = getElementFactory().getTextBox(
            By.xpath("//input[@class='w-full']"), "email input box");
    private IButton submitButton = getElementFactory().getButton(
            By.xpath("//input[@type='submit' and not(@disabled)]"), "submit to newsletter button");

    public NewslettersPage() {
        super(By.xpath("//span[contains(@class, 'h1') and contains(., 'newsletters')]"), "newsletters page");
    }

    public void selectRandomNewsletter() {
        List<ITextBox> newsletterBoxList = getElementFactory().findElements(By.xpath("//div[contains(@class, 'bg-white')]"), ITextBox.class);
        ITextBox randomNewsletter = newsletterBoxList.get(RandomUtils.getRandomIntInRange(newsletterBoxList.size()));
        logger.info("Subscribing to: " + randomNewsletter.findChildElement(By.xpath("//h2"), ITextBox.class).getText());
        randomNewsletter.findChildElement(
                By.xpath("//label[contains(@class, 'btn-tertiary') and not(contains(@class, 'hidden'))]"),
                "select newsletter button",
                IButton.class).click();
    }

    public boolean isEmailSubscriptionFormDisplayed() {
        return emailForm.state().isDisplayed();
    }

    public void enterEmailIntoSubscriptionForm(String email) {
        emailInputBox.type(email);
    }

    public void clickSubmitButton() {
        submitButton.click();
    }
}
