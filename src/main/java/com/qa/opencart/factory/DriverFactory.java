package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * 
 * @author naveenautomationlabs
 *
 */
public class DriverFactory {
	private static final Logger LOGGER = Logger.getLogger(String.valueOf(DriverFactory.class));

	WebDriver driver;
	Properties prop;
	private OptionsManager optionsManager;

	public static String highlight = null;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

	/**
	 * 
	 * @param browserName
	 * @return this method will return webdriver
	 */
	public WebDriver init_driver(String browserName, String browserVersion) {

		highlight = prop.getProperty("highlight");
		optionsManager = new OptionsManager(prop);

		// String browserName = prop.getProperty("browser").trim();
		System.out.println("browser name is : " + browserName);
		LOGGER.info("browser name is : " + browserName);

		if (browserName.equalsIgnoreCase("chrome")) {
			// System.setProperty("webdriver.chrome.verboseLogging", "true");
			WebDriverManager.chromedriver().setup();

			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				LOGGER.info("remote is true..running it on GRID......");
				init_remoteDriver(browserName, browserVersion);
			} else {
				LOGGER.info("remote is false..running it on local.....");
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			}

		} else if (browserName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.firefox.verboseLogging", "true");
			WebDriverManager.firefoxdriver().setup();

			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				init_remoteDriver(browserName, browserVersion);
			} else {
				tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			}

		} else if (browserName.equalsIgnoreCase("safari")) {
			System.setProperty("webdriver.safari.verboseLogging", "true");
			tlDriver.set(new SafariDriver());

		} else {
			System.out.println("please pass the correct browser : " + browserName);
		}

		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().get(prop.getProperty("url").trim());

		return getDriver();
	}

	private void init_remoteDriver(String browserName, String browserVersion) {

		System.out.println("Running test on remote grid server: " + browserName);

		if (browserName.equals("chrome")) {
//			DesiredCapabilities cap = DesiredCapabilities.chrome();
//			cap.setCapability("browserName", browserName);
//			cap.setCapability("browserVersion", browserVersion);
//			cap.setCapability("enableVNC", true);
//			cap.setCapability("enableVideo", true);
//			cap.setCapability(ChromeOptions.CAPABILITY, optionsManager.getChromeOptions());

			ChromeOptions co = optionsManager.getChromeOptions();
			co.setCapability("browserName", browserName);
			co.setCapability("browserVersion", browserVersion);
			co.setCapability("enableVNC", true);

			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), co));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} else if (browserName.equals("firefox")) {
//			DesiredCapabilities cap = DesiredCapabilities.firefox();
//			cap.setCapability("browserName", browserName);
//			cap.setCapability("browserVersion", browserVersion);
//			cap.setCapability("enableVNC", true);
//			cap.setCapability(FirefoxOptions.FIREFOX_OPTIONS, optionsManager.getFirefoxOptions());

			FirefoxOptions fo = optionsManager.getFirefoxOptions();
			fo.setCapability("browserName", browserName);
			fo.setCapability("browserVersion", browserVersion);
			fo.setCapability("enableVNC", true);

			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), fo));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

	}

	public static synchronized WebDriver getDriver() {
		return tlDriver.get();
	}

	/**
	 * 
	 * @return this method will return Properties Object
	 */
	public Properties init_prop() {
		FileInputStream ip = null;
		prop = new Properties();

		String env = System.getProperty("env");
		if (env == null) {
			System.out.println("Running on Environment : --> on PROD");
			try {
				ip = new FileInputStream("./src/test/resources/config/config.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Running on Environment : --> " + env);
			try {
				switch (env) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				case "stage":
					ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
					break;
				default:
					break;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}
		try {
			prop.load(ip);
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	/**
	 * take screenshot
	 */

	public String getScreenshot() {
		File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshots/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileUtils.copyFile(src, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}

}
