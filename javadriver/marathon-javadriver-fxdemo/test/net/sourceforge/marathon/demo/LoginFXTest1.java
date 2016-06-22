package net.sourceforge.marathon.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javafx.stage.Stage;
import net.sourceforge.marathon.javadriver.JavaDriver;
import net.sourceforge.marathon.javadriver.JavaProfile;
import net.sourceforge.marathon.javadriver.JavaProfile.LaunchType;

public class LoginFXTest1 extends JavaFXTest {

    private JavaDriver driver;

    @Before public void createDriver() {
        JavaProfile profile = new JavaProfile();
        profile.setLaunchType(LaunchType.FX_APPLICATION);
        driver = new JavaDriver(profile);
        driver.switchTo().window("Login");
    }

    @Test public void checkElements() {
        List<WebElement> textFields = driver.findElementsByTagName("text-field");
        assertEquals(1, textFields.size());
        List<WebElement> passwordFields = driver.findElementsByTagName("password-field");
        assertEquals(1, passwordFields.size());
        List<WebElement> cbFields = driver.findElementsByTagName("check-box");
        assertEquals(1, cbFields.size());
        List<WebElement> buttons = driver.findElementsByTagName("button");
        assertEquals(2, buttons.size());
    }

    @Test public void loginEnabledWhenNameAndPasswordAreEntered() {
        WebElement nameField = driver.findElement(By.cssSelector("text-field"));
        nameField.sendKeys("Joe");
        WebElement passwordField = driver.findElement(By.cssSelector("password-field"));
        passwordField.sendKeys("Burton");
        WebElement loginButton = driver.findElement(By.cssSelector("button[text='Login']"));
        assertTrue(loginButton.isEnabled());
    }

    @Test public void loginDisabledWhenNameIsEmpty() {
        WebElement nameField = driver.findElement(By.cssSelector("text-field"));
        nameField.clear();
        WebElement passwordField = driver.findElement(By.cssSelector("password-field"));
        passwordField.sendKeys("Burton");
        WebElement loginButton = driver.findElement(By.cssSelector("button[text='Login']"));
        assertFalse(loginButton.isEnabled());
    }

    @Test public void loginWithCredentials() {
        WebElement nameField = driver.findElement(By.cssSelector("text-field"));
        nameField.sendKeys("Joe");
        WebElement passwordField = driver.findElement(By.cssSelector("password-field"));
        passwordField.sendKeys("Burton");
        WebElement loginButton = driver.findElement(By.cssSelector("button[text='Login']"));
        loginButton.click();
        driver.switchTo().window("Login Success");
        WebElement messageLabel = driver.findElementByTagName("label");
        assertEquals("You logged in as: Joe with password: Burton", messageLabel.getText());
    }

    @Override public Stage getMainStage() {
        LoginFX loginFX = new LoginFX();
        return loginFX.getStage();
    }

}
