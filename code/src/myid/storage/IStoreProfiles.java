package myid.storage;

import java.util.List;
import myid.model.*;

public interface IStoreProfiles {
    
    public void add(Profile profile) throws Exception;
    
    public void remove(Profile profile) throws Exception;
    
    public Profile read(int primaryKey) throws Exception;
    
    public void update(Profile profile) throws Exception;
    
    public List<Profile> readAll() throws Exception;
    
    public String getPasswordHash() throws Exception;
    
    public void setPasswordHash(String password) throws Exception;
    
    public boolean hasPassword() throws Exception;
    
}
