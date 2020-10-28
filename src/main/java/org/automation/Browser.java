package org.automation;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

// класс отвечающий за настройку веб-дрфйвера
public class Browser implements WrapsDriver {

  //поле для хранения экземпляра Browser класса
  private static Browser instance;
  private static WebDriver driver;
  private static Wait<WebDriver> wait;

  // приватный конструктор, чтобы реализовать синглтон и не позволить создавать new Btowser() вне этого класса
  //По примеру Степана
  private Browser() {
    System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
    // from here: https://stackoverflow.com/questions/26772793/org-openqa-selenium-unhandledalertexception-unexpected-alert-open
    // ПОзволяет настроить поведение драйвера. В нашем случае он нажимает на кнопку "ПРИНЯТЬ"
    // во всех АЛЕРТАХ которые будут показываться браузером
    DesiredCapabilities dc = new DesiredCapabilities();
    dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
    driver = new ChromeDriver(dc);
    // разворачиваем окно на весь экран
    driver.manage().window().maximize();
    // неявное ожидание браузера в 30 секунд
    driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    // Создаем и настраиваем объект для ЯВНЫХ ожиданий. Ждет 10 секунд, опрашивая ДОМ каздые 0.5 секунд
    wait = new WebDriverWait(driver, 30, 500)
        .withMessage("Element was not found in X seconds");
  }

  //Метод, который надо вызывать вместо new Browser(), чтобы получить экземпляр синглтора
  public static Browser getInstance() {
    if (instance == null || driver == null) {
      instance = new Browser();
    }
    return instance;
  }

 // @Override
  //Единственный метод интерфейса WrapsDriver который нужно реализовать (взято из примера Степана)
  public WebDriver getWrappedDriver() {
    return driver;
  }

  public void stopBrowser() {
    try {
      getInstance().getWrappedDriver().quit();
      getInstance().driver = null;
      instance = null;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void open(String url) {
    driver.get(url);
  }

  // from here: https://stackoverflow.com/questions/10660291/highlight-elements-in-webdriver-during-runtime
  // Подсвечиваем элемент красной рамкой
  public WebElement highlightElement(WebElement element) {
    if (driver instanceof JavascriptExecutor) {
      ((JavascriptExecutor) driver)
          .executeScript("arguments[0].style.border='3px solid red'", element);
    }
    return element;
  }


  // Ждет пока указанный элемент не появится на странице и не станет видимым (опрос элемента происходит в соответствии с настройками wait)
  public WebElement waitVisible(String xpathLocator) {
    //Ждем пока элемент не появится на странице (presenceOfElementLocated).
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathLocator)));
    //Ждем пока элемент не станет видимым на странице.
    WebElement el = wait
        .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathLocator)));
    //Этот метод - единственное место через kоторое мы обращаеся к элементам страницы. поэтому подсвечиваем только тут.
    el = highlightElement(el);
    return el;
  }
}
