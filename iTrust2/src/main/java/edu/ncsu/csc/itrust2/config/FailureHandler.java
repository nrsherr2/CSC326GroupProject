package edu.ncsu.csc.itrust2.config;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import edu.ncsu.csc.itrust2.models.enums.TransactionType;
import edu.ncsu.csc.itrust2.models.persistent.LoginAttempt;
import edu.ncsu.csc.itrust2.models.persistent.LoginBan;
import edu.ncsu.csc.itrust2.models.persistent.LoginLockout;
import edu.ncsu.csc.itrust2.models.persistent.Patient;
import edu.ncsu.csc.itrust2.models.persistent.Personnel;
import edu.ncsu.csc.itrust2.models.persistent.User;
import edu.ncsu.csc.itrust2.utils.EmailUtil;
import edu.ncsu.csc.itrust2.utils.LoggerUtil;

/**
 * Custom AuthenticationFailureHandler to record Failed attempts, and lockout or
 * ban a user or IP if necessary.
 *
 * @author Thomas
 *
 */
public class FailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure ( final HttpServletRequest request, final HttpServletResponse response,
            final AuthenticationException ae ) throws IOException, ServletException {
        /*
         * Credit for username to
         * https://stackoverflow.com/questions/8676206/how-can-i-get-the
         * -username-from-a-failed-login-using-spring-security
         */
        final String username = request
                .getParameter( UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY );
        User user = null;
        final String addr = request.getRemoteAddr();

        if ( ae instanceof BadCredentialsException ) {
            // need to lockout IP
            if ( LoginAttempt.getIPFailures( addr ) >= 5 ) {
                LoginAttempt.clearIP( addr );
                // Check if need to ban IP
                if ( LoginLockout.getRecentIPLockouts( addr ) >= 2 ) {
                    // BAN
                    final LoginBan ban = new LoginBan();
                    ban.setIp( addr );
                    ban.setTime( Calendar.getInstance() );
                    ban.save();
                    LoginLockout.clearIP( addr );
                    LoggerUtil.log( TransactionType.IP_BANNED, addr, null, addr + " has been banned." );
                    this.getRedirectStrategy().sendRedirect( request, response, "/login?ipbanned" );
                }
                else {
                    // lockout IP.
                    final LoginLockout lockout = new LoginLockout();
                    lockout.setIp( addr );
                    lockout.setTime( Calendar.getInstance() );
                    lockout.save();
                    LoggerUtil.log( TransactionType.IP_LOCKOUT, addr, null, addr + " has been locked out for 1 hour." );
                    try {
                        EmailUtil.sendEmail( "cameroncrazy13@gmail.com", "Error",
                                "Hey dude, you're locked out. Get it together." );
                    }
                    catch ( final MessagingException e ) {
                        e.printStackTrace();
                    }
                    this.getRedirectStrategy().sendRedirect( request, response, "/login?iplocked" );

                }
                return;
            }
            else {
                // fail for IP
                final LoginAttempt attempt = new LoginAttempt();
                attempt.setTime( Calendar.getInstance() );
                attempt.setIp( addr );
                attempt.save();
            }

            // check username
            if ( username != null ) {
                user = User.getByName( username );
            }

            if ( user != null ) {
                // check if need to lockout username
                if ( LoginAttempt.getUserFailures( user ) >= 2 ) {
                    LoginAttempt.clearUser( user );
                    // check if need to ban user
                    if ( LoginLockout.getRecentUserLockouts( user ) >= 2 ) {
                        LoginLockout.clearUser( user );
                        final LoginBan ban = new LoginBan();
                        ban.setTime( Calendar.getInstance() );
                        ban.setUser( user );
                        ban.save();
                        LoggerUtil.log( TransactionType.USER_BANNED, username, null, username + " has been banned." );
                        try {
                            String email = "";
                            String name = "";
                            final List<Personnel> faculty = Personnel.getPersonnel();
                            Personnel person = null;
                            for ( int i = 0; i < faculty.size(); i++ ) {
                                if ( faculty.get( i ).getSelf().getId().equals( ban.getUser().getId() ) ) {
                                    person = faculty.get( i );
                                    break;
                                }
                            }

                            if ( person != null ) {
                                email = person.getEmail();
                                name = person.getFirstName();
                            }
                            else {
                                Patient pat = null;
                                final List<Patient> patientList = Patient.getPatients();
                                for ( int i = 0; i < patientList.size(); i++ ) {
                                    if ( patientList.get( i ).getSelf().getId().equals( ban.getUser().getId() ) ) {
                                        pat = patientList.get( i );
                                        break;
                                    }
                                }

                                if ( pat != null ) {
                                    email = pat.getEmail();
                                    name = pat.getFirstName();
                                }
                                else {
                                    try {
                                        throw new Exception( "No patient or Personnel on file for " + ban.getId() );
                                    }
                                    catch ( final Exception e ) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            EmailUtil.sendEmail( email, "Error", "Hey " + name + ", you're banned. Tough luck." );
                            LoggerUtil.log( TransactionType.ACCOUNT_LOCKOUT_EMAIL_SENT, username,
                                    TransactionType.ACCOUNT_LOCKOUT_EMAIL_SENT.getDescription() );
                        }
                        catch ( final MessagingException e ) {
                            e.printStackTrace();
                        }
                        this.getRedirectStrategy().sendRedirect( request, response, "/login?banned" );
                    }
                    else {
                        // lockout user
                        final LoginLockout lock = new LoginLockout();
                        lock.setTime( Calendar.getInstance() );
                        lock.setUser( user );
                        lock.save();
                        LoggerUtil.log( TransactionType.USER_LOCKOUT, username, null,
                                username + " has been locked out for 1 hour." );

                        try {
                            String email = "";
                            String name = "";
                            final List<Personnel> faculty = Personnel.getPersonnel();
                            Personnel person = null;
                            for ( int i = 0; i < faculty.size(); i++ ) {
                                if ( faculty.get( i ).getSelf().getId().equals( lock.getUser().getId() ) ) {
                                    person = faculty.get( i );
                                    break;
                                }
                            }

                            if ( person != null ) {
                                email = person.getEmail();
                                name = person.getFirstName();
                            }
                            else {
                                Patient pat = null;
                                final List<Patient> patientList = Patient.getPatients();
                                for ( int i = 0; i < patientList.size(); i++ ) {
                                    if ( patientList.get( i ).getSelf().getId().equals( lock.getUser().getId() ) ) {
                                        pat = patientList.get( i );
                                        break;
                                    }
                                }

                                if ( pat != null ) {
                                    email = pat.getEmail();
                                    name = pat.getFirstName();
                                }
                                else {
                                    try {
                                        LoggerUtil.log( TransactionType.MISSING_EMAIL_NOT_SENT, username,
                                                TransactionType.MISSING_EMAIL_NOT_SENT.getDescription() );
                                        throw new Exception( "No patient or Personnel on file for " + lock.getId() );
                                    }
                                    catch ( final Exception e ) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            EmailUtil.sendEmail( email, "Error",
                                    "Hey " + name + ", you're locked out. Get it together." );
                            LoggerUtil.log( TransactionType.ACCOUNT_LOCKOUT_EMAIL_SENT, username,
                                    TransactionType.ACCOUNT_LOCKOUT_EMAIL_SENT.getDescription() );
                        }
                        catch ( final MessagingException e ) {
                            e.printStackTrace();
                        }
                        this.getRedirectStrategy().sendRedirect( request, response, "/login?locked" );
                    }
                    return;
                }
                else {
                    // fail for username
                    final LoginAttempt attempt = new LoginAttempt();
                    attempt.setTime( Calendar.getInstance() );
                    attempt.setUser( user );
                    attempt.save();
                }
            }

        }
        else if ( ae instanceof DisabledException ) {
            if ( username != null ) {
                user = User.getByName( username );
            }
            if ( user != null ) {
                // redirect to user lockout or user ban
                if ( LoginBan.isUserBanned( user ) ) {
                    this.getRedirectStrategy().sendRedirect( request, response, "/login?banned" );
                    return;
                }
                else if ( LoginLockout.isUserLocked( user ) ) {
                    this.getRedirectStrategy().sendRedirect( request, response, "/login?locked" );
                    return;
                }
                // else, otherwise disabled
            }

            this.getRedirectStrategy().sendRedirect( request, response, "/login?locked" );
            return;
        }
        this.getRedirectStrategy().sendRedirect( request, response, "/login?error" );
    }

}
