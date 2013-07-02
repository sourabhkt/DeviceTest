
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DeviceTest {

	public static void main(String[] args) throws MalformedURLException, InterruptedException
	{
		WebDriver driver = null;
		DesiredCapabilities cap = null;
		List<String> devices = null;
		
		List<String> android = Arrays.asList(//"Samsung Galaxy S",
//				"Samsung Galaxy S II",
//				"Samsung Galaxy S III",
//				"Samsung Galaxy Note",
//				"Samsung Galaxy Note II",
//				"Samsung Galaxy Nexus",
//				"Motorola Droid Razr",
//				"Motorola Droid 4",
//				//"Motorola Photon 4G",
//				"Motorola Atrix HD",
//				"Motorola Razr",
//				"Motorola Razr Maxx HD",
//				"Amazon Kindle Fire 2",
				"Amazon Kindle Fire HD 8.9",
				"Google Nexus 7",
				"Samsung Galaxy Note 10.1",
				"Samsung Galaxy Tab 2 10.1",
				"HTC Wildfire",
				"HTC Evo 3D",
				"HTC One X",
				"Sony Xperia Tipo",
				"LG Optimus 3D",
				"LG Nexus 4"
				);
		
		List<String> ipad = Arrays.asList(
//				"iPad 2 (5.0)",
//				"iPad 3rd",
//				"iPad 3rd (6.0)",
//				"iPad Mini"
				);
		
		List<String> iphone = Arrays.asList(//"iPhone 3GS",
				//"iPhone 4",
//				"iPhone 4S",
//				"iPhone 4S (6.0)",
//				"iPhone 5"
				);
				 
		HashMap<DesiredCapabilities, List<String>> iteration = new HashMap<DesiredCapabilities, List<String>>();
		
		iteration.put(new DesiredCapabilities().android(), android);
		iteration.put(new DesiredCapabilities().ipad(), ipad);
		iteration.put(new DesiredCapabilities().iphone(), iphone);
		
		for(Entry<DesiredCapabilities, List<String>> i : iteration.entrySet())
		{
				String status = " F A I L E D ";
				cap = i.getKey();
				devices = i.getValue();
				
				for(String device : devices)
				{
					try{
					cap.setCapability("device", device);
					
					System.out.println("---------------------- "+device + " ---------------------- ");
					
					driver = new RemoteWebDriver(new URL("http://username:authkey@wtfhub.browserstack.com:4444/wd/hub"),cap);
					WebDriverWait wait = new WebDriverWait(driver, 15);
					driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
					
					System.out.println("Navigating to google..");
					driver.navigate().to("http://www.google.com");
					
					System.out.println("Trying for screenshot..");
					driver = new Augmenter().augment(driver);
					File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
					try {
					FileUtils.copyFile(srcFile, new File("/Users/sourabh/selenium/"+device+".png"));
					} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
					
					System.out.println("Saying hello to google..");
					driver.findElement(By.name("q")).sendKeys("Hello Google...!!!");
					driver.findElement(By.name("btnG")).submit();
		
					//driver.findElement(By.cssSelector("div#res li a")).click();
					//wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("home_page_submit")));
					
					//System.out.println(driver.findElement(By.id("home_page_submit")).isDisplayed());
					//Assert.assertTrue(driver.findElement(By.id("home_page_submit")).isDisplayed());
					
					
					if(driver!=null)
						driver.quit();
					
					status = " P A S S E D ";
					System.out.println("Going to sleep for 10 seconds.");
					Thread.sleep(10000);
					}
					catch(Exception ex)
					{
						System.out.println("Exception : ");ex.printStackTrace();
						System.out.println("Going to sleep for 95 seconds.");
						Thread.sleep(95000);
					}
					finally
					{
						System.out.println("Quitting the driver..");
						System.out.println(System.lineSeparator()+status+System.lineSeparator());
					}
				}	
		}
	}
}
