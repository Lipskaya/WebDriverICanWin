package org.automation.page;

import org.automation.Browser;
import org.openqa.selenium.WebElement;

public class PastebinPage {
    private Browser browser;
    private static final String PASTEBIN_PAGE_URL = "https://pastebin.com/";
    private static final String POST_FORM_XPATH = "//*[@id='postform-text']";

    private static final String PAST_EXPIRATION_XPATH = "//div[@class='form-group field-postform-expiration']//span[contains(@class, 'select2-selection--single')]";
    private static final String TEN_MINUTES_XPATH = "//li[text() ='10 Minutes']";
    private static final String FORM_NAME_XPATH ="//*[@id='postform-name']";
    private static final String POST_FORM_VALUE = "Hello from WebDriver";
    private static final String FORM_NAME_VALUE = "helloweb";
    private static final String SUBMIT_BUTTON_XPATH="//button[text()='Create New Paste']";

    public PastebinPage() {
        browser = Browser.getInstance();
    }
//    public WebDriver getDriver() {
//        return browser.getWrappedDriver();
//    }


    public void openPage(){
        browser.open(PASTEBIN_PAGE_URL);
    }

    public void fillCode(){
        waitVisible(POST_FORM_XPATH).sendKeys(POST_FORM_VALUE);
    }

    public void fillExpiration(){
        waitVisible(PAST_EXPIRATION_XPATH).click();
        waitVisible(TEN_MINUTES_XPATH).click();
    }

    public void fillTitle(){
        waitVisible(FORM_NAME_XPATH).sendKeys(FORM_NAME_VALUE);
    }

    public void submitForm(){
        waitVisible(SUBMIT_BUTTON_XPATH).click();
    }



    // Ждет пока указанный элемент не появится на странице и не станет видимым (опрос элемента происходит в соответствии с настройками wait)
    private WebElement waitVisible(String xpathLocator) {
        WebElement el = browser.waitVisible(xpathLocator);
        return el;
    }

    public boolean isTitleCorrect(){
        boolean result = false;
        try {
            //генерим динамический xPath для переданного в параметре метода имени папки
            String xpathString = "//h1";
            waitVisible(xpathString);
            result = FORM_NAME_VALUE.equals(waitVisible(xpathString).getText());
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public boolean isExpirationCorrect(){
        boolean result = false;

        try {
            //генерим динамический xPath для переданного в параметре метода имени папки
            String xpathString = "//div[@class='expire']";
            waitVisible(xpathString);
            result = "10 MIN".equals(waitVisible(xpathString).getText().trim());
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public boolean isCodeCorrect(){
        boolean result = false;

        try {
            //генерим динамический xPath для переданного в параметре метода имени папки
            String xpathString = "//textarea[@class='textarea']";
            waitVisible(xpathString);
            result = POST_FORM_VALUE.equals(waitVisible(xpathString).getText().trim());
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }
}
