package no.ntnu.iir.olavval.oblig2;

import java.time.LocalDate;

/**
 * Represents a Silver level Bonus member.
 */
public class SilverMember extends BonusMember {

  /**
   * Silver level member constructor.
   *
   * @param memberNo     the member number.
   * @param personals    the personal details of the member.
   * @param enrolledDate date member was first enrolled.
   * @param points       current points held by member.
   */
  public SilverMember(int memberNo, Personals personals, LocalDate enrolledDate, int points) {
    super(memberNo, personals, enrolledDate);

    // Transfers existing points from old member to the new member.
    // Direct call to super.registerPoints avoids factoring by FACTOR_SILVER
    super.registerPoints(points);

  }


  /**
   * Adds points to this Silver level Bonus member, factoring in the Silver level bonus multiplier.
   *
   * @param points Number of points to be added.
   */
  @Override
  public void registerPoints(int points) {
    super.registerPoints((int) (FACTOR_SILVER * points));
  }
}
