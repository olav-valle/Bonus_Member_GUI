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

  @Override
  public void init(){
    archive = new MemberArchive();
    this.addDummies();
  }
  @Override
  public void start(Stage primaryStage) throws Exception {

    // BorderPane as scene root
    BorderPane root = new BorderPane();

    //HBox for toolbar
    HBox toolBar = new HBox();
    root.setTop(toolBar);

// TODO: 18/03/2020 We need to get name from the Member object's Personals.
//  Maybe we can call a method in the member, instead? Yep, the PropertyValueFactory
//  finds the correct method through magic.

    // TODO: 18/03/2020 refactor table creation
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


    //VBox for center view
    VBox vBoxCenter = new VBox();
    //Add table to center VBox
    vBoxCenter.getChildren().add(centerTable);

    // set center VBox as root center view
    root.setCenter(vBoxCenter);

    // Set the scene
    Scene scene = new Scene(root, 200, 200);

    // Set the stage
    primaryStage.setScene(scene);
    //show the UI
    primaryStage.show();
  }

  private ObservableList<BonusMember> getMemberListWrapper(){
    return FXCollections.observableArrayList(archive.getArchiveValuesAsList());
  }
  private Node makeCenterTable(){
    return null;
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
