import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.sun.xml.internal.ws.server.DefaultResourceInjector;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.page.Page;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.html.HTMLElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Metro {

    public static List<ProductMetro> finalProducts = new ArrayList<ProductMetro>();
    //public static WebDriver driver = new ChromeDriver();

    public static void main(String[] args) {
        //System.setProperty("webdriver.chrome.driver", "C:\\Users\\mironov.matvey\\IdeaProjects\\metroParser\\libs\\chromedriver_win32\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", "E:\\Program Files\\metroParser\\libs\\chromedriver_win32\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        new WebDriverWait(driver, 5L);
        driver.manage().window().maximize();
        String url = "https://delivery.metro-cc.ru/metro";

        //collectProducts("https://delivery.metro-cc.ru/metro/skoro-v-shkolu", driver);
        //ParseBySelenium(url, driver);
        test(driver);
        System.out.println("______________finalProducts.size() : "+finalProducts.size());
    }

    public  static void collectProducts(String url, WebDriver driver ){
        driver.get(url);
        JavascriptExecutor jse = (JavascriptExecutor)driver;

        for (int i = 0;i<200;i++) {
            jse.executeScript("window.scrollBy(0,300)");
            try {
                Thread.sleep(300);
            }
            catch (InterruptedException e){
                System.out.println("InterruptedException has been catched!!!!!");
            }
        }

        List<WebElement> productsElements= driver.findElements(By.className("product__link")); //временный
        List<String> productsList = new ArrayList<String>();
        for (int i =0;i<productsElements.size();i++){
            productsList.add(productsElements.get(i).getAttribute("href"));
        }
        System.out.println("productsList.size = "+productsList.size());

        for (int i =0; i<productsList.size();i++){
            driver.get(productsList.get(i));

            ProductMetro product = new ProductMetro();
            //Название
            if (driver.findElements(By.xpath("//*[@id=\"react-modal\"]/div/div/div/div[2]/div/div/div/div/" +
                    "div[2]/div[2]/div[1]/h1")).size() != 0) {
                product.name = driver.findElement(By.xpath("//*[@id=\"react-modal\"]/div/div/div/div[2]/div/div/div/div/" +
                        "div[2]/div[2]/div[1]/h1")).getText();
            }
            //описание
            if (driver.findElements(By.xpath("//*[@id=\"react-modal\"]/div/div/div/div[2]/div/div/" +
                    "div/div/div[3]/div/div[1]/div/div/div[1]/div[2]/div")).size() != 0) {
                product.description = driver.findElement(By.xpath("//*[@id=\"react-modal\"]/div/div/div/div[2]/div/div/" +
                        "div/div/div[3]/div/div[1]/div/div/div[1]/div[2]/div")).getText();
            }
            //ссылка
            product.link = productsList.get(i);
            //состав
            if (driver.findElements(By.xpath("//*[@id=\"react-modal\"]/div/div/div/div[2]/div/div/div/" +
                    "div/div[3]/div/div[1]/div/div/div[2]/div/div/div[1]/div[2]")).size() != 0) {
                product.consist = driver.findElement(By.xpath("//*[@id=\"react-modal\"]/div/div/div/div[2]/div/div/div/" +
                        "div/div[3]/div/div[1]/div/div/div[2]/div/div/div[1]/div[2]")).getText();
            }
            //Общая информация (таблица)
            if (driver.findElements(By.className("product-property")).size() != 0){
                for (WebElement li : driver.findElements(By.className("product-property"))) {
                    product.generalInformation.put(li.findElement(By.className("product-property__name")).getText(),
                            li.findElement(By.className("product-property__value")).getText() );
                }
            }


            //картинка главная
            if (driver.findElements(By.xpath("//*[@id=\"react-modal\"]/div/div/div/div[2]/div/div/div/div/div[2]/div[1]" +
                    "/div[1]/div/div/div/div/div/img")).size() != 0) {
                product.pictures.add(driver.findElement(By.xpath("//*[@id=\"react-modal\"]/div/div/div/div[2]/div/div/" +
                        "div/div/div[2]/div[1]/div[1]/div/div/div/div/div/img")).getAttribute("src"));
            }

            int picsNum = driver.findElements(By.className("review_cell_2HmTo")).size();
            for (int j=0;j<picsNum;j++) {
                //product.pictures.add(driver.findElement(By.xpath("//*[@id=\"react-modal\"]/div/div/div/div[2]" +
                //        "/div/div/div/div/div[2]/div[1]/div[1]/div/div[2]/div["+j+"]/img")).getAttribute("src"));
                System.out.println(driver.findElement(By.xpath("//*[@id=\"react-modal\"]/div/div/div/div[2]" +
                                "/div/div/div/div/div[2]/div[1]/div[1]/div/div[2]/div["+(j+1)+"]/img")).getSize()); //getAttribute("src"));
            }

            //System.out.println(product.name+"  :  "+product.description + "  :  "+product.link+"  :  "+product.consist+
            //        "  :  "+product.pictures.get(0));
            finalProducts.add(product);

            //*[@id="react-modal"]/div/div/div/div[2]/div/div/div/div/div[2]/div[1]/div[1]/div/div[2]/div[1]/img
            //*[@id="react-modal"]/div/div/div/div[2]/div/div/div/div/div[2]/div[1]/div[1]/div/div[2]/div[2]/img
        }

    }

    public static void ParseBySelenium(String url, WebDriver driver) {

        try {
            driver.get(url);
            Thread.sleep(6000); //ждем 5 сек , пока рагрузится страничка
            //JavascriptExecutor jse = (JavascriptExecutor)driver;

            String page1;
            List<String> rawTweets;
            ArrayList<String> sumTweets= new ArrayList<>();
            page1 = driver.getPageSource();

            List<WebElement> elemList= driver.findElements(By.className("show-all"));
            //System.out.println("elemList.size: "+elemList.size());
            //System.out.println("getPageSource: "+driver.getPageSource());

            for (int i =0;i<elemList.size();i++){
                elemList.get(i).click();
                //Thread.sleep(1L);

                List<WebElement> elemList2 = driver.findElements(By.className("show-all"));
                if (elemList2.size() != 0) {
                    for (int j = 0; j < elemList2.size();j++) {
                        elemList2.get(j).click();
                        //Thread.sleep(1L);
                        System.out.println("driver.getCurrentUrl: " + driver.getCurrentUrl());

                        List<WebElement> elemList3 = driver.findElements(By.className("show-all"));
                        if (elemList3.size() != 0) {
                            for (int k = 0; k < elemList3.size();k++) {
                                elemList3.get(k).click();
                                //Thread.sleep(1L);
                                System.out.println("driver.getCurrentUrl: " + driver.getCurrentUrl());
                                //итерация по продуктам
                                collectProducts(driver.getCurrentUrl(), driver);

                                int slashIndex = driver.getCurrentUrl().lastIndexOf('/');
                                String backURL = driver.getCurrentUrl().substring(0,slashIndex);
                                driver.get(backURL);
                                //driver.findElement(By.className("breadcrumbs_ilDgv")).click();
                                //Thread.sleep(1L);
                                elemList3 = driver.findElements(By.className("show-all"));
                            }


                        }
                        if (elemList3.size() == 0) {
                            //итерация по продуктам
                            collectProducts(driver.getCurrentUrl(), driver);
                        }

                        driver.findElement(By.className("breadcrumbs_ilDgv")).click();
                        //Thread.sleep(1L);
                        elemList2 = driver.findElements(By.className("show-all"));

                    }

                }
                if (elemList2.size() == 0){
                    System.out.println("driver.getCurrentUrl: " + driver.getCurrentUrl());
                    //итерация по продуктам
                    collectProducts(driver.getCurrentUrl(), driver);

                }
                driver.get(url);
                //Thread.sleep(1L);
                elemList = driver.findElements(By.className("show-all"));
            }


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
                //editTweet(sumTweets);

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

    public static void test(WebDriver driver) {

        driver.get("https://delivery.metro-cc.ru/metro/sobstviennyie-torghovyie-marki");
        JavascriptExecutor jse = (JavascriptExecutor)driver;

        //jse.executeScript("window.scrollBy(0,300)");
        jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        try {
            Thread.sleep(10L);
        }
        catch (InterruptedException e){
            System.out.println("InterruptedException has been catched!!!!!");
        }



    }


}
