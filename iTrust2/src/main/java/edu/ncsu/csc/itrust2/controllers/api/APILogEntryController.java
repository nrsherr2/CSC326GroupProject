package edu.ncsu.csc.itrust2.controllers.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import edu.ncsu.csc.itrust2.models.enums.TransactionType;
import edu.ncsu.csc.itrust2.models.persistent.AccessLogData;
import edu.ncsu.csc.itrust2.models.persistent.LogEntry;
import edu.ncsu.csc.itrust2.models.persistent.User;
import edu.ncsu.csc.itrust2.utils.LoggerUtil;

/**
 * REST controller for interacting with Log Entry-related endpoints This will
 * have somewhat reduced functionality compared to the other controllers because
 * we don't want users to be able to delete logged events (_even_ if they are
 * Personnel/an admin)
 *
 * @author Kai Presler-Marshall
 *
 */
@RestController
@SuppressWarnings ( { "unchecked", "rawtypes" } )
public class APILogEntryController extends APIController {

    /**
     * Retrieves and returns a List of all LogEntries in the system
     *
     * @return list of log entries
     */
    @GetMapping ( BASE_PATH + "/logentries" )
    public List<LogEntry> getLogEntries () {
        return LogEntry.getLogEntries();
    }

    // ***** Below is what Cameron editted ***** //
    /**
     * Retrieves and returns a List of all LogEntries in the system for a given
     * user, as AccessLogData
     *
     * @return list of log entries
     */
    // @PreAuthorize ( "hasRole('ROLE_PATIENT')" )
    @GetMapping ( BASE_PATH + "/logentries/patient" )
    public List<AccessLogData> getLogEntriesForPatient () {
        final String user = LoggerUtil.currentUser();
        LoggerUtil.log( TransactionType.VIEW_LOG_EVENTS, user );
        final List<LogEntry> userLog = new ArrayList<LogEntry>();
        final int len = LogEntry.getAllForUser( user ).size();
        for ( int i = 0; i < len; i++ ) {
            userLog.add( LogEntry.getAllForUser( user ).get( len - i - 1 ) );
        }

        final List<AccessLogData> dataList = new ArrayList<AccessLogData>();

        for ( int i = 0; i < userLog.size(); i++ ) {
            final String accessor = userLog.get( i ).getPrimaryUser(); // username
            String role = "Self";
            if ( !accessor.equals( user ) ) {
                role = User.getByName( accessor /* username */ ).getRole().getLanding().split( "," )[0];
                final String first = role.substring( 0, 1 ).toUpperCase();
                role = role.split( "/" )[0];
                role = first + role.substring( 1 );
            }
            final String date = userLog.get( i ).getTime().getTime().toString();
            final String type = userLog.get( i ).getLogCode().getDescription();

            final AccessLogData data = new AccessLogData( accessor, role, date, type );
            dataList.add( data );
        }

        return dataList;
    }

    // ***** Above is from Cameron's edits ***** //
    /**
     * Retrieves and returns a list of the top ten log entries for a user, as
     * AccessLogData
     *
     * @return the list of the top ten log entries for the user
     */
    @GetMapping ( BASE_PATH + "/logentries/patient10" ) // {username} instead of
    // patient
    public List<AccessLogData> getTop10ForPatient () {
        final String user = LoggerUtil.currentUser();
        // this will need to change to be more specific when changing between
        // different users ^^
        final List<LogEntry> userLog = new ArrayList<LogEntry>();
        final int len = LogEntry.getAllForUser( user ).size();
        for ( int i = 0; i < len; i++ ) {
            userLog.add( LogEntry.getAllForUser( user ).get( len - i - 1 ) );
        }

        final List<AccessLogData> dataList = new ArrayList<AccessLogData>();
        final int listSize = userLog.size() < 10 ? userLog.size() : 10;
        for ( int i = 0; i < listSize; i++ ) {
            final String accessor = userLog.get( i ).getPrimaryUser(); // username
            String role = "Self";
            if ( !accessor.equals( user ) ) {
                role = User.getByName( accessor /* username */ ).getRole().getLanding().split( "," )[0];
                final String first = role.substring( 0, 1 ).toUpperCase();
                role = role.split( "/" )[0];
                role = first + role.substring( 1 );
            }
            final String date = userLog.get( i ).getTime().getTime().toString();
            final String type = userLog.get( i ).getLogCode().getDescription();

            final AccessLogData data = new AccessLogData( accessor, role, date, type );
            dataList.add( data );
        }

        return dataList;
    }

    /**
     * Retrieves and returns a list of log entries for a user by date range, as
     * AccessLogData
     *
     * @param startDate
     *            The beginning of the date range for the list of LogEntries,
     *            inclusive
     * @param endDate
     *            The end of the date range for the list of LogEntries,
     *            inclusive
     * @return the list of log entries for the user within the date range
     */
    @GetMapping ( BASE_PATH + "/logentriesrange/{startDate}/{endDate}/" )
    public List<AccessLogData> getEntriesinRange ( @PathVariable ( "startDate" ) final String startDate,
            @PathVariable ( "endDate" ) final String endDate ) {
        final String user = LoggerUtil.currentUser();
        LoggerUtil.log( TransactionType.VIEW_LOG_EVENTS, user );
        final SimpleDateFormat sdf = new SimpleDateFormat( "MM.dd.yyyy", Locale.ENGLISH );
        Date parsedDate = null;
        try {
            parsedDate = sdf.parse( startDate );
        }
        catch ( final ParseException e ) {
            // Ignore, Hibernate will catch the null date
        }
        final Calendar beginCalendar = Calendar.getInstance();
        beginCalendar.setTime( parsedDate );
        try {
            parsedDate = sdf.parse( endDate );
        }
        catch ( final ParseException e ) {
            // Ignore, Hibernate will catch the null date
        }
        final Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime( parsedDate );
        endCalendar.add( Calendar.DAY_OF_MONTH, 1 );
        final List<LogEntry> logList = LoggerUtil.getForUserInDateRange( User.getByName( LoggerUtil.currentUser() ),
                beginCalendar, endCalendar );
        final List<AccessLogData> dataList = new ArrayList<AccessLogData>();

        for ( int i = 0; i < logList.size(); i++ ) {
            final String accessor = logList.get( i ).getPrimaryUser(); // username
            String role = "Self";
            if ( !accessor.equals( user ) ) {
                role = User.getByName( accessor /* username */ ).getRole().getLanding().split( "," )[0];
                final String first = role.substring( 0, 1 ).toUpperCase();
                role = role.split( "/" )[0];
                role = first + role.substring( 1 );
            }
            final String date = logList.get( i ).getTime().getTime().toString();
            final String type = logList.get( i ).getLogCode().getDescription();

            final AccessLogData data = new AccessLogData( accessor, role, date, type );
            dataList.add( data );
        }

        return dataList;
    }

    /**
     * Retrieves and returns a specific log entry specified by the id provided.
     *
     * @param id
     *            The id of the log entry, as generated by Hibernate and used as
     *            the primary key
     * @return response
     */
    // @GetMapping ( BASE_PATH + "/logentries/{id}" )
    // public ResponseEntity getEntry ( @PathVariable ( "id" ) final Long id ) {
    // final LogEntry entry = LogEntry.getById( id );
    // return null == entry
    // ? new ResponseEntity( errorResponse( "No log entry found for id " + id ),
    // HttpStatus.NOT_FOUND )
    // : new ResponseEntity( entry, HttpStatus.OK );
    // }

}
