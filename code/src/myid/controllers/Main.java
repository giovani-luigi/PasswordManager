package myid.controllers;

import myid.cryptography.Cypher;
import myid.cryptography.CypherAES256;
import myid.cryptography.CypherByPass;
import myid.storage.IStoreProfiles;
import myid.storage.SQLiteStorage;
import myid.view.*;

public class Main {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //request login (to validate the master key)
        LoginView lv = new LoginView(GetStorageSystem(new CypherByPass()));
        lv.setVisible(true); //show dialog and wait for it to close
       
        //if we are not authenticated after the user closed the form, exit application
        if (!lv.isAuthenticated()) System.exit(0);
        
        IStoreProfiles storage = GetStorageSystem(new CypherAES256(lv.getPassword()));
        MainView mv = new MainView(storage);
        lv.dispose(); // get rid of the login view, as we won't use it anymore

        //show main window
        mv.setVisible(true);
    }    
    
    // This method centralizes the instantiation of the storage object used
    private static IStoreProfiles GetStorageSystem(Cypher cypher){
        return new SQLiteStorage(cypher);
    }
    
}