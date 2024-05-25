package SDET_CAPSTON.SDET_CAPSTON;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class NSEIindia {
	WebDriver driver;
	ExtentReports extentreport;
	ExtentTest extenttest;

	@SuppressWarnings("deprecation")
	@BeforeMethod
	public void setup() {
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"ExtentReportResults.html");
		extentreport = new ExtentReports();
		extentreport.attachReporter(htmlReporter);

//		WebDriverManager.edgedriver().setup();
//		driver = new EdgeDriver();
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		

		driver.manage().window().maximize();
		// Open NSE India website
		driver.get("https://www.nseindia.com/");
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);

	}

	@Test
	public void test() {
		// Define the stock you want to search
		String stockName = "TATAMOTORS";
		String Low52 = "509.10";
		String High52 = "1,065.60";
		extenttest = extentreport.createTest("NSE India Stock Test", "Verify 52 Weeks High and Low Prices");
		
	
		// Find the search input box and enter the stock name
		WebElement searchBox = driver.findElement(By.id("header-search-input"));
		searchBox.clear();
		searchBox.sendKeys(stockName);

		searchBox.sendKeys(Keys.ENTER);
		// Wait for the search results to load

		// Find the link to the stock page and click on it
		WebElement stockLink = driver.findElement(By.linkText(stockName));
		stockLink.click();

		// Find and print 52 weeks high and low prices
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement highPrice = driver.findElement(By.xpath("//*[@id=\"week52highVal\"]"));
		WebElement lowPrice = driver.findElement(By.id("week52lowVal"));

		// To verify 52 Weeks High and Low price of stock
		if (High52.contains(highPrice.getText()) && Low52.contains(lowPrice.getText())) {

			extenttest.log(Status.PASS, "52 Weeks High: " + highPrice.getText());
			extenttest.log(Status.PASS, "52 Weeks Low: " + lowPrice.getText());
		} else {
			extenttest.log(Status.FAIL, "52 Weeks High Price and low price not matched with expected values");
		}

	}

	public void teardown() {
		driver.quit();
		extentreport.flush();
	}
}
