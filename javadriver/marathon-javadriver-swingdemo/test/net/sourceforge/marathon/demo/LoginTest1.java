package net.sourceforge.marathon.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.SwingUtilities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import net.sourceforge.marathon.javadriver.JavaDriver;

public class LoginTest1 {

	private Login login;
	private JavaDriver driver;

	@Before
	public void setUp() {
		login = new Login();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				login.setVisible(true);
			}
		});
		driver = new JavaDriver();
		driver.switchTo().window("Login");
	}

	@After
	public void tearDown() throws InvocationTargetException, InterruptedException {
		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				login.dispose();
			}
		});
		driver.close();
	}

	@Test
	public void checkElements() {
		List<WebElement> textFields = driver.findElementsByTagName("text-field");
		assertEquals(1, textFields.size());
		List<WebElement> passwordFields = driver.findElementsByTagName("password-field");
		assertEquals(1, passwordFields.size());
		List<WebElement> cbFields = driver.findElementsByTagName("check-box");
		assertEquals(1, cbFields.size());
		List<WebElement> buttons = driver.findElementsByTagName("button");
		assertEquals(2, buttons.size());
	}

	@Test
	public void loginEnabledWhenNameAndPasswordAreEntered() {
		WebElement nameField = driver.findElement(By.cssSelector("text-field"));
		nameField.sendKeys("Joe");
		WebElement passwordField = driver.findElement(By.cssSelector("password-field"));
		passwordField.sendKeys("Burton");
		WebElement loginButton = driver.findElement(By.cssSelector("button[text='Login']"));
		assertTrue(loginButton.isEnabled());
	}

	@Test
	public void loginDisabledWhenNameIsEmpty() {
		WebElement nameField = driver.findElement(By.cssSelector("text-field"));
		nameField.clear();
		WebElement passwordField = driver.findElement(By.cssSelector("password-field"));
		passwordField.sendKeys("Burton");
		WebElement loginButton = driver.findElement(By.cssSelector("button[text='Login']"));
		assertFalse(loginButton.isEnabled());
	}

	@Test
	public void loginDisabledWhenPasswordIsEmpty() {
		WebElement nameField = driver.findElement(By.cssSelector("text-field"));
		nameField.sendKeys("Joe");
		WebElement passwordField = driver.findElement(By.cssSelector("password-field"));
		passwordField.clear();
		WebElement loginButton = driver.findElement(By.cssSelector("button[text='Login']"));
		assertFalse(loginButton.isEnabled());
	}
	
	@Test
	public void loginWithCredentials() {
		WebElement nameField = driver.findElement(By.cssSelector("text-field"));
		nameField.sendKeys("Joe");
		WebElement passwordField = driver.findElement(By.cssSelector("password-field"));
		passwordField.sendKeys("Burton");
		WebElement loginButton = driver.findElement(By.cssSelector("button[text='Login']"));
		loginButton.click();
		driver.switchTo().window("Login Success");
		WebElement messageLabel = driver.findElementsByTagName("label").get(1);
		assertEquals("You logged in as: Joe with password: Burton", messageLabel.getText());
	}
}
