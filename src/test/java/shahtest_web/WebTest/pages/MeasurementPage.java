package shahtest_web.WebTest.pages;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class MeasurementPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public MeasurementPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openMeasurements() {
        WebElement measurement = wait.until(
            ExpectedConditions.elementToBeClickable(By.id("sb-menu-measurements"))
        );
        measurement.click();
    }

    public void openRetroSignGRX() {
        WebElement submenuItem = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href, '/measurements/retro-sign-grx')]")
            )
        );
        submenuItem.click();

        WebElement textCell = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//td[normalize-space(text()) != '']")
            )
        );
        textCell.click();
    }

    public void clickBackButton() {
        WebElement backClick = wait.until(
            ExpectedConditions.elementToBeClickable(By.id("btn-back-measurements"))
        );
        backClick.click();
    }
}

