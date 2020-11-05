package testcase;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import utility.ProReader;

public class SamplePgm {
	ExtentReports report;
	ExtentTest log;
	WebDriver dr;
	SoftAssert sa;
	@BeforeTest(alwaysRun=true)
	public void setup()
	{
		report= new ExtentReports("./Reports/AutomationResult.html");
		log= report.startTest("Sanofi automation result");
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Prem Kumar\\Downloads\\chromedriver_win32_85\\chromedriver.exe");
		 dr= new ChromeDriver();
		 log.log(LogStatus.INFO, "Launching in chrome browser");
		dr.manage().window().maximize();
		dr.get(ProReader.getLocator("URL"));
		sa = new SoftAssert();
	}
	@Test(groups= {"sanity"})
	@Parameters({"firstname","lastname"})
	public void name(String fnm,String lnm)
	{
		String title=	dr.getTitle();
		
		//Assert.assertEquals(title, "Register");
		sa.assertEquals(title, "egister");
	
	WebElement fn=dr.findElement(By.xpath(ProReader.getLocator("firstname_xpath")));
	Assert.assertEquals(true, fn.isDisplayed());
		fn.clear();
		fn.sendKeys(fnm);
	String ph=	fn.getAttribute("placeholder");
	Assert.assertEquals(ph, "First name");
//	sa.assertEquals(ph, "First name");
		dr.findElement(By.xpath(ProReader.getLocator("lastname_xpath"))).sendKeys(lnm);
	//	sa.assertAll();
	}
	@Test(groups= {"smoke"})
	@Parameters({"Gender"})
	public void gender(String gen)
	{

		List<WebElement>	 rb=	dr.findElements(By.xpath(ProReader.getLocator("gender_xpath")));
		for(WebElement r:rb)
		{
		String val=	r.getAttribute("value");
		if(val.equalsIgnoreCase(gen))
		{
		r.click();	
		}
		}
	}
@Test(groups= {"smoke","sanity"})
	public void hobbies()
	{
	List<WebElement> cb =dr.findElements(By.xpath(ProReader.getLocator("hobby_xpath")));
	for(WebElement c:cb)
	{
	String val=	c.getAttribute("value");
	if(val.equalsIgnoreCase("cricket")||val.equalsIgnoreCase("hockey"))
	{
		c.click();
	}
	}
	}
@Test
	public void choosefile()
	{
	dr.findElement(By.id("imagesrc")).sendKeys("C:\\Users\\Prem Kumar\\Desktop\\modifydl.png");
	}
@Test
	public void password()
	{
	dr.findElement(By.id("firstpassword")).sendKeys("sdasd");
	dr.findElement(By.cssSelector("#secondpassword")).sendKeys("232234");
	}
@AfterMethod
	public void takeSs(ITestResult result) throws IOException
	{
		if(result.isSuccess())
		{
			log.log(LogStatus.PASS, "Test case got passed "+result.getName());
		}
		else
		{
			File sc=	((TakesScreenshot)dr).getScreenshotAs(OutputType.FILE);
			String filename="/screenshot/"+result.getName()+result.getStartMillis()+".png";
			FileUtils.copyFile(sc, new File(filename));
			log.log(LogStatus.FAIL, "Test case got failed "+result.getName()+ log.addScreenCapture(filename));
		}
	}
	@AfterTest
	public void tearDown()
	{
		dr.quit();
		report.endTest(log);
		report.flush();
	}


}
