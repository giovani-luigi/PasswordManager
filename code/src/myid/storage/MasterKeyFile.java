package myid.storage;

import java.io.File;
import java.nio.file.FileSystems;
import myid.cryptography.Hashing;


public class MasterKeyFile  {
    
    private final PlainTextFile file;
        
    private static File getDefaultFilePath(){
        return FileSystems.getDefault().getPath("myid.pwd").toFile();
    }
    
    /** 
     * Class used to safely save/read a password to a file
     */
    public MasterKeyFile(){
        file = new PlainTextFile(getDefaultFilePath().getAbsolutePath());
    }
    
    public String getPasswordHash(){
        if (getDefaultFilePath().exists()){
            return file.toString();
        }else{
            return null;
        }
    }
    
    /**
     * Stores a hash calculated from the given password in a local file
     * @param password 
     */
    public void storePasswordHash(String password){
        file.writeString(Hashing.hashPassword(password));
    }
    
    public boolean exists(){
        return file.exists();
    }
    
    public boolean isEmpty(){
        if (!exists()) return true;
        return file.length()==0;
    }
    
}
