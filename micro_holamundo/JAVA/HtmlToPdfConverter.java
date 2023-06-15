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
            By buttonSelector = By.cssSelector(".btn.btn-link.btn-xs.dropdown-toggle[data-toggle='collapse'][href='#bodySyntheticResponseTimeDistribution']");
            wait.until(ExpectedConditions.presenceOfElementLocated(buttonSelector));

            // Verificar todos los botones abiertos
            boolean allButtonsOpen = true;
            for (WebElement button : driver.findElements(buttonSelector)) {
                String ariaExpandedValue = button.getAttribute("aria-expanded");
                if (!ariaExpandedValue.equals("true")) {
                    allButtonsOpen = false;
                    break;
                }
            }

            if (allButtonsOpen) {
                // Todos los botones están abiertos
                System.out.println("Todos los botones están abiertos");

                // Realizar las acciones relacionadas con el PDF
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

                JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                jsExecutor.executeScript("downloadsManager.setSavePath('" + pdfOutputPath + "');");
                WebElement saveButton = driver.findElement(By.cssSelector("#save"));
                saveButton.click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("downloads-item")));
            } else {
                // Al menos un botón está cerrado
                System.out.println("Al menos un botón está cerrado");
            }

        } finally {
            driver.quit();
        }
    }
}


