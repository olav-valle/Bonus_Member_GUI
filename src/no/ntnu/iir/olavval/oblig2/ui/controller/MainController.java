package no.ntnu.iir.olavval.oblig2.ui.controller;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;
import no.ntnu.iir.olavval.oblig2.model.MemberArchive;
import no.ntnu.iir.olavval.oblig2.model.Personals;

/**
 * Main class for Bonus Member app.
 * @author mort
 */
public class MainController {
  // TODO: 18/03/2020 refactor this class to be the MainController.
  //  remove inner class UserInterfaceMenu
  //  Keep all methods that call and manipulate MemberArchive and BonusMember.
  //  Reimplement calls to these control methods in MainView,
  //  since that's our "new" UserInterfaceMenu.
  //  .
  private static MemberArchive archive;

//  /**
//   * Main method of BonusMember program app.
//   *
//   * @param args No arguments.
//   */
//  public static void main(String[] args) {
//
//    UserInterfaceMenu ui = new UserInterfaceMenu();
//    archive = new MemberArchive();
//    ui.showMenu();
//  }


  private static class UserInterfaceMenu {

    private static final int ADD_MEMBER = 1;
    private static final int LIST_MEMBERS = 2;
    private static final int UPGRADE_MEMBERS = 3;
    private static final int REGISTER_POINTS = 4;
    private static final int ADD_TEST_MEMBERS = 5;
    private static final int SORT_BY_POINTS = 6;
    private static final int QUIT = 9;


    private final Scanner input = new Scanner(System.in);

    // TODO: 09/02/2020 Write tests for all methods in UserInterfaceMenu

    private UserInterfaceMenu() {
    }

    private void showMenu() {
      boolean run = true;
      while (run) {
        printMainMenu();
        switch (input.nextInt()) {
          case ADD_MEMBER: // user wants to add new member.
            try{
              archive.addMember(newPersona(), newDate());
              System.out.println("Member created.");
            } catch (IllegalStateException e){
              System.out.println("Member not created: Invalid inputs.");
            }
            break;
          case LIST_MEMBERS: // user wants to see list of all members.
            System.out.println("Displaying all registered members.");
            listAllMembers();
            break;
          case UPGRADE_MEMBERS: // user wants all members to be upgraded.
            // TODO: 09/02/2020 Add user date input request. System is still dumb, though...
            archive.checkAndUpgradeMembers(LocalDate.of(2008, 2, 10));
            break;
          case REGISTER_POINTS: // user wants to register points to a member account.
            registerPoints();
            break;
          case ADD_TEST_MEMBERS:
            System.out.println("Adding test members to archive.");
            addTestMembers();
            break;
          case SORT_BY_POINTS:
            System.out.println("Listing all members, sorting by points held.");
            printSortedByPoints();
            break;
          case QUIT: // user has called exit command.
            run = false;
            System.out.println("Goodbye.");
            break;
          default:
            throw new IllegalStateException("Unexpected value: " + input.nextInt());
        }
      }
    }

    /**
     * Requests user input for member number and point value.
     * If either input is invalid, the user is informed of this, and is asked to retry.
     * Input validity is determined by exceptions thrown by MemberArchive.registerPoints().
     */
    private void registerPoints() {
      boolean success = false;
      int tries = 0;
      do {

        System.out.println("Enter membership number: ");
        int memberNo = input.nextInt();
        System.out.println("Enter number of points to add: ");
        int points = input.nextInt();

        try{
          archive.registerPoints(memberNo, points);
          success = true;
          System.out.println("Points added successfully.");
        } catch(IllegalArgumentException e){
          System.out.println(e);
          tries++;
          System.out.println("Aborting in " + (5 - tries) + "more tries.");
        }

        if(tries >= 5) {
          System.out.println("Points registration aborted: invalid member number or point value.");
        }

      } while(!success && tries < 5);
    }

    private void printMainMenu() {
      System.out.println(String.format("Please select option 1 through %1$d.", QUIT));
      System.out.println(String.format("%1$d. Add a new member.", ADD_MEMBER));
      System.out.println(String.format("%1$d. List all members.", LIST_MEMBERS));
      System.out.println(String.format("%1$d. Upgrade all qualified members.", UPGRADE_MEMBERS));
      System.out.println(String.format("%1$d. Add points to a member account.", REGISTER_POINTS));
      System.out.println(String.format("%1$d. Add test members.", ADD_TEST_MEMBERS));
      System.out.println(String.format("%1$d. List members sorted by points held.", SORT_BY_POINTS));
      System.out.println(String.format("%1$d. Quit program.", QUIT));

    }

    private void listAllMembers() {
      archive
          .forEach(m ->
              System.out.println(
                  m.getMemberNo() + ": \t "
                      + m.getPersonals().getSurname() + ", "
                      + m.getPersonals().getFirstname() + "\t "
                  + m.getMembershipLevel() + " level member: "
                  + m.getPoints() + " points."));
    }

    private void printSortedByPoints(){
      archive
          .stream()
          .sorted()
          .forEachOrdered(m ->
          System.out.println(
              m.getMemberNo() + ": \t "
                  + m.getPersonals().getSurname() + ", "
                  + m.getPersonals().getFirstname() + "\t "
                  + m.getMembershipLevel() + " level member: "
                  + m.getPoints() + " points."));
    }



    private Personals newPersona() {

      // TODO: 09/02/2020 Add security and validity checks to user input.
      System.out.println("Please enter the personal details of the member: ");
      System.out.println("First name: ");
      final String firstName = input.next();
      System.out.println("Surname: ");
      String surname = input.next();
      System.out.println("Email address: ");
      String emailAddress = input.next();
      System.out.println("Password: ");
      String password = input.next();

      return new Personals(firstName, surname, emailAddress, password);
// TODO: 18/02/2020 should this try to catch the exception that the Personals constructor can throw?
    }

    private LocalDate newDate() {

      boolean success = false;
      int year = 0;
      int month = 0;
      int day = 0;

      System.out.println("Please enter the date this member was added: ");

     // get the year from user
      do {
        try {
          System.out.println("Year (4 digit): ");
          year = input.nextInt();
          success = true;
        } catch (InputMismatchException e) {
          input.next(); //clears the input left behind from the failed call in the try block
          System.out.println("Please enter the year as a four digit number.");
        }
      } while (!success);

      do {
        success = false;
        try {
          System.out.println("Month (1 - 12): ");
          month = input.nextInt();
          success = true;
        } catch (InputMismatchException e) {
          input.next(); //clears the input left behind from the failed call in the try block
          System.out.println("Please enter the month as a number between 1 and 12.");
        }
      } while(!success);

      do {
        success = false;
        try {
          System.out.println("Day (1 - 31): ");
          day = input.nextInt();
          success = true;
        } catch (InputMismatchException e) {
          input.next(); //clears the input left behind from the failed call in the try block
          System.out.println("Please enter the year as a four digit number.");
        }
      } while(!success);

      if (month == 0 || day == 0) {
        throw new IllegalStateException(
            String.format("Date cannot be 1$/2$. Zero is not a valid day or month.", day, month));
      }

      return LocalDate.of(year, month, day);
    }

    private void addTestMembers() {

      LocalDate oleEnrollDate = LocalDate.of(2006, 2, 15);
      LocalDate toveEnrollDate = LocalDate.of(2007, 5, 3);
      LocalDate liseEnrollDate = LocalDate.of(2008, 2, 10);
      LocalDate jonasEnrollDate = oleEnrollDate; //same date as Ole
      LocalDate erikEnrollDate = LocalDate.of(2007, 3, 1);

      Personals ole = new Personals("Ole", "Olsen",
          "ole.olsen@dot.com", "ole");
      Personals tove = new Personals("Tove", "Hansen",
          "tove.hansen@dot.com", "tove");
      Personals lise = new Personals("Lise", "Lisand",
          "lise@lisand.no", "lise");
      Personals jonas = new Personals("Jonas", "Johnsen",
          "jon@johnsen.no", "jonny");
      Personals erik = new Personals("Erik", "Eriksen",
          "rikken@eriksen.no", "rikkenrules");

      archive.addMember(ole, oleEnrollDate);
      archive.addMember(tove, toveEnrollDate);
      archive.addMember(lise, liseEnrollDate);
      archive.addMember(jonas, jonasEnrollDate);
      archive.addMember(erik, erikEnrollDate);
    }

  }
}
