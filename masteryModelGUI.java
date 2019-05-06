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
  
  Button initButton, editButton, saveButton, nextButton, prevButton, checkButton, crossButton, crossCheckButton;
  Stage initWindow;
  Scene initScene, chartScene, chartScene2;
  
  private TextField selectedTextField;
  TextField testText = new TextField();
  TextField testText2 = new TextField();
  
  // "\u237B" unicode for cross check
  // "\u2713" unicode for check mark
  // "\u2717" unicode for cross
  
  
  // main method used to launch gui
  public static void main(String[] args) {
    launch(args);
  }
  
  //main gui method
  public void start(Stage primaryStage) throws Exception {
    
    initWindow = primaryStage;
    initWindow.setTitle("Mastery Model");
    
    //Initialize Button
    initButton = new Button("Initialize");
    initButton.setOnAction(e -> { 
      String filePathway = fileGetter();
      System.out.println(filePathway);
    });
    
    //Edit button
    editButton = new Button("Edit");
    editButton.setOnAction(e -> { 
      initWindow.setScene(chartScene);
      initWindow.setMaximized(true);
    });
    
    //next page button
    nextButton = new Button("Next Page");
    nextButton.setOnAction(e-> { 
      initWindow.setScene(chartScene2);
      initWindow.setMaximized(false);
      initWindow.setMaximized(true);
    });
    
    //previous page button
    prevButton = new Button("Previous Page");
    prevButton.setOnAction(e -> {
      initWindow.setScene(chartScene);
     initWindow.setMaximized(false);
     initWindow.setMaximized(true);
    });
    //check button
    checkButton = new Button("\u2713");
    checkButton.setFocusTraversable(false);
    
    checkButton.setOnAction(e -> {
      
      Node fo = chartScene.getFocusOwner();
      if (fo instanceof TextInputControl) {
        ((TextInputControl) fo).replaceSelection("\u2713");
      }
        
    });
    
    //cross button 
    Button crossButton = new Button("\u2717");
    crossButton.setFocusTraversable(false);
    
    crossButton.setOnAction(e -> {
      Node fo = chartScene.getFocusOwner();
      if (fo instanceof TextInputControl) {
        ((TextInputControl) fo).replaceSelection("\u2717");
      }
      
    });
    
    //cross check button
    Button crossCheckButton = new Button("\u237B");
    crossCheckButton.setFocusTraversable(false);
    crossCheckButton.setOnAction(e -> {
      Node fo = chartScene.getFocusOwner();
      if (fo instanceof TextInputControl) {
        ((TextInputControl) fo).replaceSelection("\u237B");
      }
      
    });
      
    BorderPane checklistLayout = new BorderPane();
    checklistLayout.setPadding(new Insets(20, 20, 20, 20));
    checklistLayout.setCenter(addGridPane());
    checklistLayout.setBottom(nextButton);
    checklistLayout.setRight(addgridVBox(checkButton, crossButton, crossCheckButton));
    
    BorderPane checklistLayout2 = new BorderPane();
    checklistLayout2.setPadding(new Insets(20, 20, 20, 20));
    checklistLayout2.setCenter(addGridPane2());
    checklistLayout2.setBottom(prevButton);
    
    
    
    initScene = new Scene(addVBox(), 250, 200); 
    chartScene = new Scene(checklistLayout, 800, 600);
    chartScene2 = new Scene(checklistLayout2, 800, 600);
    /*
    chartScene.getStylesheets().add(getClass().getResource("grid-with-borders.css").toExternalForm());
    chartScene2.getStylesheets().add(getClass().getResource("grid-with-borders.css").toExternalForm());
*/
    initWindow.setResizable(true);
    
    
    
    initWindow.setScene(initScene);
    initWindow.show();
                       
    
  }
  
  
  // *******************SUB METHODS********************
  
  
  //Opens file explorer and saves selected file pathway
  public String fileGetter() {
    String filePathway;
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    File file = fileChooser.showOpenDialog(initWindow);
      if (file != null) {
        filePathway = file.toString();        
      }
      else {
        filePathway = null;
      }
      
      return filePathway;
  }
  
  //adds the VBox layout to the first scene (small window)
  public VBox addVBox() {
    VBox layout1 = new VBox(10);
    layout1.setPadding(new Insets(0, 20, 20, 20));
    layout1.getChildren().addAll(initButton, editButton);
    layout1.setAlignment(Pos.CENTER);
    
    return layout1;
  }
  
  //this method adds the grid for all of the mastery model nodes 
  public GridPane addGridPane() {
    
    
    
    
    // all labels that will be added to checklist such as titles and checkmark areas.
    //knowledge labels
    Label knowledgeLabel = new Label("Knowledge [      /4]\nDemonstrate an understanding of uniform\nand non-uniform linear motion, in one and two dimensions");
    knowledgeLabel.setId("bold-label");
    Label knowledge1 = new Label("distinguish between the terms constant, instantaneous, and average\nwith reference to speed, velocity, and acceleration");
    Label knowledge2 = new Label("distinguish between scalar and vector quantities as they relate to\nuniform and non-uniform linear motion");
    Label knowledge3 = new Label("describe the characteristics and give examples of a\nprojectile’s motion in vertical and horizontal planes");
    //thinking labels
    Label thinkingLabel = new Label("Thinking [   /8]\n1: Investigate, in qualitative and quantitative terms,\nuniform and non-uniform linear motion, and solve related problems");
    thinkingLabel.setId("bold-label");
    Label thinking1 = new Label("analyse and interpret position–time, velocity– time, and acceleration–time graphs of motion in one dimension");
    Label thinking2 = new Label("solve problems involving distance, position, and displacement");
    Label thinking3 = new Label("solve related problems using graphical analysis");
    Label thinking4 = new Label("solve related problems using algebraic equations");
    Label thinking5 = new Label("solve problems related to the motion of a projectile");
    Label thinkingLabLabel = new Label("2: Kinematic Labs  [       /4]");
    thinkingLabLabel.setId("bold-label");
    Label thinkingLab1 = new Label("conduct an inquiry into the uniform and non-uniform linear motion of an object");
    Label thinkingLab2 = new Label("plan and conduct an inquiry into the motion of objects in one dimension");
    Label thinkingLab3 = new Label("conduct an inquiry into the projectile motion of an object");
    
    // Result Labels
    Label gradeLabel1 = new Label("Not Attempted [0]");
    Label gradeLabel2 = new Label("Developing [2]");
    Label gradeLabel3 = new Label("Unerstanding [3]");
    Label gradeLabel4 = new Label("Mastery [4]");
    Label gradeLabel5 = new Label("Not Attempted [0]");
    Label gradeLabel6 = new Label("Developing [2]");
    Label gradeLabel7 = new Label("Unerstanding [3]");
    Label gradeLabel8 = new Label("Mastery [4]");
    Label gradeLabel9 = new Label("Not Attempted [0]");
    Label gradeLabel10 = new Label("Developing [2]");
    Label gradeLabel11 = new Label("Unerstanding [3]");
    Label gradeLabel12 = new Label("Mastery [4]");
    //Score with checks labels
    
    
    
    
    gradeLabel1.setId("bold-label");
    gradeLabel2.setId("bold-label");
    gradeLabel3.setId("bold-label");
    gradeLabel4.setId("bold-label");
    gradeLabel5.setId("bold-label");
    gradeLabel6.setId("bold-label");
    gradeLabel7.setId("bold-label");
    gradeLabel8.setId("bold-label");
    gradeLabel9.setId("bold-label");
    gradeLabel10.setId("bold-label");
    gradeLabel11.setId("bold-label");
    gradeLabel12.setId("bold-label");
    
    
    
    
    
    
    
      
    
   // knowledgeLabel.setPadding(new Insets(10,10,10,10));
    
    GridPane chartGrid = new GridPane();
    //sets gap between cells
    chartGrid.setVgap(0);
    chartGrid.setHgap(0);
    
    //adds content to each cell. first two paramters are coloumn and row and other two are how many cells one node can take up
    //knowledge
    chartGrid.add(knowledgeLabel, 0,0,1,1);
    chartGrid.add(knowledge1, 0,1,1,1);
    chartGrid.add(knowledge2, 0,2,1,1);
    chartGrid.add(knowledge3, 0,3,1,1);
    //thinking
    chartGrid.add(thinkingLabel, 0,4,1,1);
    chartGrid.add(thinking1, 0,5,1,1);
    chartGrid.add(thinking2, 0,6,1,1);
    chartGrid.add(thinking3, 0,7,1,1);
    chartGrid.add(thinking4, 0,8,1,1);
    chartGrid.add(thinking5, 0,9,1,1);
    //thinking labs
    chartGrid.add(thinkingLabLabel, 0,10,1,1);
    chartGrid.add(thinkingLab1, 0,11,1,1);
    chartGrid.add(thinkingLab2, 0,12,1,1);
    chartGrid.add(thinkingLab3, 0,13,1,1);
    
    //Grade labels
    chartGrid.add(gradeLabel1, 1,0,1,1);
    chartGrid.add(gradeLabel2, 2,0,1,1);
    chartGrid.add(gradeLabel3, 3,0,1,1);
    chartGrid.add(gradeLabel4, 4,0,1,1);
    
    chartGrid.add(gradeLabel5, 1,4,1,1);
    chartGrid.add(gradeLabel6, 2,4,1,1);
    chartGrid.add(gradeLabel7, 3,4,1,1);
    chartGrid.add(gradeLabel8, 4,4,1,1);
    
    chartGrid.add(gradeLabel9, 1,10,1,1);
    chartGrid.add(gradeLabel10, 2,10,1,1);
    chartGrid.add(gradeLabel11, 3,10,1,1);
    chartGrid.add(gradeLabel12, 4,10,1,1);
    
    //score labels that hold checks
    chartGrid.add(testText,1,1,1,1); 
    chartGrid.add(testText2,2,1,1,1); 
    
    
    
    
    
    //adds borders around each cell
    chartGrid.setGridLinesVisible(true);
    
    //returns chart grid so it can be added to the borderpane
    return chartGrid;
  }
  
  //Second page of charts
  public GridPane addGridPane2() {
    
    //communication labels
    Label comLabel = new Label("Communication [   /4]");
    comLabel.setId("bold-label");
    Label com1 = new Label("Organizes ideas in a clear and logical manner");
    Label com2 = new Label("Communicates appropriately for audience (test vs. lab)");
    Label com3 = new Label("Uses appropriate terminology");
    Label com4 = new Label("Uses appropriate symbols");
    Label com5 = new Label("Uses appropriate formulae");
    Label com6 = new Label("Uses appropriate scientific notation");
    Label com7 = new Label("Uses appropriate significant digits");
    //Application labels
    Label appLabel = new Label("Application [   /4]\nAnalyse technologies that apply concepts related to kinematics, and assess the technologies’ social and environmental impact");
    appLabel.setId("bold-label");
    Label app1 = new Label("analyse, on the basis of research, a technology that applies concepts related to kinematics");
    Label app2 = new Label("assess the impact on society and the environment of a technology that applies concepts related to kinematics");
    
    //grade labels
    Label gradeLabel1 = new Label("Not Attempted [0]");
    Label gradeLabel2 = new Label("Developing [2]");
    Label gradeLabel3 = new Label("Unerstanding [3]");
    Label gradeLabel4 = new Label("Mastery [4]");
    Label gradeLabel5 = new Label("Not Attempted [0]");
    Label gradeLabel6 = new Label("Developing [2]");
    Label gradeLabel7 = new Label("Unerstanding [3]");
    Label gradeLabel8 = new Label("Mastery [4]");
    
    gradeLabel1.setId("bold-label");
    gradeLabel2.setId("bold-label");
    gradeLabel3.setId("bold-label");
    gradeLabel4.setId("bold-label");
    gradeLabel5.setId("bold-label");
    gradeLabel6.setId("bold-label");
    gradeLabel7.setId("bold-label");
    gradeLabel8.setId("bold-label");
    
    GridPane chartGrid2 = new GridPane();
    //sets gap between cells
    chartGrid2.setVgap(0);
    chartGrid2.setHgap(0);
    
    
    
    //Communication
    chartGrid2.add(comLabel, 0,0,1,1);
    chartGrid2.add(com1, 0,1,1,1);
    chartGrid2.add(com2, 0,2,1,1);
    chartGrid2.add(com3, 0,3,1,1);
    chartGrid2.add(com4, 0,4,1,1);
    chartGrid2.add(com5, 0,5,1,1);
    chartGrid2.add(com6, 0,6,1,1);
    chartGrid2.add(com7, 0,7,1,1);
    //Application
    chartGrid2.add(appLabel, 0,8,1,1);
    chartGrid2.add(app1, 0,9,1,1);
    chartGrid2.add(app2, 0,10,1,1);
    //grades header
    chartGrid2.add(gradeLabel1, 1,0,1,1); 
    chartGrid2.add(gradeLabel2, 2,0,1,1);
    chartGrid2.add(gradeLabel3, 3,0,1,1);
    chartGrid2.add(gradeLabel4, 4,0,1,1);
    
    chartGrid2.add(gradeLabel5, 1,8,1,1);
    chartGrid2.add(gradeLabel6, 2,8,1,1);
    chartGrid2.add(gradeLabel7, 3,8,1,1);
    chartGrid2.add(gradeLabel8, 4,8,1,1);
    
    
    //adds borders around each cell
    chartGrid2.setGridLinesVisible(true);
    
    //returns chart grid so it can be added to the borderpane
    return chartGrid2;
    
  }
  
  
  public VBox addgridVBox(Button checkButton, Button crossButton, Button crossCheckButton) {
    VBox gridVBox = new VBox(20);
    gridVBox.setPadding(new Insets(20,20,20,20));
    
    gridVBox.getChildren().addAll(checkButton, crossButton, crossCheckButton);
    
    return gridVBox;
    
    
                        
  }
  
 
  
  
  
  
  
  
  
  
}
