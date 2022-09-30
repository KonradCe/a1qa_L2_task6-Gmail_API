package forms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class MainPage extends Form {
    private final IButton newsletterButton = getElementFactory().getButton(
            By.xpath("//span[@data-event='newsletter-link-header']"), "newsletter top button");
    private final IButton acceptCookiesButton = getElementFactory().getButton(
            By.xpath("//button[@id='didomi-notice-agree-button']"), "accept cookies button");

    public MainPage() {
        super(By.xpath("//div[@class='b-topstories-home__main']"), "euronews home page");
    }

    public void acceptCookies() {
        acceptCookiesButton.click();
    }

    public void goToNewslettersPage() {
        newsletterButton.click();
    }
}
