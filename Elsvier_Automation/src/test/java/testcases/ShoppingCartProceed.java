package testcases;

import org.testng.ITestContext;
import org.testng.annotations.Test;
import testbase.BaseTest;

public class ShoppingCartProceed extends BaseTest{
	
	@Test
	public void addShopingCart(ITestContext context) {
		
		
		app.openBrowser("browser");
		app.navigate("url");
		app.movingMouse("dresses_xpath");
		app.click("summerdresses_css");
		app.movingMouse("dress1_xpath");
		app.click("addbasket1_xpath");
		app.click("continueshop_xpath");
		app.movingMouse("dress2_xpath");
		app.click("addbasket2_xpath");
		app.click("procedecheckout_xpath");
		app.click("checkout_xpath");
		app.validateElementPresent("signin_xpath");
		app.click("signin_xpath");
		app.validateText("createaccount_xpath", app.prop.getProperty("expectedText"));
		app.closeBrowser();
	}

}
