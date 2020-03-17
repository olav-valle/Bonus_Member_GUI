package no.ntnu.iir.olavval.oblig2;

import java.time.LocalDate;

/**
 * Represents a Basic level member, the lowest level of Bonus member.
 * @author mort
 */
public class BasicMember extends BonusMember {

  public BasicMember(int memberNo, Personals personal, LocalDate enrolledDate) {
    super(memberNo, personal, enrolledDate);
  }

  @Override
  public String getMembershipLevel() {
    return "Basic";
  }

  @Override
  public void registerPoints(int points) {
    super.point += points;
  }
  // TODO: 11/03/2020 input check
}
