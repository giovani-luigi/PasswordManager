package controllers;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import myid.cryptography.Cypher;
import myid.model.Profile;
import myid.storage.IStoreProfiles;
import myid.storage.SQLiteStorage;


public class MainViewController {

    private final IStoreProfiles storage;
    private Profile currentProfile = null;

    public MainViewController(Cypher cypher) {
        this.storage = new SQLiteStorage(cypher);
    }
    
    public void setCurrentProfile(Profile profile){
        this.currentProfile = profile;        
    }
    
    public Profile getCurrentProfile() {
        return currentProfile;
    }
    
    public ListModel getAllProfiles(){
        DefaultListModel items = new DefaultListModel();
        try {
            for (Profile p : storage.readAll()){
                items.addElement(p);
            }
        } catch (Exception ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro lendo banco de dados.");
        }
        return items;
    }
    
}
