package testSauceDemo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginTests {

    WebDriver driver;

    @Before
    public void beforeTest()
    {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com");
        Assert.assertTrue(driver.findElement(By.className("login_logo")).isDisplayed());
    }

    @Test
    public void verifyValidLogin()
    {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.name("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        String expectedProductPageHeading  = "Products";
        String actualProductPageHeading  = driver.findElement(By.className("title")).getText();
        Assert.assertEquals(expectedProductPageHeading, actualProductPageHeading);

    }

    @Test
    public void verifyInvalidLogin()
    {

        driver.findElement(By.id("user-name")).sendKeys("standard_user_inv");
        driver.findElement(By.name("password")).sendKeys("secret_sauce_inv");
        driver.findElement(By.id("login-button")).click();

        String expectedLoginErrorMsg = "Epic sadface: Username and password do not match any user in this service";
        String actualLoginErrorMsg = driver.findElement(By.xpath("//h3[@data-test='error']")).getText();
        Assert.assertEquals(expectedLoginErrorMsg, actualLoginErrorMsg);
    }

    @Test
    public void verifyInvalidLoginWithNoData()
    {

        driver.findElement(By.id("login-button")).click();

        String expectedLoginErrorMsg = "Epic sadface: Username and password is required";
        String actualLoginErrorMsg = driver.findElement(By.xpath("//h3[@data-test='error']")).getText();
        Assert.assertEquals(expectedLoginErrorMsg, actualLoginErrorMsg);

    }

    @After
    public void afterTest()
    {
        driver.quit();
    }

}
