package testSauceDemo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BasketTests {

    WebDriver driver;

    @Before
    public void beforeTest()
    {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com");
        Assert.assertTrue(driver.findElement(By.className("login_logo")).isDisplayed());
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.name("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        String expectedProductPageHeading  = "Products";
        String actualProductPageHeading  = driver.findElement(By.className("title")).getText();
        Assert.assertEquals(expectedProductPageHeading, actualProductPageHeading);
        driver.findElement(By.xpath("//div[text()='Sauce Labs Backpack']")).click();
        Assert.assertTrue(driver.findElement(By.id("back-to-products")).isDisplayed());
        driver.findElement(By.name("add-to-cart-sauce-labs-backpack")).click();
        Assert.assertTrue(driver.findElement(By.className("shopping_cart_badge")).getText().contains("1"));
        driver.findElement(By.className("shopping_cart_badge")).click();

        String expectedBagPageHeading = "Your Cart";
        String actualBagPageHeading = driver.findElement(By.className("title")).getText();
        Assert.assertEquals(expectedBagPageHeading, actualBagPageHeading);

    }

    @Test
    public void verifyAddProductToBag()
    {
        String expectedProductName = "Sauce Labs Backpack";
        String actualProductName  = driver.findElement(By.className("inventory_item_name")).getText();
        Assert.assertEquals(expectedProductName, actualProductName);
    }

    @Test
    public void verifyRemoveProductFromBag()
    {
        driver.findElement(By.id("remove-sauce-labs-backpack")).click();
        Assert.assertFalse(driver.findElement(By.className("shopping_cart_link")).getText().contains("1"));

    }

    @Test
    public void verifyMultipleItemsInBag()
    {
        driver.findElement(By.name("continue-shopping")).click();
        String expectedProductPageHeading  = "Products";
        String actualProductPageHeading  = driver.findElement(By.className("title")).getText();
        Assert.assertEquals(expectedProductPageHeading, actualProductPageHeading);
        driver.findElement(By.xpath("//div[text()='Sauce Labs Bike Light']")).click();
        Assert.assertTrue(driver.findElement(By.id("back-to-products")).isDisplayed());
        driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();
        Assert.assertTrue(driver.findElement(By.className("shopping_cart_badge")).getText().contains("2"));
        driver.findElement(By.className("shopping_cart_badge")).click();
        String expectedBagPageHeading = "Your Cart";
        String actualBagPageHeading = driver.findElement(By.className("title")).getText();
        Assert.assertEquals(expectedBagPageHeading, actualBagPageHeading);

        Assert.assertTrue(driver.findElement(By.xpath("//div[text()='Sauce Labs Backpack']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//div[text()='Sauce Labs Bike Light']")).isDisplayed());
    }

    @After
    public void afterTest()
    {
        driver.quit();
    }
}
