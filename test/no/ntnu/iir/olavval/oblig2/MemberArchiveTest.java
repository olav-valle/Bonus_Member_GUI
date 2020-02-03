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

    @Test
    void registerPointsTest() {
        int oleMemberNo = archive.addMember(ole, oleEnrollDate);
        BasicMember b1 = archive.findMember(oleMemberNo);
        int pointsToAdd = 10000;

        System.out.println("Test 7: Tests successfully adding points to a member.");
        assertTrue(archive.registerPoints(oleMemberNo, pointsToAdd));
            //asserts that adding points is successful when a valid member number is used

        // manually add 10 000 points to Ole, bypassing registerPoints method in MemberArchive
        System.out.println("Test 6: Check that findPoints returns correct amount when balance is not 0.");
        assertEquals(pointsToAdd, archive.findPoints(oleMemberNo, "ole"));
        // asserts that member has received the correct amount of points


        // TODO: 03/02/2020 add tests for silver and gold level members

        SilverMember s1 = new SilverMember(
                b1.getMemberNo(),
                b1.getPersonals(),
                b1.getEnrolledDate(),
                b1.getPoints());

        archive.replaceUpgradedMember(s1); //


        //How do we test silver and gold level members, without also using the private upgrade methods in
        // member archive? Make the putMember method public?

        //Is testing silver/gold required? Silver and gold members have been tested in BonusMemberTest,
        // so by induction MemberArchive.registerPoints() also works, iff it is calling the appropriate
        // subclass' method, rather than the BonusMember superclass.

        //That might be a big iff.. Should it be tested?

    }

    @Test
    void checkMembersTest() {
    }
}
