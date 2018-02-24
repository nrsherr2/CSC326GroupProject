package edu.ncsu.csc.itrust2.models.persistent;

/**
 * This class is the object that holds the data needed to be stored in an access
 * log.
 *
 * @author cmharri5
 */
public class AccessLogData {

    private String accessor;     // = userLog.get( 0 ).getPrimaryUser();
    private String accessorRole; // =
                                 // Role.ROLE_PATIENT.getLanding().split(
                                 // "," )[0];
    private String dateandtime;  // = userLog.get( 0
                                 // ).getTime().toString();
    private String transType;    // =
                                 // TransactionType.ACCOUNT_LOCKOUT_EMAIL_SENT.getDescription();

    /**
     * Empty constructor of Access Log
     */
    public AccessLogData () {
        // empty constructor
        setAccessor( "" );
        setAccessorRole( "" );
        setDateandtime( "" );
        setTransType( "" );
    }

    public AccessLogData ( final String accessor, final String accessorRole, final String dateandtime,
            final String transType ) {
        setAccessor( accessor );
        setAccessorRole( accessorRole );
        setDateandtime( dateandtime );
        setTransType( transType );
    }

    private String getAccessor () {
        return accessor;
    }

    private void setAccessor ( final String accessor ) {
        this.accessor = accessor;
    }

    private String getAccessorRole () {
        return accessorRole;
    }

    private void setAccessorRole ( final String accessorRole ) {
        this.accessorRole = accessorRole;
    }

    private String getDateandtime () {
        return dateandtime;
    }

    private void setDateandtime ( final String dateandtime ) {
        this.dateandtime = dateandtime;
    }

    private String getTransType () {
        return transType;
    }

    private void setTransType ( final String transType ) {
        this.transType = transType;
    }

}
