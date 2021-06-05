package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.listeners.TestAllureListener;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.CommonsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegistrationPage;
import com.qa.opencart.pages.SearchResultPage;

@Listeners(TestAllureListener.class)
public class BaseTest {

	DriverFactory df;
	public WebDriver driver;
	public Properties prop;

	public LoginPage loginPage;
	public AccountsPage accPage;
	public SearchResultPage searchResPage;
	public ProductInfoPage productInfoPage;
	public RegistrationPage registrationPage;
	public CommonsPage commonsPage;

	@Parameters({"browser", "browserversion"})
	@BeforeTest
	public void setUp(String browserName, String browserVersion) {
		df = new DriverFactory();
		prop = df.init_prop();
		prop.setProperty("browser", browserName);
		driver = df.init_driver(browserName, browserVersion);
		loginPage = new LoginPage(driver);
		commonsPage = new CommonsPage(driver);
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}