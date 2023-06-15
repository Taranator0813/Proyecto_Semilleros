import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

        String pdfOutputPath = "/home/EstefaniaM/workspace/test/archivo_selenium.pdf";

        WebDriver driver = new ChromeDriver(options);

        try {

            driver.get("file://" + htmlFilePath);

            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".tab")));

            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript("var tabs = document.querySelectorAll('.tab'); " +
                                     "for (var i = 0; i < tabs.length; i++) { " +
                                     "    tabs[i].click(); " +
                                     "}");

            driver.get("chrome://print");
            WebElement printPreviewButton = driver.findElement(By.cssSelector(".action-button"));
            printPreviewButton.click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".preview-container")));

            driver.switchTo().frame(driver.findElement(By.cssSelector("iframe")));
            WebElement printButton = driver.findElement(By.cssSelector(".action-button"));
            printButton.click();
            wait.until(ExpectedConditions.urlContains("pdf"));

            driver.switchTo().defaultContent();
            driver.get("chrome://downloads");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("downloads-item")));

            jsExecutor.executeScript("downloadsManager.setSavePath('" + pdfOutputPath + "');");
            WebElement saveButton = driver.findElement(By.cssSelector("#save"));
            saveButton.click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("downloads-item")));
        } finally {

            driver.quit();
        }
    }
}
