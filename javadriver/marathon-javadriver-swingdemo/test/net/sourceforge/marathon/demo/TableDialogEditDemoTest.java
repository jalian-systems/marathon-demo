package net.sourceforge.marathon.demo;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.json.JSONArray;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import net.sourceforge.marathon.javadriver.JavaDriver;

public class TableDialogEditDemoTest {

	private JavaDriver driver;
	private JFrame frame;

	@Before
	public void setUp() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI();
			}
		});
		driver = new JavaDriver();
		driver.switchTo().window("TableDialogEditDemo");
	}

	@After
	public void tearDown() throws InvocationTargetException, InterruptedException {
		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				frame.dispose();
			}
		});
		driver.close();
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	private void createAndShowGUI() {
		// Create and set up the window.
		frame = new JFrame("TableDialogEditDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		JComponent newContentPane = new TableDialogEditDemo();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	@Test
	public void initialContentOK() {
		WebElement table = driver.findElementByTagName("table");
		JSONArray expected = new JSONArray(
				"[[\"Mary\",\"java.awt.Color[r=153,g=0,b=153]\",\"Snowboarding\",\"5\",\"false\"],"
						+ " [\"Alison\",\"java.awt.Color[r=51,g=51,b=153]\",\"Rowing\",\"3\",\"true\"],"
						+ " [\"Kathy\",\"java.awt.Color[r=51,g=102,b=51]\",\"Knitting\",\"2\",\"false\"],"
						+ " [\"Sharon\",\"java.awt.Color[r=255,g=0,b=0]\",\"Speed reading\",\"20\",\"true\"],"
						+ " [\"Philip\",\"java.awt.Color[r=255,g=175,b=175]\",\"Pool\",\"10\",\"false\"]]");
		JSONArray actual = new JSONArray(table.getAttribute("content"));
		assertEquals(expected.toString(), actual.toString());
	}

	@Test
	public void editTextField() {
		WebElement table = driver.findElementByTagName("table");
		WebElement cell13 = table.findElement(By.cssSelector(".::mnth-cell(1,3)"));
		assertEquals("Snowboarding", cell13.getText());
		WebElement cell13Editor = cell13.findElement(By.cssSelector(".::editor"));
		cell13Editor.sendKeys("Cricket", Keys.ENTER);
		assertEquals("Cricket", cell13.getText());
	}

	@Test
	public void editCheckBoxField() {
		WebElement table = driver.findElementByTagName("table");
		WebElement cell25 = table.findElement(By.cssSelector(".::mnth-cell(2,5)"));
		assertEquals("true", cell25.getText());
		cell25.click();
		assertEquals("false", cell25.getText());
	}

	@Test
	public void editColorField() throws InterruptedException {
		WebElement table = driver.findElementByTagName("table");
		WebElement cell32 = table.findElement(By.cssSelector(".::mnth-cell(3,2)"));
		assertEquals("[r=51,g=102,b=51]", cell32.getAttribute("pseudoComponent.background"));
		cell32.click();
		driver.switchTo().window("Pick a Color");
		WebElement colorChooser = driver.findElementByTagName("color-chooser");
		assertEquals("[r=51,g=102,b=51]", colorChooser.getAttribute("color"));
		driver.executeScript("$1.setColor(java.awt.Color.BLACK);", colorChooser);
		assertEquals("[r=0,g=0,b=0]", colorChooser.getAttribute("color"));
		driver.findElement(By.cssSelector("button[text='OK']")).click();
		driver.switchTo().window("TableDialogEditDemo");
		table = driver.findElementByTagName("table");
		cell32 = table.findElement(By.cssSelector(".::mnth-cell(3,2)"));
		assertEquals("[r=0,g=0,b=0]", cell32.getAttribute("pseudoComponent.background"));
	}
}
