package net.sourceforge.marathon.demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class PageLoginFX2 {

    WebElement userName;
    WebElement password;
    WebElement rememberMe;
    WebElement login;
    WebElement cancel;

    @SuppressWarnings("unused") private WebDriver driver;

    public PageLoginFX2(WebDriver driver) {
        this.driver = driver;
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
