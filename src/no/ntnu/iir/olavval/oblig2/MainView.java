package no.ntnu.iir.olavval.oblig2;

import java.lang.reflect.Member;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javax.smartcardio.CardChannel;

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

    //HBox for toolbar
    HBox toolBar = new HBox();
    root.setTop(toolBar);

    //VBox for center view
    VBox vBoxCenter = new VBox();
    //Fetch member table
    TableView<BonusMember> memberTable = makeCenterTable();
    //Anchor pane for center of BorderPane
    AnchorPane centerPane = new AnchorPane();
    //Add member table to anchor pane
    centerPane.getChildren().add(memberTable);
    AnchorPane.setTopAnchor(memberTable, 10.0);
    AnchorPane.setRightAnchor(memberTable, 10.0);
    AnchorPane.setLeftAnchor(memberTable, 10.0);

    //Add table to center VBox
    //vBoxCenter.getChildren().add(makeCenterTable());
    // set center VBox as root center view

    root.setCenter(centerPane);

    // Set the scene
    Scene scene = new Scene(root);
    primaryStage.setTitle("Bonus Member Administration");
    // Set the stage
    primaryStage.setScene(scene);
    //show the UI
    primaryStage.show();
  }

  private ObservableList<BonusMember> getMemberListWrapper(){
    return FXCollections.observableArrayList(archive.getArchiveValuesAsList());
  }
  private TableView<BonusMember> makeCenterTable(){
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
