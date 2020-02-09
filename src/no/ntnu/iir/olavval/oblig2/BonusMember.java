package no.ntnu.iir.olavval.oblig2;

import java.time.LocalDate;
import java.time.Period;

// TODO: 09/02/2020 Refactor into an abstract class. 
// TODO: 09/02/2020 Ensure all usages are refactored to allow for abstraction. 
/**
 * Represents a Bonus Member. A member earns bonus points from traveling with the company.
 * There are three subclasses of BonusMember, BasicMember, SilverMember and GoldMember.
 */
public abstract class BonusMember {
  //
  protected static final double FACTOR_SILVER = 1.2;
  protected static final double FACTOR_GOLD = 1.5;

  private final int memberNo;
  private final Personals personals;
  private final LocalDate enrolledDate;
  private int point = 0;

  /**
   * Bonus member costructor.
   *
   * @param memberNo     A unique integer representing member number.
   * @param personals    A Personals object containing details regarding the member.
   * @param enrolledDate The date the member was first enrolled in the Bonus program.
   */
  public BonusMember(int memberNo, Personals personals, LocalDate enrolledDate) {

    this.memberNo = memberNo;
    this.personals = personals;
    this.enrolledDate = enrolledDate;
  }


  /**
   * Returns the number of points this member has earned.
   *
   * @return the number of points this member has earned.
   */
  public int getPoints() {
    return point;
  }


  /**
   * +
   * Returns the member number.
   *
   * @return the member number.
   */
  public int getMemberNo() {
    return memberNo;
  }

  /**
   * Returns the Personals object of this member.
   *
   * @return the Personals object of this member.
   */
  public Personals getPersonals() {
    return personals;
  }

  /**
   * Returns a LocalDate object holding the date this member was enrolled in the Bonus program.
   *
   * @return the date this member was enrolled in the Bonus program.
   */
  public LocalDate getEnrolledDate() {
    return enrolledDate;
  }

  public abstract String getMembershipLevel();

  /**
   * Adds points to this members account.
   *
   * @param points The number of points to be added to the member account.
   */
  public void registerPoints(int points) {

    this.point += points;

  }

  /**
   * Checks if the member has been part of the Bonus program for less than one year, using the
   * date passed as parameter.
   * If membership has lasted for less than one year, it returns the number of points in the member
   * account. Returns 0 if membership is more than one year old.
   *
   * @param testDate The date to compare member enrollment date to.
   * @return 0 if membership is more than one year old,
   *        or the number of points held by the member if
   *        membership is less than one year old.
   */
  public int findQualificationPoints(LocalDate testDate) {

    int qualifiedPoints = 0;

    if (Period.between(enrolledDate, testDate).getYears() < 1) {
      qualifiedPoints = point;
    }
    // check if less than a year has passed.

    return qualifiedPoints;

  }

  /**
   * Tests whether the provided password matches the password registered to this member.
   *
   * @param passwd the password to be tested.
   * @return True if provided and registered passwords match.
   */
  // TODO: 09/02/2020 Refactor/remove this. It is already present in Personals class.
  public boolean okPassword(String passwd) {
    if (passwd == null) {
      return false;
    } // null password is never valid

    return personals.okPassword(passwd);
  }
}
