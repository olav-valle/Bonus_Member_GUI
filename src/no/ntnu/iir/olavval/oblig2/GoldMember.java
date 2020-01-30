package no.ntnu.iir.olavval.oblig2;

import java.time.LocalDate;

public class GoldMember extends BonusMember {
    public GoldMember(int memberNo, Personals personals, LocalDate enrolledDate, int points) {
        super(memberNo, personals, enrolledDate);
        //TODO fix point transfer when being upgraded from silver to gold.

    }
}
