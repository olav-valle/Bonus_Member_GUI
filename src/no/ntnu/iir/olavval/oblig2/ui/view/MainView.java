package no.ntnu.iir.olavval.oblig2.ui.view;

import java.time.LocalDate;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import no.ntnu.iir.olavval.oblig2.model.*;

public class MainView extends Application {

  private MemberArchive archive;

  public static void main(String[] args){
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
    archive = new MemberArchive();
    this.addDummies();
  }

  /**
   * The main entry point for all JavaFX applications.
   * The start method is called after the init method has returned,
   * and after the system is ready for the application to begin running.
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

  private VBox makeCenterPane(){
    // TODO: 18/03/2020 add grid view for member details
    //-- VBox with member table and member details view --
    VBox vBox = new VBox(10);

    //Fetch member table for top of VBox
    TableView<BonusMember> memberTable = makeMemberTable();
    // Member table should grow to fill VBox
    VBox.setVgrow(memberTable, Priority.ALWAYS);
    VBox.setMargin(memberTable, new Insets(10.0, 10.0, 0, 10.0));

    // --GridPane for member details at bottom of VBox--
    VBox memberDetailGrid = makeMemberDetailGrid(memberTable);
    VBox.setVgrow(memberDetailGrid, Priority.NEVER);

    VBox.setMargin(memberDetailGrid, new Insets(0, 10.0, 10.0, 10.0));

    vBox.getChildren().addAll(memberTable, memberDetailGrid);

    return vBox;
  }


  private TableView<BonusMember> makeMemberTable(){
    // Name column
    TableColumn<BonusMember, String> nameCol = new TableColumn<>("First Name");
    nameCol.setCellValueFactory(new PropertyValueFactory<>("FirstName"));

    // Surname column
    TableColumn<BonusMember, String> surnameCol= new TableColumn<>("Surname");
    surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));

    // Member ID number column
    TableColumn<BonusMember, Integer> memberNoCol= new TableColumn<>("ID Number");
    memberNoCol.setCellValueFactory(new PropertyValueFactory<>("memberNo"));

    TableColumn<BonusMember, Integer> pointCol = new TableColumn<>("Bonus Points");
    pointCol.setCellValueFactory(new PropertyValueFactory<>("points"));

    // Member level column
    TableColumn<BonusMember, String> levelCol = new TableColumn<>("Bonus Level");
    levelCol.setCellValueFactory(new PropertyValueFactory<>("MembershipLevel"));

    //TableView for center content
    TableView<BonusMember> centerTable = new TableView<>();
    centerTable.setItems(this.getMemberListWrapper());
    centerTable.getColumns().addAll(memberNoCol, surnameCol, nameCol, /*pointCol,*/ levelCol);

    return centerTable;
  }

  private VBox makeMemberDetailGrid(TableView<BonusMember> memberTable){
    GridPane gridPane = new GridPane();

    gridPane.setHgap(10);
    gridPane.setVgap(10);

    // First Name field (0.0) (1.0)
    TextField firstName = new TextField();
    firstName.setPromptText("First name");
    gridPane.add(new Label("First Name: "), 0, 0);
    gridPane.add(firstName, 1, 0);

    // Surname field (0.1)(1.1)
    TextField surname = new TextField();
    surname.setPromptText("Surname");
    gridPane.add(new Label("Surname: "), 0, 1);
    gridPane.add(surname, 1, 1);

    // Member ID field (0.2)(1.2)
    TextField id = new TextField();
    id.setPromptText("Member ID Number");
    gridPane.add(new Label("Member ID Number: "), 0, 2);
    gridPane.add(id, 1, 2);

    // Member level field (0.3)(1.3)
    TextField level = new TextField();
    level.setPromptText("Membership Level");
    gridPane.add(new Label("Membership Level: "), 0, 3);
    gridPane.add(level, 1, 3);

    // Member points field (0.4)(1.4)
    TextField points = new TextField();
    points.setPromptText("Bonus Points Balance");
    gridPane.add(new Label("Bonus Points Balance: "), 0, 4);
    gridPane.add(points, 1, 4);

    // -- GridPane observes TableView for changes in selected element
    // TODO: 19/03/2020 Maybe make this a method and save observer as a class field?
    memberTable.getSelectionModel()
        .selectedItemProperty()
        .addListener((o, ov, nv) -> {
          if (nv != null){
            firstName.setText(nv.getFirstName());
            surname.setText(nv.getSurname());
            id.setText(Integer.toString(nv.getMemberNo()));
            level.setText(nv.getMembershipLevel());
            points.setText(Integer.toString(nv.getPoints()));
          }
        });

    // TODO: 19/03/2020 add toolbar with delete, edit options, and change return type to vbox.
    Button edit = new Button("Edit Member");
    Button addPoints = new Button("Add Points");
    Button save = new Button("Save Changes");
    HBox buttons = new HBox(10, edit, addPoints, save);
    //buttons.setPadding(new Insets(5));

    Button delete = new Button("Delete Member");



    return new VBox(10, buttons, gridPane, delete);

  }

  private ObservableList<BonusMember> getMemberListWrapper(){
    return FXCollections.observableArrayList(archive.getArchiveValuesAsList());
  }

  private void addDummies() {

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
