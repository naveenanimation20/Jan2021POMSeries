package com.qa.opencart.tests;

import java.util.Collections;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.Error;

public class AccountsPageTest extends BaseTest {

	@BeforeClass
	public void accPageSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@Test
	public void accPageTitleTest() {
		String title = accPage.getAccPageTitle();
		System.out.println("acc page title is: " + title);
		Assert.assertEquals(title, Constants.ACCOUNTS_PAGE_TITLE, Error.ACC_PAGE_TITLE_ERROR);
	}

	@Test
	public void accPageHeaderTest() {
		String header = accPage.getAccPageHeader();
		System.out.println("acc page header is : " + header);
		Assert.assertEquals(header, Constants.ACCOUNTS_PAGE_HEADER, Error.ACC_PAGE_HEADER_ERROR);
	}

	@Test
	public void accSectionsListTest() {
		List<String> secList = accPage.getAccountSectionsList();
		secList.stream().forEach(e -> System.out.println(e));
		Collections.sort(Constants.EXP_ACC_SEC_LIST);
		Assert.assertEquals(secList, Constants.EXP_ACC_SEC_LIST);
	}

	@Test
	public void logoutLinkTest() {
		Assert.assertTrue(accPage.isLogoutExist(), Error.LOGOUT_LINK_NOT_PRESENT);
	}

}
