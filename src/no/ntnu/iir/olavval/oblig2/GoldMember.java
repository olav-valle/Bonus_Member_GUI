package no.ntnu.iir.olavval.oblig2;

import java.time.LocalDate;

/**
 * Represents a Gold level Bonus member.
 */
public class GoldMember extends BonusMember {

    public GoldMember(int memberNo, Personals personals, LocalDate enrolledDate, int points)
    {
        super(memberNo, personals, enrolledDate);

        // Transfers existing points from old member to the new member.
        // Direct call to super.registerPoints avoids factoring by FACTOR_GOLD.
        super.registerPoints(points);

    }

    /**
    * Adds points to this Gold level Bonus member, factoring in the Gold level bonus multiplier.
    * @param points Number of points to be added.
    **/
    public void registerPoints(int points)
    {
        super.registerPoints( (int) (FACTOR_GOLD * points) );
    }
}
