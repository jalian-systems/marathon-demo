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

public class LoginTest2 {

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
	public void loginEnabledWhenNameAndPasswordAreEntered() {
		PageLogin pl = new PageLogin(driver);
		pl.setUser("Joe");
		pl.setPassword("Burton");
		assertTrue(pl.getLogin().isEnabled());
	}

	@Test
	public void loginDisabledWhenNameIsEmpty() {
		PageLogin pl = new PageLogin(driver);
		pl.setUser("");
		pl.setPassword("Burton");
		assertFalse(pl.getLogin().isEnabled());
	}

	@Test
	public void loginDisabledWhenPasswordIsEmpty() {
		PageLogin pl = new PageLogin(driver);
		pl.setUser("Joe");
		pl.setPassword("");
		assertFalse(pl.getLogin().isEnabled());
	}
	
	@Test
	public void loginWithCredentials() {
		PageLogin pl = new PageLogin(driver);
		pl.login("Joe", "Burton");
		driver.switchTo().window("Login Success");
		WebElement messageLabel = driver.findElementsByTagName("label").get(1);
		assertEquals("You logged in as: Joe with password: Burton", messageLabel.getText());
	}
}
