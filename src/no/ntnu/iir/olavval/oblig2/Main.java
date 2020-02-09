package no.ntnu.iir.olavval.oblig2;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Scanner;

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

    run();
  }

  private static void run() {
    ui.showMenu();
  }

  private static class UserInterfaceMenu {

    private static final int ADD_MEMBER = 1;
    private static final int LIST_MEMBERS = 2;
    private static final int UPGRADE_MEMBERS = 3;
    private static final int REGISTER_POINTS = 4;
    private static final int ADD_TEST_MEMBERS = 5;
    private static final int QUIT = 6;


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
            archive.addMember(newPersona(), newDate());
            System.out.println("Member created.");
            break;
          case LIST_MEMBERS: // user wants to see list of all members.
            System.out.println("Displaying all registered members.");
            listAllMembers(archive.getArchiveIterator());
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
          case QUIT: // user has called exit command.
            run = false;
            System.out.println("Goodbye.");
            break;
          default:
            throw new IllegalStateException("Unexpected value: " + input.nextInt());
        }
      }
    }

    private void registerPoints() {
      System.out.println("Enter membership number: ");
      int memberNo = input.nextInt();
      System.out.println("Enter number of points to add: ");
      int points = input.nextInt();
      if (archive.registerPoints(memberNo, points)) {
        System.out.println("Points added successfully.");
      } else {
        System.out.println("Points registration aborted: invalid member number or point value.");
      }

    }

    private void printMainMenu() {
      System.out.println(String.format("Please select option 1 through %1$d.", QUIT));
      System.out.println(String.format("%1$d. Add a new member.", ADD_MEMBER));
      System.out.println(String.format("%1$d. List all members.", LIST_MEMBERS));
      System.out.println(String.format("%1$d. Upgrade all qualified members.", UPGRADE_MEMBERS));
      System.out.println(String.format("%1$d. Add points to a member account.", REGISTER_POINTS));
      System.out.println(String.format("%1$d. Add test members.", ADD_TEST_MEMBERS));
      System.out.println(String.format("%1$d. Quit program.", QUIT));

    }

    private void listAllMembers(Iterator<BonusMember> members) {

      // TODO: 09/02/2020 should this be refactored into separate methods for each member level?
      members
          .forEachRemaining(m ->
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

    }

    private LocalDate newDate() {
      System.out.println("Please enter the date this member was added: ");
      System.out.println("Year: ");
      int year = input.nextInt();
      System.out.println("Month (1 - 12): ");
      int month = input.nextInt();
      System.out.println("Day (1 - 31): ");
      int day = input.nextInt();


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
