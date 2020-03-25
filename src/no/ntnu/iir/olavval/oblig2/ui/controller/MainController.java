package no.ntnu.iir.olavval.oblig2.ui.controller;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Optional;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import no.ntnu.iir.olavval.oblig2.model.BonusMember;
import no.ntnu.iir.olavval.oblig2.model.MemberArchive;
import no.ntnu.iir.olavval.oblig2.model.Personals;
import no.ntnu.iir.olavval.oblig2.ui.view.MainView;

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


  public MainController() {
    // TODO: 24/03/2020 memberarchive
  }

  /**
   * Displays a modal dialogue for adding points to a member.
   *
   * @param selectedMember the BonusMember to add points to.
   * @param archive The archive in which the member is located.
   * @param parent The parent of this modal dialogue window.
   */
  public void doShowAddPointsModal(MemberArchive archive,
                                   BonusMember selectedMember,
                                   MainView parent) {
    Stage addPointsStage = new Stage();
    addPointsStage.setTitle("Add Points");
    addPointsStage.initModality(Modality.WINDOW_MODAL);

    VBox vBox = new VBox();
    vBox.setAlignment(Pos.CENTER);
    vBox.setPadding(new Insets(10));
    vBox.setSpacing(10);

    // -- Text box for point value input
    Text pointValuePrompt = new Text("Enter value to add:");
    TextField pointInput = new TextField();
    pointInput.setEditable(true);
    pointInput.setPromptText("Add points...");
    // -- Add and Cancel buttons
    HBox hBox = new HBox();

    // The "OK" button. Labeled "Add"
    Button addBtn = new Button("Add");
    // calls registerPoints when Add is clicked
    addBtn.setOnAction(actionEvent -> {
      if (selectedMember != null) {
        try {
          Integer pointValue = Integer.parseInt(pointInput.getText());
          archive.registerPoints(selectedMember.getMemberNo(), pointValue);
          // If we get here, adding was successful
          parent.updateMemberListWrapper();
          addPointsStage.close();
        } catch (NumberFormatException e) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Invalid point value.");
          alert.setHeaderText("You have tried to add an invalid number of points.");
          alert.setContentText("Please enter a non-negative number.");
          alert.showAndWait();
        } catch (NullPointerException e) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Error");
          alert.setHeaderText("Member not found in archive.");
          alert.showAndWait();
        }
      } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("You must first select which member you want to add points to.");
        alert.showAndWait();
      }
    });


    // The cancel button
    Button cancelBtn = new Button("Cancel");
    // closes modal window if Cancel button is clicked
    cancelBtn.setOnAction(actionEvent -> addPointsStage.close());
    hBox.setAlignment(Pos.CENTER);
    hBox.setSpacing(5);
    hBox.getChildren().addAll(addBtn, cancelBtn);


    vBox.getChildren().addAll(pointValuePrompt, pointInput, hBox);
    addPointsStage.setScene(new Scene(vBox));
    addPointsStage.showAndWait();


  }

  /**
   * todo: javadoc
   */
  public boolean doShowDeleteMemberConfirmation(BonusMember removedMember) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Delete Member");
    alert.setHeaderText("This action will permanently delete this member");
    alert.setContentText(removedMember.getFirstName() + " " + removedMember.getSurname() + "\n"
        + "Member ID: " + removedMember.getMemberNo());
    Optional<ButtonType> result = alert.showAndWait();

    // shortcircuits  if isPresent == false
    return (result.isPresent() && (result.get() == ButtonType.OK));


  }

  /**
   * todo: javadoc
   */
  public Boolean doShowUpgradeConfirmDialog( ) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Upgrade Members");
    alert.setHeaderText("Upgrade all eligible members?");
    Label dialogText = new Label("This action will check all members for upgrade eligibility."
        + "\nand automatically upgrade any members that pass.");
    dialogText.setWrapText(true);
    alert.getDialogPane().setContent(dialogText);
    Optional<ButtonType> result = alert.showAndWait();

    return (result.isPresent() && (result.get() == ButtonType.OK));
  }


  // TODO: 19/03/2020 implement method that combines newPersona() and newDate(),
  //  to create a new member. It should take the archive from MainView as a parameter,

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


