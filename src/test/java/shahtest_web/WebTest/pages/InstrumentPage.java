package shahtest_web.WebTest.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.List;

public class InstrumentPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public InstrumentPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /** Opens the Instruments menu from sidebar */
    public void openInstrumentMenu() {
        WebElement instrumentMenu = wait.until(
            ExpectedConditions.elementToBeClickable(By.id("sb-menu-instruments"))
        );
        instrumentMenu.click();
    }

    /** Opens LTL3500 submenu under Instruments */
    public void openLTL3500() {
        WebElement ltl3500 = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//mat-list-item[contains(@id, 'sb-submenu-instrument')]//a[contains(text(), 'LTL3500')]")
        ));
        ltl3500.click();
    }

    /** Selects the first instrument row from the table/list */
    public void selectFirstInstrument() {
    	WebElement firstRow = wait.until(
    	        ExpectedConditions.elementToBeClickable(
    	            By.xpath("//table//tr[1]//td[normalize-space(text()) != '']")
    	        )
    	    );
        firstRow.click();
    }

    /** Clicks the back button on the instrument details page */
    public void clickBackButton() {
        try {
        	WebElement backButton = wait.until(
    				ExpectedConditions.elementToBeClickable(By.id("btn-handle-back"))
    				);
            backButton.click();
            System.out.println("🔙 Clicked back button on instrument page.");
        } catch (TimeoutException e) {
            System.out.println("⚠️ Back button not found — skipping.");
        }
    }

    /** Adds a new instrument and handles snackbar responses */
    public void addNewInstrument(String distributorName, String serialNumber) throws InterruptedException {
        WebElement addButton = wait.until(
            ExpectedConditions.elementToBeClickable(By.id("btn-add-new-instrument"))
        );
        addButton.click();

        WebElement serialField = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("txt-serialNumber"))
        );
        serialField.clear();
        serialField.sendKeys(serialNumber);
        Thread.sleep(500);

        // ✅ Open distributor dropdown
        WebElement distributorDropdown = wait.until(
            ExpectedConditions.elementToBeClickable(By.id("sel-distributorId"))
        );
        distributorDropdown.click();
        Thread.sleep(1000);
	    try {
	        // Wait for options to appear
	        List<WebElement> distributors = wait.until(
	            ExpectedConditions.visibilityOfAllElementsLocatedBy(
	                By.cssSelector("mat-option .mdc-list-item__primary-text")
	            )
	        );

	        boolean found = false;
	        for (WebElement distributor : distributors) {
	            String name = distributor.getText().trim();
	            if (name.equalsIgnoreCase(distributorName)) {
	                distributor.click();
	                found = true;
	                break;
	            }
	        }

	        // If not found, select "-"
	        if (!found) {
	            for (WebElement distributor : distributors) {
	                if (distributor.getText().trim().equals("-")) {
	                    distributor.click();
	                    found = true;
	                    break;
	                }
	            }
	        }

	    } catch (TimeoutException e) {}
	 // Click Save button
	    WebElement saveButton = wait.until(
	        ExpectedConditions.elementToBeClickable(By.id("btn-submit-instrument-form"))
	    );
	    Thread.sleep(1000);
	    saveButton.click();
	    
	    Thread.sleep(2000);

        boolean serialExists = false;

        // ✅ Step 1: Check if snackbar message appears
        try {
            WebElement snackbar = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".mat-mdc-snack-bar-label, .mat-simple-snackbar, .mdc-snackbar__label")
            ));
            String message = snackbar.getText().trim();
            System.out.println("📩 Snackbar message detected: " + message);

            if (message.toLowerCase().contains("already exist")) {
                serialExists = true;
            }
        } catch (TimeoutException e) {
            System.out.println("ℹ️ No snackbar message appeared.");
        }

        // ✅ Step 2: If serial not exists but a confirmation popup appears
        if (!serialExists) {
            try {
                WebElement createNewBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(., 'Create New')]")
                ));
                createNewBtn.click();
                System.out.println("✅ Clicked 'Create New' button.");
            } catch (TimeoutException e) {
                System.out.println("ℹ️ No 'Create New' popup found.");
            }
        }
    }

}
