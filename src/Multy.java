import org.openqa.selenium.WebDriver;

public class Multy extends Thread {
    int i;
    int j;
    WebDriver driver;

    public Multy(int i, int j, WebDriver driver) {
        this.j = j;
        this.i = i;
        this.driver = driver;
    }

    @Override
    public void run() {
        //Metro.iterateProducts(i,j,driver);
    }
}
