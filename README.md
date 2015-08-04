# agile-2015-samples
Samples used for Agile 2015 conference talk on automated testing for mobile. These are just a very basic test for Calabash 2.0 and Appium. The intention is to show how easy it is to get started and write the first tests.


# Talk Notes

To get started and run tests locally from your machine, you need a base installation. This consists of the basic developer tools for iOS and Android. 

## Base Installation

1. Xcode
2. Android SDK
3. Java JDK

You can run Calabash for Android on Windows, Mac or Linux. We recommend that you use Ruby or 2.x.

You need the following configured on your computer:

The `ANDROID_HOME` environment variable should point to your Android SDK. 
For me: 

    ➜  ~  echo $ANDROID_HOME
    /Users/krukow/android/adt/sdk
    ➜  ~  ls $ANDROID_HOME
    ..
    drwxr-xr-x  12 krukow  staff   408B May 29 04:46 platform-tools/
    
 i.e. `$ANDROID_HOME` points to the directory which contains the `platform-tools` sub-directory.
 
The `JAVA_HOME` environment variable points to the Java JDK. For me:
 
    JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_45.jdk/Contents/Home

Also make sure you have a debug keystore for Android. The IDE you're using may have created one for you, but if not you can run this command to generate it.

```
keytool -genkey -v -keystore ~/.android/debug.keystore \
  -alias androiddebugkey -storepass android \
  -keypass android -keyalg RSA -keysize 2048 \
  -validity 10000 \
  -dname "CN=Android Debug,O=Android,C=US"
```


### Devices, devices, devices
You need an Android emulator, simulator or real devices. When using a real device, your Android device should be connected via USB and allow USB-debugging. For iOS you need to enable development on the device in XCode and enable UIAutomation in the Developer settings menu under Settings. See also: [http://developer.xamarin.com/guides/testcloud/calabash/working-with/testing-on-devices/](http://developer.xamarin.com/guides/testcloud/calabash/working-with/testing-on-devices/)

I recommend you try out the fast Xamarin Android Player: [https://xamarin.com/android-player](https://xamarin.com/android-player).


# Calabash 2.0
The tests use a pre-release of Calabash 2.0 which will ship a final 2.0 very soon. Once 2.0 ships finally you'll just install it by running a curl command described here: 
[https://github.com/calabash/install](https://github.com/calabash/install).

Right just install from source: [https://github.com/calabash/calabash](calabash/calabash).

## Scaffold a project

The end result is present in the `calabash-sample` directory. Here is how we got there. You can also watch the screencast recording from the Agile 2015 talk:
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
