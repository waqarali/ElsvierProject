package keywords;

import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class GenericKeyWords {
	public WebDriver driver;
	public Properties prop;
	public ExtentTest test;
	public SoftAssert softAssert;
	
	
	
	
	//Open Browser on which to run test
	
	public void openBrowser(String bNamekey) {
		String bName = getProperty(bNamekey);
		log("Opening the "+bName+" Browser");
		
		if(bName.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\chromedriver.exe");
			driver = new ChromeDriver();
		}else if(bName.equals("Mozilla")) {
			System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"\\geckodriver.exe");
			driver = new FirefoxDriver();
		}else if(bName.equals("IE")) {
			System.setProperty("webdriver.ie.driver",System.getProperty("user.dir")+"\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}else if(bName.equals("Edge")) {
			System.setProperty("webdriver.edge.driver",System.getProperty("user.dir")+"\\msedgedriver.exe");
			driver = new EdgeDriver();
		}
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	//This for reading properties file data
	public String getProperty(String key) {
    	return prop.getProperty(key);
    }


	//Open URL
	public void navigate(String urlKey) {
		log("Opening url");
		driver.get(getProperty(urlKey));
	}
	
	//Find Locator By class
	public By getLocator(String locatorKey) {
		By by = null;
		if(locatorKey.endsWith("_id"))
			by = By.id(getProperty(locatorKey));
		else if(locatorKey.endsWith("_name"))
			by = By.name(getProperty(locatorKey));
		else if(locatorKey.endsWith("_xpath"))
			by = By.xpath(getProperty(locatorKey));
		else
			by = By.cssSelector(getProperty(locatorKey));
		
		return by;
		
	}
	
	//Finds the element
	public WebElement getElement(String locatorKey) {
		
		log("Looking for element "+locatorKey);
		
		if(!isElementPresent(locatorKey)) {
			log("Element is not present "+locatorKey);
			reportFailure("No element found "+locatorKey, true);
		}
		
		if(!isElementVisible(locatorKey)) {
			log("Element is not visible "+locatorKey);
			reportFailure("No element found "+locatorKey, true);
		}
		
		WebElement element = driver.findElement(getLocator(locatorKey));
	
		return element;
	}
	
	//Click on element
	public void click(String locatorKey) {
		
		getElement(locatorKey).click();
		log("Clicking on "+locatorKey);
	}

	//Type data in field
	public void type(String locator, String data) {
		getElement(locator).sendKeys(data);
	}
	
	//To get text from element
	public String getText(String locator) {
		String text = getElement(locator).getText();
		log("Here is text of "+locator+"---"+text);
		return text;
	}
	
	//Moving mouse to element
	public void movingMouse(String locator) {
		 Actions act = new Actions(driver);
		act.moveToElement(getElement(locator)).build().perform();
	}
	
	//Check isElement present
	public boolean isElementPresent(String locatorkey) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {	
			 wait.until(ExpectedConditions.presenceOfElementLocated((getLocator(locatorkey))));
			
		}catch(Exception e) {
			return false;
		}
		return true;
	}
	
	//Check if element is visible
	public boolean isElementVisible(String locatorkey) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated((getLocator(locatorkey))));
			
		}catch(Exception e) {
			return false;
		}
		return true;
	}
	//Select option from drop down
	public void selectByVisibleText(String locatorKey, String data) {
		Select s = new Select(getElement(locatorKey));
		s.selectByVisibleText(data);
	}

	
	//wait function
	public void wait(int i) {
		try {
			Thread.sleep(i*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Load the page properly
	public void waitForPageLoad() {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		int i =0;
		while(i!=10) {
			String state = (String) js.executeScript("return document.readystate;");
			if(state.equals("complete"))
				break;
			else
				wait(2);
			i++;
		}
		
		i=0;
		while(i!=10) {
			Long d = (Long) js.executeScript("return jQuery.active;");
			if(d.longValue()==0)
				break;
			else
				wait(2);
			i++;
		}
	}
	
	//closing browser function
	public void closeBrowser() {
		log("Closing browser");
		driver.quit();
	}
	
	//Log function for reporting messages
	public void log(String msg) {
		test.log(Status.INFO, msg);
	}
	
	//Report failure
	public void reportFailure(String failureMsg, boolean stopOnFailure) {
		test.log(Status.FAIL, failureMsg);
		softAssert.fail(failureMsg);
		
		if(stopOnFailure) {
			Reporter.getCurrentTestResult().getTestContext().setAttribute("CriticalFailure", "Y");
			assertAll();
		}
	}
	
	//Report all failures
	public void assertAll() {
		
		softAssert.assertAll();
	}
	
}
