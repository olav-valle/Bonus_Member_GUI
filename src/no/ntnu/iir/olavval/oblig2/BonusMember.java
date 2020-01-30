package no.ntnu.iir.olavval.oblig2;

import java.time.LocalDate;
import java.time.Period;

public class BonusMember
{
    //
    protected static final double FACTOR_SILVER = 1.2;
    protected static final double FACTOR_GOLD = 1.5;

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

    public void registerPoints(int points) {

        this.point += points;

    }

    public int findQualificationPoints(LocalDate testDate)
    {

        int qualifiedPoints = 0;

        if ( Period.between( enrolledDate, testDate ).getYears() < 1) qualifiedPoints = point;
        // check if less than a year has passed.

        return qualifiedPoints;

    }

    public boolean okPassword(String passwd)
    {
        return personals.okPassword(passwd);
    }
}
