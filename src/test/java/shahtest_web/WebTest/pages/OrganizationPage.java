package shahtest_web.WebTest.pages;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class OrganizationPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public OrganizationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openOrganizations() {
        try {
            WebElement orgPanel = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//mat-expansion-panel-header[.//span[contains(text(), 'Organizations')]]")
            ));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", orgPanel);
            Thread.sleep(500);
            orgPanel.click();
        } catch (Exception e) {}
    }

    public void openDistributors() {
        try {
            WebElement distributorsLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(), 'Distributors') and contains(@href, '/organizations/distributors')]")
            ));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", distributorsLink);
            Thread.sleep(500);
            distributorsLink.click();
        } catch (Exception e) {}
    }

    public void openFirstDistributorRow() {
        try {
            List<WebElement> rows = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("tr.mat-mdc-row"))
            );
            if (rows.isEmpty()) return;

            WebElement firstRow = rows.get(0);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", firstRow);
            Thread.sleep(500);
            firstRow.click();
        } catch (Exception e) {}
    }

    public void cancelMenu() {
        try {
            WebElement cancelButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[.//span[normalize-space()='Cancel']]")
            ));
            if (cancelButton.isDisplayed()) {
                cancelButton.click();
            }
        } catch (Exception e) {}
    }
    
    /** Adds a new organization and handles snackbar responses */
    public void addNewDistributor(String distributorName) throws InterruptedException {
    	
    	WebElement addButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("btn-add-new-distributor"))
            );
            addButton.click();
            
         // Enter distributor name in the input field
            WebElement distributorInput = driver.findElement(By.xpath("//input[@formcontrolname='name']"));
            distributorInput.clear();
            distributorInput.sendKeys(distributorName);
            
         // Enter phone number in the distributor form
            WebElement phoneInput = driver.findElement(By.xpath("//input[@formcontrolname='contactPhone']"));
            phoneInput.clear();
            phoneInput.sendKeys("03001234567");
            
         // Enter email in the distributor form
            WebElement emailInput = driver.findElement(By.xpath("//input[@formcontrolname='salesEmail']"));
            emailInput.clear();
            emailInput.sendKeys("testdistributor@example.com");
                        
         // Enter contact email in the distributor form
            WebElement contactEmailInput = driver.findElement(By.xpath("//input[@formcontrolname='contactEmail']"));
            contactEmailInput.clear();
            contactEmailInput.sendKeys("contactperson@example.com");

         // Enter first name in the form
            WebElement firstNameInput  = driver.findElement(By.xpath("//input[@formcontrolname='userFirstName']"));
            firstNameInput.clear();
            firstNameInput.sendKeys("Test");
            
         // Enter last name in the form
            WebElement lastNameInput  = driver.findElement(By.xpath("//input[@formcontrolname='userLastName']"));
            lastNameInput.clear();
            lastNameInput.sendKeys("Automation");
            
         // Enter phone number
            WebElement secondPhoneInput  = driver.findElement(By.xpath("//input[@formcontrolname='userPhone']"));
            secondPhoneInput.clear();
            secondPhoneInput.sendKeys("03121234567");
            
         // Enter email address
            WebElement secondEmailInput  = driver.findElement(By.xpath("//input[@formcontrolname='userEmail']"));
            secondEmailInput.clear();
            secondEmailInput.sendKeys("shahzeb@example.com");
            
         // Submit button
            WebElement saveButton = wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("btn-submit-distributor-form"))
                );
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", saveButton);
                Thread.sleep(1000);
                saveButton.click();
                
                Thread.sleep(2000);
                
                boolean distributionExists = false;
                try {
                    WebElement snackbar = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".mat-mdc-snack-bar-label, .mat-simple-snackbar, .mdc-snackbar__label")
                    ));
                    String message = snackbar.getText().trim();
                    System.out.println("📩 Snackbar message: " + message);
                    if (message.toLowerCase().contains("already exist")) {
                    	distributionExists = true;
                    }
                } catch (TimeoutException e) {}

                if (distributionExists) {
                    try {
                        WebElement cancelButton = wait.until(ExpectedConditions.elementToBeClickable(
                            By.id("btn-cancel-distributor-form")
                        ));
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cancelButton);
                        Thread.sleep(1000);
                        cancelButton.click();
                    } catch (Exception ex) {}
                }   	
    }
}
