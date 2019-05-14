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
import java.util.*;

public class confirmInit {
   static boolean cont = true;
  public static boolean initDisplay() {
    
    cont = true;
      
    Stage initAlert = new Stage();
    initAlert.setTitle("Alert");
    //makes the user have to respond to window before continuing 
    initAlert.initModality(Modality.APPLICATION_MODAL);
    
    Button okButton = new Button("Ok");
    Button cancelButton = new Button("Cancel");
    
    okButton.setOnAction(e -> { 
      initAlert.close();
    });
    cancelButton.setOnAction(e -> {
      cont = false;
      initAlert.close();
    });
    initAlert.setOnCloseRequest(e -> {
      e.consume();
      cont = false;
      initAlert.close();
    });
      
    Label warningLabel = new Label("     Enter name CSV, then enter chart CSV");
    
    
    VBox vBox = new VBox(20);
    HBox hBox = new HBox(20);
    vBox.setPadding(new Insets(20,20,20,20));
    hBox.setAlignment(Pos.CENTER);
    hBox.getChildren().addAll(okButton, cancelButton);
    vBox.getChildren().addAll(warningLabel, hBox);
    warningLabel.setAlignment(Pos.CENTER);
    Scene scene = new Scene(vBox, 275, 100);
    
    initAlert.setScene(scene);
    initAlert.showAndWait();
    
    return cont;
  }
}
  