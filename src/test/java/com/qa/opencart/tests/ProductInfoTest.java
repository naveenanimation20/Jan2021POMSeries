package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;

public class ProductInfoTest extends BaseTest {

	SoftAssert softAssert = new SoftAssert();

	@BeforeClass
	public void productInfoSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@DataProvider
	public Object[][] searchData() {
		return new Object[][] {{"Macbook"},
								{"iMac"},
								{"iPhone"}};
	}
	
	@Test(dataProvider = "searchData")
	public void productCountTest(String productName) {
		searchResPage = accPage.doSearch(productName);
		Assert.assertTrue(searchResPage.getProductResultsCount()>0);
	}

	@Test(enabled = false)
	public void productInfoHeaderTest() {
		searchResPage = accPage.doSearch("iMac");
		productInfoPage = searchResPage.selectProductFromResults("iMac");
		Assert.assertEquals(productInfoPage.getProductHeaderText(), "iMac");
	}

	@Test(enabled = false)
	public void productImagesTest() {
		searchResPage = accPage.doSearch("Macbook");
		productInfoPage = searchResPage.selectProductFromResults("MacBook Pro");
		Assert.assertTrue(productInfoPage.getProductImagesCount() == 4);
	}

	@Test(enabled = false)
	public void getProductInfoTest() {
		searchResPage = accPage.doSearch("Macbook");
		productInfoPage = searchResPage.selectProductFromResults("MacBook Pro");
		Map<String, String> actProductMetaData = productInfoPage.getProductInformation();
		actProductMetaData.forEach((k, v) -> System.out.println(k + " : " + v));

		softAssert.assertEquals(actProductMetaData.get("name"), "MacBook Pro");
		softAssert.assertEquals(actProductMetaData.get("Brand"), "Apple");
		softAssert.assertEquals(actProductMetaData.get("Availability"), "Out Of Stock");
		softAssert.assertEquals(actProductMetaData.get("price"), "$2,000.00");

		softAssert.assertAll();
	}

}
