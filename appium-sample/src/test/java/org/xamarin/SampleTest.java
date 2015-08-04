package org.xamarin;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;

/**
 * Created by krukow on 03/08/15.
 */
public class SampleTest {

    AppiumDriver<WebElement> wd;
    @Before
    public void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "4.4");
        capabilities.setCapability("deviceName", "");
        capabilities.setCapability("app", "/Users/krukow/samples/Superheroes.apk");
        wd = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
        wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @After
    public void tearDown() {
        wd.quit();
    }
    @Test
    public void sample() {
        wd.findElement(By.id("com.icontrivia.GuessTheSuperheroes:id/btnplay")).click();
        WebElement superHero = wd.findElement(By.id("com.icontrivia.GuessTheSuperheroes:id/imageView"));
        assertNotNull(superHero);
        wd.findElement(By.id("com.icontrivia.GuessTheSuperheroes:id/button")).click();

        AndroidElement firstSolutionRow = (AndroidElement)wd.findElement(By.id("com.icontrivia.GuessTheSuperheroes:id/gui_answer1"));
        String selectedText = firstSolutionRow.findElementByClassName("android.widget.Button").getText();
        assertNotNull(selectedText);

    }
}
