package com.macys.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.macys.pages.HomePage;
import com.macys.pages.SearchResultsPage;
import com.macys.util.Utility;
public class MacysTests {

	WebDriver driver;
	HomePage homePage;
	SearchResultsPage searchResultsPage ;
	
	@Parameters({"browserType","url"})
	@BeforeClass
	public void invokeBrowser(String browserType,String url)
	{
		if(browserType.equals("FF"))
		{
		
			  driver = new FirefoxDriver();
		}
		else if(browserType.equals("IE"))
		{
			System.setProperty("webdriver.ie.driver","IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}
		else if(browserType.equals("CH"))
		{
			  System.setProperty("webdriver.chrome.driver", "C:\\Users\\Sandhya\\Downloads\\chromedriver_win32\\chromedriver.exe");
			  driver  = new ChromeDriver();
		}
		driver.get(url);
		driver.manage().window().maximize();
		homePage = new HomePage(driver);
		homePage.closePopUp();
	}
	
	/*
	//Store the 2-d array in DataProvider
	@DataProvider(name="DP")
	public String[][] feedDP() throws BiffException, IOException
	{
		String data[][]= Utility.readExcel("inputdata.xls", "sanity");
		return data;
	}
	*/
	@Parameters({"expected","itemName"})
	@BeforeMethod
	public void verifyResultsPageTitle(String expected,String itemName)
	{
		searchResultsPage = homePage.enterSearchDetails(itemName);
		boolean result = searchResultsPage.verifySearchResultPageTitle(expected);
		Assert.assertTrue(result);
		
	}
	@Parameters({ "itemName","expectedItemCount"})
	//@Test(dataProvider="DP")
	@Test
	public void verifyItemsDescriptionCount( String itemName,String expectedItemCount)
	{
		 
	    int actual = searchResultsPage.getItemsDescriptionCount(itemName);
	    System.out.println("Actual count : "+actual);
		int expected =Integer.parseInt(expectedItemCount);
		System.out.println("Expected count : "+expected);
		Assert.assertEquals(expected,actual);
		
		
	}
	@AfterClass
	public void closeBrowser()
	{
		driver.close();
	}

}
