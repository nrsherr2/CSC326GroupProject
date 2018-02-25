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
import edu.ncsu.csc.itrust2.models.enums.BloodType;
import edu.ncsu.csc.itrust2.models.enums.Ethnicity;
import edu.ncsu.csc.itrust2.models.enums.Gender;
import edu.ncsu.csc.itrust2.models.enums.HouseholdSmokingStatus;
import edu.ncsu.csc.itrust2.models.enums.PatientSmokingStatus;
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
        BasicHealthMetrics temp = null;

        assertNull( mets );
        assertNull( temp );

        mets = new BasicHealthMetrics();

        mets.hashCode();

        assertFalse( mets.equals( temp ) );

        temp = new BasicHealthMetrics();

        temp.hashCode();

        assertFalse( mets.equals( new Patient() ) );

        temp.setDiastolic( 1 );
        assertFalse( mets.equals( temp ) );

        temp.hashCode();

        mets.setDiastolic( 2 );

        assertFalse( mets.equals( temp ) );

        mets = new BasicHealthMetrics();
        temp = new BasicHealthMetrics();
        temp.setHcp( new User( "hcpUsername", "hcpPassword", Role.ROLE_HCP, 1 ) );
        temp.hashCode();
        assertFalse( mets.equals( temp ) );
        mets.setHcp( new User( "other", "other", Role.ROLE_HCP, 1 ) );
        assertFalse( mets.equals( temp ) );

        mets = new BasicHealthMetrics();
        temp = new BasicHealthMetrics();
        temp.setHdl( 1 );
        temp.hashCode();
        assertFalse( mets.equals( temp ) );
        mets.setHdl( 2 );
        assertFalse( mets.equals( temp ) );

        mets = new BasicHealthMetrics();
        temp = new BasicHealthMetrics();
        temp.setHeadCircumference( (float) 1.1 );
        temp.hashCode();
        assertFalse( mets.equals( temp ) );
        mets.setHeadCircumference( (float) 1.2 );
        assertFalse( mets.equals( temp ) );

        mets = new BasicHealthMetrics();
        temp = new BasicHealthMetrics();
        temp.setHeight( (float) 2.1 );
        temp.hashCode();
        assertFalse( mets.equals( temp ) );
        mets.setHeight( (float) 3.3 );
        assertFalse( mets.equals( temp ) );

        mets = new BasicHealthMetrics();
        temp = new BasicHealthMetrics();
        temp.setHouseSmokingStatus( HouseholdSmokingStatus.INDOOR );
        temp.hashCode();
        assertFalse( mets.equals( temp ) );
        mets.setHouseSmokingStatus( HouseholdSmokingStatus.OUTDOOR );
        assertFalse( mets.equals( temp ) );

        mets = new BasicHealthMetrics();
        temp = new BasicHealthMetrics();
        temp.setLdl( 1 );
        temp.hashCode();
        assertFalse( mets.equals( temp ) );
        mets.setLdl( 2 );
        assertFalse( mets.equals( temp ) );

        mets = new BasicHealthMetrics();
        temp = new BasicHealthMetrics();
        temp.setPatient( new User( "user", "pw", Role.ROLE_PATIENT, 1 ) );
        temp.hashCode();
        assertFalse( mets.equals( temp ) );
        mets.setPatient( new User( "fake", "faux", Role.ROLE_PATIENT, 1 ) );
        assertFalse( mets.equals( temp ) );

        mets = new BasicHealthMetrics();
        temp = new BasicHealthMetrics();
        temp.setPatientSmokingStatus( PatientSmokingStatus.EVERYDAY );
        temp.hashCode();
        assertFalse( mets.equals( temp ) );
        mets.setPatientSmokingStatus( PatientSmokingStatus.NEVER );
        assertFalse( mets.equals( temp ) );

        mets = new BasicHealthMetrics();
        temp = new BasicHealthMetrics();
        temp.setSystolic( new Integer( 1 ) );
        temp.hashCode();
        assertFalse( mets.equals( temp ) );
        mets.setSystolic( new Integer( 2 ) );
        assertFalse( mets.equals( temp ) );

        mets = new BasicHealthMetrics();
        temp = new BasicHealthMetrics();
        temp.setTri( new Integer( 100 ) );
        temp.hashCode();
        assertFalse( mets.equals( temp ) );
        mets.setTri( new Integer( 200 ) );
        assertFalse( mets.equals( temp ) );

        mets = new BasicHealthMetrics();
        temp = new BasicHealthMetrics();
        temp.setWeight( (float) 1.1 );
        temp.hashCode();
        assertFalse( mets.equals( temp ) );
        mets.setWeight( (float) 2.2 );
        assertFalse( mets.equals( temp ) );

        temp = mets;
        assertTrue( mets.equals( temp ) );
    }

    @Test
    public void testOfficeVisit () throws NumberFormatException, ParseException {
        final User patti = new User( "patient", "ppwd", Role.ROLE_PATIENT, 1 );
        final User hcpuser = new User( "husn", "hpwd", Role.ROLE_HCP, 1 );

        final Calendar dob = Calendar.getInstance();
        dob.set( 1997, Calendar.JULY, 2, 16, 43 );

        final Patient p = new Patient( patti );
        p.setDateOfBirth( dob );
        p.setAddress1( "101 Address Court" );
        p.setAddress2( "P.O. Box 202" );
        p.setBloodType( BloodType.ABPos );
        p.setCauseOfDeath( "iTrust2" );
        p.setCity( "MyCity" );

        p.setEmail( "csc326.gps2018.g4@gmail.com" );
        p.setEthnicity( Ethnicity.AfricanAmerican );
        p.setFather( new User() );
        p.setFirstName( "Firstname" );
        p.setGender( Gender.NotSpecified );
        p.setId( 12345L );
        p.setLastName( "Lastname" );
        p.setMother( new User() );
        p.setPhone( "919-555-5555" );
        p.setPreferredName( "Preferredname" );
        p.setSelf( new User() );
        p.setState( State.NC );
        p.setZip( "27606" );

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

        final OfficeVisitForm myform = new OfficeVisitForm( visit );

        assertEquals( "patient", visit.getPatient().getUsername() );
        assertEquals( "patient", patti.getUsername() );
        assertFalse( myform.equals( form ) );

        assertEquals( patti.getUsername(), myform.getPatient() );

        try {
            final OfficeVisit newVisit = new OfficeVisit( myform );
        }
        catch ( final Exception e ) {
            assertNotNull( e.getMessage() );
        }

        myform.setPreScheduled( null );
        try {
            final OfficeVisit newVisit = new OfficeVisit( myform );
            assertFalse( newVisit.equals( visit ) );
        }
        catch ( final Exception e ) {
            assertEquals( null, myform.getPreScheduled() );
        }

        myform.setDiagnoses( null );
        try {
            final OfficeVisit newVisit = new OfficeVisit( myform );
            assertEquals( myform.getPatient(), newVisit.getPatient().getUsername() );
            User u = newVisit.getPatient();
            u = User.getByNameAndRole( u.getUsername(), Role.ROLE_PATIENT );
            assertFalse( newVisit.equals( visit ) );
        }
        catch ( final Exception e ) {
            assertEquals( null, myform.getDiagnoses() );
        }

    }

}
