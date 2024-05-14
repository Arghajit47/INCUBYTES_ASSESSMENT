package StepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class AssessmentTest {
    WebDriver driver = new ChromeDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    public String mailTo = "arghajitsingha47@gmail.com";
    public String body = "Automation QA test for Incubyte";
    public String subject = "Incubyte";
    public By emailInput = By.id("identifierId");
    public By passwordInput = By.name("Passwd");
    public By composeButton = By.xpath("//div[text()='Compose']");
    public By newMessage = By.xpath("//div/span[text()='New Message']");
    public By toRecipient = By.xpath("//input[@aria-label=\"To recipients\"]");
    public By subjectBox = By.xpath("//input[@name=\"subjectbox\"]");
    public By messageBody = By.xpath("//div[@aria-label=\"Message Body\"]");
    public By sendButton = By.xpath("//div[text()=\"Send\"]");




    public void enterEmailAddress(String username) throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        driver.findElement(emailInput).sendKeys(username);
        driver.findElement(By.id("identifierNext")).click();
    }

    public void clickOnComposeButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(composeButton));
        driver.findElement(composeButton).click();
    }

    public void enterSubject(String subject) {
        driver.findElement(subjectBox).sendKeys(subject);
    }

    public void enterMsgBody(String msgBody) {
        driver.findElement(messageBody).sendKeys(msgBody);
    }

    public void enterMailToAddress(String mailTo) {
        driver.findElement(toRecipient).sendKeys(mailTo);
    }

    public void enterPassword(String pass) throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        driver.findElement(passwordInput).sendKeys(pass);
        driver.findElement(By.id("passwordNext")).click();
    }

    public void clickSend() {
        wait.until(ExpectedConditions.elementToBeClickable(sendButton));
        driver.findElement(sendButton).click();
    }

    public void checkNewMessageIsDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(newMessage));
        driver.findElement(newMessage).isDisplayed();
    }

    @Given("Setup Browser")
    public void setup_browser() {
        System.out.println("Browser is open");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @And("User is navigated to the Gmail")
    public void user_is_navigated_to_gmail() {
        System.out.println("User is navigating to Gmail Page");
        driver.get("https://mail.google.com");
    }

    @When("^User input the valid userName and password")
    public void user_input_the_valid_userName_and_password() throws IOException {
        Properties properties = new Properties();
        FileInputStream fileInputStream;

        {
            try {
                fileInputStream = new FileInputStream("src/main/resources/data.properties");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        properties.load(fileInputStream);
        String username = properties.getProperty("username");
        String pass = properties.getProperty("password");
        fileInputStream.close();
        enterEmailAddress(username);
        enterPassword(pass);
    }

    @Then("^User should be on the Gmail inbox page$")
    public void user_should_be_on_the_Gmail_inbox_page() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(composeButton));
        assertTrue(driver.getTitle().contains("Inbox"));
    }

    @When("^User clicks on compose button$")
    public void user_clicks_on_compose_button() {
        clickOnComposeButton();
        checkNewMessageIsDisplayed();
    }

    @When("^User fills in the email details with subject and body")
    public void user_fills_in_the_email_details_with_subject_and_body() {
        enterMailToAddress(mailTo);
        enterSubject(subject);
        enterMsgBody(body);
    }

    @When("^User clicks on send button$")
    public void user_clicks_on_send_button() {
        clickSend();
    }

    @Then("^Email should be sent successfully$")
    @And("Close the Browser")
    public void tearDown() {
        driver.quit();
    }
}
