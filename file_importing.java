//Imports packages/modules required
//import java.nio.file.*; not being used currently
import java.io.*;
import java.util.*;

class file_importing{
  public static void main(String[] args) throws IOException{
    String currentDirPath = System.getProperty("user.dir"); //Finds the path of the working directory
    System.out.println("Current dir using System:" +currentDirPath); //Prints out path of working directory (Remove later)
    
    String MainFolderPath = currentDirPath + "\\MasteryModel"; //Stores the path of the main folder
    File MasteryModelFolder = new File(MainFolderPath); //Creates Folder that stores everything the program uses.
    boolean mainfoldercreated = MasteryModelFolder.mkdirs(); //Boolean value of whether folder was created or not
    
    if (mainfoldercreated == true){ //If the folder was created
      System.out.println("Folder Intialized! Please insert your files and restart the program!");
      System.exit(0); //Terminates program
    }
    
    ArrayList<String[]> student_csv_Storer = new ArrayList<String[]>(); //Initializes ArrayList that stores csv data
    try {
      File student_info = new File(MainFolderPath + "\\Student Info.csv"); //Finds the student info file
      String line = " "; //Initializes line variable
      BufferedReader filereader = new BufferedReader(new FileReader(student_info)); //Bufferedreader to read the file
      
      line = filereader.readLine(); //Reads first line and discards it
      while ((line = filereader.readLine())!= null){ //Keeps running until all lines are read
        String[] temprowarray = line.split(","); //Stores the seperated values of each row into an array
        student_csv_Storer.add(temprowarray); //Adds array into an arraylist
      }
      filereader.close();
    }
    catch (IOException e){ //If a file wasn't found
      System.out.println("A file was not found.");
    }
    
    //Creates a folder for each student
    for (int i = 0; i < student_csv_Storer.size(); i++){
      //Creates a folder named after the student's number
      File StudentFolder = new File(MainFolderPath + "\\" + student_csv_Storer.get(i)[0]);
      boolean studentCreated = StudentFolder.mkdirs();    
    }
    
    ArrayList<String[]> chart_template_Storer = new ArrayList<String[]>(); //Initializes ArrayList that stores the chart template
    try{
      File chart_Template = new File(MainFolderPath + "\\Assessment Chart.csv"); //Finds the chart template
      String line = " "; //Intializes line variable
      BufferedReader filereader = new BufferedReader(new FileReader(chart_Template)); //Bufferedreader to read the file
      while ((line = filereader.readLine())!= null){ //Keeps running until all lines are read
        String[] temprowarray = line.split(","); //Stores the seperated values of each row into an array
        chart_template_Storer.add(temprowarray); //Adds array into an arraylist
      }
      filereader.close();
    }
    catch (IOException e){ //If a file wasn't found
      System.out.println("A file was not found.");
    }
    
    for (int i = 0; i < chart_template_Storer.size(); i++){
      for (int j = 0; j < chart_template_Storer.get(i).length; j++){
        System.out.println(chart_template_Storer.get(i)[j]);
      }
    }
    
  
    
    
    
    
  }
}