package App;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import services.TicketService;
    
public class Test {
//
 public static void main(String[] args) throws IOException
    {	
        
        String newName ="newname.txt";
        
        File  f1  = new File("C:\\Users\\WaelChorfan\\Desktop\\sara_scratch\\5.bmp");
        File  f2 =  new File("C:\\folder\\"+newName);
           
    }
 
 private static void copyFileUsingChannel(File source, File dest) throws IOException {
    FileChannel sourceChannel = null;
    FileChannel destChannel = null;
    try {
        sourceChannel = new FileInputStream(source).getChannel();
        destChannel = new FileOutputStream(dest).getChannel();
        destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
       }finally{
           sourceChannel.close();
           destChannel.close();
       }
}
}
