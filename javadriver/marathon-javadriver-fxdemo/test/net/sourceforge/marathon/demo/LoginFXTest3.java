package net.sourceforge.marathon.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import javafx.stage.Stage;
import net.sourceforge.marathon.javadriver.JavaDriver;
import net.sourceforge.marathon.javadriver.JavaProfile;
import net.sourceforge.marathon.javadriver.JavaProfile.LaunchType;

public class LoginFXTest3 extends JavaFXTest {

    private JavaDriver driver;

    @Before public void setUp() {
        JavaProfile profile = new JavaProfile();
        profile.setLaunchType(LaunchType.FX_APPLICATION);
        driver = new JavaDriver(profile);
    }

    @Test public void loginEnabledWhenNameAndPasswordAreEntered() {
        PageLoginFX2 pl = new PageLoginFX2(driver);
        pl.setUser("Joe");
        pl.setPassword("Burton");
        assertTrue(pl.getLogin().isEnabled());
    }

    @Test public void loginDisabledWhenNameIsEmpty() {
        PageLoginFX2 pl = new PageLoginFX2(driver);
        pl.setUser("");
        pl.setPassword("Burton");
        assertFalse(pl.getLogin().isEnabled());
    }

    @Test public void loginDisabledWhenPasswordIsEmpty() {
        PageLoginFX2 pl = new PageLoginFX2(driver);
        pl.setUser("Joe");
        pl.setPassword("");
        assertFalse(pl.getLogin().isEnabled());
    }

    @Test public void loginWithCredentials() {
        PageLoginFX2 pl = new PageLoginFX2(driver);
        pl.login("Joe", "Burton");
        driver.switchTo().window("Login Success");
        WebElement messageLabel = driver.findElementByTagName("label");
        assertEquals("You logged in as: Joe with password: Burton", messageLabel.getText());
    }

    @Override public Stage getMainStage() {
        LoginFX loginFX = new LoginFX();
        return loginFX.getStage();
    }
}
