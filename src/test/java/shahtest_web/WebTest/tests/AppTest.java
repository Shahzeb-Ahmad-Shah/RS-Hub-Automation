package shahtest_web.WebTest.tests;

import org.testng.annotations.Test;
import shahtest_web.WebTest.base.BaseTest;
import shahtest_web.WebTest.pages.*;

public class AppTest extends BaseTest {

    @Test(priority = 1)
    public void logInTest() {
        String engrLogIn = "ft.engineer@teo-intl.com";
        String engrPwd = "Lyngby2024";

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(engrLogIn, engrPwd);
    }

    @Test(priority = 2, dependsOnMethods = "logInTest")
    public void measurementTest() throws InterruptedException {
        MeasurementPage measurementPage = new MeasurementPage(driver);
        measurementPage.openMeasurements();
        Thread.sleep(3000);
        measurementPage.openRetroSignGRX();
        Thread.sleep(4000);
        measurementPage.clickBackButton();
    }

    @Test(priority = 3, dependsOnMethods = "logInTest")
    public void instrumentTest() throws InterruptedException {
        InstrumentPage instrumentPage = new InstrumentPage(driver);
        instrumentPage.openInstrumentMenu();
        Thread.sleep(2000);
        instrumentPage.openLTL3500();
        Thread.sleep(2000);
        instrumentPage.selectFirstInstrument();
        Thread.sleep(7000);
        instrumentPage.clickBackButton();
    }

    @Test(priority = 4, dependsOnMethods = "logInTest")
    public void addInstrumentTest() throws InterruptedException {
        String distributorName = "Arctic Distributors";
        String serialNumber = "11557";

        InstrumentPage instrumentPage = new InstrumentPage(driver);
        instrumentPage.addNewInstrument(distributorName, serialNumber);
    }

    @Test(priority = 5, dependsOnMethods = "logInTest")
    public void userTest() throws InterruptedException {
        UserPage userPage = new UserPage(driver);
        userPage.clickOutside();
        userPage.openUsers();
        Thread.sleep(2000);
        userPage.openUserMenu();
        Thread.sleep(2000);
        userPage.clickOutside();
        Thread.sleep(1000);
        userPage.addNewUser();
        Thread.sleep(2000);
        userPage.fillUserForm();
    }

    @Test(priority = 6, dependsOnMethods = "logInTest")
    public void organizationTest() throws InterruptedException {
        OrganizationPage organizationPage = new OrganizationPage(driver);
        organizationPage.openOrganizations();
        Thread.sleep(1000);
        organizationPage.openDistributors();
        Thread.sleep(2000);
        organizationPage.openFirstDistributorRow();
        Thread.sleep(2000);
        organizationPage.cancelMenu();
        Thread.sleep(1000);
    }
    
    @Test(priority = 7, dependsOnMethods = "logInTest")
    public void addOrganizationTest() throws InterruptedException {
        String distributorName = "Arctic Distributors Test";
        OrganizationPage organizationPage = new OrganizationPage(driver);
        organizationPage.addNewDistributor(distributorName);
    }
}

