package shahtest_web.WebTest.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // adjust locator for logout or user menu
    By userMenuButton = By.id("dropdownMenuButton1");
    By logoutButton = By.id("btn-logout");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public boolean isLoggedIn() {
        try {      	
            wait.until(ExpectedConditions.visibilityOfElementLocated(userMenuButton));
            return true;
        } catch (TimeoutException e) {
            return false;
        } catch (Exception e) {
            System.out.println("⚠️ Error checking login status: " + e.getMessage());
            return false;
        }
    }

    public void logout() {
        try {
            driver.findElement(userMenuButton).click();
            Thread.sleep(500);
            driver.findElement(logoutButton).click();
            System.out.println("✅ Successfully logged out");
        } catch (Exception e) {
            System.out.println("⚠️ Logout failed or already logged out: " + e.getMessage());
        }
    }
}
