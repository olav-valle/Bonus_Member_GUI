package no.ntnu.iir.olavval.oblig2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class MemberArchiveTest {

  private LocalDate testDate;
  private LocalDate oleEnrollDate;
  private LocalDate toveEnrollDate;
  private LocalDate liseEnrollDate;
  private LocalDate jonasEnrollDate;
  private LocalDate erikEnrollDate;

  private Personals ole;
  private Personals tove;
  private Personals lise;
  private Personals jonas;
  private Personals erik;

  private MemberArchive archive;

  // TODO: 04/02/2020 add verbose prints of object status to all tests
  // TODO: 17/03/2020 rewrite all point adding test to handle exceptions
  //  thrown by MemberArchive.registerPoints()

  @BeforeEach
  void setUp() {

    this.archive = new MemberArchive();

    this.testDate = LocalDate.of(2008, 2, 10);

    this.oleEnrollDate = LocalDate.of(2006, 2, 15);
    this.ole = new Personals("Ole", "Olsen",
            "ole.olsen@dot.com", "ole");

    this.toveEnrollDate = LocalDate.of(2007, 5, 3);
    this.tove = new Personals("Tove", "Hansen",
            "tove.hansen@dot.com", "tove");

    this.liseEnrollDate = testDate; //Same date as test date
    this.lise = new Personals("Lise", "Lisand",
        "lise@lisand.no", "lise");

    this.jonasEnrollDate = oleEnrollDate; //same date as Ole
    this.jonas = new Personals("Jonas", "Johnsen",
            "jon@johnsen.no", "jonny");

    this.erikEnrollDate = LocalDate.of(2007, 3, 1);
    this.erik = new Personals("Erik", "Eriksen",
        "rikken@eriksen.no", "rikkenrules");

  }

  /**
   * Tests positive and negative cases for the addMember method.
   * Asserts failure when either parameter is null.
   * Asserts successfully adding a member by asserting that member number returned by addMember is
   * indeed used as a key for a member object in the collection, and that this member object
   * returns the same number as the one used as its key.
   */
  @Test
  void addMemberTest() {
    // negative tests asserting addMember() failure with null parameters
    System.out.println("Test 1: Add member with null dateEnrolled.");
    assertEquals(-1, archive.addMember(ole, null));
    System.out.println("Test 2: Add member with null person.");
    assertEquals(-1, archive.addMember(null, oleEnrollDate));

    // positive test for correctly adding ole as new Bonus member
    // asserts that the member number returned by addMember
    //  is the same as the one stored in the "Ole"
    // member object created by addMember.
    System.out.println("Test 3: Adds Ole correctly as new Basic level member.");
    int oleNumber = archive.addMember(ole, oleEnrollDate);
    //add Ole to archive, and store member number.
    assertNotEquals(-1, oleNumber, "Ole number was not -1.");
    // assert that addMember did not fail
    assertEquals(oleNumber, archive.findMember(oleNumber).getMemberNo(),
        "Ole number was " + oleNumber);
    // assert that a member is found in members HashSet with the memberNo returned by addMember

    System.out.println("Test 4: Adds Tove as silver member to list, " +
        "and tests for conflicts with the Ole member.");
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
    System.out.println("Test 6: Check that findPoints returns " +
        "correct amount when balance is not 0.");
    assertEquals(pointsToAdd, archive.findPoints(oleMemberNo, "ole"));
    // asserts that member has received the correct amount of points


  }

  /**
   * Tests positive and negative cases for the registerPoints method.
   * This test bypasses the MemberArchive findPoints method,
   * and calls getPoints directly on the member objects.
   * This is done to isolate tests to the registerPoints method,
   * and eliminate possible side effects caused by findPoints.
   * <p>
   * We create a Basic level member, and tests adding points to it.
   * Also tests for expected failure when method parameters are invalid.
   * <p>
   * Further, the Basic level member is then upgraded to Silver.
   * The points are set to 0, to avoid interference from previous tests.
   * Tests assert that the Silver member receives the expected x1.2
   * multiplier to points.
   * <p>
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

//    //Basic level member tests
//    System.out.println("Test 7: Tests successfully adding points to a member.");
//    assertTrue(archive.registerPoints(oleMemberNo, pointsToAdd));
//    //asserts that adding points is successful when a valid member number is used
//    System.out.println("Test 8: Test that basic member has gained expected number of points.");
//    assertEquals(pointsToAdd, b1.getPoints());
//    // asserts that basic member has received the correct amount of points, e.g. added == received
//    System.out.println("Test 9: Tests failure when adding points to invalid member number.");
//    assertFalse(archive.registerPoints(-1, pointsToAdd));
//    //asserts failure when member number is invalid
//    System.out.println("Test 10: Test failure when adding negative point value.");
//    assertFalse(archive.registerPoints(oleMemberNo, -1));
//    //asserts failure when points < 0

    //Silver level member tests
    SilverMember s1 = new SilverMember( //upgrade Ole from Basic to Silver level
            b1.getMemberNo(), //same number
            b1.getPersonals(), //same 'hood
            b1.getEnrolledDate(),//it's all good
            0); //wipe balance to 0 to eliminate interference from basic level testing

    archive.replaceUpgradedMember(b1, s1); // replaces basic Ole with silver level Ole

    assertFalse(archive.findMember(oleMemberNo) instanceof BasicMember);
    //assert that Ole is not Basic
    assertTrue(archive.findMember(oleMemberNo) instanceof SilverMember);
    //assert that Ole is now Silver
    System.out.println("Ole is now Silver Level.");

    System.out.println("Test 11: Add points to silver level member.");
//    assertTrue(archive.registerPoints(oleMemberNo, pointsToAdd));
    System.out.println("Test 12: Test that Silver level member " +
        "has received expected number of points.");
    assertEquals(pointsToAdd * 1.2, s1.getPoints());


    //Gold level member tests

    GoldMember g1 = new GoldMember(
            s1.getMemberNo(),
            s1.getPersonals(),
            s1.getEnrolledDate(),
            0); //wipe balance to 0, to eliminate interference from silver level testing
    archive.replaceUpgradedMember(s1, g1);
    assertFalse(archive.findMember(oleMemberNo) instanceof SilverMember);
    assertTrue(archive.findMember(oleMemberNo) instanceof GoldMember);
    //check that Ole is now Gold level, and not Silver level.
    System.out.println("Ole is now Gold Level.");


    System.out.println("Test 13: Add points to gold level member.");
//    assertTrue(archive.registerPoints(oleMemberNo, pointsToAdd));
    System.out.println("Test 14: Test that Gold level member " +
        "has received expected number of points.");
    assertEquals(pointsToAdd * 1.5, g1.getPoints());

  }

  /**
   * Tests the process of checking members for membership level upgrade eligibility,
   * and the upgrade process itself. The tests are combined because the methods that upgrade
   * member objects, and the methods that check members for upgrade eligibility are all private.
   * <p>
   * Every method that is relevant to the check and upgrade process is called by the
   * checkAndUpgradeMembers method, and code coverage analysis will show that all relevant methods
   * are tested.
   */
  // TODO: 04/02/2020 Check test thoroughness. Have doubts regarding veracity of negative tests.
  // What are some good negative tests?
  //  - upgrade should fail if date object is null.
  @Test
  void checkAndUpgradeMembersTest() {
    // adds members to archive for testing
    int b1 = archive.addMember(ole, oleEnrollDate);
    int b2 = archive.addMember(tove, toveEnrollDate);
    int b3 = archive.addMember(lise, liseEnrollDate);
    int b4 = archive.addMember(jonas, jonasEnrollDate);
    int b5 = archive.addMember(erik, erikEnrollDate);
    System.out.println("Ole, Tove, Lise, Jonas and Erik added as basic level members.");

    assertEquals(5, archive.getArchiveSize());
    System.out.println("Archive size is: " + archive.getArchiveSize());

    // adds points to member accounts and asserts successful adding
//    assertTrue(archive.registerPoints(b1, 10000)); // few points and wrong date
//    assertTrue(archive.registerPoints(b2, 25000)); // at silver limit
//    assertTrue(archive.registerPoints(b3, 74999)); // just below gold limit
//    assertTrue(archive.registerPoints(b4, 80000)); // above gold limit, but wrong date.
//    assertTrue(archive.registerPoints(b5, 75000)); // at gold limit
    // TODO: 04/02/2020 make profile with correct date, but too few points?

    // runs the upgrade process on member archive
    System.out.println("Test 15: Upgrading qualified members.");
    archive.checkAndUpgradeMembers(testDate);
    System.out.println("Upgrade complete. Member status should now be \n" +
            "Ole: Basic\n" +
            "Tove: Silver\n" +
            "Lise: Silver\n" +
            "Jonas: Basic\n" +
            "Erik: Gold");

    System.out.println("Test 16: Asserts that each member now has expected membership level.");
    assertTrue(archive.findMember(b1) instanceof BasicMember,
            "Ole is not a basic member.");
    assertTrue(archive.findMember(b2) instanceof SilverMember,
            "Tove is not a silver member.");
    assertTrue(archive.findMember(b3) instanceof SilverMember,
            "Lise is not a silver member.");
    assertTrue(archive.findMember(b4) instanceof BasicMember,
            "Jonas is not a basic member.");
    assertTrue(archive.findMember(b5) instanceof GoldMember,
            "Erik is not a gold member");

    // new round of adding points and checking for qualified upgrades
    System.out.println("Adding more points to members.");
//    assertTrue(archive.registerPoints(b1, 10000));
//    // too few points and wrong date
//    assertTrue(archive.registerPoints(b2, 25000));
//    // is silver, so this should add 25k*1.2 = 30k, for a 55k total
//    assertTrue(archive.registerPoints(b3, 1200));
//    // is silver so this should add 1000 * 1.2 = 1200, for a 76199 total
//    assertTrue(archive.registerPoints(b4, 80000));
    // is basic, total should be 160k, way above gold limit but date is still invalid.

    // Erik (b5) is already gold level, and should not be affected by upgrade process.
    // Therefore, we will not register additional points, as it would not add
    // anything meaningful to the testing since registerPoints has its own test method

    System.out.println("Test 17: Second round of upgrades.");
    archive.checkAndUpgradeMembers(testDate);
    System.out.println("Upgrade complete. Member status should now be \n" +
            "Ole: Still Basic\n" +
            "Tove: Still Silver\n" +
            "Lise: Gold, was Silver\n" +
            "Jonas: Still Basic\n" +
            "Erik: Still Gold (cannot be upgraded)");

    System.out.println("Test 18: Asserts that each member is " +
            "the expected level after second round of upgrades.");
    assertTrue(archive.findMember(b1) instanceof BasicMember,
            "Ole is not a basic member.");
    assertTrue(archive.findMember(b2) instanceof SilverMember,
            "Tove is not a silver member.");
    assertTrue(archive.findMember(b3) instanceof GoldMember,
            "Lise is not a silver member.");
    assertTrue(archive.findMember(b4) instanceof BasicMember,
            "Jonas is not a basic member.");
    assertTrue(archive.findMember(b5) instanceof GoldMember,
            "Erik is not a gold member");

    // TODO: 04/02/2020 find some more negative tests?


  }
}
