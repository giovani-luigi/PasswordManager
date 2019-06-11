package myid.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PlainTextFile extends File {

    public PlainTextFile(String filePath){
        super(filePath);
    }
        
    /**
     * Try to write a String to a file, suppressing 'IOException'. 
     * If an error happens, nothing will be written.
     * @param data The content to writeString to the file
     */
    public void writeString(String data) {
        try (FileWriter fw = new FileWriter(this, false);
                BufferedWriter writer = new BufferedWriter(fw)) 
        {
            writer.write(data);
            writer.newLine();
            writer.flush();
        } catch (IOException ex) {
            Logger.getLogger(PlainTextFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Try to read all content from a file to a String, suppressing 'IOException'.
     * @return The content as a string, or if an error happens, an empty string.
     */
    @Override
    public String toString(){
        try (FileReader fr = new FileReader(this);
                BufferedReader reader = new BufferedReader(fr)){
            return reader.readLine();
        }catch(IOException ex){
            Logger.getLogger(PlainTextFile.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }
    
    protected boolean isEmpty(){
        if (!exists()) return true;
        return this.length() > 0;
    }
    
}

