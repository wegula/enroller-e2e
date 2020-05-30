package com.company.enroller.e2e;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class AuthorizationTestSuite {
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://localhost:8088/");
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

	}

	@Test
	public void SmokeTest() throws Exception {
		 assertEquals("System do zapisów na zajęcia", driver.findElement(By.xpath("//div[@id='app']/h1")).getText());
	}


	@Test
	public void testLoginLogout() throws Exception {
		driver.get("http://localhost:8088/");
		assertEquals("System do zapisów na zajęcia", driver.findElement(By.xpath("//div[@id='app']/h1")).getText());
		driver.findElement(By.xpath("//input[@type='text']")).click();
		driver.findElement(By.xpath("//input[@type='text']")).clear();
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys("user");
		driver.findElement(By.xpath("//input[@type='password']")).clear();
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("user");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.linkText("WYLOGUJ")).click();
		assertEquals("System do zapisów na zajęcia", driver.findElement(By.xpath("//div[@id='app']/h1")).getText());
	}

	@Test
	public void testRegisteringExistingUser() throws Exception {
		driver.get("http://localhost:8088/");
		driver.findElement(By.xpath("//div[@id='app']/div/button[2]")).click();
		driver.findElement(By.xpath("//input[@type='text']")).click();
		driver.findElement(By.xpath("//input[@type='text']")).clear();
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys("user");
		driver.findElement(By.xpath("//input[@type='password']")).clear();
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("user");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals("Błąd przy zakładaniu konta. Kod odpowiedzi: 409", driver.findElement(By.xpath("//div[@id='app']/div/div")).getText());
	}

	@Test
	public void testLoginUnregisteredUsed() throws Exception {
		driver.get("http://localhost:8088/");
		driver.findElement(By.xpath("//input[@type='text']")).click();
		driver.findElement(By.xpath("//input[@type='text']")).clear();
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys("user1");
		driver.findElement(By.xpath("//input[@type='password']")).clear();
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("user1");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals("Logowanie nieudane.", driver.findElement(By.xpath("//div[@id='app']/div/div")).getText());
	}

	@Test
	public void testLoginCreateMeetingLogout() throws Exception {
		driver.get("http://localhost:8088/");
		driver.findElement(By.xpath("//input[@type='text']")).click();
		driver.findElement(By.xpath("//input[@type='text']")).clear();
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys("user");
		driver.findElement(By.xpath("//input[@type='password']")).clear();
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("user");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//div[@id='app']/div/div/div/button")).click();
		driver.findElement(By.xpath("//input[@type='text']")).click();
		driver.findElement(By.xpath("//input[@type='text']")).clear();
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys("spotkanie");
		driver.findElement(By.xpath("//div[@id='app']/div/div/div/form/button")).click();
		driver.findElement(By.linkText("WYLOGUJ")).click();
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

}
