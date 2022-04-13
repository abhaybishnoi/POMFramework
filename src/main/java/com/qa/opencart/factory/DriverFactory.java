package com.qa.opencart.factory;

import com.qa.opencart.utils.Browser;
import com.qa.opencart.utils.Errors;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class DriverFactory {

    public WebDriver driver;
    public Properties prop;
    public static String highlight;
    public OptionsManager optionsManager;
    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

    public static final Logger log = Logger.getLogger(DriverFactory.class);

    /**
     * This method is used to initialize the webdriver on the basis of given browser
     * name. This method will take care of local and remote execution
     *
     * @param prop
     * @return
     */
        public WebDriver init_driver(Properties prop) {

            String browserName = prop.getProperty("browser").trim();
            System.out.println("browser name is : " + browserName);
            log.info("browser name is: " +browserName);
            highlight = prop.getProperty("highlight").trim();
            optionsManager = new OptionsManager(prop);

            if (browserName.equalsIgnoreCase(Browser.CHROME_BROWSER_VALUE)) {
                log.info("running on chrome browser");
                WebDriverManager.chromedriver().setup();
                //System.setProperty(Browser.CHROME_DRIVER_BINARY_KEY,Browser.CHROME_DRIVER_PATH);
                // driver = new ChromeDriver(optionsManager.getChromeOptions());
                tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));

            } else if (browserName.equalsIgnoreCase(Browser.FIREFOX_BROWSER_VALUE)) {
                //WebDriverManager.firefoxdriver().setup();
                System.setProperty(Browser.GECKO_DRIVER_BINARY_KEY,Browser.FIREFOX_DRIVER_PATH);
                // driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
                tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));

            } else if (browserName.equalsIgnoreCase(Browser.SAFARI_BROWSER_VALUE)) {
                // driver = new SafariDriver();
                tlDriver.set(new SafariDriver());
            } else {
                System.out.println(Errors.BROWSER_NOT_FOUND_ERROR_MESSG + browserName);
            }

            getDriver().manage().deleteAllCookies();
            getDriver().manage().window().maximize();
            getDriver().get(prop.getProperty("url"));
            log.info(prop.getProperty("url") + " ..url is launched");

            return getDriver();

        }

        /**
         * this will return the thread local copy of the webdriver(driver)
         *
         * @return
         */
        public static WebDriver getDriver() {
            return tlDriver.get();
        }

        /**
         * This method is used to initialize the properties on the basis of given
         * environment: QA/DEV/Stage/PROD
         *
         * @return this returns prop
         */
        public Properties init_prop() {

            prop = new Properties();
            FileInputStream ip = null;

            //mvn clean install -Denv="qa"
            String envName = System.getProperty("env");
            System.out.println("Running on env: " +envName);

            if (envName == null){
                try {
                    System.out.println("No Environment was passed hence running on QA");
                    log.info("No env is given... hence running it on QA");
                    ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    switch(envName.toLowerCase()){
                        case "qa":
                            ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
                            break;
                        case "stage":
                            ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
                            break;
                        case "uat":
                            ip = new FileInputStream("./src/test/resources/config/uat.config.properties");
                            break;
                        case "dev":
                            ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
                            break;
                        case "prod":
                            ip = new FileInputStream("./src/test/resources/config/config.properties");
                            break;
                        default:
                            System.out.println("Please pass the right env: " +envName);
                            log.error("Please pass the right env ... " +envName);
                            log.warn("env is not found..");
                            log.fatal("env is not found..");
                            break;
                    }
                }catch (Exception e){
                }
            }  try {
                prop.load(ip);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return prop;
        }


        /**
         * take screenshot
         */
        public static String getScreenshot(){
            File srcFile =((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
            String path = System.getProperty("user.dir")+"/screenshot/"+System.currentTimeMillis()+".png";
            File destination=new File(path);
            try {
                FileUtils.copyFile(srcFile,destination);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return path;


        }

    }

