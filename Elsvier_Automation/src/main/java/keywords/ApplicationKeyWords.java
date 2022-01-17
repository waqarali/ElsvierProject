package keywords;

import java.io.FileInputStream;
import java.util.Properties;

import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;

public class ApplicationKeyWords extends ValidationKeyWords {
	
	public ApplicationKeyWords() {
		
		try{
			prop = new Properties();
			String path = System.getProperty("user.dir")+"\\src\\test\\resources\\project.properties";
			FileInputStream fis = new FileInputStream(path);
			prop.load(fis);
		}catch (Exception e) {
			e.printStackTrace();
			}
		
		softAssert = new SoftAssert();
	}
	
	
	public void setReport(ExtentTest test) {
		this.test = test;
	}
	
	public void validateLogin() {
		
	}
	
}
