package no.ntnu.iir.olavval.oblig2.ui.controller;

import java.util.Optional;
import java.util.logging.Logger;
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
import no.ntnu.iir.olavval.oblig2.ui.view.MainView;

/**
 * Main class for Bonus Member app.
 *
 * @author Olav Valle
 */
@SuppressWarnings("CheckStyle")
public class MainController {
  private Logger logger;



  public MainController() {
    logger = Logger.getLogger(getClass().toString());
  }

  /**
   * Displays a modal dialogue for adding points to a member.
   *
   * @param selectedMember the BonusMember to add points to.
   * @param archive        The archive in which the member is located.
   * @param parent         The parent of this modal dialogue window.
   */
  @SuppressWarnings("checkstyle:LocalVariableName")
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
          int pointValue = Integer.parseInt(pointInput.getText());
          archive.registerPoints(selectedMember.getMemberNo(), pointValue);
          // If we get here, adding was successful
          parent.updateMemberListWrapper();
          addPointsStage.close();
        } catch (NumberFormatException e) {
          logger.warning(String.format("Caught %1$s in %2$s : %3$s",
              e.getClass(), this.getClass(), e));
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Invalid point value.");
          alert.setHeaderText("You have tried to add an invalid number of points.");
          alert.setContentText("Please enter a non-negative number.");
          alert.showAndWait();
        } catch (NullPointerException e) {
          logger.warning(String.format("Caught %1$s in %2$s : %3$s",
              e.getClass(), this.getClass(), e));
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
   * Shows the confirmation dialog for deleting a member.
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
   * Shows the confirmation dialog for running upgrade check on members.
   */
  public Boolean doShowUpgradeConfirmDialog() {
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
}


