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


public class MasteryModelIntegrated extends Application {
  
  //GUI global variables
  Stage window;
  
  Scene initScene, chartScene, chartScene2, nameScene;
  
  ComboBox <String> nameList;
  
  static GridPane knowledgeThinkingGrid = new GridPane();
  static GridPane comCriteriaGrid = new GridPane();
  static GridPane comGrid = new GridPane();
  static GridPane appGrid = new GridPane();
  
  BorderPane chart1BP;
  BorderPane chart2BP;
  
  Button initButton = new Button("Initialize");
  Button editButton = new Button("Edit");
  Button submitNameButton = new Button("Submit");
  Button nextPageButton = new Button("Next Page");
  Button prevPageButton = new Button("Previous Page");
  Button saveChangesButton = new Button("Save Changes");
  Button saveChangesButton2 = new Button("Save Changes");
  Button backButton = new Button("Back");
  //for first page
  Button checkButton = new Button("\u2713");
  Button crossButton = new Button("\u2717");
  Button crossCheckButton = new Button("\u237B");
  //for second page
  Button checkButton2 = new Button("\u2713");
  Button crossButton2 = new Button("\u2717");
  Button crossCheckButton2 = new Button("\u237B");
  
  
  static String nameFilePath = null;
  static String checkMarkFilePath = null;
  static String chartFilePath = null;
  static String studentName = null;
  
  static int textFieldCounter;
  static int indexSaver;
  
  static boolean combined;
  static boolean[] firstTime = new boolean[1];
  static boolean[] openedFirst = new boolean[1];
  static boolean[] initPressed = new boolean[1];
  
  //Labels arrays
  static ArrayList <String> leftLabels1 = new ArrayList<String>();
  static ArrayList <String> leftLabels2 = new ArrayList<String>();
  //TextFields 
  static ArrayList <TextField> textFields = new ArrayList <TextField>();
  //com assesment labels
  static ArrayList<String> comAssessments = new ArrayList<String>();
  //saving textfield changes
  static ArrayList<String[]> savedText = new ArrayList<String[]>();
  
  //__________________________________________________________________________________________________________
  //IO Global variables
  static String currentDirPath = System.getProperty("user.dir"); //Finds the path of the working directory
  static String MainFolderPath = currentDirPath + "\\MasteryModel"; //Stores the path of the main folder
  static ArrayList<String[]> student_csv_Storer = new ArrayList<String[]>(); //Initializes ArrayList that stores csv data for student info 
  static ArrayList<String[]> chart_template_Storer = new ArrayList<String[]>(); //Initializes ArrayList that stores the chart template
  static ArrayList<String[]> student_Check_Storer = new ArrayList<String[]>(); //Initializes ArrayList that stores the checks for a student for reading
  
  
  
  
//______________________________________________________________________________________________________________________________________
  //Method to create the main folder that the program refers to
  public static void mainFolderCreator(String mainPath) throws IOException{
    File MasteryModelFolder = new File(mainPath); //Creates a new file 
    boolean mainfoldercreated = MasteryModelFolder.mkdirs(); //Converts the file into the main folder
  }
  
 //______________________________________________________________________________________________________________________________________
  //Method to move selected files into the main folder (You can rename the folder to whatever you want)
  public static String fileMover(String mainPath, String filePath, String fileName) throws IOException{
    Path filemove = Files.move(Paths.get(filePath), Paths.get(mainPath + "\\" + fileName)); //Moves the file
    String path = filemove.toString(); //Converts path to a String value
    return path; //Returns the path of the moved file
  }
  //______________________________________________________________________________________________________________________________________
  //Reads and stores the info in the student info csv
  public static void student_info_Storer(String mainPath, ArrayList<String[]> studentarrlist) throws IOException{
    File student_info = new File(mainPath + "\\Student_Info.csv"); //Finds the student info file
    String line = " "; //Initializes line variable
    BufferedReader filereader = new BufferedReader(new FileReader(student_info)); //Bufferedreader to read the file
    try {
      line = filereader.readLine(); //Reads first line and discards it
      while ((line = filereader.readLine())!= null){ //Keeps running until all lines are read
        String[] temprowarray = line.split(","); //Stores the seperated values of each row into an array
        studentarrlist.add(temprowarray); //Adds array into an arraylist
      }
    }
    catch (IOException e){ //If a file wasn't found
      System.out.println("A file was not found.");
    }
    finally{
      filereader.close(); //Closes filereader
    }
  }
  //______________________________________________________________________________________________________________________________________
  //Creates a folder for each student based on their student number
  public static void student_Folder_Creator(String mainPath, ArrayList<String[]> studentarrlist) throws IOException{
    for (int i = 0; i < studentarrlist.size(); i++){ //Runs through each row of the csv
      //Creates a folder named after the student's number
      File StudentFolder = new File(mainPath + "\\" + studentarrlist.get(i)[0]); //Creates files for each student
      boolean studentCreated = StudentFolder.mkdirs(); //Converts each file into a folder
    }
  }
  //______________________________________________________________________________________________________________________________________
  //Reads and stores the info in the chart template csv
  public static void chart_template_Storer(String mainPath, ArrayList<String[]> chartarrlist) throws IOException{
    File chart_Template = new File(mainPath + "\\Assessment_Chart.csv"); //Finds the chart template
    String line = " "; //Intializes line variable
    BufferedReader filereader = new BufferedReader(new FileReader(chart_Template)); //Bufferedreader to read the file
    try{
      while ((line = filereader.readLine())!= null){ //Keeps running until all lines are read
        String newline = line.replaceAll(", ", "#####"); //Replaces the commas that aren't delimiters with temporary text
        String[] temprowarray = newline.split(","); //Stores the seperated values of each row into an array
        chartarrlist.add(temprowarray); //Adds array into an arraylist
      }
    }
    catch (IOException e){ //If a file wasn't found
      System.out.println("A file was not found.");
    }
    finally{
      filereader.close(); //Closes filereader
    }
    //Runs through each element in the arraylist and arrays inside and restores the temporary text to normal
    for (int i = 0; i < chartarrlist.size(); i++){
      for (int j = 0; j < chartarrlist.get(i).length; j++){
        //Replaces the temporary text with its original text
        chartarrlist.get(i)[j] = chartarrlist.get(i)[j].replaceAll("#####", ", "); 
      }
    }
  }
  //______________________________________________________________________________________________________________________________________
  //Method that returns the index (row) of which array contains the student info
  public static int student_index_Finder(String full_name, String mainPath, ArrayList<String[]> studentarrlist) {
    //Initializes variable to store the index number of the arraylist which contains the student's name
    int index_number = 0; 
    //Checks for the student in the array
    for (int i = 0; i < studentarrlist.size(); i++){
      if (full_name.equals(studentarrlist.get(i)[1] + ", " + studentarrlist.get(i)[2])){
        index_number = i; //Saves which array the student is stored in
        break;
      }
    }
    return index_number; //returns the index number
  }
  //______________________________________________________________________________________________________________________________________
  //Method that returns the path of the student
  public static String student_Finder(int index_number, String mainPath, ArrayList<String[]> studentarrlist){
    //Obtains the path of the student
    String student_Path = (mainPath + "\\" + studentarrlist.get(index_number)[0]);
    return student_Path; //Returns the path of the student as a string
  }
  //______________________________________________________________________________________________________________________________________
  //Methods that initializes a csv file that stores checkmarks for each student and stores it into their folder
  public static void student_chart_Initializer(String mainPath, ArrayList<String[]> studentarrlist, 
                                               ArrayList<String[]> chartarrlist) throws IOException{
    for (int i = 0; i < studentarrlist.size(); i++){
      //Creates a csv chart template for each student
      File student_Chart = new File(mainPath + "\\" + studentarrlist.get(i)[0] + "\\" 
                                      + studentarrlist.get(i)[1] + ", " + studentarrlist.get(i)[2] + ".csv");
      FileWriter writer = new FileWriter(student_Chart, false); //False makes the writer overwrite the file
      writer.close(); //Closes file writer
    }
  }
  //______________________________________________________________________________________________________________________________________
  //Method that reads and stores the student chart and returns its path
  public static String student_chart_Reader(String studentPath, int index_number, ArrayList<String[]> studentarrlist, 
                                            ArrayList<String[]> studentchartarrlist) throws IOException{
    File student_Chart = new File(studentPath + "\\" + studentarrlist.get(index_number)[1] + ", " +
                                  studentarrlist.get(index_number)[2] + ".csv"); //Finds the chart template
    String student_chart_Path = student_Chart.getPath(); //Gets the path of the student
    String line = " "; //Intializes line variable
    BufferedReader filereader = new BufferedReader(new FileReader(student_Chart)); //Bufferedreader to read the file
    try{
      while ((line = filereader.readLine())!= null){ //Keeps running until all lines are read
        String newline = line.replaceAll(", ", "#####"); //Replaces the commas that aren't delimiters with temporary text
        String[] temprowarray = newline.split(","); //Stores the seperated values of each row into an array
        studentchartarrlist.add(temprowarray); //Adds array into an arraylist
      }
    }
    catch (IOException e){ //If a file wasn't found
      System.out.println("A file was not found.");
    }
    finally{
      filereader.close(); //Closes filereader
    }
    //______________________________________________________________________________________________________________________________________
    //Runs through each element in the arraylist and arrays inside and restores the temporary text to normal
    for (int i = 0; i < studentchartarrlist.size(); i++){
      for (int j = 0; j < studentchartarrlist.get(i).length; j++){
        //Replaces the temporary text with its original text
        studentchartarrlist.get(i)[j] = studentchartarrlist.get(i)[j].replaceAll("#####", ", "); 
      }
    }
    System.out.println("Student chart array length is " + studentchartarrlist.size());
    return student_chart_Path; //Returns the path of the file read
  }
  //______________________________________________________________________________________________________________________________________
    //Methods that stores the checks of a student
    public static void student_chart_Saver(String chart_Path,
                                                 ArrayList<String[]> student_save_info) throws IOException {
    File student_Checks = new File(chart_Path);
    //Writer set to false so that each write would overwrite its contents
    FileWriter writer = new FileWriter(student_Checks, false); 
    try{
      //Loop that populates the csv file
      for (int j = 0; j < student_save_info.size(); j++){
        for (int k = 0; k < student_save_info.get(j).length; k++){
          writer.append(student_save_info.get(j)[k] + ","); //Writes in each value into the row
        }
        writer.append("\n"); //Moves to next row
      }
    }
    catch (IOException e){
      System.out.println("An IOException has occurred.");
    }
    finally{
      writer.close(); //Closes writer
    }
  }
 //______________________________________________________________________________________________________________________________________
//Method that clears the arraylists that stores the information on the student check csv files
    public static void arraylist_clearer(ArrayList<String[]> student_save_info, ArrayList<String[]> studentchartarrlist){
      studentchartarrlist.clear();
      student_save_info.clear();
    }
  //______________________________________________________________________________________________________________________________________
  
  //______________________________________________________________________________________________________________________________________
  public static void main(String[] args) throws IOException {
    initPressed[0] = false;
    //needed for submitting
    openedFirst[0] = true;
    //needed for saving textfields
     firstTime[0] = true;
     
    //Calls the main folder creator method
    mainFolderCreator(MainFolderPath); 
    
    
    
   
    
    //Starts up GUI
    launch(args); 
  }
  public void start(Stage primaryStage) throws Exception,IOException {
    //Sets this window as the main window of this start method
    window = primaryStage;
    //sets the title of the window
    window.setTitle("Physics Mastery Model Chart Creator");
    
    //init Button
    initButton.setOnAction(e -> { 
      try {
        nameFilePath = nameGetter();
        fileMover(MainFolderPath, nameFilePath, "Student_Info.csv");
        chartFilePath = chartGetter();
        fileMover(MainFolderPath, chartFilePath, "Assessment_Chart.csv");
      }
      catch (IOException e2) {
        System.out.println("moving name and chart file to mm folder error");
      }
      initPressed[0] = true;
    });
    
    //edit Button
    editButton.setOnAction(e -> {
      
      try {
        //Calls the student_csv_Storer method
        student_info_Storer(MainFolderPath, student_csv_Storer); 
        //Calls the chart template storer method
        chart_template_Storer(MainFolderPath, chart_template_Storer); 
        //Calls method that creates a folder for each student
        student_Folder_Creator(MainFolderPath, student_csv_Storer); 
      }
      catch (IOException e2) {
        System.out.println("init error");
      }
      if (initPressed[0] == true){
        try{
          //Calls method that creates a file in each student's folder
          student_chart_Initializer(MainFolderPath, student_csv_Storer, chart_template_Storer);
          initPressed[0] = false;
        }
        catch (IOException e3){
          System.out.println("Init 2 error");
        }
      }
      nameScene = new Scene(VBoxDropDown(), 300, 350);
      nameScener();
    });
    
    //Submit name button 
    submitNameButton.setOnAction(e -> { 
      studentName = nameList.getValue(); 
      
      
      
      
      
      //chart scenes
      if (openedFirst[0]) {
        
        openedFirst[0] = false;
        int index = student_index_Finder(studentName, MainFolderPath, student_csv_Storer);
      String studentPath = student_Finder(index, MainFolderPath, student_csv_Storer);
      try {
        checkMarkFilePath = student_chart_Reader(studentPath, index, student_csv_Storer, student_Check_Storer);
      }
      catch (IOException a) {
        System.out.println("IOException has occured");
      }

        System.out.println(studentName);
        System.out.println(studentPath);
        System.out.println(checkMarkFilePath);
      }
      chartScene = new Scene(createChart1(), 900,900);
        chartScene2 = new Scene(createChart2(), 900,900);
    
        chartScene.getStylesheets().add(getClass().getResource("grid-with-borders.css").toExternalForm());
        chartScene2.getStylesheets().add(getClass().getResource("grid-with-borders.css").toExternalForm());
        window.setScene(chartScene);
         window.setMaximized(true);
      /*
      else {
        int index = student_index_Finder(studentName, MainFolderPath, student_csv_Storer);
        String studentPath = student_Finder(index, MainFolderPath, student_csv_Storer);
      try {
        checkMarkFilePath = student_chart_Reader(studentPath, index, student_csv_Storer, student_Check_Storer);
      }
      catch (IOException a) {
        System.out.println("IOException has occured");
      }
      textSetter();
        window.setScene(chartScene);
        window.setMaximized(true);
        
        System.out.println(studentName);
        System.out.println(studentPath);
        System.out.println(checkMarkFilePath);
      }
      */
    });
    
    //check button
    checkButton.setMinWidth(150);
    checkButton.setFocusTraversable(false);
    
    checkButton.setOnAction(e -> {
      
      Node fo = chartScene.getFocusOwner();
      if (fo instanceof TextInputControl) {
        ((TextInputControl) fo).replaceSelection("\u2713");
      }
        
    });
    
    //cross button 
    crossButton.setMinWidth(150);
    crossButton.setFocusTraversable(false);
    
    crossButton.setOnAction(e -> {
      Node fo = chartScene.getFocusOwner();
      if (fo instanceof TextInputControl) {
        ((TextInputControl) fo).replaceSelection("\u2717");
      }
      
    });
    
    //cross check button
    crossCheckButton.setMinWidth(150);
    crossCheckButton.setFocusTraversable(false);
    crossCheckButton.setOnAction(e -> {
      Node fo = chartScene.getFocusOwner();
      if (fo instanceof TextInputControl) {
        ((TextInputControl) fo).replaceSelection("\u237B");
      }
      
    });
    
    //check2 button
    checkButton2.setMinWidth(150);
    checkButton2.setFocusTraversable(false);
    
    checkButton2.setOnAction(e -> {
      
      Node fo = chartScene2.getFocusOwner();
      if (fo instanceof TextInputControl) {
        ((TextInputControl) fo).replaceSelection("\u2713");
      }
        
    });
    
    //cross2 button 
    crossButton2.setMinWidth(150);
    crossButton2.setFocusTraversable(false);
    
    crossButton2.setOnAction(e -> {
      Node fo = chartScene2.getFocusOwner();
      if (fo instanceof TextInputControl) {
        ((TextInputControl) fo).replaceSelection("\u2717");
      }
      
    });
    
    //cross2 check button
    crossCheckButton2.setMinWidth(150);
    crossCheckButton2.setFocusTraversable(false);
    crossCheckButton2.setOnAction(e -> {
      Node fo = chartScene2.getFocusOwner();
      if (fo instanceof TextInputControl) {
        ((TextInputControl) fo).replaceSelection("\u237B");
      }
      
    });
    
    //next page button
    nextPageButton.setOnAction(e -> nextPageMethod());
    
    //prev page button
    prevPageButton.setOnAction( e-> prevPageMethod());
    
    //save changes button 
    saveChangesButton.setOnAction(e -> textFieldCollector());
    
    //save changes button2 (for second page) 
    saveChangesButton2.setOnAction(e -> textFieldCollector());
    
    //back button
    backButton.setOnAction(e -> backButtonMethod());
    
    //Scene
    initScene = new Scene(initVBox(), 300, 250);
    
    
    //chart scenes
    
    
    
    //basic Window Callibration
    window.setScene(initScene);
    window.show();
    
  }
  //_______________________________________________________________________________________________
  //Sets the Scene to the one with the drop down list 
  public void nameScener() {
    window.setScene(nameScene);
  }
  //________________________________________________________________________________________________
  //creates layout for initialize/edit scene (first scene)
  public VBox initVBox() {
    VBox initLayout = new VBox(10);
    initLayout.setAlignment(Pos.CENTER);
    
    initLayout.getChildren().addAll(initButton, editButton);
    
    return initLayout;
  }
  //_________________________________________________________________________________________________
  //asks user to pick CSV name file
  public String nameGetter() {
    String nameFilePathway;
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Name Data File");
    File file = fileChooser.showOpenDialog(window);
      if (file != null) {
        nameFilePathway = file.toString();        
      }
      else {
        nameFilePathway = null;
      }
      
      System.out.println(nameFilePathway);
      
      return nameFilePathway;
  }
  //________________________________________________________________________________________________
  //asks user to pick CSV chart file
  public String chartGetter() {
    String chartFilePathway;
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Chart Data File");
    File file = fileChooser.showOpenDialog(window);
      if (file != null) {
        //sets string value to pathway of file
        chartFilePathway = file.toString();        
      }
      else {
        //if no file is selected then the value of the string is null
        chartFilePathway = null;
      }
      
      //prints pathway for now
      System.out.println(chartFilePathway);
      
      return chartFilePathway;
  }
  //_________________________________________________________________________________________________
  //creates layout for drop down scene 
  public VBox VBoxDropDown() {
    VBox nameVBox = new VBox(10);
    nameVBox.setPadding(new Insets(20,20,20,20));
    nameVBox.getChildren().addAll(comboBoxMaker(),submitNameButton);
    nameVBox.setAlignment(Pos.TOP_LEFT);
    
    
    return nameVBox;
  }
  //_________________________________________________________________________________________________
  //creates Drop down list
  public ComboBox<String> comboBoxMaker() {
    
    nameList = new ComboBox<>();
    
    for (int i = 0; i < student_csv_Storer.size(); i++){
      
       nameList.getItems().addAll(student_csv_Storer.get(i)[1] + ", " + student_csv_Storer.get(i)[2]);
        
      
    }
    System.out.println("the size of the name arraylist is " + student_csv_Storer.size()); 
    
    
    nameList.setOnAction(e -> { 
      
    });
    
    
    
    
    
    
    nameList.setPromptText("Choose a Student"); //sets prompt text
    nameList.setMinWidth(200); //sets minimum width of drop down list 
    
    return nameList;
    
  }
  //_______________________________________________________________________________________________
  //next page method
  public void nextPageMethod() {
    window.setScene(chartScene2);
    window.setMaximized(false);
    window.setMaximized(true);
  }
  //_______________________________________________________________________________________________
  //prev page method
  public void prevPageMethod() {
    window.setScene(chartScene);
    window.setMaximized(false);
    window.setMaximized(true);
  }
  //________________________________________________________________________________________________
  //creates chart for page 1 (knowledge, thinking, and thinking labs)
  
  public BorderPane createChart1() {
    
    chart1BP = new BorderPane();
    chart1BP.setCenter(knowledgeThinkingGrid);
    
    
    
    textFieldCounter = 0;
    
    
    //CHART CREATION for page 1**********************************************
    combined = false;
    
    indexSaver = 0;
    //first page
    outerloop:
    for (int i = 0; i < chart_template_Storer.size(); i++) {
      for (int j = 0; j < chart_template_Storer.get(i).length; j++) {
        if (combined) {
          combined = false;
        }
        else if (chart_template_Storer.get(i)[j].contains("Communication")) {
          indexSaver = i;
          break outerloop;
        }
        else if (chart_template_Storer.get(i)[j].length()>20) { 
           
          leftLabels1.add(chart_template_Storer.get(i)[j]); 
          
        }
        else if (chart_template_Storer.get(i)[j].contains("Knowledge")
           || chart_template_Storer.get(i)[j].contains("Thinking")) {
          leftLabels1.add(chart_template_Storer.get(i)[j] + "\n" + chart_template_Storer.get(i+1)[j]);
         
          combined = true;
        }  
      }
    }
    
    
    //adding the labels and textfields
    
    for (int i = 0; i < leftLabels1.size(); i++) {
       
       
       
      knowledgeThinkingGrid.add(new Label(leftLabels1.get(i)),0,i);
       
      if (leftLabels1.get(i).contains("Knowledge") 
                  || leftLabels1.get(i).contains("Thinking")
                  || leftLabels1.get(i).contains("Labs")) {
         knowledgeThinkingGrid.add(new Label("     Not Attempted [0]"),1,i);
         knowledgeThinkingGrid.add(new Label("        Developing [2]"),2,i);
         knowledgeThinkingGrid.add(new Label("     Undertstanding [3]"),3,i);
         knowledgeThinkingGrid.add(new Label("     Mastery [4]"),4,i);
       }
       for (int j = 1; j < 5; j++) {
         if (!leftLabels1.get(i).contains("Knowledge") 
               && !leftLabels1.get(i).contains("Thinking") 
               && !leftLabels1.get(i).contains("Labs"))  {
           textFields.add(new TextField());
           knowledgeThinkingGrid.add(textFields.get(textFieldCounter),j,i);
           textFieldCounter++;
         }
       }
       
                  
     }
    //END OF CHART CREATION for page 1 *************************************************************
    
    //sets grid lines so user can see individual cells
    knowledgeThinkingGrid.setGridLinesVisible(true);
    
    
                               
    chart1BP.setBottom(inputButtons());
    
    
    textFields.get(0).setOnAction(e -> System.out.println(textFields.get(0).getText()));
    
    
    
    return chart1BP;
  }
  
  //____________________________________________________________________________________________________
  //creates layout for buttons (check, cross, and cross check buttons)
  public HBox inputButtons() {
    HBox hBox = new HBox(50);
    hBox.getChildren().addAll(backButton, crossCheckButton, crossButton, checkButton, saveChangesButton, nextPageButton);
    
    hBox.setAlignment(Pos.CENTER);
    hBox.setPadding(new Insets(20,20,200,20));
    
    return hBox;
  }
  //____________________________________________________________________________________________________
  //creates layout for buttons (check, cross, and cross check buttons) Page 2
  public HBox inputButtons2() {
    HBox hBox = new HBox(50);
    hBox.getChildren().addAll(crossCheckButton2, crossButton2, checkButton2, saveChangesButton2, prevPageButton);
    
    hBox.setAlignment(Pos.CENTER);
    hBox.setPadding(new Insets(20,20,200,20));
    
    return hBox;
  }
  //____________________________________________________________________________________________________
  //creates chart for second page (communication and Application)
  public BorderPane createChart2() {
    
    String appLabel = "";
    
    chart2BP = new BorderPane();
    
    VBox layout = new VBox(20);
    layout.getChildren().addAll(appGrid, inputButtons2());
    
    chart2BP.setLeft(comCriteriaGrid);
    chart2BP.setCenter(comGrid);
    chart2BP.setBottom(layout);
    
    //CHART 2 CREATOR *******************************************
    
    
    int keptIndex = 0;
    //second page
    
    
    for (int i = indexSaver; i < chart_template_Storer.size(); i++) {
        
      for (int j = 0; j < chart_template_Storer.get(i).length; j++) {
        
        if (combined) {
          combined = false;
        }
        else if (chart_template_Storer.get(i)[j].contains("Test") 
                   || chart_template_Storer.get(i)[j].contains("Quiz") 
                   || chart_template_Storer.get(i)[j].contains("Lab") 
                   || chart_template_Storer.get(i)[j].contains("Quest") 
                   || chart_template_Storer.get(i)[j].contains("Exit")
                   || chart_template_Storer.get(i)[j].contains("Peer") 
                   || chart_template_Storer.get(i)[j].contains("Investigation")) {
          comAssessments.add(chart_template_Storer.get(i)[j]);
          
        }
        
        else if (chart_template_Storer.get(i)[j].contains("Application")) {
          leftLabels2.add(chart_template_Storer.get(i)[j] + "\n" + chart_template_Storer.get(i+1)[j]);
          combined = true;
          keptIndex = i;
          break;
        }
        
      }
    }
    
    //adds default labels that remian contant 
    comCriteriaGrid.add(new Label("Communication   [    /4]"), 0, 0);
    comCriteriaGrid.add(new Label("- Organizes ideas in a clear and logical manner\n- Communicates appropriately for audience (test vs. lab)" +
      "\n- Uses appropriate terminology\n- Uses appropriate symbols\n- Uses appropriate formulae\n- Uses appropriate scientific notation" +
      "\n- Uses appropriate significant digits\n*Mark is averaged from all assesments\n\n\n\n"), 0, 1);
    
    //adds default labels that will never change
    comGrid.add(new Label("Assessment"),0,0);
    comGrid.add(new Label("Developing [2]"),1,0);
    comGrid.add(new Label("Understanding [3]"),2,0);
    comGrid.add(new Label("Mastery [4]"),3,0); 
    
    //Com assesments labels
    for (int i = 0; i < comAssessments.size(); i++) {
       
      comGrid.add(new Label(comAssessments.get(i)),0,i+1);
       
    }
    
    //adding the communication textfields
    for (int i = 0; i < comAssessments.size(); i++) {
       
      textFields.add(new TextField());
      comGrid.add(textFields.get(textFieldCounter),1,i+1);
         
      textFieldCounter++;
      
      textFields.add(new TextField());
      comGrid.add(textFields.get(textFieldCounter),2,i+1);
         
      textFieldCounter++;
      
      textFields.add(new TextField());
      comGrid.add(textFields.get(textFieldCounter),3,i+1);
         
      textFieldCounter++;
       
       
     }
    
    //Application label creator
    for (int i = keptIndex+2; i < chart_template_Storer.size(); i++) {
        
      for (int j = 0; j < chart_template_Storer.get(i).length; j++) {
        
        appLabel += "\n" + chart_template_Storer.get(i)[j];
        
      }
    }
    
    for (int i = 0; i < leftLabels2.size(); i++) {
       if (leftLabels2.get(i).contains("Application")) {
         appGrid.add(new Label(leftLabels2.get(i)),0,0);
         appGrid.add(new Label("Not Attempted [0]"),1,0);
         appGrid.add(new Label("Developing [2]"),2,0);
         appGrid.add(new Label("Understanding [3]"),3,0);
         appGrid.add(new Label("Mastery [4]"),4,0);
       } 
     }
    
    appGrid.add(new Label(appLabel),0,1);
    
    //These textboxes are always in this position
    textFields.add(new TextField());
    appGrid.add(textFields.get(textFieldCounter),1,1);
    textFieldCounter++;
    
    textFields.add(new TextField());
    appGrid.add(textFields.get(textFieldCounter),2,1);
    textFieldCounter++;
    
    textFields.add(new TextField());
    appGrid.add(textFields.get(textFieldCounter),3,1);
    textFieldCounter++;
    
    textFields.add(new TextField());
    appGrid.add(textFields.get(textFieldCounter),4,1);
    textFieldCounter++;
    
    
    
    // END OF CHART 2 CREATOR************************************
    
    
    
    comCriteriaGrid.setGridLinesVisible(true);
    comGrid.setGridLinesVisible(true);
    appGrid.setGridLinesVisible(true);
    appGrid.setPadding(new Insets(0,0,100,0));
    
    //reading textfield values
    textSetter();
    
    
    System.out.println("chart array length is " + student_Check_Storer.size());
   
    
    //returns created borderPane layout 
    return chart2BP;
  }
  //______________________________________________________________________________________________
  //saving TextFields into an ArrayList
  public void textFieldCollector() {
  
    //This condition sets the string[] to some values just so the next loop can replace them. not important the first time
    //but is needed if user wants to save multiple times over in the same session
    if (firstTime[0]) {
      for (int i = 0; i < textFields.size(); i++) {
      savedText.add(textFields.get(i).getText().split(" "));
      }
      firstTime[0] = false;
    }
    //Saves textfield inputs to a string ArrayList
    for (int i = 0; i < textFields.size(); i++) {
      textFields.get(i).setText(textFields.get(i).getText().replace("\u2713", "0")); 
      textFields.get(i).setText(textFields.get(i).getText().replace("\u2717", "1"));
      textFields.get(i).setText(textFields.get(i).getText().replace("\u237B", "2"));
      
      savedText.set(i, textFields.get(i).getText().split(" "));
      
      textFields.get(i).setText(textFields.get(i).getText().replace("0", "\u2713")); 
      textFields.get(i).setText(textFields.get(i).getText().replace("1", "\u2717"));
      textFields.get(i).setText(textFields.get(i).getText().replace("2", "\u237B"));
      System.out.println(savedText.get(i)[0]);
    }
    
    try {
      student_chart_Saver(checkMarkFilePath, savedText);
      
    }
    catch (IOException e) {
      System.out.println("Saving checkmarks error");
    }
    
    
     
  }
  
  public void backButtonMethod() {
    
    window.setScene(nameScene);
    

    
    arraylist_clearer(savedText, student_Check_Storer);
  }
  public void textSetter() {
    for (int i = 0; i < student_Check_Storer.size(); i++) {
      for (int j = 0; j < student_Check_Storer.get(i).length; j++) {
        student_Check_Storer.get(i)[j] = student_Check_Storer.get(i)[j].replace("0", "\u2713");
        student_Check_Storer.get(i)[j] = student_Check_Storer.get(i)[j].replace("1","\u2717");
        student_Check_Storer.get(i)[j] = student_Check_Storer.get(i)[j].replace("2", "\u237B");
        textFields.get(i).setText(student_Check_Storer.get(i)[j]);
      }
    }
  }
  
  
  
  
  
  
  
  
}
