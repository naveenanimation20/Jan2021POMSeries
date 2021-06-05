package com.qa.opencart.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	private static final Logger LOGGER = Logger.getLogger(String.valueOf(LoginPage.class));

	private WebDriver driver;
	private ElementUtil elementUtil;

	// 1. private By locators:
	private By username = By.id("input-email");
	private By password = By.id("input-password");
	private By loginButton = By.xpath("//input[@type='submit']");
	private By forgotPwdLink = By.xpath("//div[@class='form-group']//a[text()='Forgotten Password']");
	private By registerLink = By.linkText("Register");
	private By loginErrorMessg = By.cssSelector("div.alert.alert-danger.alert-dismissible");
	
	
	// 2. constructors
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}

	// 3. public Page actions (methods)
	@Step("getting login page title")
	public String getLoginPageTitle() {
		return elementUtil.waitForTitle(5, Constants.LOGIN_PAGE_TITLE);
	}

	@Step("getting login url title")
	public String getLoginPageUrl() {
		return elementUtil.getPageUrl();
	}

	@Step("getting forgot pwd is exist")
	public boolean isForgotPwdLinkExist() {
		return elementUtil.doIsDisplayed(forgotPwdLink);
	}

	@Step("login with username : {0} and password : {1}")
	public AccountsPage doLogin(String un, String pwd) {
		elementUtil.doSendKeys(username, un);
		elementUtil.doSendKeys(password, pwd);
		elementUtil.doClick(loginButton);
		return new AccountsPage(driver);
	}
	
	@Step("login with username : {0} and password : {1}")
	public boolean doLoginWrongData(String un, String pwd) {
		elementUtil.doSendKeys(username, un);
		elementUtil.doSendKeys(password, pwd);
		elementUtil.doClick(loginButton);
		return elementUtil.doIsDisplayed(loginErrorMessg);
	}
	
	@Step("navigating to registration page")
	public RegistrationPage navigateToRegisterPage() {
		elementUtil.doClick(registerLink);
		return new RegistrationPage(driver);
	}

}
