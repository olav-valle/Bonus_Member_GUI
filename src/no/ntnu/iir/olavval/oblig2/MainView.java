package no.ntnu.iir.olavval.oblig2;

import java.time.LocalDate;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
  public void init(){
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
    //  center is anchor with member table on top and grid view for details on bottom
    //

    // BorderPane as scene root
    BorderPane root = new BorderPane();

    // TODO: 18/03/2020 method for making toolbar and menu for root top
    //HBox for toolbar
    HBox toolBar = new HBox();

    root.setTop(toolBar);

    //VBox for center view
    VBox vBoxCenter = new VBox();

    // Center of root is an anchor pane with a table and grid view
    root.setCenter(makeCenterPane());

    // Set the scene
    Scene scene = new Scene(root);
    primaryStage.setTitle("Bonus Member Administration");
    // Set the stage
    primaryStage.setScene(scene);
    //show the UI
    primaryStage.show();
  }

  private Node makeCenterPane(){
    // TODO: 18/03/2020 add grid view for member details
    //-- VBox with member table and member details view --
    VBox vBox = new VBox(10);

    //Fetch member table for top of VBox
    TableView<BonusMember> memberTable = makeMemberTable();
    // Member table should grow to fill VBox
    VBox.setVgrow(memberTable, Priority.ALWAYS);
    VBox.setMargin(memberTable, new Insets(10.0));

    // --GridPane for member details at bottom of VBox--
    GridPane gridPane = makeMemberDetailGrid();
    VBox.setVgrow(gridPane, Priority.NEVER);
    VBox.setMargin(gridPane, new Insets(10.0));

    vBox.getChildren().addAll(memberTable, gridPane);

    return vBox;
  }

  private GridPane makeMemberDetailGrid(){
    GridPane gridPane = new GridPane();
    gridPane.setHgap(10);
    gridPane.setVgap(10);

    // First Name field
    TextField firstName = new TextField();
    firstName.setPromptText("First name");
    gridPane.add(new Label("First Name: "), 0, 0);
    gridPane.add(firstName, 1, 0);

    // Surname field
    TextField surname = new TextField();
    surname.setPromptText("Surname");
    gridPane.add(new Label("Surname: "), 0, 1);
    gridPane.add(surname, 1, 1);


    return gridPane;
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

    //TableView for center content
    TableView<BonusMember> centerTable = new TableView<>();
    centerTable.setItems(this.getMemberListWrapper());
    centerTable.getColumns().addAll(memberNoCol, nameCol, surnameCol, pointCol);
    return centerTable;
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
