package edu.ncsu.csc.itrust2.apitest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import edu.ncsu.csc.itrust2.config.RootConfiguration;
import edu.ncsu.csc.itrust2.mvc.config.WebMvcConfiguration;

/**
 * Test for API functionality for interacting with log entries.
 *
 * @author Kai Presler-Marshall
 *
 */
@RunWith ( SpringJUnit4ClassRunner.class )
@ContextConfiguration ( classes = { RootConfiguration.class, WebMvcConfiguration.class } )
@WebAppConfiguration
public class APILogEntryTest {

    private MockMvc               mvc;

    @Autowired
    private WebApplicationContext context;

    /**
     * Sets up test
     */
    @Before
    public void setup () {
        mvc = MockMvcBuilders.webAppContextSetup( context ).build();
    }

    /**
     * Tests LogEntryAPI
     *
     * @throws Exception
     */
    @Test
    public void testLogEntryAPI () throws Exception {
        // Ensure there is at least one log entry by viewing users
        mvc.perform( get( "/api/v1/users" ) ).andExpect( status().isOk() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON_UTF8_VALUE ) );

        mvc.perform( get( "/api/v1/logentries" ) ).andExpect( status().isOk() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON_UTF8_VALUE ) );

        // final Long id = LogEntry.getLogEntries().get( 0 ).getId();
        // Test getting a specific log entry.
        mvc.perform( get( "/api/v1/logentries/" + "patient" ) ).andExpect( status().isOk() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON_UTF8_VALUE ) );

        // Test getting a non-existent log entry
        mvc.perform( get( "/api/v1/logentries/-1" ) ).andExpect( status().isNotFound() );

        // Test getting the top 10 entries
        mvc.perform( get( "/api/v1/logentries/patient10" ) ).andExpect( status().isOk() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON_UTF8_VALUE ) );

        // Test getting the list of entries between two dates
        // Get today's date
        final Calendar today = Calendar.getInstance();
        final int todayMonth = today.get( Calendar.MONTH ) + 1;
        final int todayDate = today.get( Calendar.DAY_OF_MONTH );
        final int todayYear = today.get( Calendar.YEAR );
        final String todayString = String.format( "%02d.%02d.%04d", todayMonth, todayDate, todayYear );
        System.out.println( "Today String: " + todayString );
        mvc.perform( get( "/api/v1/logentriesrange/" + todayString + "/" + todayString + "/" ) )
                .andExpect( status().isOk() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON_UTF8_VALUE ) );
    }

}
