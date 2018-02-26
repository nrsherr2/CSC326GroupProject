package edu.ncsu.csc.itrust2.cucumber;

import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.ncsu.csc.itrust2.models.persistent.LogEntry;
import edu.ncsu.csc.itrust2.utils.LoggerUtil;

/**
 * Step definitions for Email feature
 *
 * @author Vincent Renich
 * @author Cameron Harris
 * @author Nick Sherrill
 */
public class EmailStepDefs {

    private final WebDriver driver       = new HtmlUnitDriver( true );
    private final String    baseUrl      = "http://localhost:8080/iTrust2";
    WebDriverWait           wait         = new WebDriverWait( driver, 20 );
    private final String    jenkinsUname = "Drjenkins" + ( new Random() ).nextInt();

    @When ( "I change my password" )
    public void iChangePassword () {
        ( (JavascriptExecutor) driver ).executeScript( "document.getElementById('changePassword').click();" );

        // Wait until page loads
        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "currentPW" ) ) );

        setTextField( By.name( "currentPW" ), "123456" );
        setTextField( By.name( "newPW" ), "111111" );
        setTextField( By.name( "confirmPW" ), "111111" );

        final WebElement submit = driver.findElement( By.name( "submitButton" ) );
        submit.click();

    }

    @Then ( "I get an email as user (.+)" )
    public void iGetEmail ( final String user ) {
        final List<LogEntry> logs = LoggerUtil.getAllForUser( user );
        for ( final LogEntry l : logs ) {
            if ( l.getMessage().contains( "Email notification sent" ) ) {
                Assert.assertTrue( true );
                return;
            }
        }
        Assert.fail();
    }

    @Then ( "I get a failed email log entry as user (.+)" )
    public void iDontGetEmail ( final String user ) {
        final List<LogEntry> logs = LoggerUtil.getAllForUser( user );
        for ( final LogEntry l : logs ) {
            if ( l.getMessage().contains( "Email notification could not be sent" ) ) {
                Assert.assertTrue( true );
                return;
            }
        }
        Assert.fail();
    }

    private void setTextField ( final By byval, final Object value ) {
        final WebElement elem = driver.findElement( byval );
        elem.clear();
        elem.sendKeys( value.toString() );
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

}
