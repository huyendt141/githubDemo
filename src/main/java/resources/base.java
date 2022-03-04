package resources;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class base {

    public WebDriver driver;
    public Properties prop;

    public WebDriver initializeDriver() throws IOException {

        prop = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/resources/data.properties");

        prop.load(fis);
        String browserName = prop.getProperty("browser");//take browser parameter from .properties file

//String browserName = System.getProperty("browser");
// send browser parameter via mvn command: mvn test -Dbrowser
        System.out.println(browserName);

        if (browserName.contains("chrome")) {
            System.setProperty("webdriver.chrome.driver", "src/main/java/resources/chromedriver.exe");
            ChromeOptions option = new ChromeOptions();
            if (browserName.contains("headless")) {
                option.addArguments("headless");
            }
            driver = new ChromeDriver(option);
            //Create chrome option to run test in headless mode
            //execute in chrome driver

        } else if (browserName.equals("firefox")) {
            driver = new FirefoxDriver();
            //firefox code
        } else if (browserName.equals("IE")) {
//	IE code
        }

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;


    }


    public String getScreenShotPath(String testCaseName, WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destinationFile = System.getProperty("user.dir") + "\\reports\\" + testCaseName + ".png";
        FileUtils.copyFile(source, new File(destinationFile));
        return destinationFile;


    }

}
