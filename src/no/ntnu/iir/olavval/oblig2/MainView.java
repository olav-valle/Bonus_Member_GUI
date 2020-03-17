package no.ntnu.iir.olavval.oblig2;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainView extends Application {

  private static void main(String[] args){
   launch(MainView.class, args);
  }
  @Override
  public void start(Stage primaryStage) throws Exception {

    // BorderPane as scene root
    BorderPane root = new BorderPane();

    //HBox for toolbar
    HBox toolBar = new HBox();
    root.setTop(toolBar);

    //VBox for center view
    VBox center = new VBox();

    //TableView for center content
    TableView<String> centerList = new TableView<>();
    //Add table to center VBox
    center.getChildren().add(centerList);

    // set center VBox as root center view
    root.setCenter(center);

    // Set the scene
    Scene scene = new Scene(root, 200, 200);

    // Set the stage
    primaryStage.setScene(scene);
    //show the UI
    primaryStage.show();
  }

  private Node makeCenterTable(){
    return null;
  }
}
