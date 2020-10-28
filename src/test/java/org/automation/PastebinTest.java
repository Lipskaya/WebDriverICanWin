package org.automation;

import org.automation.page.PastebinPage;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class PastebinTest {
    private static Browser browser;

    @BeforeAll//открывает браузер перед выполнением сценария suite
    public static void setup() {
        browser = Browser.getInstance();
    }

    @AfterAll
    public static void tearDown() {
        browser.stopBrowser();
    }
    @Test
    public void baseTest(){

        PastebinPage page = new PastebinPage();
        page.openPage();
        page.fillCode();
        page.fillExpiration();
        page.fillTitle();
        page.submitForm();

        Assert.assertTrue(page.isTitleCorrect());
        Assert.assertTrue(page.isExpirationCorrect());
        Assert.assertTrue(page.isCodeCorrect());

    }

}
