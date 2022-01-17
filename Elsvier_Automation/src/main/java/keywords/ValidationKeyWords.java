package keywords;

import org.openqa.selenium.WebElement;

public class ValidationKeyWords extends GenericKeyWords {
	
	public void validateText(String locator, String expectedText) {
		String text = getText(locator);
		String actualtext= text.trim();
		System.out.println(actualtext);
		System.out.println(expectedText);
		boolean b = expectedText.equalsIgnoreCase(actualtext);
		System.out.println(b);
		if(b=true) {
			log("Text matched "+actualtext+"----"+expectedText);
		}else
			
			reportFailure("Text does not match "+locator, false);
		
	}
	
	public void validateElementPresent(String locator) {
		log("Checking Is element present?");
		WebElement e = getElement(locator);
		if(!e.equals(null)) {
			log("Element present");
		}else
			reportFailure("Element not found "+locator, false);
	}

}
