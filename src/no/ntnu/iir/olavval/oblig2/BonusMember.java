package no.ntnu.iir.olavval.oblig2;

import java.time.LocalDate;

public class BonusMember
{
    private final int memberNo;
    private final Personals personals;
    private final LocalDate enrolledDate;
    private int point = 0;

    public BonusMember(int memberNo, Personals personals, LocalDate enrolledDate) {

        this.memberNo = memberNo;
        this.personals = personals;
        this.enrolledDate = enrolledDate;
    }


    public int getPoints() {
        return point;
    }


    public int getMemberNo() {
        return memberNo;
    }

    public Personals getPersonals() {
        return personals;
    }

    public LocalDate getEnrolledDate() {
        return enrolledDate;
    }

    public int findQualificationPoints(LocalDate testDate) {
        return 0;
    }

    public void registerPoints(int points) {

    }
}
