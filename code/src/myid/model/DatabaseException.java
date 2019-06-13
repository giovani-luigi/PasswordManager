package myid.model;

public class DatabaseException extends Exception {
      
    public DatabaseException(String message, Exception cause){
        super(message, cause);
    }
    
}
