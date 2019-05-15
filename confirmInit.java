//imports
import java.lang.String;
import java.util.*;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.collections.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.control.cell.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import java.nio.file.*;
import java.io.*;

//class name
public class confirmInit {
  //global boolean
  static boolean cont = true;
  
  public static boolean initDisplay() {
    //sets cont to true as default 
    cont = true;
    
    //creates a stage (a window)
    Stage initAlert = new Stage();
    //sets the title of the Stage to "Alert"
    initAlert.setTitle("Alert");
    //makes the user have to respond to window before continuing 
    initAlert.initModality(Modality.APPLICATION_MODAL);
    //creates the buttons
    Button okButton = new Button("Ok");
    Button cancelButton = new Button("Cancel");
    //the ok button will continue the program as normal
    okButton.setOnAction(e -> { 
      //closes window
      initAlert.close();
    });
    //the cancel button will convert cont to false and when returned, will essentially cancel the initialization
    cancelButton.setOnAction(e -> {
      //sets cont to false
      cont = false;
      //closes window
      initAlert.close();
    });
    //closing the window also converts cont to false
    initAlert.setOnCloseRequest(e -> {
      //consumes the close window event so I can do some code before it closes 
      e.consume();
      //sets cont to false
      cont = false;
      //closes window
      initAlert.close();
    });
    //creates label telling user what to do 
    Label warningLabel = new Label("     Enter name CSV, then enter chart CSV");
    
    //creates VBox layout 
    VBox vBox = new VBox(20);
    //creates HBox layout 
    HBox hBox = new HBox(20);
    //sets the padding of the VBox
    vBox.setPadding(new Insets(20,20,20,20));
    //puts the HBox in the center
    hBox.setAlignment(Pos.CENTER);
    //collects children
    hBox.getChildren().addAll(okButton, cancelButton);
    //collects children, including the HBox
    vBox.getChildren().addAll(warningLabel, hBox);
    //puts label in center 
    warningLabel.setAlignment(Pos.CENTER);
    //creates scene with layout vBox and sets the dimensions 
    Scene scene = new Scene(vBox, 275, 100);
    
    //sets the scene 
    initAlert.setScene(scene);
    //lets user see program
    initAlert.showAndWait();
    
    //returns the value of cont
    return cont;
  }
}
//END of this class
  
