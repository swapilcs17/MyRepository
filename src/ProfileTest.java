import java.sql.Driver;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;


public class ProfileTest {
	public static WebDriver driver;
	String portfolioName="Xavi7";
	String newPorfolioName="Iniesta1";
	@Test
	public void createProfileTest() throws InterruptedException {
		String browser = "Mozilla";
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
		Thread.sleep(5000);
		driver.findElement(By.xpath("//*[@id='createPortfolio']")).click();
		driver.findElement(By.xpath("//*[@id='create']")).clear();
		driver.findElement(By.xpath("//*[@id='create']")).sendKeys(portfolioName);
		driver.findElement(By.xpath("//*[@id='createPortfolioButton']")).click();
		
		isElementPresent("//select[@id='portfolioid']/option[text()='"+portfolioName+"']",7);
		
		WebElement dropDown = driver.findElement(By.xpath("//*[@id='portfolioid']"));
		Select select = new Select(dropDown);
		String selectedName = select.getFirstSelectedOption().getText();
		System.out.println(selectedName);
		String stockText = driver.findElement(By.xpath("//table[@id='stock']/tbody")).getText().trim();
		Assert.assertEquals(stockText,"");
		Assert.assertEquals(selectedName,portfolioName);
		
		
	}
	
	@Test(priority=2,dependsOnMethods={"createProfileTest"})
	public void renamePorfolio() throws InterruptedException {
		
		isElementPresent("//*[@id='portfolioid']",7);
		driver.findElement(By.xpath("//*[@id='portfolioid']")).sendKeys(portfolioName);
		isElementPresent("//*[@id='portfolioid']",5);
		driver.findElement(By.xpath("//*[@id='renamePortfolio']")).click();
		driver.findElement(By.xpath("//*[@id='rename']")).clear();
		driver.findElement(By.xpath("//*[@id='rename']")).sendKeys(newPorfolioName);
		driver.findElement(By.xpath("//*[@id='renamePortfolioButton']")).click();
		
		isElementPresent("//select[@id='portfolioid']/option[text()='"+newPorfolioName+"']",7);
		

		WebElement dropDown = driver.findElement(By.xpath("//*[@id='portfolioid']"));
		Select select = new Select(dropDown);
		String selectedName = select.getFirstSelectedOption().getText();
		System.out.println("New Porfolio name is:- "+selectedName);
				
	}
	
	@Test(priority=3,dependsOnMethods={"createProfileTest"})
	public void deleteProfileTest() {
		isElementPresent("//*[@id='portfolioid']",5);
		driver.findElement(By.xpath("//*[@id='portfolioid']")).sendKeys(newPorfolioName);
		isElementPresent("//*[@id='portfolioid']",5);
		driver.findElement(By.xpath("//*[@id='deletePortfolio']")).click();
		Alert alt = driver.switchTo().alert();
		alt.accept();
		isElementPresent("//*[@id='portfolioid']",5);
		System.out.println("Profolio "+newPorfolioName+" deleted successfully");
		
	}
	
	
	public boolean isElementPresent(String xpathExp, int time) {
		driver.manage().timeouts().implicitlyWait(time,TimeUnit.SECONDS);
		int s = driver.findElements(By.xpath(xpathExp)).size();
		if(s>0) {
			return true;
		}
		else{
			return false;
		}
	
	}

}
