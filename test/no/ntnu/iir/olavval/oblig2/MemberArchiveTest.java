package no.ntnu.iir.olavval.oblig2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class MemberArchiveTest {

    private LocalDate testDate;
    private LocalDate oleEnrollDate;
    private LocalDate toveEnrollDate;
    private Personals ole;
    private Personals tove;
    private MemberArchive archive;

    @BeforeEach
    void setUp() {

        this.archive = new MemberArchive();

        this.testDate = LocalDate.of(2008, 2, 10);

        this.oleEnrollDate = LocalDate.of(2006, 2, 15);
        this.ole = new Personals("Olsen", "Ole",
                "ole.olsen@dot.com", "ole");

        this.toveEnrollDate = LocalDate.of(2007, 5, 3);
        this.tove = new Personals("Hansen", "Tove",
                "tove.hansen@dot.com", "tove");
    }

    /**
     * Tests positive and negative cases for the addMember method.
     * Asserts failure when either parameter is null.
     * Asserts successfully adding a member by asserting that member number returned by addMember is
     * indeed used as a key for a member object in the collection, and that this member object returns the same
     * number as the one used as its key.
     */
    @Test
    void addMemberTest()
    {
        // negative tests asserting addMember() failure with null parameters
        System.out.println("Test 1: Add member with null dateEnrolled.");
        assertEquals(-1, archive.addMember(ole, null));
        System.out.println("Test 2: Add member with null person.");
        assertEquals(-1, archive.addMember(null, oleEnrollDate));

        // positive test for correctly adding ole as new Bonus member
        // asserts that the member number returned by addMember is the same as the one stored in the "Ole"
        // member object created by addMember.
        System.out.println("Test 3: Adds Ole correctly as new Basic level member.");
        int oleNumber = archive.addMember(ole, oleEnrollDate);
            //add Ole to archive, and store member number.
        assertNotEquals(-1, oleNumber, "Ole number was not -1.");
            // assert that addMember did not fail
        assertEquals(oleNumber, archive.findMember(oleNumber).getMemberNo(), "Ole number was " + oleNumber);
            // assert that a member is found in members HashSet with the memberNo returned by addMember

        System.out.println("Test 4: Adds Tove as silver member to list, and tests for conflicts with the Ole member.");
        assertNotEquals(-1, archive.addMember(tove, toveEnrollDate));
            // adds tove to archive


    }

    /**
     * Tests positive and negative cases for the findPoints method.
     * Bypasses MemberArchive.registerPoints() method, and calls the member object's
     * registerPoints() method directly to avoid dependency on other MemberArchive methods.
     * Does not test for specific member levels, as none of the subclasses override getPoints().
     */
    @Test
    void findPointsTest() {
        int oleMemberNo = archive.addMember(ole, oleEnrollDate);
        System.out.println("Test 5: Check that a new Basic member has 0 points.");
        assertEquals(0, archive.findPoints(oleMemberNo, "ole"));
            // asserts that a newly added basic member does indeed have 0 points.
        int pointsToAdd = 10000;
        archive.findMember(oleMemberNo).registerPoints(pointsToAdd);
            // manually add 10 000 points to Ole, bypassing registerPoints method in MemberArchive
        System.out.println("Test 6: Check that findPoints returns correct amount when balance is not 0.");
        assertEquals(pointsToAdd, archive.findPoints(oleMemberNo, "ole"));
            // asserts that member has received the correct amount of points


    }

    /**
     * Tests positive and negative cases for the registerPoints method.
     * This test bypasses the MemberArchive findPoints method,
     * and calls getPoints directly on the member objects.
     * This is done to isolate tests to the registerPoints method,
     * and eliminate possible side effects caused by findPoints.
     *
     * We create a Basic level member, and tests adding points to it.
     * Also tests for expected failure when method parameters are invalid.
     *
     * Further, the Basic level member is then upgraded to Silver.
     * The points are set to 0, to avoid interference from previous tests.
     * Tests assert that the Silver member receives the expected x1.2
     * multiplier to points.
     *
     * Lastly, the Silver member is upgraded to Gold and points are again set to 0.
     * Finally, we assert that a Gold level member receives the correct x1.5 multiplier to points.
     */
    @Test
    void registerPointsTest() {
        // add new member to archive
        int oleMemberNo = archive.addMember(ole, oleEnrollDate);

        // fetch newly added member from archive, so we can use it directly
        BasicMember b1 = (BasicMember) archive.findMember(oleMemberNo);

        int pointsToAdd = 10000; // number of points to add

        //Basic level member tests

        System.out.println("Test 7: Tests successfully adding points to a member.");
        assertTrue(archive.registerPoints(oleMemberNo, pointsToAdd));
            //asserts that adding points is successful when a valid member number is used
        System.out.println("Test 8: Test that basic member has gained expected number of points.");
        assertEquals(pointsToAdd, b1.getPoints());
            // asserts that basic member has received the correct amount of points, e.g. added == received
        System.out.println("Test 9: Tests failure when adding points to invalid member number.");
        assertFalse(archive.registerPoints(-1, pointsToAdd));
            //asserts failure when member number is invalid
        System.out.println("Test 10: Test failure when adding negative point value.");
        assertFalse(archive.registerPoints(oleMemberNo, -1));
            //asserts failure when points < 0

        //Silver level member tests

        SilverMember s1 = new SilverMember(
                b1.getMemberNo(),
                b1.getPersonals(),
                b1.getEnrolledDate(),
                0); //wipe balance to 0 to eliminate interference from basic level testing
        archive.replaceUpgradedMember(s1); // replaces basic Ole with silver level Ole
        assertFalse(archive.findMember(oleMemberNo) instanceof BasicMember);
        assertTrue(archive.findMember(oleMemberNo) instanceof SilverMember);
            //check that Ole is now Silver level, and not Basic level.
        System.out.println("Ole is now Silver Level.");

        System.out.println("Test 11: Add points to silver level member.");
        assertTrue(archive.registerPoints(oleMemberNo, pointsToAdd));
        System.out.println("Test 12: Test that Silver level member has received expected number of points.");
        assertEquals( pointsToAdd*1.2, s1.getPoints());


        //Gold level member tests

        GoldMember g1 = new GoldMember(
                s1.getMemberNo(),
                s1.getPersonals(),
                s1.getEnrolledDate(),
                0); //wipe balance to 0, to eliminate interference from silver level testing
        archive.replaceUpgradedMember(g1);
        assertFalse(archive.findMember(oleMemberNo) instanceof SilverMember);
        assertTrue(archive.findMember(oleMemberNo) instanceof GoldMember);
        //check that Ole is now Gold level, and not Silver level.
        System.out.println("Ole is now Gold Level.");


        System.out.println("Test 13: Add points to gold level member.");
        assertTrue(archive.registerPoints(oleMemberNo, pointsToAdd));
        System.out.println("Test 14: Test that Gold level member has received expected number of points.");
        assertEquals(pointsToAdd*1.5, g1.getPoints());

        //How do we test silver and gold level members, without also using the private upgrade methods in
        // member archive? Make the putMember method public?

        //Is testing silver/gold required? Silver and gold members have been tested in BonusMemberTest,
        // so by induction MemberArchive.registerPoints() also works, iff it is calling the appropriate
        // subclass' method, rather than the BonusMember superclass.

        //That might be a big iff.. Should it be tested?

    }

    @Test
    void checkMembersTest() {
        // TODO: 03/02/2020 fuuuuuuuuuuuuuuuuuuuck...
    }
}
