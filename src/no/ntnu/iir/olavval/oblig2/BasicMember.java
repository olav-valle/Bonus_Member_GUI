package no.ntnu.iir.olavval.oblig2;

import java.time.LocalDate;

/**
 * Represents a Basic level member, the lowest level of Bonus member.
 */
public class BasicMember extends BonusMember {

  public BasicMember(int memberNo, Personals personal, LocalDate enrolledDate) {
    super(memberNo, personal, enrolledDate);
  }
}
