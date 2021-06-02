package com.qa.opencart.pages;


public class DealsPage {

	String page = "Deals";
	int timeOut = 10;

	public void getPage() {
		System.out.println(page);
		System.out.println(timeOut);
		System.out.println("values are: " + page + ":" + timeOut);
	}

}
