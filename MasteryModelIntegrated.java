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
  
  GridPane knowledgeThinkingGrid = new GridPane();
  GridPane comCriteriaGrid = new GridPane();
  GridPane comGrid = new GridPane();
  GridPane appGrid = new GridPane();
  
  Button initButton = new Button("Initialize");
  Button editButton = new Button("Edit");
  //Button initButton = new Button("Initialize");
  
  static String[] leftLabels = new String[50];
  static String[] leftLabels2 = new String[50];
  static TextField[] textFields = new TextField[50];
  static TextField[] comTextFields = new TextField[50];
  static TextField[] appTextFields = new TextField[4];  
  static String[] comAssessments = new String[15];
  //Global variables
  static String currentDirPath = System.getProperty("user.dir"); //Finds the path of the working directory
  static String MainFolderPath = currentDirPath + "\\MasteryModel"; //Stores the path of the main folder
  static ArrayList<String[]> student_csv_Storer = new ArrayList<String[]>(); //Initializes ArrayList that stores csv data for student info 
  static ArrayList<String[]> chart_template_Storer = new ArrayList<String[]>(); //Initializes ArrayList that stores the chart template
  static ArrayList<String[]> student_Check_Storer = new ArrayList<String[]>(); //Initializes ArrayList that stores the checks for a student for reading
  static ArrayList<String[]> student_save_info = new ArrayList<String[]>(); //Initializes ArrayList that stores the saved checks for a student for writing
  
  
  
//______________________________________________________________________________________________________________________________________
  //Method to create the main folder that the program refers to
  public static void mainFolderCreator(String mainPath) throws IOException{
    File MasteryModelFolder = new File(mainPath); //Creates a new file 
    boolean mainfoldercreated = MasteryModelFolder.mkdirs(); //Converts the file into the main folder
    }
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
  public static int student_index_Finder(String full_name, String mainPath, ArrayList<String[]> studentarrlist){
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
        studentarrlist.add(temprowarray); //Adds array into an arraylist
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
    for (int i = 0; i < studentarrlist.size(); i++){
      for (int j = 0; j < studentarrlist.get(i).length; j++){
        //Replaces the temporary text with its original text
        studentarrlist.get(i)[j] = studentarrlist.get(i)[j].replaceAll("#####", ", "); 
      }
    }
    return student_chart_Path; //Returns the path of the file read
  }
  //______________________________________________________________________________________________________________________________________
    //Methods that stores the checks of a student
    public static void student_chart_Initializer(String chart_Path,
                                                 ArrayList<String[]> student_save_info) throws IOException{
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
  public static void main(String[] args) throws IOException{
    
    //Calls the main folder creator method
    mainFolderCreator(MainFolderPath); 
    
    //Calls the student_csv_Storer method
    student_info_Storer(MainFolderPath, student_csv_Storer); 
    //Calls the chart template storer method
    chart_template_Storer(MainFolderPath, chart_template_Storer); 
    //Calls method that creates a folder for each student
    student_Folder_Creator(MainFolderPath, student_csv_Storer); 
    
    //Calls method that creates a file in each student's folder
    student_chart_Initializer(MainFolderPath, student_csv_Storer, chart_template_Storer); 
    
    //Starts up GUI
    launch(args); 
  }
  public void start(Stage primaryStage) throws Exception{
    //Sets this window as the main window of this start method
    window = primaryStage;
    //sets the title of the window
    window.setTitle("Physics Mastery Model Chart Creator");
    
    //init Button
    initButton.setOnAction(e -> { 
      nameGetter();
      chartGetter();
      
    });
    
    //edit Button
    editButton.setOnAction(e -> nameScener());
    
    //Scene
    initScene = new Scene(initVBox(), 300, 250);
    nameScene = new Scene(VBoxDropDown(), 300, 550);
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
  public void nameGetter() {
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
  }
  //________________________________________________________________________________________________
  //asks user to pick CSV chart file
  public void chartGetter() {
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
  }
  //_________________________________________________________________________________________________
  //creates layout for drop down scene 
  public VBox VBoxDropDown() {
    VBox nameVBox = new VBox(10);
    nameVBox.getChildren().addAll(comboBoxMaker());
    nameVBox.setAlignment(Pos.TOP_LEFT);
    
    return nameVBox;
  }
  //_________________________________________________________________________________________________
  //creates Drop down list
  public ComboBox<String> comboBoxMaker() {
    
    nameList = new ComboBox<>();
    nameList.getItems().addAll("placeholder1", "placeholder2");
    nameList.setPromptText("Choose a Student"); //sets prompt text
    nameList.setMinWidth(300); //sets minimum width of drop down list 
    
    return nameList;
    
  }
  
  
  
  
  
  
  
  
  
  
}
