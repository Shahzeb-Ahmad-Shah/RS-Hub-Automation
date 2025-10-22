package shahtest_web.WebTest.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;

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
    
    public void verifyLoginFailed() {
        try {
            // Include #passwordError in the wait
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#passwordError, .login-error, .error-message, .mat-mdc-snack-bar-label, div[role='alert']"))
            );

            String errorText = errorElement.getText().trim();
            System.out.println("🔎 Found error text: " + errorText);

            // Case-insensitive check
            String lower = errorText.toLowerCase();

            if (lower.contains("incorrect") ||
                lower.contains("wrong") ||
                lower.contains("invalid") ||
                lower.contains("please enter your password") ||
                lower.contains("account or password is incorrect")) {

                System.out.println("✅ Login failed as expected: " + errorText);
                Assert.assertTrue(true, "Login failed message displayed correctly.");

            } else {
                throw new AssertionError("⚠️ Unexpected login message: " + errorText);
            }

        } catch (TimeoutException e) {
            throw new AssertionError("❌ Expected login failure message, but none appeared within timeout.");
        } catch (NoSuchElementException e) {
            throw new AssertionError("❌ Login error element not found on page.");
        }
    }


}

