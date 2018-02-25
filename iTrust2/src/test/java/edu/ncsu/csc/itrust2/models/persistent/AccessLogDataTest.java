package edu.ncsu.csc.itrust2.models.persistent;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AccessLogDataTest {

    /**
     * This tests the AccessLogData and its methods
     */
    @Test
    public void testAccessLogData () {
        AccessLogData data = new AccessLogData();
        assertTrue( data.getAccessor().isEmpty() );
        assertTrue( data.getAccessorRole().isEmpty() );
        assertTrue( data.getDateandtime().isEmpty() );
        assertTrue( data.getTransType().isEmpty() );
        AccessLogData newData = new AccessLogData( data.getAccessor(), data.getAccessorRole(), data.getDateandtime(),
                data.getTransType() );
        assertTrue( data.equals( newData ) );

        final int temp = 0;
        assertFalse( data.equals( temp ) );

        newData.setAccessor( null );
        newData.setAccessorRole( null );
        newData.setDateandtime( null );
        newData.setTransType( null );

        data = null;
        assertFalse( newData.equals( data ) );

        // data = new AccessLogData();
        // data.setAccessor( "asdf" );
        // newData.hashCode();
        // assertFalse( data.equals( newData ) );
        // data.setAccessor( "fdsa" );
        // assertFalse( data.equals( newData ) );

        data = new AccessLogData();
        newData = new AccessLogData();
        data.setAccessor( null );
        data.hashCode();
        newData.setAccessor( "asdf" );
        newData.hashCode();
        assertFalse( data.equals( newData ) );
        data.setAccessor( "fdsa" );
        assertFalse( data.equals( newData ) );

        data = new AccessLogData();
        newData = new AccessLogData();
        data.setAccessor( null );
        newData.setAccessor( null ); // additino
        data.setAccessorRole( null );
        data.hashCode();
        newData.setAccessorRole( "asdf" );
        newData.hashCode();
        assertFalse( data.equals( newData ) );
        data.setAccessorRole( "fdsa" );
        assertFalse( data.equals( newData ) );

        data = new AccessLogData();
        newData = new AccessLogData();
        data.setAccessor( null );
        newData.setAccessor( null ); // additino
        data.setAccessorRole( null );
        newData.setAccessorRole( null ); // additino
        data.setDateandtime( null );
        data.hashCode();
        newData.setDateandtime( "asdf" );
        newData.hashCode();
        assertFalse( data.equals( newData ) );
        data.setDateandtime( "fdsa" );
        assertFalse( data.equals( newData ) );

        data = new AccessLogData();
        newData = new AccessLogData();
        data.setAccessor( null );
        newData.setAccessor( null ); // additino
        data.setAccessorRole( null );
        newData.setAccessorRole( null ); // additino
        data.setDateandtime( null );
        newData.setDateandtime( null );
        data.setTransType( null );
        data.hashCode();
        newData.setTransType( "asdf" );
        newData.hashCode();
        assertFalse( data.equals( newData ) );
        data.setTransType( "fdsa" );
        assertFalse( data.equals( newData ) );

        data.setAccessor( null );
        newData.setAccessor( null ); // additino
        data.setAccessorRole( null );
        newData.setAccessorRole( null ); // additino
        data.setDateandtime( null );
        newData.setDateandtime( null );
        data.setTransType( null );
        newData.setTransType( null );
        assertTrue( data.equals( newData ) );

        newData = data;

        assertTrue( data.equals( newData ) );

    }

}
