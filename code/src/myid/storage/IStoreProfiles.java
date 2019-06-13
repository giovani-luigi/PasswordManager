package myid.storage;

import java.util.List;
import myid.model.*;

public interface IStoreProfiles {
    
    public void add(Profile profile) throws DatabaseException;
    
    public void remove(Profile profile) throws DatabaseException;
    
    public Profile read(int primaryKey) throws DatabaseException;
    
    public void update(Profile profile) throws DatabaseException;
    
    public List<Profile> readAll() throws DatabaseException;
    
    public String getPasswordHash() throws DatabaseException;
    
    public void setPasswordHash(String password) throws DatabaseException;
    
    public boolean hasPassword() throws DatabaseException;
    
}
