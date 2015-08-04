# agile-2015-samples
Samples used for Agile 2015 conference talk on automated testing for mobile

# Notes

* Base Installation
** Xcode
** Android SDK
** Java JDK

ANDROID_HOME is set to "/Users/krukow/android/adt/sdk"
JAVA_HOME is set to "/Library/Java/JavaVirtualMachines/jdk1.8.0_45.jdk/Contents/Home."

** Debug keystore
** device, or sim/emulator
** Need an app to test :)


* Calabash 2.0

** Installation:
https://github.com/calabash/install -- Just run the curl command. Now I've already done this.
Right just use: https://github.com/calabash/calabash

** Scaffold a project

  mkdir calabash-sample
  cd calabash-sample
  calabash generate

Using Ruby and Cucumber.
BDD with Cucumber.

Run it CLI. Resign and run again.

** Write a test!
*** Sample feature:

Feature: Playing the game
  Scenario: Tapping a letter
    Given I'm on the main page
    And I start playing
    Then an image of a super hero is shown

    When I choose a letter
    Then that letter becomes selected

*** How would I go about actually testing this?
The Calabash console is a great interactive environment,
but you can also use uiautomatorviewer.

 ~/android/adt/sdk/tools/uiautomatorviewer


** Run it

   calabash run ../Superheroes.apk


* Appium

** Installation:
http://appium.io/ Download the app
Run the doctor and make sure you've set (.bash_profile)


** Sanity test it.
Set device name (with one connected device - just blank)
Make sure you stop appium when reconfiguring.

Run inspector.
Doesn't Live update so Refresh.
Click around to inspect.
Stop.


** Scaffold a project

*** Setup IDE: We'll use Java and jUnit. Easiest to use JetBrains' IntelliJ.
New Project > Maven > Archetype: maven-archetype-quickstart or

    mvn archetype:generate -DgroupId=com.xamarin.sample \
         -DartifactId=appium-sample \
         -DarchetypeArtifactId=maven-archetype-quickstart \
         -DinteractiveMode=false


*** Update dependencies
Update to jUnit 4

Add Appium Java Client

<dependency>
  <groupId>io.appium</groupId>
  <artifactId>java-client</artifactId>
  <version>3.1.0</version>
  <scope>test</scope>
</dependency>


*** Use the Appium inspector to scaffold a test...
Create a Class and setup+tear down.
- Create an instance var AppiumDriver<WebElement> wd;
- Import deps and replace AppiumDriver with AndroidDriver.
- capabilities.setCapability("deviceName", "");
- replace wd.close with  wd.quit();


** Sanity test it
A few gotcha's
-- Restart the server if you get an error about existing session

In Settings. Override existing sessions

capabilities.setCapability("deviceName", "");


** Write a test!

*** How would I go about actually testing this?
Use the inspector to find ids

 wd.findElement(By.id("com.icontrivia.GuessTheSuperheroes:id/btnplay")).click();
 WebElement superHero = wd.findElement(By.id("com.icontrivia.GuessTheSuperheroes:id/imageView"));
 assertNotNull(superHero);
 wd.findElement(By.id("com.icontrivia.GuessTheSuperheroes:id/button")).click();

 AndroidElement firstSolutionRow = (AndroidElement)wd.findElement(By.id("com.icontrivia.GuessTheSuperheroes:id/gui_answer1"));
 String selectedText = firstSolutionRow.findElementByClassName("android.widget.Button").getText();
 assertNotNull(selectedText);


** Run it
