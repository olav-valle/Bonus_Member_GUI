package no.ntnu.iir.olavval.oblig2;

import java.time.LocalDate;

public class GoldMember extends BonusMember {

    public GoldMember(int memberNo, Personals personals, LocalDate enrolledDate, int points)
    {
        super(memberNo, personals, enrolledDate);
        super.registerPoints(points); // Direct call to super.registerPoints avoids factoring by FACTOR_GOLD.

        //TODO fix point transfer when being upgraded from silver to gold. Fixed?
    }

    public void registerPoints(int points)
    {
        super.registerPoints( (int) (FACTOR_GOLD * points) );
    }
}
