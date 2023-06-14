import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HtmlToPdfConverter {

    public static void main(String[] args) {
        // Configuración de ChromeOptions para ejecutar en modo headless
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--disable-gpu");

        // Ruta al archivo HTML de entrada
        String htmlFilePath = "/home/nuevo-directorio/micro_holamundo/JMETER/ResultadosHTML/index.html";

        // Ruta de salida para el archivo PDF
        String pdfOutputPath = "/home/EstefaniaM/workspace/Rollback/archivo_selenium.pdf";

        // Crear instancia de WebDriver
        WebDriver driver = new ChromeDriver(options);

        try {
            // Cargar el archivo HTML en el navegador
            driver.get("file://" + htmlFilePath);

            // Esperar a que la página se cargue completamente
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".tab")));

            // Ejecutar código JavaScript para abrir las pestañas
            driver.executeScript("var tabs = document.querySelectorAll('.tab'); " +
                                 "for (var i = 0; i < tabs.length; i++) { " +
                                 "    tabs[i].click(); " +
                                 "}");

            // Esperar a que se complete la carga de contenido generado dinámicamente (agregar espera personalizada según necesidades)

            // Guardar el contenido como PDF
            driver.save_screenshot("/home/EstefaniaM/workspace/Rollback/captura_de_pantalla.png"); // Capturar una imagen de la página (opcional)
            driver.save_pdf(pdfOutputPath);
        } finally {
            // Cerrar el navegador
            driver.quit();
        }
    }
}
