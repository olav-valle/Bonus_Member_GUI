package no.ntnu.iir.olavval.oblig2;

import java.time.LocalDate;

public class BasicMember extends BonusMember {

    public BasicMember(int memberNo, Personals personal, LocalDate enrolledDate)
    {
        super(memberNo, personal, enrolledDate);

    }

    public void registerPoints(int points) {
        super.registerPoints(points);

    }
//
//    public int findQualificationPoints(LocalDate testDate) {
//        return ;
//    }

    public int getPoints() {
        return super.getPoints();
    }
//
//    public int getMemberNo() {
//        return -1;
//    }

//    public Personals getPersonals() {
//        return null;
//    }

//    public LocalDate getEnrolledDate() {
//        return null;
//    }
}
