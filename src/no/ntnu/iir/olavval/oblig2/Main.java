package no.ntnu.iir.olavval.oblig2;

import java.lang.reflect.Member;

public class Main {

  private static UserInterfaceMenu ui;
  private static MemberArchive archive;

  /**
   * Main method of BonusMember program app.
   *
   * @param args No arguments.
   */
  public static void main(String[] args) {

    ui = new UserInterfaceMenu();
    archive = new MemberArchive();


  }

}
