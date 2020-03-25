package no.ntnu.iir.olavval.oblig2.ui.view;

import java.time.LocalDate;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import no.ntnu.iir.olavval.oblig2.model.BonusMember;
import no.ntnu.iir.olavval.oblig2.model.MemberArchive;
import no.ntnu.iir.olavval.oblig2.model.Personals;
import no.ntnu.iir.olavval.oblig2.ui.controller.MainController;

public class MainView extends Application {

  private Logger log;
  private MainController mainController;
  private MemberArchive archive;
  private ObservableList<BonusMember> memberListWrapper;
  private BonusMember selectedMember;


  public static void main(String[] args) {
    launch(args);
  }

  /**
   * The application initialization method.
   * This method is called immediately after the Application class is loaded and constructed.
   * An application may override this method to perform initialization prior to the
   * actual starting of the application.
   */
  @Override
  public void init() {
    this.mainController = new MainController();
    archive = new MemberArchive();
    this.addDummies();
    memberListWrapper = getMemberListWrapper(); // Wrapped observable member list.
  }


  /**
   * The main entry point for all JavaFX applications.
   * The start method is called after the init method has returned,
   * and after the system is ready for the application to begin running.
   *
   * @param primaryStage the primary stage for this application,
   *                     onto which the application scene can be set.
   *                     The primary stage will be embedded in the browser if the
   *                     application was launched as an applet. Applications may create
   *                     other stages, if needed, but they will not be primary stages
   *                     and will not be embedded in the browser.
   */

  @SuppressWarnings("checkstyle:LocalVariableName")
  @Override
  public void start(Stage primaryStage) {
    // TODO: 18/03/2020 refactor pane node creations.
    //  separate methods for each of the BorderPane areas:
    //  center is VBox with member table on top and grid view for details on bottom.
    //  Top is VBox with main menu and toolbar (adding points, creating members).
    //  Bottom is statusbar.

    // BorderPane as scene root
    BorderPane root = new BorderPane();

    // Center of root is a VBox with a table and grid
    TableView<BonusMember> memberTable = makeMemberTable();
    GridPane memberDetailGrid = makeMemberDetailGrid(memberTable);
    Button deleteMemberButton = makeDeleteMemberButton(memberTable);
    HBox hBoxCenter = makeCenterPane(memberTable, new VBox(memberDetailGrid, deleteMemberButton));

    // Set selected member listener
    selectedMemberListener(memberTable);

    root.setCenter(hBoxCenter);

    // TODO: 18/03/2020 method for making toolbar and menu for root top
    //HBox for toolbar
    HBox toolBar = makeTopToolBar(memberTable);

    root.setTop(toolBar);

    // Set the scene
    Scene scene = new Scene(root);
    primaryStage.setTitle("Bonus Member Administration");
    // Set the stage
    primaryStage.setScene(scene);
    //show the UI
    primaryStage.show();
  }

  /**
   * todo: javadoc
   */
  private HBox makeTopToolBar(TableView<BonusMember> memberTable) {

    // -- Add New Member button
    Button addMemberBtn = new Button("Add New Member");
    Tooltip.install(addMemberBtn, new Tooltip("Create a new member account."));
    // -- Upgrade all members button.
    Button upgradeBtn = new Button("Run Upgrade Checks");
    Tooltip.install(upgradeBtn, new Tooltip("Upgrade all members who are eligible."));
    // -- Edit member details. Unused.
    Button editBtn = new Button("Edit Member");
    Tooltip.install(editBtn, new Tooltip("Edit member details."));
    // -- Save edits to member details. Unused.
    Button saveChangesBtn = new Button("Save Changes");
    Tooltip.install(saveChangesBtn, new Tooltip("Save changes to member."));

    // -- Add points button
    Button addPointsBtn = new Button("Add Points");
    Tooltip.install(addPointsBtn, new Tooltip("Add points to currently selected member"));
    //-- "Add Points" button action--
    addPointsBtn.setOnAction(actionEvent -> {
      BonusMember selectedMember = memberTable.getSelectionModel().getSelectedItem();
      if (selectedMember != null) {
        mainController.doShowAddPointsModal(archive, selectedMember, this);
      } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("You must first select which member you want to add points to.");
        alert.showAndWait();
      }

    });// addPointsBtn.setOnAction
    // -- TooBar --
    ToolBar tb = new ToolBar(addMemberBtn, upgradeBtn, addPointsBtn);

    return new HBox(tb);
  }

  /**
   * Assembles the node for the center of the border pane.
   *
   * @return The center node VBox.
   */
  private HBox makeCenterPane(TableView<BonusMember> memberTable, VBox memberDetailBox) {
    // TODO: 18/03/2020 add grid view for member details
    //-- VBox with member table and member details view --
    HBox hBox = new HBox(10);

    //-- Set Member table for top of VBox --
    VBox.setVgrow(memberTable, Priority.ALWAYS); // Member table should grow to fill VBox
    VBox.setMargin(memberTable, new Insets(10.0, 10.0, 0, 10.0));

    // --Set GridPane for member details at bottom of VBox--
    //Text fields in details grid listen to table view for selected member, so we pass the table.
    VBox.setVgrow(memberDetailBox, Priority.NEVER);

    VBox.setMargin(memberDetailBox, new Insets(0, 10.0, 10.0, 10.0));

    hBox.getChildren().addAll(memberTable, memberDetailBox);

    return hBox;
  }

  /**
   * Assembles the table view that displays the list of members currently in the archive.
   *
   * @return TableView of members.
   */
  private TableView<BonusMember> makeMemberTable() {
    // Name column
    TableColumn<BonusMember, String> nameCol = new TableColumn<>("First Name");
    nameCol.setCellValueFactory(new PropertyValueFactory<>("FirstName"));

    // Surname column
    TableColumn<BonusMember, String> surnameCol = new TableColumn<>("Surname");
    surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));

    // Member ID number column
    TableColumn<BonusMember, Integer> memberNoCol = new TableColumn<>("ID");
    memberNoCol.setCellValueFactory(new PropertyValueFactory<>("memberNo"));

    // Unused points column
    TableColumn<BonusMember, Integer> pointCol = new TableColumn<>("Bonus Points");
    pointCol.setCellValueFactory(new PropertyValueFactory<>("points"));

    // Member level column
    TableColumn<BonusMember, String> levelCol = new TableColumn<>("Level");
    levelCol.setCellValueFactory(new PropertyValueFactory<>("MembershipLevel"));

    //TableView for center content
    TableView<BonusMember> centerTable = new TableView<>();
    centerTable.setItems(memberListWrapper);
    centerTable.getColumns().addAll(memberNoCol, surnameCol, nameCol, levelCol);
    // TODO: 25/03/2020 set window height.

    return centerTable;
  }

  /**
   * Assembles the grid pane that displays member details at the bottom half of the window.
   *
   * @param memberTable
   * @return VBox set up to display member details.
   */
  private GridPane makeMemberDetailGrid(TableView<BonusMember> memberTable) {
    // TODO: 24/03/2020 hide detail grid until member is selected from table. How?
    GridPane gridPane = new GridPane();

    gridPane.setHgap(10);
    gridPane.setVgap(10);

    // First Name field (0.0) (1.0)
    gridPane.add(new Label("First Name: "), 0, 0);
    TextField firstName = new TextField();
    firstName.setPromptText("First name");
    gridPane.add(firstName, 1, 0);

    // Surname field (0.1)(1.1)
    gridPane.add(new Label("Surname: "), 0, 1);
    TextField surname = new TextField();
    surname.setPromptText("Surname");
    gridPane.add(surname, 1, 1);

    // Member ID field (0.2)(1.2)
    gridPane.add(new Label("Member ID Number: "), 0, 2);
    TextField id = new TextField();
    id.setPromptText("Member ID Number");
    gridPane.add(id, 1, 2);

    // Member level field (0.3)(1.3)
    gridPane.add(new Label("Membership Level: "), 0, 3);
    TextField level = new TextField();
    level.setPromptText("Membership Level");
    gridPane.add(level, 1, 3);

    // Member points field (0.4)(1.4)
    gridPane.add(new Label("Bonus Points Balance: "), 0, 4);
    TextField points = new TextField();
    points.setPromptText("Bonus Points Balance");
    gridPane.add(points, 1, 4);

    // Member email address
    gridPane.add(new Label("Email Address: "), 0, 5);
    TextField email = new TextField();
    email.setPromptText("Email Address");
    gridPane.add(email, 1, 5);

    // -- GridPane observes TableView for changes in selected element
    // TODO: 19/03/2020 Maybe make this a method and save observer as a class field?
    memberTable.getSelectionModel()
        .selectedItemProperty()
        .addListener((o, ov, member) -> {
          if (member != null) {
            firstName.setText(member.getFirstName());
            surname.setText(member.getSurname());
            id.setText(Integer.toString(member.getMemberNo()));
            level.setText(member.getMembershipLevel());
            points.setText(Integer.toString(member.getPoints()));
            email.setText(member.getEmail());
          }
        });

    return gridPane;
  }

  /**
   * todo: javadoc
   */
  private void selectedMemberListener(TableView<BonusMember> memberTable) {
    memberTable.getSelectionModel().selectedItemProperty().addListener((oldVal, newVal, member) -> {
      if (member != null) {
        this.selectedMember = member;
      }
    });
  }

  /**
   * todo: javadoc
   */
  private Button makeDeleteMemberButton(TableView<BonusMember> memberTable) {
    Button deleteBtn = new Button("Delete Member");
    //-- "Delete Member" button action--
    deleteBtn.setOnAction(actionEvent -> {
      //BonusMember member = memberTable.getSelectionModel().getSelectedItem();
      if (selectedMember != null) {
        if (mainController.doShowDeleteMemberConfirmation(selectedMember)) {
          archive.removeMember(selectedMember);
          updateMemberListWrapper();
        }
      } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("You must first select which member you want to delete.");
        alert.showAndWait();
      }
    });// deleteBtn.setOnAction
    return deleteBtn;
  }


  /**
   * Creates an observable version of archive list.
   *
   * @return
   */
  private ObservableList<BonusMember> getMemberListWrapper() {
    return FXCollections.observableArrayList(archive.getArchiveValuesAsList());
  }

  /**
   * Fetches an updated version of the observable member list.
   */
  public void updateMemberListWrapper() {
    this.memberListWrapper.setAll(this.getMemberListWrapper());
  }

  /**
   * Support method for debugging which adds 5 different members to the collection.
   */
  private void addDummies() {

    LocalDate oleEnrollDate = LocalDate.of(2006, 2, 15);
    Personals ole = new Personals("Ole", "Olsen",
        "ole.olsen@dot.com", "ole");
    archive.addMember(ole, oleEnrollDate);

    LocalDate toveEnrollDate = LocalDate.of(2007, 5, 3);
    Personals tove = new Personals("Tove", "Hansen",
        "tove.hansen@dot.com", "tove");
    archive.addMember(tove, toveEnrollDate);

    LocalDate liseEnrollDate = LocalDate.of(2008, 2, 10);
    Personals lise = new Personals("Lise", "Lisand",
        "lise@lisand.no", "lise");
    archive.addMember(lise, liseEnrollDate);

    LocalDate jonasEnrollDate = oleEnrollDate; //same date as Ole
    Personals jonas = new Personals("Jonas", "Johnsen",
        "jon@johnsen.no", "jonny");
    archive.addMember(jonas, jonasEnrollDate);

    LocalDate erikEnrollDate = LocalDate.of(2007, 3, 1);
    Personals erik = new Personals("Erik", "Eriksen",
        "rikken@eriksen.no", "rikkenrules");
    archive.addMember(erik, erikEnrollDate);


  }
}

