package forms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class UnsubscribePage extends Form {
    private ITextBox emailTextBox = getElementFactory().getTextBox(
            By.xpath("//input[@type='email']"), "unsubscription email text box");
    private IButton confirmButton = getElementFactory().getButton(
            By.xpath("//button"), "confirm unsubscription button");
    private ILink unsubscribeMessage = getElementFactory().getLink(
            By.xpath("//strong[contains(text(), 'unsubscribe')]"), "unsubscribe message");

    public UnsubscribePage() {
        super(By.xpath("//img[@class='unsubscribe-logo']"), "newsletter unsubscription page");
    }

    public void enterEmailToUnsubscribe(String email) {
        emailTextBox.clearAndType(email);
    }

    public void clickUnsubscribeButton() {
        confirmButton.click();
    }

    public boolean isUnsubscribeMessageExists() {
        return unsubscribeMessage.state().waitForDisplayed();
    }
}
