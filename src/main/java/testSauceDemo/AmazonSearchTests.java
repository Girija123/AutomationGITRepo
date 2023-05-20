package testSauceDemo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.checkerframework.checker.units.qual.C;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class AmazonSearchTests {

    WebDriver browser;

    @Before
    public void beforeTest()
    {
        WebDriverManager.chromedriver().setup();
        browser = new ChromeDriver();
        browser.get("https://www.amazon.co.uk/");
        browser.manage().window().maximize();
    }

    public void verifyInvalidSearch()
    {

    }

    @Test
    public void verifyValidSearch()
    {
        new Select(browser.findElement(By.id("searchDropdownBox"))).selectByVisibleText("Books");
        browser.findElement(By.id("twotabsearchtextbox")).sendKeys("Fiction Books");
        browser.findElement(By.id("nav-search-submit-button")).click();
        Assert.assertTrue(browser.findElement(By.xpath("//span[@data-component-type='s-result-info-bar']/div/h1/div/div/div/div/span[3]"))
                          .getText().contains("fiction books"));
    }

    @After
    public void afterTest()
    {
        browser.quit();
    }

}
