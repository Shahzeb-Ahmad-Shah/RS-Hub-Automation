package shahtest_web.WebTest.pages;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

public class UserPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public UserPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickOutside() {
        Actions actions = new Actions(driver);
        actions.moveByOffset(5, 5).click().perform();

        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector(".cdk-overlay-backdrop.cdk-overlay-backdrop-showing")
            ));
        } catch (TimeoutException e) {}
    }

    public void openUsers() {
        WebElement ftUsers = wait.until(
            ExpectedConditions.elementToBeClickable(By.id("sb-menu-users"))
        );
        ftUsers.click();
    }

    public void openUserMenu() {
        WebElement menu = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//table//tr[1]//td[contains(@class, 'mat-column-actions')]//i[contains(@class, 'menu-dots')]")
            )
        );
        menu.click();
    }

    public void addNewUser() {
        WebElement addUser = wait.until(
            ExpectedConditions.elementToBeClickable(By.id("btn-add-new-user"))
        );
        addUser.click();
    }

    public void fillUserForm() throws InterruptedException {
        WebElement firstNameInput = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[formcontrolname='firstName']")
            )
        );
        firstNameInput.clear();
        firstNameInput.sendKeys("Test");

        WebElement lastNameInput = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[formcontrolname='lastName']")
            )
        );
        lastNameInput.clear();
        lastNameInput.sendKeys("Automation");

        WebElement phoneNumberInput = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[formcontrolname='contactNo']")
            )
        );
        phoneNumberInput.clear();
        phoneNumberInput.sendKeys("03001234567");

        WebElement emailInput = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[formcontrolname='email']")
            )
        );
        emailInput.clear();
        emailInput.sendKeys("testuser@example.com");

        WebElement roleDropdown = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.cssSelector("mat-select[formcontrolname='roleId']")
            )
        );
        roleDropdown.click();

        WebElement roleOption = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//mat-option//span[contains(text(), 'Engineer')]")
            )
        );
        roleOption.click();

        WebElement sendButton = wait.until(
            ExpectedConditions.elementToBeClickable(By.id("btn-submit-user-form"))
        );
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", sendButton);
        Thread.sleep(1000);
        sendButton.click();

        Thread.sleep(2000);

        boolean userExists = false;
        try {
            WebElement snackbar = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".mat-mdc-snack-bar-label, .mat-simple-snackbar, .mdc-snackbar__label")
            ));
            String message = snackbar.getText().trim();
            System.out.println("📩 Snackbar message: " + message);
            if (message.toLowerCase().contains("already exist")) {
                userExists = true;
            }
        } catch (TimeoutException e) {}

        if (userExists) {
            try {
                WebElement cancelButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.id("btn-cancel-user-form")
                ));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cancelButton);
                Thread.sleep(1000);
                cancelButton.click();
            } catch (Exception ex) {}
        }
    }
}

