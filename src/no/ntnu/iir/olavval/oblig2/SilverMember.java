package no.ntnu.iir.olavval.oblig2;

import java.time.LocalDate;

public class SilverMember extends BonusMember {
    public SilverMember(int memberNo, Personals personals, LocalDate enrolledDate, int points) {
        super(memberNo, personals, enrolledDate);
        //TODO fix point transfer when being upgraded to silver member.
    }

    public void registerPoints(int i) {

    }
}
