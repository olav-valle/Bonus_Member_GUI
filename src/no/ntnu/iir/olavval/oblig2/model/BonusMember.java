package no.ntnu.iir.olavval.oblig2.model;

import java.time.LocalDate;
import java.time.Period;

// TODO: 18/02/2020 Implement hashCode() override 

/**
 * Represents a Bonus Member. A member earns bonus points from traveling with the company.
 * There are three subclasses of BonusMember, BasicMember, SilverMember and GoldMember.
 *
 * @author mort
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
   * @throws IllegalArgumentException for null parameters, or negative value memberNo.
   */
  public BonusMember(int memberNo, Personals personals, LocalDate enrolledDate) {
    // TODO: 17/03/2020 change ex to IllegalState since it's constructing an object?
    if (memberNo < 0 || personals == null || enrolledDate == null) {
      throw new IllegalArgumentException(
          "Either personals or enrolledDate was null, or memberNo was negative.");
    } else {
      this.memberNo = memberNo;
      this.personals = personals;
      this.enrolledDate = enrolledDate;
    }
  }

  /**
   * Compares this object with the specified object for order.
   * Throws IllegalArgumentException if otheMember parameter is null.
   *
   * @param otherMember the object to be compared
   * @return a negative integer, zero, or a positive integer if this object is less than,
   * equal to, or greater than the specified object.
   * @throws IllegalArgumentException for null parameters
   */
  public int compareTo(BonusMember otherMember) {
    if (otherMember == null) {
      throw new IllegalArgumentException("Parameter cannot be null.");
    }
    return Integer.compare(this.point, otherMember.point);
  }

  /**
   * Indicates whether some other object is "equal to" this object.
   * More specifically, it checks if two members have the same member number.
   *
   * @param obj the object to compare to.
   * @return True if objects are equal.
   */
  @Override
  public boolean equals(Object obj) {
    // Guard clause.
    // (null instanceof Class) is false
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

  // TODO: 18/03/2020 javadoc
  public String getFirstName() {
    return personals.getFirstname();
  }

  public String getSurname() {
    return personals.getSurname();
  }

  public String getEmail() {
    return personals.getEMailAddress();
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
   * or the number of points held by the member if
   * membership is less than one year old.
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
