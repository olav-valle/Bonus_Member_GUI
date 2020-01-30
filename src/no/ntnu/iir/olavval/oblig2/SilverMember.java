package no.ntnu.iir.olavval.oblig2;

import java.time.LocalDate;

public class SilverMember extends BonusMember {

    public SilverMember(int memberNo, Personals personals, LocalDate enrolledDate, int points)
    {
        super(memberNo, personals, enrolledDate);
        super.registerPoints(points); // Direct call to super.registerPoints avoids factoring by FACTOR_SILVER
        //TODO fix point transfer when being upgraded to silver member. Fixed?
    }


    public void registerPoints(int points)
    {
        super.registerPoints( (int) (FACTOR_SILVER * points) );

    }
}
