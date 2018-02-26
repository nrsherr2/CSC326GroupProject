package edu.ncsu.csc.itrust2.cucumber;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.ncsu.csc.itrust2.models.persistent.LogEntry;
import edu.ncsu.csc.itrust2.models.persistent.User;
import edu.ncsu.csc.itrust2.utils.LoggerUtil;

/**
 * Step definitions for AddHosptial feature
 */
public class AccessLogStepDefs {

    private final WebDriver driver       = new HtmlUnitDriver( true );
    private final String    baseUrl      = "http://localhost:8080/iTrust2";
    WebDriverWait           wait         = new WebDriverWait( driver, 20 );
    private String          jenkinsUname = "Drjenkins" + ( new Random() ).nextInt();

    @Given ( "I am a new authenticated user" )
    public void userAuthenticated () {
        final List<User> users = User.getUsers();
        for ( final User user : users ) {
            if ( user.getUsername().equals( jenkinsUname ) ) {
                try {
                    user.delete();
                }
                catch ( final Exception e ) {
                    Assert.fail();
                }
            }
        }
        // log in as admin
        driver.get( baseUrl );
        WebElement username = driver.findElement( By.name( "username" ) );
        username.clear();
        username.sendKeys( "admin" );
        WebElement password = driver.findElement( By.name( "password" ) );
        password.clear();
        password.sendKeys( "123456" );
        final WebElement submit = driver.findElement( By.className( "btn" ) );
        submit.click();
        // navigate to the add user page
        ( (JavascriptExecutor) driver ).executeScript( "document.getElementById('addnewuser').click();" );
        // fill in values of the add user form
        username = driver.findElement( By.id( "username" ) );
        username.clear();
        username.sendKeys( jenkinsUname );

        password = driver.findElement( By.id( "password" ) );
        password.clear();
        password.sendKeys( "123456" );

        final WebElement password2 = driver.findElement( By.id( "password2" ) );
        password2.clear();
        password2.sendKeys( "123456" );

        final Select role = new Select( driver.findElement( By.id( "role" ) ) );
        role.selectByVisibleText( "ROLE_PATIENT" );

        final WebElement enabled = driver.findElement( By.className( "checkbox" ) );
        enabled.click();

        driver.findElement( By.className( "btn" ) ).click();
        // log out
        driver.findElement( By.id( "logout" ) ).click();
    }

    @Given ( "my activity log has (\\d+) entries" )
    public void hasActivities ( final int numEntries ) {
        final List<LogEntry> logs = LoggerUtil.getAllForUser( jenkinsUname );
        if ( logs.size() > numEntries ) {
            jenkinsUname = "Drjenkins" + ( new Random() ).nextInt();
            userAuthenticated();
            hasActivities( numEntries );
        }
        else {
            loginUser();
            while ( logs.size() < numEntries ) {
                final By selector = By.id( "viewAccessLog-patient" );
                wait.until( ExpectedConditions.visibilityOfElementLocated( selector ) );
                final WebElement element = driver.findElement( selector );
                element.click();
            }
            driver.findElement( By.linkText( "iTrust2" ) ).click();
        }
    }

    @Given ( "I am logged in" )
    public void loginUser () {
        driver.get( baseUrl );
        final WebElement username = driver.findElement( By.name( "username" ) );
        username.clear();
        username.sendKeys( jenkinsUname );
        final WebElement password = driver.findElement( By.name( "password" ) );
        password.clear();
        password.sendKeys( "123456" );
        final WebElement submit = driver.findElement( By.className( "btn" ) );
        submit.click();
    }

    @When ( "I return to the home page" )
    public void returnHome () {
        final By selector = By.linkText( "iTrust2" );
        wait.until( ExpectedConditions.visibilityOfElementLocated( selector ) );
        final WebElement element = driver.findElement( selector );
        element.click();
    }

    @Then ( "I can view everything in my log" )
    public void viewAllLogs () {
        wait.until( ExpectedConditions.textToBePresentInElementLocated( By.tagName( "body" ), "Successful login" ) );
        Assert.assertTrue( driver.getPageSource().contains( "Successful login" ) );
    }

    @Then ( "I can view the last 10 items in my log" )
    public void viewLast10 () {
        try {
            wait.until(
                    ExpectedConditions.textToBePresentInElementLocated( By.tagName( "body" ), "Successful login" ) );
            Assert.fail();
        }
        catch ( final Exception e ) {
            Assert.assertFalse( driver.getPageSource().contains( "Successful login" ) );
        }
    }

    @When ( "I choose to view my activity log" )
    public void viewLogPage () {
        final By selector = By.id( "viewAccessLog-patient" );
        wait.until( ExpectedConditions.visibilityOfElementLocated( selector ) );
        final WebElement element = driver.findElement( selector );
        element.click();
    }

    /**
     * Admin login
     */
    @Given ( "I am logged in as an admin" )
    public void loginAdminH () {
        driver.get( baseUrl );
        final WebElement username = driver.findElement( By.name( "username" ) );
        username.clear();
        username.sendKeys( "admin" );
        final WebElement password = driver.findElement( By.name( "password" ) );
        password.clear();
        password.sendKeys( "123456" );
        final WebElement submit = driver.findElement( By.className( "btn" ) );
        submit.click();
    }

    @When ( "I create a user account" )
    public void createUser () {
        userAuthenticated();
    }

    @Then ( "the user cannot see that action in their history" )
    public void cantSeeCreation () {
        loginUser();
        try {
            wait.until(
                    ExpectedConditions.textToBePresentInElementLocated( By.tagName( "body" ), "New user created" ) );
            Assert.fail();
        }
        catch ( final Exception e ) {
            Assert.assertFalse( driver.getPageSource().contains( "New user created" ) );
        }
    }

    @When ( "I put in an invalid date" )
    public void enterInvalidDate () {
        setTextField( By.name( "startDate" ), "2/4" );
        setTextField( By.name( "endDate" ), "11/21/1231" );

        final WebElement submit = driver.findElement( By.linkText( "Get Access Logs" ) );
        submit.click();
    }

    private void setTextField ( final By byval, final Object value ) {
        final WebElement elem = driver.findElement( byval );
        elem.clear();
        elem.sendKeys( value.toString() );
    }

    @Then ( "there will be an empty log" )
    public void noLogsShown () {
        try {
            wait.until(
                    ExpectedConditions.textToBePresentInElementLocated( By.tagName( "body" ), "Log events viewed" ) );
            Assert.fail();
        }
        catch ( final Exception e ) {
            Assert.assertFalse( driver.getPageSource().contains( "Log events viewed" ) );
        }
    }

    @When ( "I put in a valid date" )
    public void enterValidDate () {
        // Get today's date
        final Calendar today = Calendar.getInstance();
        final int todayMonth = today.get( Calendar.MONTH ) + 1;
        final int todayDate = today.get( Calendar.DAY_OF_MONTH );
        final int todayYear = today.get( Calendar.YEAR );
        final String todayString = String.format( "%02d/%02d/%04d", todayMonth, todayDate, todayYear );
        setTextField( By.name( "startDate" ), todayString );
        setTextField( By.name( "endDate" ), todayString );

        final WebElement submit = driver.findElement( By.linkText( "Get Access Logs" ) );
        submit.click();
    }

    @Then ( "the page shows all activities in that range" )
    public void showsAllInRange () {
        wait.until( ExpectedConditions.textToBePresentInElementLocated( By.tagName( "body" ), "Log events viewed" ) );
        Assert.assertTrue( driver.getPageSource().contains( "Log events viewed" ) );
    }
}
