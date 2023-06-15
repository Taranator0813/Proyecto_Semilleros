import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HtmlToPdfConverter {

    public static void main(String[] args) {
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--disable-gpu");

        String htmlFilePath = "/home/nuevo-directorio/micro_holamundo/JMETER/ResultadosHTML/index.html";

        String pdfOutputPath = "/home/EstefaniaM/workspace/Rollback/archivo_selenium.pdf";

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("file://" + htmlFilePath);

            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".tab")));

            driver.executeScript("var tabs = document.querySelectorAll('.tab'); " +
                                 "for (var i = 0; i < tabs.length; i++) { " +
                                 "    tabs[i].click(); " +
                                 "}");

           
            driver.save_screenshot("/home/EstefaniaM/workspace/Rollback/captura_de_pantalla.png"); 
            driver.save_pdf(pdfOutputPath);
        } finally {
           
            driver.quit();
        }
    }
}
