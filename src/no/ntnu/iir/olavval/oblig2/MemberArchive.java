package no.ntnu.iir.olavval.oblig2;

import java.time.LocalDate;

public class MemberArchive {
    protected static final int SILVER_LIMIT = 25000;
    protected static final int GOLD_LIMIT = 75000;

    /**
     * Member archive registry.
     */
    public MemberArchive()
    {

    }
    /**
     * Imitates a member logging in to check their point balance.
     * Requires a valid user number and password.
     * @param memberNo The users membership number.
     * @param passwd The users password.
     * @return Number of points held by the member, or -1 if password or memberNo was invalid.
     */
    private int findPoints(int memberNo, String passwd)
    {
        return -1; // TODO add tests
    }

    /**
     * Adds the specified number of points to a member.
     * Any bonus multipliers are factored automatically.
     * @param memberNo
     * @param points
     * @return True if points were succesfully added to member account, false if memberNo was invalid.
     */
    private boolean registerPoints(int memberNo, int points)
    {
        return false; //TODO add tests
    }

    /**
     * Creates a new member account using the personal details and date provided.
     * Returns the unique membership number generated for the member.
     * @param person The personal details of the member.
     * @param dateEnrolled The date of first enrollment into the program.
     * @return the unique membership number of the member.
     */
    private int addMember(Personals person, LocalDate dateEnrolled)
    {
        return findAvailableNo();
    }

    /**
     * Generates a unique membership number.
     * @return a unique membership number.
     */
    private int findAvailableNo()
    {
        return 100;
    }

    /**
     * Main method of BonusMember program app.
     * @param args
     */
    public static void main(String[] args){

    }
}
