package no.ntnu.iir.olavval.oblig2;

import java.time.LocalDate;

/**
 * Represents a Gold level Bonus member.
 * @author mort
 */
public class GoldMember extends BonusMember {

  static final double FACTOR_GOLD = 1.5;

  /**
   * Gold level member constructor.
   *
   * @param memberNo     the member number.
   * @param personals    the personal details of the member.
   * @param enrolledDate date member was first enrolled
   * @param points       current points held by member.
   */
  public GoldMember(int memberNo, Personals personals, LocalDate enrolledDate, int points) {
    super(memberNo, personals, enrolledDate);

    // Transfers existing points from old member to the new member.
    // Direct call to super.registerPoints avoids factoring by FACTOR_GOLD.
    super.point = points;

  }

  @Override
  public String getMembershipLevel() {
    return "Gold";
  }

  /**
   * Adds points to this Gold level Bonus member, factoring in the Gold level bonus multiplier.
   *
   * @param points Number of points to be added.
   **/
  @Override
  public void registerPoints(int points) {
    super.point += ((int) (FACTOR_GOLD * points));
  }
  // TODO: 11/03/2020 input check
}
