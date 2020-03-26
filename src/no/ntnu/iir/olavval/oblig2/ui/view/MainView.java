package no.ntnu.iir.olavval.oblig2.ui.view;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.value.ObservableObjectValue;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import no.ntnu.iir.olavval.oblig2.model.BonusMember;
import no.ntnu.iir.olavval.oblig2.model.MemberArchive;
import no.ntnu.iir.olavval.oblig2.model.Personals;
import no.ntnu.iir.olavval.oblig2.ui.controller.MainController;

/**
 * MainView class for Bonus Member Administration app.
 */

@SuppressWarnings("CheckStyle")
public class MainView extends Application {

  private Logger logger;
  private MainController mainController;
  private MemberArchive archive;
  private ObservableList<BonusMember> memberListWrapper;
  private ObservableObjectValue<BonusMember> observableSelectedMember;
  private LocalDate testDate; // Date object for testing

  /**
   * Main method.
   *
   * @param args No valid args.
   */
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
    this.logger = Logger.getLogger(getClass().toString());

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

  @Override
  public void start(Stage primaryStage) {
    // Center of root is an HBox with a table and grid
    TableView<BonusMember> memberTable = makeMemberTable(); // member table

    // Set selected member listener
    // !! Crucial that this field is initialized before ANY OTHER UI elements are created. !!
    // Other UI elements use this Observable, and it must be set to avoid NullPointerExceptions.
    this.observableSelectedMember = memberTable.getSelectionModel().selectedItemProperty();

    GridPane memberDetailsGrid = makeMemberDetailGrid();

    HBox centerBox = makeCenterPane(memberTable, memberDetailsGrid);
    centerBox.setPadding(new Insets(5));


    // Status bar for bottom of root.
    HBox statusBar = new HBox();
    Text members = new Text("Total members: " + memberListWrapper.size());
    Pane spacer = new Pane();
    Text date = new Text("Today's date: " + testDate.toString());
    statusBar.getChildren().addAll(members, spacer, date);
    HBox.setHgrow(spacer, Priority.SOMETIMES);
    statusBar.setStyle("-fx-background-color: #a9a9a9; -fx-border-color: #000000");
    statusBar.setPadding(new Insets(2));
    statusBar.setSpacing(10);

    // VBox for Menu and Toolbar
    ToolBar toolBar = makeTopToolBar();
    VBox topBox = new VBox(toolBar);

    // BorderPane as scene root
    BorderPane root = new BorderPane();
    root.setCenter(centerBox);
    root.setTop(topBox);
    root.setBottom(statusBar);
    // Set the scene
    Scene scene = new Scene(root);
    primaryStage.setTitle("Bonus Member Administration");
    // Set the stage
    primaryStage.setScene(scene);
    //show the UI
    primaryStage.show();
  }

  /**
   * Makes the toolbar that goes at the top of the UI.
   */
  private ToolBar makeTopToolBar() {
    // -- Add New Member button
    Button addMemberBtn = makeButtonAddMember();
    Tooltip.install(addMemberBtn, new Tooltip("Create a new member account."));

    // -- Upgrade all members button.
    Button upgradeBtn = makeButtonUpgradeMembers();
    Tooltip.install(upgradeBtn, new Tooltip("Upgrade all members who are eligible."));

    // -- Edit member details. Unused.
    Button editBtn = new Button("Edit Member");
    Tooltip.install(editBtn, new Tooltip("Edit member details."));

    // -- Save edits to member details. Unused.
    Button saveChangesBtn = new Button("Save Changes");
    Tooltip.install(saveChangesBtn, new Tooltip("Save changes to member."));

    // -- Add points button
    Button addPointsBtn = makeButtonAddPoints();
    Tooltip.install(addPointsBtn, new Tooltip("Add points to currently selected member"));

    // -- Delete Member button
    Button deleteMemberButton = makeButtonDeleteMember();
    deleteMemberButton.setStyle("-fx-font-weight: bold; -fx-background-color: #f44336");
    Tooltip.install(deleteMemberButton, new Tooltip("Delete this member from the registry."));

    // expanding spacer between the right and left side buttons
    Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.SOMETIMES);

    return new ToolBar(
        addMemberBtn,
        upgradeBtn,
        spacer,
        addPointsBtn,
        deleteMemberButton
    );
  }

  /**
   * Makes the Add Member button for the toolbar.
   */
  private Button makeButtonAddMember() {
    Button addMemberBtn = new Button("Add New Member");

    addMemberBtn.setOnAction(actionEvent -> {
    });

    return addMemberBtn;
  }

  /**
   * Makes the Upgrade Member button for the toolbar.
   */
  private Button makeButtonUpgradeMembers() {
    Button upgradeBtn = new Button("Run Upgrade Checks");

    upgradeBtn.setOnAction(eventAction -> {
      boolean confirm = mainController.doShowUpgradeConfirmDialog();
      if (Boolean.TRUE.equals(confirm)) {
        archive.checkAndUpgradeMembers(testDate);
        updateMemberListWrapper();
      }

    });

    return upgradeBtn;
  }

  /**
   * Makes the Add Points button for the toolbar.
   */
  private Button makeButtonAddPoints() {
    Button addPointsBtn = new Button("Add Points");
    addPointsBtn.setDisable(true);
    // Button enable toggle
    observableSelectedMember.addListener((obs, ov, nv) -> addPointsBtn.setDisable((nv == null)));
    //-- "Add Points" button action--
    addPointsBtn.setOnAction(actionEvent -> {
      BonusMember selectedMember = observableSelectedMember.get();
      if (selectedMember != null) {
        mainController.doShowAddPointsModal(archive, selectedMember, this);
      } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("You must first select which member you want to add points to.");
        alert.showAndWait();
      }
    });// addPointsBtn.setOnAction

    return addPointsBtn;
  }

  /**
   * Makes the Delete Member button for the toolbar.
   */
  private Button makeButtonDeleteMember() {
    Button deleteBtn = new Button("Delete Member");
    deleteBtn.setDisable(true);
    // Button enable toggle
    observableSelectedMember.addListener((obs, ov, nv) -> deleteBtn.setDisable(nv == null));
    //-- "Delete Member" button action--
    deleteBtn.setOnAction(actionEvent -> {
      BonusMember member = observableSelectedMember.get();
      if (member != null) {
        if (mainController.doShowDeleteMemberConfirmation(member)) {

          logger.log(Level.INFO, "Member "
              + member.getMemberNo()
              + ", "
              + member.getSurname()
              + " was deleted.");

          archive.removeMember(member);
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
   * Assembles the node for the center of the border pane.
   *
   * @param memberTable      The TableView object displaying the member list.
   * @param memberDetailGrid The GridPane object displaying member details in text fields.
   * @return HBox Node containing memberTable and memberDetailBox
   */
  private HBox makeCenterPane(TableView<BonusMember> memberTable, GridPane memberDetailGrid) {
    //-- VBox with member table and member details view --

    //-- Set Member table for top of VBox --
    VBox.setVgrow(memberTable, Priority.ALWAYS); // Member table should grow to fill VBox
    VBox.setMargin(memberTable, new Insets(10.0, 10.0, 0, 10.0));

    // --Set GridPane for member details at bottom of VBox--
    //Text fields in details grid listen to table view for selected member, so we pass the table.
    VBox.setVgrow(memberDetailGrid, Priority.NEVER);

    VBox.setMargin(memberDetailGrid, new Insets(0, 10.0, 10.0, 10.0));

    HBox hBox = new HBox(10);
    hBox.getChildren().addAll(memberTable, memberDetailGrid);

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

    return centerTable;
  }

  /**
   * Assembles the grid pane that displays member details at the bottom half of the window.
   *
   * @return GridPane Node for displaying member details.
   */
  private GridPane makeMemberDetailGrid() {
    GridPane gridPane = new GridPane();
    gridPane.setHgap(10);
    gridPane.setVgap(10);
    gridPane.setPadding(new Insets(5));

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

    // Member email address (0.5)(1.5)
    gridPane.add(new Label("Email Address: "), 0, 5);
    TextField email = new TextField();
    email.setPromptText("Email Address");
    gridPane.add(email, 1, 5);

    // Enrollment date in three separate fields (yyyy/mm/dd)
    TextField year = new TextField();
    year.setPromptText("YYYY");
    year.setPrefColumnCount(4);
    TextField month = new TextField();
    month.setPromptText("MM");
    month.setPrefColumnCount(2);
    TextField day = new TextField();
    day.setPromptText("DD");
    day.setPrefColumnCount(2);
    HBox dateBox = new HBox(year, new Text("/"), month, new Text("/"), day);
    dateBox.setSpacing(2);
    gridPane.add(new Label("Enroll Date: "), 0, 6);
    gridPane.add(dateBox, 1, 6);


    firstName.setEditable(false);
    surname.setEditable(false);
    id.setEditable(false);
    level.setEditable(false);
    points.setEditable(false);
    email.setEditable(false);
    year.setEditable(false);
    month.setEditable(false);
    day.setEditable(false);

    // -- GridPane observes TableView for changes in selected element
    observableSelectedMember
        .addListener((o, ov, member) -> {
          if (member != null) {
            firstName.setText(member.getFirstName());
            surname.setText(member.getSurname());
            id.setText(Integer.toString(member.getMemberNo()));
            level.setText(member.getMembershipLevel());
            points.setText(Integer.toString(member.getPoints()));
            email.setText(member.getEmail());
            year.setText(Integer.toString(member.getEnrolledDate().getYear()));
            month.setText(Integer.toString(member.getEnrolledDate().getMonthValue()));
            day.setText(Integer.toString(member.getEnrolledDate().getDayOfMonth()));
          }
        });

    return gridPane;
  }


  /**
   * Creates an observable version of archive list.
   *
   * @return ObservarbleList of the BonusMember registry.
   */
  private ObservableList<BonusMember> getMemberListWrapper() {
    return FXCollections.observableArrayList(archive.getArchiveValuesAsList());
  }

  /**
   * Fetches an updated version of the observable member list.
   */
  public void updateMemberListWrapper() {
    this.memberListWrapper.setAll(this.getMemberListWrapper());
    logger.log(Level.INFO, "MemberListWrapper updated.");
  }

  /**
   * Support method for debugging which adds 5 different members to the collection.
   */
  private void addDummies() {

    this.testDate = LocalDate.of(2008, 2, 10);

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

    Personals jonas = new Personals("Jonas", "Johnsen",
        "jon@johnsen.no", "jonny");
    archive.addMember(jonas, oleEnrollDate); // same enrollDate as Ole

    LocalDate erikEnrollDate = LocalDate.of(2007, 3, 1);
    Personals erik = new Personals("Erik", "Eriksen",
        "rikken@eriksen.no", "rikkenrules");
    archive.addMember(erik, erikEnrollDate);


  }
}

