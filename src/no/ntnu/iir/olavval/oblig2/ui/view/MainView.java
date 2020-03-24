package no.ntnu.iir.olavval.oblig2.ui.view;

import com.sun.javafx.scene.control.IntegerField;
import java.time.LocalDate;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import no.ntnu.iir.olavval.oblig2.model.BonusMember;
import no.ntnu.iir.olavval.oblig2.model.MemberArchive;
import no.ntnu.iir.olavval.oblig2.model.Personals;
import no.ntnu.iir.olavval.oblig2.ui.controller.MainController;

public class MainView extends Application {

  private Logger log;
  private  MainController mainController;
  private MemberArchive archive;
  private ObservableList<BonusMember> memberListWrapper;



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
    this.getMemberListWrapper(); // Wrapped observable member list.
    archive = new MemberArchive();
    this.addDummies();
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
   * @throws Exception
   */

  @Override
  public void start(Stage primaryStage) throws Exception {
    // TODO: 18/03/2020 refactor pane node creations.
    //  separate methods for each of the BorderPane areas:
    //  center is VBox with member table on top and grid view for details on bottom.
    //  Top is VBox with main menu and toolbar (adding points, creating members).
    //  Bottom is statusbar.

    // BorderPane as scene root
    BorderPane root = new BorderPane();

    // TODO: 18/03/2020 method for making toolbar and menu for root top
    //HBox for toolbar
    HBox toolBar = new HBox();

    root.setTop(toolBar);

    //VBox for center view
    VBox vBoxCenter = makeCenterPane();

    // Center of root is a VBox with a table and grid
    root.setCenter(vBoxCenter);

    // Set the scene
    Scene scene = new Scene(root);
    primaryStage.setTitle("Bonus Member Administration");
    // Set the stage
    primaryStage.setScene(scene);
    //show the UI
    primaryStage.show();
  }

  /**
   *
   * @return
   */
  private VBox makeCenterPane() {
    // TODO: 18/03/2020 add grid view for member details
    //-- VBox with member table and member details view --
    VBox vBox = new VBox(10);

    //-- Member table for top of VBox --
    TableView<BonusMember> memberTable = makeMemberTable();
    VBox.setVgrow(memberTable, Priority.ALWAYS); // Member table should grow to fill VBox
    VBox.setMargin(memberTable, new Insets(10.0, 10.0, 0, 10.0));

    // --GridPane for member details at bottom of VBox--
    //Text fields in details grid listen to table view for selected member, so we pass the table.
    VBox memberDetailGrid = makeMemberDetailGrid(memberTable);
    VBox.setVgrow(memberDetailGrid, Priority.NEVER);

    VBox.setMargin(memberDetailGrid, new Insets(0, 10.0, 10.0, 10.0));

    vBox.getChildren().addAll(memberTable, memberDetailGrid);

    return vBox;
  }


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

    TableColumn<BonusMember, Integer> pointCol = new TableColumn<>("Bonus Points");
    pointCol.setCellValueFactory(new PropertyValueFactory<>("points"));

    // Member level column
    TableColumn<BonusMember, String> levelCol = new TableColumn<>("Level");
    levelCol.setCellValueFactory(new PropertyValueFactory<>("MembershipLevel"));

    //TableView for center content
    TableView<BonusMember> centerTable = new TableView<>();
    centerTable.setItems(memberListWrapper);
    centerTable.getColumns().addAll(memberNoCol, surnameCol, nameCol, /*pointCol,*/ levelCol);

    return centerTable;
  }

  private VBox makeMemberDetailGrid(TableView<BonusMember> memberTable) {
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
    // TODO: 19/03/2020 add email field

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
          }
        });

    // TODO: 19/03/2020 add toolbar with delete, edit options, and change return type to vbox.
    Button editBtn = new Button("Edit Member");
    Button addPointsBtn = new Button("Add Points");
    Button saveChangesBtn = new Button("Save Changes");
    HBox buttons = new HBox(10, editBtn, addPointsBtn, saveChangesBtn);
    //buttons.setPadding(new Insets(5));

    Button delete = new Button("Delete Member");

    addPointsBtn.setOnAction(actionEvent ->
        showAddPointsModal(memberTable.getSelectionModel().getSelectedItem()));

    return new VBox(10, buttons, gridPane, delete);

  }

  /**
   * Displays a modal dialogue for adding points to a member.
   * @param selectedMember
   */
  private void showAddPointsModal(BonusMember selectedMember){
    Stage addPointsStage = new Stage();
    addPointsStage.setTitle("Add Points");
    addPointsStage.initModality(Modality.WINDOW_MODAL);

    VBox vBox = new VBox();
    Text pointValuePrompt = new Text("Enter value to add:");
    IntegerField pointInput = new IntegerField();
    pointInput.setEditable(true);
    pointInput.setPromptText("Add points...");





  }

  /**
   * Creates an observable version of archive list.
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

