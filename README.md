# agile-2015-samples
Samples used for Agile 2015 conference talk on automated testing for mobile. These are just a very basic test for Calabash 2.0 and Appium. The intention is to show how easy it is to get started and write the first tests.

This repo contains two micro samples `calabash-sample` and `appium-sample`. This README describes how we got there. You can also watch the screencast recording from the Agile 2015 talk: [Automated Testing of Mobile Apps - Karl Krukow, Xamarin, Agile 2015, August, Washington DC](https://www.dropbox.com/s/jvjv2j7eogzb21x/agile-2015.mp4?dl=0).

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

Note: make sure you add this to your `~/.bash_profile` (even if you're using `zsh`) since Appium depends on it.

```
keytool -genkey -v -keystore ~/.android/debug.keystore \
  -alias androiddebugkey -storepass android \
  -keypass android -keyalg RSA -keysize 2048 \
  -validity 10000 \
  -dname "CN=Android Debug,O=Android,C=US"
```

### Devices, devices, devices
You need an Android emulator, simulator or real devices. When using a real device, your Android device should be connected via USB, enabled for development and allow USB debugging. For iOS you need to enable development on the device in XCode and enable UIAutomation in the Developer settings menu under Settings. See also: [http://developer.xamarin.com/guides/testcloud/calabash/working-with/testing-on-devices/](http://developer.xamarin.com/guides/testcloud/calabash/working-with/testing-on-devices/)

I recommend you try out the fast Xamarin Android Player: [https://xamarin.com/android-player](https://xamarin.com/android-player).


# Calabash 2.0
The tests use a pre-release of Calabash 2.0 which will ship a final 2.0 very soon. Once 2.0 is shipped, you'll just install it by running a curl command described here: [https://github.com/calabash/install](https://github.com/calabash/install).

Right now, just install from source: [https://github.com/calabash/calabash](calabash/calabash) (follow the instructions there) or you can install a non-published prerelease by running `gem install --local calabash-1.9.9.pre3.gem` from the main directory in this repo.

## Scaffold a project
The end result is present in the `calabash-sample` directory. Here is how we got there. 

We created a sample directory and ran `calabash generate` to scaffold the project: 

    mkdir calabash-sample
    cd calabash-sample
    calabash generate

To sanity check your installation run the sample:

    calabash run ../Superheroes.apk
    
If this is your first run, Calabash will tell you that you need to resign the app. Note that if this were your own app you wouldn't need to do this as you'd have the certificates used to sign the app present and you could tell Calabash to just use this. For this app we just resign it and move on:

```
Test server does not exist. Creating test server.
/Users/krukow/samples/agile-2015-samples/Superheroes.apk is not signed with any of the available keystores.
Tried the following keystores:
/Users/krukow/.android/debug.keystore

You can resign the app with /Users/krukow/.android/debug.keystore by running:
      calabash resign /Users/krukow/samples/agile-2015-samples/Superheroes.apk

Notice that resigning an app might break some functionality.
Getting a copy of the certificate used when the app was build will in general be more reliable.
/Users/krukow/samples/agile-2015-samples/Superheroes.apk is not signed with any of the available keystores
```

So we do that:

``` 
calabash resign ../Superheroes.apk
Signing using the signature algorithm: 'SHA1withRSA'
Signing using the digest algorithm: 'SHA1'
```

And we can run the empty (scaffolded test) `calabash run ../Superheroes.apk`.

## Write a test!
We'll use this sample feature:

```gherkin
Feature: Playing the game
  Scenario: Tapping a letter
    Given I'm on the main page
    And I start playing
    Then an image of a super hero is shown

    When I choose a letter
    Then that letter becomes selected
```

How would I go about actually testing this? You can see the details in the talk (fast forward to about: 00:26:20).

You'd use either (or both) the Calabash interactive console or an inspection tool like `uiautomatorviewer`.

To run console: `calabash console ../Superheroes.apk`

````
calabash console ../Superheroes.apk
Running irb...

#             =>  Useful Methods  <=              #
>     ids => List all the visible ids.
> classes => List all the visible classes.
>    tree => The app's visible view hierarchy.
>    copy => Copy console commands to the Clipboard.
>   clear => Clear the console.
> verbose => Turn debug logging on.
>   quiet => Turn debug logging off.

Calabash 1.9.9.pre3 says: 'Non Satis Scire'
irb(main):001:0> install_app
true
irb(main):002:0> start_app
true
irb(main):003:0> tree
[DecorView]
  [LinearLayout]
    [FrameLayout] [id:content]
      [SplashScreenCustom]
true
irb(main):004:0>
````

The Calabash console is a great interactive environment,
but you can also use uiautomatorviewer: `$ANDROID_HOME/tools/uiautomatorviewer`  if you prefer a point-and-click inspector.

Check out the sample code in the `calabash-sample` repo and watch the video from about `00:26:20` to see how we got there. 

# Appium

## Installation
[http://appium.io](http://appium.io) Download the app and launch it.

Run the doctor and make sure you've set (.bash_profile).

Select the android logo (the circle should be under Android) and configure it with the path to the `Superheroes.apk` app. Also set deviceName to blank (this is needed for some reason for the inspector to work).

Make sure you stop appium when reconfiguring.


## Scaffold a project

We'll use Java and jUnit. Easiest is to use JetBrains' IntelliJ.
New Project > Maven > Archetype: maven-archetype-quickstart or install maven and run this from the command line

```
mvn archetype:generate -DgroupId=com.xamarin.sample \
      -DartifactId=appium-sample \
      -DarchetypeArtifactId=maven-archetype-quickstart \
      -DinteractiveMode=false
```

to generate the directory `appium-sample` (if you actually run this, pick another name as the directory already exists). 


### Update dependencies
Update to jUnit 4 by editing the `pom.xml` file and add `4.11` for the jUnit version. 

Add Appium Java Client

```
<dependency>
  <groupId>io.appium</groupId>
  <artifactId>java-client</artifactId>
  <version>3.1.0</version>
  <scope>test</scope>
</dependency>
```

### Use the Appium inspector to scaffold a test...
Create a Class in the test package with empty `@Before` `setUp` method and a `@After` `tearDown` method and an empty `@Test` method.

See the video how we use the record button to scaffold the `DesiredCapabilities` to get started. Or copy the code from the `appium-sample` in this repo.

Note that the scaffold from the Appium inspector tool is not accurate and needs tweaking: 

- Create an instance var `AppiumDriver<WebElement> wd;`
- Import deps and replace `AppiumDriver` with `AndroidDriver` since we're running and Android test.
- add `capabilities.setCapability("deviceName", "");`
- replace `wd.close()` with `wd.quit()`

### A few gotcha's

Restart the server if you get an error about existing session. Stop the server and in Settings. Check the "Override existing sessions" checkbox.

Remember to stop the Appium server before reconfiguring. 

### Write a test!
How would I go about actually testing the same thing as in the Calabash test?

Use the inspector to find ids. See it in the video above or look at this snipplet using a selenium style API to locate elements: 

```java
 wd.findElement(By.id("com.icontrivia.GuessTheSuperheroes:id/btnplay")).click();
 WebElement superHero = wd.findElement(By.id("com.icontrivia.GuessTheSuperheroes:id/imageView"));
 assertNotNull(superHero);
 wd.findElement(By.id("com.icontrivia.GuessTheSuperheroes:id/button")).click();

 AndroidElement firstSolutionRow = (AndroidElement)wd.findElement(By.id("com.icontrivia.GuessTheSuperheroes:id/gui_answer1"));
 String selectedText = firstSolutionRow.findElementByClassName("android.widget.Button").getText();
 assertNotNull(selectedText);
```
