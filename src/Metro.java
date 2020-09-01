import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.html.HTMLElement;

import java.util.ArrayList;
import java.util.List;

public class Metro {
    public static void main(String[] args) {
        String url = "https://msk.metro-cc.ru/";
        ParseByHtmlUnit(url);
    }

    public static void ParseBySelenium(String url) {
        //System.setProperty("webdriver.chrome.driver", "D:\\Program Files\\diplom\\LIBS\\chromedriver_win32\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\mironov.matvey\\Documents\\GitHub\\diplom\\LIBS\\chromedriver_win32\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        new WebDriverWait(driver, 5L);

        try {
            driver.get("https://twitter.com/search?q=%D1%81%D0%B1%D0%B5%D1%80%D0%B1%D0%B0%D0%BD%D0%BA&src=typed_query");
            Thread.sleep(6000); //ждем 5 сек , пока рагрузится страничка
            JavascriptExecutor jse = (JavascriptExecutor)driver;

            String page1;
            List<String> rawTweets;
            ArrayList<String> sumTweets= new ArrayList<>();
            page1 = driver.getPageSource();

            List<WebElement> elemList= driver.findElements(By.className("css-1dbjc4n")); //"css-1dbjc4n r-my5ep6 r-qklmqi r-1adg3ll"
            System.out.println("getPageSource: "+driver.getPageSource());

            //class="css-901oao r-hkyrab r-1qd0xha r-a023e6 r-16dba41 r-ad9z0x r-bcqeeo r-bnwqim r-qvutc0"
/*
            for (int i = 0; i<5; i++){ //колво скролов вниз
                    rawTweets = Arrays.asList(page1.split(" «Нравится»\" "));
                    System.out.println("rawTweets.size:"+rawTweets.size());
                    for (String s : rawTweets){
                        if (!sumTweets.contains(s)){
                            sumTweets.add(s);
                        }
                    }
                    Thread.sleep(1000);
                    jse.executeScript("window.scrollBy(0,6000)");
                    Thread.sleep(6000); //ждем 5 сек , пока рагрузится страничка
                    page1 = driver.getPageSource();
                }
                editTweet(sumTweets);

                for (String s : sumTweets){
                    System.out.println("------------------START---------------");
                    System.out.println(s);
                    System.out.println("------------------END-----------------");
                }
                System.out.println("sumTweets.size"+sumTweets.size());

                System.out.println("elemList.size: "+elemList.size());
                for (WebElement e : elemList){
                    System.out.println("elemList.getText()"+e.getText());
                }
                */
        }
        catch (Exception e){
            System.out.println("ОШИБКА в GetXMLbySelenium");
            e.printStackTrace();
        }
    }

    public static void ParseByHtmlUnit(String url) {

        try (final WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.waitForBackgroundJavaScript(5000);
            webClient.getOptions().setUseInsecureSSL(true);
            webClient.getOptions().setTimeout(10000);
            webClient.setJavaScriptTimeout(8000);

            HtmlPage page = webClient.getPage(url);
            System.out.println(page.asXml());
            //Assert.assertEquals("HtmlUnit - Welcome to HtmlUnit", page.getTitleText());
            //final String pageAsXml = page.asXml();
            //HtmlElement htmlElement=  (HtmlElement) page.getFirstByXPath("//*[@id=\"__layout\"]/div/div[1]/div[1]/div[3]/ul/div[1]");
            //HtmlPage newPage = htmlElement.click();

            HtmlButton button = page.getFirstByXPath("//*[@id=\"__layout\"]/div/div[1]/div[1]/div[3]/ul/div[1]/button");
            HtmlElement htmlElement = page.getFirstByXPath("//*[@id=\"__layout\"]/div/div[1]/div[1]/div[3]/ul/div[1]");

            //button.click();



            System.out.println("divivivi: "+htmlElement.getNodeName());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void test() {

        String url = "https://msk.metro-cc.ru/";
        WebClient client = new WebClient();

        //client.setCssEnabled(true);
        //client.setJavaScriptEnabled(true);

        try {
            HtmlPage page = client.getPage(url);
            System.out.println(page.getPage());
            //List<?> date = page.getByXPath("//div/@class='list-box-comment'");
            //System.out.println(date.size());
            //for(int i =0 ; i<date.size();i++){
            //   System.out.println(date.get(i).asText());
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
