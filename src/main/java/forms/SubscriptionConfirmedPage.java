package forms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class SubscriptionConfirmedPage extends Form {
    private IButton backToTheSiteButton = getElementFactory().getButton(
            By.xpath("//a[contains(@aria-label, 'Back to the site')]"), "back to the site button");

    public SubscriptionConfirmedPage() {
        super(By.xpath("//img[@class='enw-block-confirmation__icon']"), "successful subscription confirmation page");
    }

    public void clickOnBackToTheSiteButton() {
        backToTheSiteButton.click();
    }
}
