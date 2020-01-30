package no.ntnu.iir.olavval.oblig2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class MemberArchive {
    public static final int SILVER_LIMIT = 25000;
    public static final int GOLD_LIMIT = 75000;
    private static final Random RANDOM_NUMBER = new Random();

    private ArrayList<BonusMember> members;

    /**
     * Member archive registry.
     */
    public MemberArchive()
    {
        members = new ArrayList<>();

    }
    /**
     * Imitates a member logging in to check their point balance.
     * Requires a valid user number and password.
     * @param memberNo The users membership number.
     * @param passwd The users password.
     * @return Number of points held by the member, or -1 if password or memberNo was invalid.
     */
    public int findPoints(int memberNo, String passwd)
    {
        return -1; // TODO add tests
    }

    /**
     * Adds the specified number of points to a member.
     * Any bonus multipliers are factored automatically.
     * @param memberNo the membership number of the member.
     * @param points the number of points to be added to the member.
     * @return True if points were succesfully added to member account, false if memberNo was invalid.
     */
    public boolean registerPoints(int memberNo, int points)
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
    public int addMember(Personals person, LocalDate dateEnrolled)
    {
        return findAvailableNo();
    }

    /**
     * Checks member registry for members that are eligible for upgrade to silver or gold level.
     * @param testDate Date to test member enrollment date with.
     */
    public void checkMembers(LocalDate testDate)
    {
        //TODO implement instanceof check for basic, silver and gold members,
        // and call methods accordingly.
    }

    /**
     * Generates a unique membership number.
     * @return a unique membership number.
     */
    private int findAvailableNo()
    {
        return 100;
        //TODO find a way to test that this always returns an unused number
        // Keep an array of used numbers? Or maybe use a HashMap for member storage instead
        // of an ArrayList?
    }

    //TODO Is this a good way to handle this upgrading members?
    // Perhaps checks of point limits should be performed before sending member to be upgraded.

    /**
     * Checks if a member is eligible for upgrade to silver level.
     * @param memberNo the number of the member to be checked.
     * @param testDate the date to test the members enrollment date with.
     * @return New silver level member object if member was upgradeable,
     * or the unchanged member if it was not.
     */
    private BonusMember checkSilverLimit(int memberNo, LocalDate testDate)
    {
        return null;
        //TODO Basically just check if member.findQualificationPoints returns more than SILVER_LIMIT?
    }

    /**
     * Checks if a member is eligible for upgrade to gold level.
     * @param memberNo the number of the member to be checked.
     * @param testDate the date to test the members enrollment date with.
     * @return New gold level member object if member was upgradeable,
     * or the unchanged member if it was not.
     */
    private BonusMember checkGoldLimit(int memberNo, LocalDate testDate)
    {
        return null;
        //TODO Basically just check if member.findQualificationPoints returns more than GOLD_LIMIT?

    }

    /**
     * Main method of BonusMember program app.
     * @param args
     */
    public static void main(String[] args){


    }
}
