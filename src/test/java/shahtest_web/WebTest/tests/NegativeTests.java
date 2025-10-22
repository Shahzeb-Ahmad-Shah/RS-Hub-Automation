package shahtest_web.WebTest.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import shahtest_web.WebTest.base.BaseTest;
import shahtest_web.WebTest.pages.*;


import java.time.Duration;

public class NegativeTests extends BaseTest {

    private final String validEmail = "ft.engineer_test@teo-intl.com";
    private final String validPassword = "Lyngby2024";

    @SuppressWarnings("unused")
	private WebDriverWait shortWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    private WebDriverWait getWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(10));
    } 

    private void ensureLoggedIn() throws InterruptedException {
        HomePage home = new HomePage(driver);
        if (!home.isLoggedIn()) {
            LoginPage loginPage = new LoginPage(driver);
            loginPage.login(validEmail, validPassword);
            Thread.sleep(3000);
        }
    }

    private void logoutIfLoggedIn() throws InterruptedException {
        HomePage home = new HomePage(driver);
        if (home.isLoggedIn()) {
            home.logout();
            Thread.sleep(3000);
        }
    }

    @Test(priority = 1)
    public void setupLoginForNegativeTests() throws InterruptedException {
        ensureLoggedIn();
    }

    @Test(priority = 2, dependsOnMethods = "setupLoginForNegativeTests")
    public void addInstrumentWithEmptySerialShowsValidationError() {
        InstrumentPage instrumentPage = new InstrumentPage(driver);
        instrumentPage.openInstrumentMenu();
        instrumentPage.openLTL3500();

        WebDriverWait w = getWait();
        WebElement addButton = w.until(ExpectedConditions.elementToBeClickable(By.id("btn-add-new-instrument")));
        addButton.click();

        // Leave serial empty, try to save
        WebElement saveButton = w.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-submit-instrument-form")));
        
        boolean disabled = !saveButton.isEnabled();
        System.out.println("Save button enabled state: " + saveButton.isEnabled());

        // Expect validation error for required field
        boolean errorShown = false;
        try {
            WebElement error = w.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".mat-mdc-form-field-error, .mat-error, div[role='alert']")
            ));
            errorShown = error.isDisplayed();
        } catch (TimeoutException e) {}
        
        WebElement cancelButton = w.until(
    	        ExpectedConditions.elementToBeClickable(By.id("btn-cancel-instrument-form"))
    	    );
    	    cancelButton.click();

        //Assert.assertTrue(errorShown || disabled, "Expected inline validation error or disabled save button for empty serial.");
        Assert.assertTrue(disabled, "Save button should be disabled when serial number is empty.");
    }

    @Test(priority = 3, dependsOnMethods = "setupLoginForNegativeTests")
    public void addInstrumentWithInvalidSerialShowsValidationError() {
        WebDriverWait w = getWait();
        WebElement addButton = w.until(ExpectedConditions.elementToBeClickable(By.id("btn-add-new-instrument")));
        addButton.click();

        WebElement serialField = w.until(ExpectedConditions.visibilityOfElementLocated(By.id("txt-serialNumber")));
        serialField.clear();
        serialField.sendKeys("INVALID@@@");

        WebElement saveButton = w.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-submit-instrument-form")));
        boolean disabled = !saveButton.isEnabled();
        WebElement cancelButton = w.until(
    	        ExpectedConditions.elementToBeClickable(By.id("btn-cancel-instrument-form"))
    	    );
    	    cancelButton.click();


        Assert.assertTrue(disabled, "Expected validation error for invalid serial.");
    }

    @Test(priority = 4, dependsOnMethods = "setupLoginForNegativeTests")
    public void addUserWithInvalidEmailShowsValidationError() throws InterruptedException {
        UserPage userPage = new UserPage(driver);
        userPage.clickOutside();
        userPage.openUsers();
        Thread.sleep(1000);
        userPage.addNewUser();

        WebDriverWait w = getWait();

        WebElement firstNameInput = w.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("input[formcontrolname='firstName']")
        ));
        firstNameInput.clear();
        firstNameInput.sendKeys("Test");

        WebElement lastNameInput = driver.findElement(By.cssSelector("input[formcontrolname='lastName']"));
        lastNameInput.clear();
        lastNameInput.sendKeys("Negative");

        WebElement phoneInput = driver.findElement(By.cssSelector("input[formcontrolname='contactNo']"));
        phoneInput.clear();
        phoneInput.sendKeys("03001234567");

        WebElement emailInput = driver.findElement(By.cssSelector("input[formcontrolname='email']"));
        emailInput.clear();
        emailInput.sendKeys("invalid-email");

        WebElement roleDropdown = w.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("mat-select[formcontrolname='roleId']")
        ));
        roleDropdown.click();
        WebElement roleOption = w.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//mat-option//span[contains(text(), 'Engineer')]")
        ));
        roleOption.click();

        WebElement sendButton = w.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-submit-user-form")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", sendButton);
        Thread.sleep(1000);
        //sendButton.click();
        
        boolean disabled = !sendButton.isEnabled();
        
        WebElement cancelButton = w.until(
    	        ExpectedConditions.elementToBeClickable(By.id("btn-cancel-user-form"))
    	    );
    	    cancelButton.click();

        Assert.assertTrue(disabled, "Expected validation error for invalid email.");
    }

    @Test(priority = 5, dependsOnMethods = "setupLoginForNegativeTests")
    public void addDistributorMissingNameShowsValidationError() throws InterruptedException {
        OrganizationPage organizationPage = new OrganizationPage(driver);
        organizationPage.openOrganizations();
        Thread.sleep(500);
        organizationPage.openDistributors();
        Thread.sleep(500);

        WebDriverWait w = getWait();
        WebElement addButton = w.until(ExpectedConditions.elementToBeClickable(By.id("btn-add-new-distributor")));
        addButton.click();

        // Leave name empty, fill other fields
        WebElement phoneInput = w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@formcontrolname='contactPhone']")));
        phoneInput.clear();
        phoneInput.sendKeys("03001234567");

        WebElement emailInput = driver.findElement(By.xpath("//input[@formcontrolname='salesEmail']"));
        emailInput.clear();
        emailInput.sendKeys("testdistributor@example.com");

        WebElement contactEmailInput = driver.findElement(By.xpath("//input[@formcontrolname='contactEmail']"));
        contactEmailInput.clear();
        contactEmailInput.sendKeys("contactperson@example.com");

        WebElement firstNameInput  = driver.findElement(By.xpath("//input[@formcontrolname='userFirstName']"));
        firstNameInput.clear();
        firstNameInput.sendKeys("Test");

        WebElement lastNameInput  = driver.findElement(By.xpath("//input[@formcontrolname='userLastName']"));
        lastNameInput.clear();
        lastNameInput.sendKeys("Automation");

        WebElement secondPhoneInput  = driver.findElement(By.xpath("//input[@formcontrolname='userPhone']"));
        secondPhoneInput.clear();
        secondPhoneInput.sendKeys("03121234567");

        WebElement secondEmailInput  = driver.findElement(By.xpath("//input[@formcontrolname='userEmail']"));
        secondEmailInput.clear();
        secondEmailInput.sendKeys("shahzeb@example.com");

        WebElement saveButton = w.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-submit-distributor-form")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", saveButton);
        Thread.sleep(500);
        //saveButton.click();
        boolean disabled = !saveButton.isEnabled();
        
        WebElement cancelButton = w.until(
    	        ExpectedConditions.elementToBeClickable(By.id("btn-cancel-distributor-form"))
    	    );
    	    cancelButton.click();

        Assert.assertTrue(disabled, "Expected validation error for missing distributor name.");
    }

    @Test(priority = 6, dependsOnMethods = "setupLoginForNegativeTests")
    public void addDistributorWithInvalidEmailsShowsValidationError() throws InterruptedException {
        OrganizationPage organizationPage = new OrganizationPage(driver);
        organizationPage.openOrganizations();
        Thread.sleep(500);
        organizationPage.openDistributors();
        Thread.sleep(500);

        WebDriverWait w = getWait();
        WebElement addButton = w.until(ExpectedConditions.elementToBeClickable(By.id("btn-add-new-distributor")));
        addButton.click();

        WebElement nameInput = w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@formcontrolname='name']")));
        nameInput.clear();
        nameInput.sendKeys("Invalid Email Distributor " + System.currentTimeMillis());

        WebElement phoneInput = driver.findElement(By.xpath("//input[@formcontrolname='contactPhone']"));
        phoneInput.clear();
        phoneInput.sendKeys("03001234567");

        WebElement emailInput = driver.findElement(By.xpath("//input[@formcontrolname='salesEmail']"));
        emailInput.clear();
        emailInput.sendKeys("invalid-email");

        WebElement contactEmailInput = driver.findElement(By.xpath("//input[@formcontrolname='contactEmail']"));
        contactEmailInput.clear();
        contactEmailInput.sendKeys("invalid-email");

        WebElement firstNameInput  = driver.findElement(By.xpath("//input[@formcontrolname='userFirstName']"));
        firstNameInput.clear();
        firstNameInput.sendKeys("Test");

        WebElement lastNameInput  = driver.findElement(By.xpath("//input[@formcontrolname='userLastName']"));
        lastNameInput.clear();
        lastNameInput.sendKeys("Automation");

        WebElement secondPhoneInput  = driver.findElement(By.xpath("//input[@formcontrolname='userPhone']"));
        secondPhoneInput.clear();
        secondPhoneInput.sendKeys("03121234567");

        WebElement secondEmailInput  = driver.findElement(By.xpath("//input[@formcontrolname='userEmail']"));
        secondEmailInput.clear();
        secondEmailInput.sendKeys("invalid-email");

        WebElement saveButton = w.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-submit-distributor-form")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", saveButton);
        Thread.sleep(500);
        //saveButton.click();
        boolean disabled = !saveButton.isEnabled();
        
        WebElement cancelButton = w.until(
    	        ExpectedConditions.elementToBeClickable(By.id("btn-cancel-distributor-form"))
    	    );
    	    cancelButton.click();
    	    
    	Assert.assertTrue(disabled, "Expected validation error for invalid distributor name.");
    }

    @Test(priority = 50)
    public void invalidLoginEmptyPassword() throws InterruptedException {
        logoutIfLoggedIn();
        WebDriverWait w = getWait();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(validEmail, "");
        WebElement errorElement = w.until(ExpectedConditions.visibilityOfElementLocated(By.id("passwordError")));
        String errorText = errorElement.getText().trim();
        System.out.println("Error message displayed: " + errorText);

        Assert.assertEquals(errorText, "Please enter your password.", 
            "Expected validation message not displayed for empty password.");
    }

    @Test(priority = 51)
    public void invalidLoginEmptyEmail() throws InterruptedException {
        logoutIfLoggedIn();

        WebDriverWait w = getWait();
        // open login
        WebElement loginButton = w.until(ExpectedConditions.elementToBeClickable(By.id("btn-login")));
        loginButton.click();

        WebElement emailInput = w.until(ExpectedConditions.visibilityOfElementLocated(By.id("i0116")));
        emailInput.clear();
        // leave empty
        WebElement nextButton = w.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));
        nextButton.click();

        boolean errorShown = false;
        try {
            WebElement error = w.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div[role='alert'], .error, .alert, .input-error")
            ));
            errorShown = error.isDisplayed();
        } catch (TimeoutException e) {}

        Assert.assertTrue(errorShown, "Expected validation error when email is empty.");
    }
}