package no.ntnu.iir.olavval.oblig2;

import java.time.LocalDate;
import java.time.Period;

// TODO: 18/02/2020 Implement hashCode() override 
// TODO: 18/02/2020 implement an abstract getDescriptionString() method
/**
 * Represents a Bonus Member. A member earns bonus points from traveling with the company.
 * There are three subclasses of BonusMember, BasicMember, SilverMember and GoldMember.
 */
public abstract class BonusMember implements Comparable<BonusMember> {

  private final int memberNo;
  private final Personals personals;
  private final LocalDate enrolledDate;
  protected int point = 0;

  /**
   * Bonus member costructor.
   *
   * @param memberNo     A unique integer representing member number.
   * @param personals    A Personals object containing details regarding the member.
   * @param enrolledDate The date the member was first enrolled in the Bonus program.
   */
  public BonusMember(int memberNo, Personals personals, LocalDate enrolledDate) {
    // QUESTION: Should an abstact class still have a constructor that subclasses can use?
    this.memberNo = memberNo;
    this.personals = personals;
    this.enrolledDate = enrolledDate;
  }

  /**
   * Compares this object with the specified object for order.
   *
   * @param otherMember the object to be compared
   * @return     a negative integer, zero, or a positive integer as this object is less than,
   *        equal to, or greater than the specified object.
   */
  public int compareTo(BonusMember otherMember){
    return Integer.compare(this.point, otherMember.point);
  }

  /**
   * Indicates whether some other object is "equal to" this object.
   * More specifically, it checks if two members have the same member number.
   * @param obj the object to compare to.
   * @return True if objects are equal.
   */
  @Override
  public boolean equals(Object obj) {
    // Guard clause,
    if (!(obj instanceof BonusMember)) {
      return false;
    } else {
      BonusMember other = (BonusMember) obj;
      // Only requirement for equality is to have same member number.
      return this.memberNo == other.memberNo;
    }

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
  public abstract void registerPoints(int points);

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
}
