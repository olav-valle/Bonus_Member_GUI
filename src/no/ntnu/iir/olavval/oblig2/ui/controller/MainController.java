package no.ntnu.iir.olavval.oblig2.ui.controller;

import java.time.LocalDate;
import java.util.InputMismatchException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import no.ntnu.iir.olavval.oblig2.model.BonusMember;
import no.ntnu.iir.olavval.oblig2.model.MemberArchive;
import no.ntnu.iir.olavval.oblig2.model.Personals;

/**
 * Main class for Bonus Member app.
 *
 * @author mort
 */
public class MainController {

  // TODO: 18/03/2020 refactor this class to be the MainController.
  //  remove inner class UserInterfaceMenu
  //  Keep all methods that call and manipulate MemberArchive and BonusMember.
  //  Reimplement calls to these control methods in MainView,
  //  since that's our "new" UserInterfaceMenu.


  public MainController(){
    // TODO: 24/03/2020 memberarchive


  }



  /**
   * Requests user input for member number and point value.
   * If either input is invalid, the user is informed of this, and is asked to retry.
   * Input validity is determined by exceptions thrown by MemberArchive.registerPoints().
   */
  private void registerPoints(BonusMember member) {
    // TODO: 19/03/2020 refactor this to control adding points through GUI.
    //  We can do this by passing the point value and member in question from
    //  the tableView as a parameter.
    boolean success = false;
    int tries = 0;
    do {
      // TODO: 19/03/2020 Add modal dialog requesting user input of point value.

      System.out.println("Enter membership number: ");
      //int memberNo = input.nextInt();
      System.out.println("Enter number of points to add: ");
      //int points = input.nextInt();

      try {
        //archive.registerPoints(memberNo, points);
        success = true;
        System.out.println("Points added successfully.");
      } catch (IllegalArgumentException e) {
        System.out.println(e);
        tries++;
        System.out.println("Aborting in " + (5 - tries) + "more tries.");
      }

      if (tries >= 5) {
        System.out.println("Points registration aborted: invalid member number or point value.");
      }

    } while (!success && tries < 5);
  }
  // TODO: 19/03/2020 implement method that combines newPersona() and newDate(),
  //  to create a new member. It should take the archive from MainView as a parameter,

  //  and add the created member to it. Then it should call updateTableView() in MainView.

  private Personals newPersona() {
    // TODO: 19/03/2020 refactor for use with GUI
    // TODO: 09/02/2020 Add security and validity checks to user input.
    System.out.println("Please enter the personal details of the member: ");
    System.out.println("First name: ");
//    final String firstName = input.next();
    System.out.println("Surname: ");
//    String surname = input.next();
    System.out.println("Email address: ");
//    String emailAddress = input.next();
    System.out.println("Password: ");
//    String password = input.next();

    // TODO: 18/02/2020 should this try to catch the exception that the Personals constructor can throw?
//    return new Personals(firstName, surname, emailAddress, password);
    return null; // FIXME: 24/03/2020 return new Personals()
  }

  private LocalDate newDate() {
    // TODO: 19/03/2020 refactor for use with gui
    boolean success = false;
    int year = 0;
    int month = 0;
    int day = 0;

    System.out.println("Please enter the date this member was added: ");

    // get the year from user
    do {
      try {
        System.out.println("Year (4 digit): ");
//        year = input.nextInt();
        success = true;
      } catch (InputMismatchException e) {
//        input.next(); //clears the input left behind from the failed call in the try block
        System.out.println("Please enter the year as a four digit number.");
      }
    } while (!success);

    do {
      success = false;
      try {
        System.out.println("Month (1 - 12): ");
//        month = input.nextInt();
        success = true;
      } catch (InputMismatchException e) {
//        input.next(); //clears the input left behind from the failed call in the try block
        System.out.println("Please enter the month as a number between 1 and 12.");
      }
    } while (!success);

    do {
      success = false;
      try {
        System.out.println("Day (1 - 31): ");
//        day = input.nextInt();
        success = true;
      } catch (InputMismatchException e) {
//        input.next(); //clears the input left behind from the failed call in the try block
        System.out.println("Please enter the year as a four digit number.");
      }
    } while (!success);

    if (month == 0 || day == 0) {
      throw new IllegalStateException(
          String.format("Date cannot be 1$/2$. Zero is not a valid day or month.", day, month));
    }

    return LocalDate.of(year, month, day);
  }



}


