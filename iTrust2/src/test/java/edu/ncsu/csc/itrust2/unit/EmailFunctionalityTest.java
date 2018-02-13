package edu.ncsu.csc.itrust2.unit;

import static org.junit.Assert.*;

import javax.mail.MessagingException;

import org.junit.Test;

import edu.ncsu.csc.itrust2.utils.EmailUtil;

public class EmailFunctionalityTest {

    @Test
    public void testSendEmail () {
        final String destination = "csc326.gps2018.g4@gmail.com";
        final String subject = "test";
        final String body = "According to all known laws of aviation,\n" + "there is no way a bee\n"
                + "should be able to fly.\n" + "  \n" + "Its wings are too small to get\n"
                + "its fat little body off the ground.\n" + "  \n" + "The bee, of course, flies anyway\n" + "  \n"
                + "because bees don't care\n" + "what humans think is impossible.";
        try {
            EmailUtil.sendEmail( destination, subject, body );
            System.out.println(
                    "If you are in the GP-201-4 group or have access to their throwaway email, check that email's inbox to see if they got that message." );
            System.out.println(
                    "If you are a group that came after us, change the destination field to whatever email you want to send it to" );
        }
        catch ( MessagingException e ) {
            fail( "Something went wrong when sending email" + e.getMessage() );
        }

    }
}
