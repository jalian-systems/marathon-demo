package net.sourceforge.marathon.demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PageLogin {

	@FindBy(tagName = "text-field")
	WebElement userName;

	@FindBy(tagName = "password-field")
	WebElement password;
	@FindBy(tagName = "check-box")
	WebElement rememberMe;
	@FindBy(css = "button[text='Login']")
	WebElement login;
	@FindBy(css = "button[text='Cancel']")
	WebElement cancel;

	@SuppressWarnings("unused")
	private WebDriver driver;
	
	public PageLogin(WebDriver driver) {
		this.driver = driver ;
		driver.switchTo().window("Login");
		PageFactory.initElements(driver, this);
	}
	
	public void login(String user, String pass) {
		userName.sendKeys(user);
		password.sendKeys(pass);
		login.click();
	}

	public void setUser(String user) {
		userName.clear();
		userName.sendKeys(user);
	}
	
	public void setPassword(String pass) {
		password.clear();
		password.sendKeys(pass);
	}
	
	public WebElement getLogin() {
		return login;
	}
}
