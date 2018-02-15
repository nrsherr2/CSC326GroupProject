package edu.ncsu.csc.itrust2.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.TransientObjectException;
import org.junit.Test;

import edu.ncsu.csc.itrust2.forms.hcp.OfficeVisitForm;
import edu.ncsu.csc.itrust2.models.enums.Role;
import edu.ncsu.csc.itrust2.models.enums.State;
import edu.ncsu.csc.itrust2.models.enums.TransactionType;
import edu.ncsu.csc.itrust2.models.persistent.BasicHealthMetrics;
import edu.ncsu.csc.itrust2.models.persistent.Diagnosis;
import edu.ncsu.csc.itrust2.models.persistent.DomainObject;
import edu.ncsu.csc.itrust2.models.persistent.Hospital;
import edu.ncsu.csc.itrust2.models.persistent.ICDCode;
import edu.ncsu.csc.itrust2.models.persistent.LogEntry;
import edu.ncsu.csc.itrust2.models.persistent.OfficeVisit;
import edu.ncsu.csc.itrust2.models.persistent.Patient;
import edu.ncsu.csc.itrust2.models.persistent.User;

public class DomainObjectTest {

    @Test
    public void testCreateDomainObject () {
        final Hospital h = new Hospital();
        h.setAddress( "2770 Wolf Village Drive, Raleigh" );
        h.setState( State.NC );
        h.setZip( "27607" );
        h.setName( "iTrust Test Hospital" );
        h.save();

        final Hospital retrieve = Hospital.getByName( "iTrust Test Hospital" );
        assertNotNull( retrieve );
        assertEquals( retrieve.getState(), State.NC );

    }

    @Test
    public void testRetrieveDomainObject () {
        final LogEntry le = new LogEntry();
        le.setLogCode( TransactionType.LOGIN_SUCCESS );
        le.setMessage( "User has logged in" );
        le.setPrimaryUser( "test" );
        le.setTime( Calendar.getInstance() );
        le.save();

        final LogEntry retrieve = (LogEntry) DomainObject.getBy( LogEntry.class, "primaryUser", "test" );
        assertNotNull( retrieve );
        assertEquals( retrieve.getLogCode(), TransactionType.LOGIN_SUCCESS );

    }

    @Test
    public void testDelete () {
        final Hospital h = new Hospital();
        h.setAddress( "2770 Wolf Village Drive, Raleigh" );
        h.setState( State.NC );
        h.setZip( "27607" );
        h.setName( "iTrust Test Hospital 2: Electric Boogaloo" );
        h.save();

        h.delete();
        final Hospital retrieve = Hospital.getByName( "iTrust Test Hospital 2: Electric Boogaloo" );
        assertNull( retrieve );

    }

    @Test
    public void testBasicHealthMetrics () {
        BasicHealthMetrics mets = null;
        final BasicHealthMetrics temp = new BasicHealthMetrics();
        assertNull( mets );
        mets = new BasicHealthMetrics();
        mets.hashCode();
        assertTrue( mets.equals( temp ) );
        // mets.delete();
        mets.setDiastolic( 1 );
        mets.hashCode();
        assertFalse( mets.equals( temp ) );

        assertNotNull( mets );
    }

    @Test
    public void testOfficeVisit () throws NumberFormatException, ParseException {
        final User patti = new User( "pusn", "ppwd", Role.ROLE_PATIENT, 1 );
        final User hcpuser = new User( "husn", "hpwd", Role.ROLE_HCP, 1 );

        Patient p = new Patient();
        p = Patient.getPatient( patti.getUsername() );

        Calendar.getInstance().set( 1997, Calendar.JULY, 2, 16, 43 );
        final Calendar dob = Calendar.getInstance();

        Calendar.getInstance().set( 2018, Calendar.FEBRUARY, 14, 15, 9 );
        final OfficeVisit visit = new OfficeVisit();
        visit.setHcp( hcpuser );
        visit.setPatient( patti );
        visit.setDate( Calendar.getInstance() );
        visit.setId( 1234321L );

        final List<Diagnosis> list = new ArrayList<Diagnosis>();
        final Diagnosis d = new Diagnosis();
        d.setCode( new ICDCode() );
        d.setId( 123L );
        d.setNote( "You should be careful with this disease." );
        d.setVisit( visit );
        list.add( d );

        final OfficeVisitForm form = new OfficeVisitForm( visit );

        // final OfficeVisit visit2 = new OfficeVisit( form );

        assertEquals( false, visit.equals( 0 ) );

        assertEquals( hcpuser.getUsername(), form.getHcp() );

        visit.setDiagnoses( list );

        assertEquals( "You should be careful with this disease.", visit.diagnoses.get( 0 ).getNote() );

        try {
            visit.delete();
        }
        catch ( final TransientObjectException e ) {
            // empty catch
        }

        assertEquals( "pusn", visit.getPatient().getUsername() );
    }

}
