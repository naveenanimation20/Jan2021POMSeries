package com.qa.opencart.tests;

import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class CommonFeaturesTest extends BaseTest {

	@Test
	public void parentMenuTest() {
		List<String> actualMenuList = commonsPage.getMenuLinksList();
		System.out.println(actualMenuList);

		// [Desktops, Laptops & Notebooks, Components, Tablets, Software, Phones & PDAs,
		// Cameras, MP3 Players]

	}

	@DataProvider
	public Object[][] menuData() {
		return new Object[][] { { "Components", "Monitors" }, 
								{ "Desktop", "Mac" } };

	}

	@Test(dataProvider = "menuData")
	public void selectMonitorsTest(String parentMenu, String subMenu) {
		commonsPage.selectSubMenu(parentMenu, subMenu);
	}

}
