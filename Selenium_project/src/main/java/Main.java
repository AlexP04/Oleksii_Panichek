import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args){
        WebDriver driver;

        //Should be changed, if .exe file is placed in other directory
        String ref = "C:\\Users\\Oleksii\\Desktop\\chromedriver.exe";

        System.setProperty("webdriver.chrome.driver", ref);
        driver = new ChromeDriver();

        // Go to webpage mentioned in task
        driver.get("https://opensource-demo.orangehrmlive.com/");

        // Login
        WebElement name_field = driver.findElement(By.id("txtUsername"));
        WebElement password_field = driver.findElement(By.id("txtPassword"));
        WebElement button = driver.findElement(By.id("btnLogin"));
        name_field.sendKeys("Admin");
        password_field.sendKeys("admin123");
        button.click();

        // Adding WorkShift
        driver.get("https://opensource-demo.orangehrmlive.com/index.php/admin/workShift");
        button = driver.findElement(By.id("btnAdd"));
        button.click();
        WebElement name = driver.findElement(By.id("workShift_name"));
        name.sendKeys("ShiftName");
        driver.findElement(By.id("workShift_workHours_from")).sendKeys("06:00");
        driver.findElement(By.id("workShift_workHours_to")).sendKeys("18:00");
        WebElement workers  = driver.findElement(By.id("workShift_availableEmp"));
        button = driver.findElement(By.id("btnAssignEmployee"));

        workers.sendKeys("Linda Jane Anderson");
        button.click();

        workers.sendKeys("Kallyani Bhute");
        button.click();

        workers.sendKeys("Charlie Carter");
        button.click();

        workers.sendKeys("Chenzira Chuki");
        button.click();

        workers.sendKeys("Kiyara Hu");
        button.click();

        button = driver.findElement(By.id("btnSave"));
        button.click();
        List<String> parameters = Arrays.asList("","ShiftName","06:00","18:00","12:00");

        // Check results
        driver.get("https://opensource-demo.orangehrmlive.com/index.php/admin/workShift");
        List<WebElement> table = driver.findElement(By.id("resultTable")).findElements(By.tagName("tr"));

        boolean is_added = true;
        int  index = 0;
        for(WebElement val : table) {
            for(WebElement e: val.findElements(By.tagName("td")) ){
                int param_index = 0;
                is_added = true;
                if(!e.equals(parameters.get(param_index))){
                    is_added = false;
                    break;
                }
                ++param_index;
            }
            if(is_added){
                break;
            }
            ++index;
        }
        if(is_added) {
            System.out.println("Shift found!");
        }


        // Deleting.

        button = table.get(index).findElement(By.tagName("input"));
        button.click();
        driver.findElement(By.id("btnDelete")).click();
        driver.findElement(By.id("dialogDeleteBtn")).click();
    }
}
