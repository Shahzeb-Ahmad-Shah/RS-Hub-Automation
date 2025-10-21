package shahtest_web.WebTest.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void login(String email, String password) {
        driver.findElement(By.id("btn-login")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("i0116"))).sendKeys(email);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("i0118"))).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9"))).click();

        try {
            WebElement kmsi = wait.until(ExpectedConditions.elementToBeClickable(By.id("KmsiCheckboxField")));
            if (!kmsi.isSelected()) kmsi.click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9"))).click();
            System.out.println("✅ Login successful");
        } catch (Exception e) {
            System.out.println("⚠️ No 'Stay signed in?' prompt");
        }
    }
}

