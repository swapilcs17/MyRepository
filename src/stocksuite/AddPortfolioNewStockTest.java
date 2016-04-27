package stocksuite;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.sun.xml.internal.ws.api.ha.StickyFeature;

public class AddPortfolioNewStockTest {
	
	public static WebDriver driver;
	String portfolioName="FC Barcelona";
	String companyName = "Tata Motors Ltd.";
	String date = "24/04/2017";
	@Test
	public void addPorfolioTest() throws InterruptedException {
		
		//login
		String browser = "chrome";
		if(browser.equalsIgnoreCase("Mozilla")) {
			driver = new FirefoxDriver();
		}else if(browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "C:\\Chrome\\chromedriver.exe");
			driver = new ChromeDriver();
		}else if(browser.equals("ie")) {
			System.setProperty("webdriver.ie.driver", "C:\\IE\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		driver.get("http://in.rediff.com/");
		driver.findElement(By.xpath("//*[@id='homewrapper']/div[5]/a[3]/div/u")).click();
		driver.findElement(By.xpath("//*[@id='wrapper']/div[2]/ul/li[2]/a")).click();
		driver.findElement(By.xpath("//*[@id='useremail']")).sendKeys("hrutwik_007@rediffmail.com");
		driver.findElement(By.xpath("//*[@id='emailsubmit']")).click();
		driver.findElement(By.xpath("//*[@id='userpass']")).sendKeys("0msair@m");
		driver.findElement(By.id("loginsubmit")).click();
		
		//select porfolio
		
		driver.findElement(By.xpath("//*[@id='portfolioid']")).sendKeys(portfolioName);
		isMyElementPresent("//*[@id='portfolioid']",5);
		driver.findElement(By.xpath("//*[@id='addStock']")).click();
		isMyElementPresent("//*[@id='addstockname']",5);
		
		//write in adding stock
		
		driver.findElement(By.xpath("//*[@id='addstockname']")).sendKeys("Tata");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//div[@id='ajax_listOfOptions']/div[text()='"+companyName+"']")).click();
		
		//select date
		driver.findElement(By.xpath("//*[@id='stockPurchaseDate']")).click();
		Date currentDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date dateToBeSelected = null;
		try {
		dateToBeSelected = formatter.parse(date);
		}catch(Exception e) {
			e.printStackTrace();
		}
		String month = new SimpleDateFormat("MMMM").format(dateToBeSelected);
		String day = new SimpleDateFormat("dd").format(dateToBeSelected);
		String year = new SimpleDateFormat("yyyy").format(dateToBeSelected);
		
		String expected = month+" "+year;
		
		while(true) {
			
			String displayed = driver.findElement(By.xpath("//*[@id='datepicker']/table/tbody/tr[1]/td[3]/div")).getText();
			if(expected.equals(displayed)) {
				break;
			}
			if(currentDate.after(dateToBeSelected))
				driver.findElement(By.xpath("//*[@id='datepicker']/table/tbody/tr[1]/td[2]/button")).click();
			else
				driver.findElement(By.xpath("//*[@id='datepicker']/table/tbody/tr[1]/td[4]/button")).click();
		}
		driver.findElement(By.xpath("//td[text()='"+day+"']")).click();
		driver.findElement(By.xpath("//*[@id='addstockqty']")).sendKeys("100");
		driver.findElement(By.xpath("//*[@id='addstockprice']")).sendKeys("10");
		driver.findElement(By.xpath("//*[@id='addStockButton']")).click();
		isMyElementPresent("//span[@id='companyname15783133']/a",5);
		System.out.println(driver.findElement(By.xpath("//a[text()='"+companyName+"']")).getText());
		System.out.println("Company name is:- "+companyName);
		
	}
	
	public boolean isMyElementPresent(String xpathExp,int time) {
		driver.manage().timeouts().implicitlyWait(time,TimeUnit.SECONDS);
		int i = driver.findElements(By.xpath(xpathExp)).size();
		if(i>0) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean isElementPresent(String xpathXpr){
		int s = driver.findElements(By.xpath(xpathXpr)).size();
		
		if(s>0)
			return true;
		else
			return false;
		
	}
	
	public void selectAjaxcompanyName(String xpath,String companyName2) {
		for(int i=0;i<companyName2.length()-3;i++) {
			char character = companyName2.charAt(i);
			driver.findElement(By.xpath("//*[@id='addstockname']")).sendKeys(String.valueOf(character));
			if(isElementPresent("//div[@id='ajax_listOfOptions']/div[text()="+companyName2+"]")) {
				driver.findElement(By.xpath("//div[@id='ajax_listOfOptions']/div[text()="+companyName2+"]")).click();
				break;
			}
		}
		driver.findElement(By.xpath("//div[@id='ajax_listOfOptions']/div[text()="+companyName2+"]")).click();
		
	}
	
	
	

	
}
