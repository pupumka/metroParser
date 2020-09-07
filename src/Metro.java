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
    public static List<String> linklist = new ArrayList<>();
    //public static WebDriver driver = new ChromeDriver();

    public static void main(String[] args) {
        //System.setProperty("webdriver.chrome.driver", "C:\\Users\\mironov.matvey\\IdeaProjects\\metroParser\\libs\\chromedriver_win32\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", "E:\\Program Files\\metroParser\\libs\\chromedriver_win32\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        new WebDriverWait(driver, 5L);
        driver.manage().window().maximize();
        String url = "https://delivery.metro-cc.ru/metro";

        //collectProducts("https://delivery.metro-cc.ru/metro/skoro-v-shkolu", driver);
        ParseBySelenium(url, driver);
        for (String s : linklist){
            System.out.println(s);
        }

        //iterateProducts(driver);
        System.out.println("______________finalProducts.size() : "+finalProducts.size());
    }

    public static void multyStart(WebDriver driver){
        int iteratePart = linklist.size()/10;
        for (int i=1;i<11;i++){
            int start = iteratePart*(i-1);
            int end = iteratePart*i;
            if (i==10){
                end = linklist.size();
            }
            Multy childThread = new Multy(start, end,driver);
        }
    }

    public  static void collectProductLinks(String url, WebDriver driver ){
        driver.get(url);
        JavascriptExecutor jse = (JavascriptExecutor)driver;

        try {
            long lastHeight = (long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight");

            while (true) {
                //((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
                ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
                Thread.sleep(1000);
                jse.executeScript("window.scrollBy(0,-100)");
                Thread.sleep(1000);

                long newHeight = (long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight");
                if (newHeight == lastHeight) {
                    break;
                }
                lastHeight = newHeight;
            }
        } catch (InterruptedException e) {
            System.out.println("Exception while scrolling has been catched!!!");
            e.printStackTrace();
        }
        //if (url.equals("https://delivery.metro-cc.ru/metro/recepty-producty")){
         //   return;
        //}

        /*for (int i = 0;i<600;i++) { //былоа 1300 при 200 скролах, для всех надо 1400. для 3600 надо:
            jse.executeScript("window.scrollBy(0,300)");
            try {
                Thread.sleep(300);
            }
            catch (InterruptedException e){
                System.out.println("InterruptedException has been catched!!!!!");
            }
        }*/
        //мотаем вниз (скрол)


        //ищем элементы (потом ссылки)
        List<WebElement> productsElements= driver.findElements(By.className("product__link")); //временный
        //List<String> productsList = new ArrayList<String>();
        for (int i =0;i<productsElements.size();i++){
            linklist.add(productsElements.get(i).getAttribute("href"));
        }
        System.out.println("linkList.size = "+linklist.size());


    }

    public static void iterateProducts(int i, int j, WebDriver driver) { //i - от - j - до
        for (i =0; i<j;i++){
            driver.get(linklist.get(i));

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
            product.link = linklist.get(i);
            //состав
            if (driver.findElements(By.xpath("//*[@id=\"react-modal\"]/div/div/div/div[2]/div/div/div/" +
                    "div/div[3]/div/div[1]/div/div/div[2]/div/div/div[1]/div[2]")).size() != 0) {
                product.consist = driver.findElement(By.xpath("//*[@id=\"react-modal\"]/div/div/div/div[2]/div/div/div/" +
                        "div/div[3]/div/div[1]/div/div/div[2]/div/div/div[1]/div[2]")).getText();
            }
            //price
            if (driver.findElements(By.className("components_2MaTn components_22kKA components_22kKA")).size() !=0){
                product.price = driver.findElement(By.className("components_2MaTn components_22kKA components_22kKA")).getText();
            }
            //discount price
            if (driver.findElements(By.className("components_2MaTn components_22kKA components_1VMv2")).size() !=0){
                product.discountPrice = driver.findElement(By.className("components_2MaTn components_22kKA components_1VMv2")).getText();
            }

            //nondiscount price
            if (driver.findElements(By.className("components_2MaTn components_22kKA components_3PqkC")).size() !=0){
                product.nondiscountPrice = driver.findElement(By.className("components_2MaTn components_22kKA components_3PqkC")).getText();
            }



            //Общая информация (таблица)
            if (driver.findElements(By.className("popup_3JinQ")).size() != 0){
                WebElement table1 = driver.findElement(By.className("popup_3JinQ"));
                for (WebElement li : table1.findElements(By.className("product-property"))) {
                    product.generalInformation.put(li.findElement(By.className("product-property__name")).getText(),
                            li.findElement(By.className("product-property__value")).getText() );
                }
            }

            //пищевая ценность (таблица)
            if (driver.findElements(By.className("nutrition")).size() != 0){
                WebElement table2 = driver.findElement(By.className("nutrition"));
                for (WebElement li : table2.findElements(By.className("product-property"))) {
                    product.nutritionalValue.put(li.findElement(By.className("product-property__name")).getText(),
                            li.findElement(By.className("product-property__value")).getText() );
                }
            }

            //картинка главная
            if (driver.findElements(By.xpath("//*[@id=\"react-modal\"]/div/div/div/div[2]/div/div/div/div/div[2]/div[1]" +
                    "/div[1]/div/div/div/div/div/img")).size() != 0) {
                product.mainPicture = driver.findElement(By.xpath("//*[@id=\"react-modal\"]/div/div/div/div[2]/div/div/" +
                        "div/div/div[2]/div[1]/div[1]/div/div/div/div/div/img")).getAttribute("src");
            }

            //System.out.println(product.name+"  :  "+product.description + "  :  "+product.link+"  :  "+product.consist+
            //        "  :  "+product.pictures.get(0));
            finalProducts.add(product);
        }
    }

    public static void ParseBySelenium(String url, WebDriver driver) {

        try {
            driver.get(url);
            Thread.sleep(6000); //ждем 5 сек , пока рагрузится страничка
            //JavascriptExecutor jse = (JavascriptExecutor)driver;

            //String page1;
            //List<String> rawTweets;
            //ArrayList<String> sumTweets= new ArrayList<>();
            //page1 = driver.getPageSource();

            List<WebElement> elemList= driver.findElements(By.className("show-all"));
            //System.out.println("elemList.size: "+elemList.size());
            //System.out.println("getPageSource: "+driver.getPageSource());

            for (int i =14;i<elemList.size();i++){
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
                                collectProductLinks(driver.getCurrentUrl(), driver);

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
                            collectProductLinks(driver.getCurrentUrl(), driver);
                        }

                        //driver.findElement(By.className("breadcrumbs_ilDgv")).click();
                        int slashIndex = driver.getCurrentUrl().lastIndexOf('/');
                        String backURL = driver.getCurrentUrl().substring(0,slashIndex);
                        driver.get(backURL);
                        //Thread.sleep(1L);
                        elemList2 = driver.findElements(By.className("show-all"));

                    }

                }
                if (elemList2.size() == 0){
                    System.out.println("driver.getCurrentUrl: " + driver.getCurrentUrl());
                    //итерация по продуктам
                    collectProductLinks(driver.getCurrentUrl(), driver);

                }
                driver.get(url);
                //Thread.sleep(1L);
                elemList = driver.findElements(By.className("show-all"));
            }

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
