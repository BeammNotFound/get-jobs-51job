import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.TelegramNotificationBot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 前程无忧自动投递简历
 *
 * @author BeamStark
 * @date 2023-05-15-05:58
 */
@Slf4j
public class ResumeSubmission {
    static boolean EnableNotifications = true;
    static Integer page = 5;
    static Integer maxPage = 10;
    static String loginUrl = "https://login.51job.com/login.php?lang=c&url=http%3A%2F%2Fwww.51job.com%2F&qrlogin=2";
    static String baseUrl = "https://we.51job.com/pc/search?keyword=java&searchType=2&sortType=0&metro=";
    static ChromeDriver driver = new ChromeDriver();
    static WebDriverWait wait15s = new WebDriverWait(driver, 15000);
    static List<String> returnList = new ArrayList<>();

    public static void main(String[] args) {
        Date sdate = new Date();
        login();
        resume();
        Date edate = new Date();
        log.info("共投递{}个简历,用时{}分", returnList.size(),
                ((edate.getTime() - sdate.getTime()) / 1000) / 60);
        if (EnableNotifications) {
            new TelegramNotificationBot().sendMessageWithList("共投递" + returnList.size() + "个简历," +
                    "用时" + ((edate.getTime() - sdate.getTime()) / 1000) / 60 + "分", returnList,
                    "前程无忧投递");
        }
    }

    @SneakyThrows
    private static Integer resume(){
        driver.get(baseUrl);
        wait15s.until(ExpectedConditions.elementToBeClickable(By.className("carrybox")));
        driver.findElement(By.className("carrybox")).click();
        List<WebElement> clist = driver.findElements(By.className("clist"));
        //杭州
        driver.findElement(By.className("allcity")).click();
        wait15s.until(ExpectedConditions.presenceOfElementLocated(By.className("el-dialog__body")));
        driver.findElements(By.className("grid-item")).get(6).findElement(By.className("option")).click();
        Thread.sleep(1000);
        driver.findElements(By.cssSelector("[class*='el-button el-button--primary']")).get(0).click();

        //10-15k
        clist.get(1).findElements(By.className("ch")).get(8).click();
        //1-3年
        clist.get(2).findElements(By.className("ch")).get(2).click();
        //最新排序
        driver.findElements(By.className("ss")).get(1).click();

        for (int i = page; i <= maxPage; i++) {
            wait15s.until(ExpectedConditions.presenceOfElementLocated(By.className("mytxt")));
            driver.findElement(By.className("mytxt")).clear();
            driver.findElement(By.className("mytxt")).sendKeys(String.valueOf(i));
            driver.findElement(By.className("jumpPage")).click();
            log.info("第{}页", i);
            if (!page()) {
                break;
            }
        }
        return returnList.size();
    }

    @SneakyThrows
    private static Boolean page(){
        Thread.sleep(1000);
        for (int i = 0; i < driver.findElements(By.cssSelector("[class*='e_icons ick']")).size(); i++) {
            WebElement element =
                    driver.findElements(By.cssSelector("[class*='e_icons ick']")).get(i);
            new Actions(driver).moveToElement(element).click().perform();
            String title =
                    driver.findElements(By.cssSelector("[class*='jname at']")).get(i).getText();

            String com =
                    driver.findElements(By.cssSelector("[class*='cname at']")).get(i).getText();
            returnList.add(com + " | " + title);
            log.info("选中{} | {}职位", com, title);
        }
        //批量申请
        boolean success = false;

        while (!success) {
            try {
                WebElement element =
                        driver.findElement(By.className("tright")).findElements(By.className(
                                "p_but")).get(1);
                //模拟鼠标点击
                Actions actions = new Actions(driver);
                actions.moveToElement(element).click().perform();
                success = true;
            } catch (ElementClickInterceptedException e) {
                log.error("失败，1s后重试..");
                Thread.sleep(1000);
            }
        }

        //处理弹窗
        String text = wait15s.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(
                "[class*='van-popup van-popup--center']"))).getText();
        if (text.contains("成功")) {
            //关闭弹窗
            driver.findElement(By.cssSelector("[class*='van-icon van-icon-cross van-popup__close-icon van-popup__close-icon--top-right']")).click();
            return true;
        }
        return false;
    }

    private static void login(){
        driver.get(loginUrl);
        log.info("等待登陆..");
        wait15s.until(ExpectedConditions.presenceOfElementLocated(By.id("choose_best_list")));
    }
}
