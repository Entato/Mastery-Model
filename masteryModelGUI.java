import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javafx.application.Application;
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

public class masteryModelGUI extends Application {
  
  Button initButton;
  Button editButton;
  Stage window;
  
  
  public static void main(String[] args) {
    launch(args);
  }
  public void start(Stage primaryStage) throws Exception{
    
    window = primaryStage;
    window.setTitle("Mastery Model");
    
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    
    initButton = new Button("Initialize");
    initButton.setOnAction(e -> { 
      File file = fileChooser.showOpenDialog(window);
      if (file != null) {
        String filePathWay = file.toString();
        System.out.println(filePathWay);
      }
    
    });
    editButton = new Button("Edit");

    
    
    
    
    
    VBox layout1 = new VBox(10);
    layout1.setPadding(new Insets(20, 20, 20, 20));
    layout1.getChildren().addAll(initButton, editButton);
    Scene scene1 = new Scene(layout1, 250, 200); 
    
    //scene1.getStylesheets().add(getClass().getResource("MasteryStyleSheet.css").toExternalForm());
    
    layout1.setAlignment(Pos.CENTER);
    window.setScene(scene1);
    window.show();
    
                       
    
  }
  
   
  
 
  
  
  
  
  
  
  
  
}