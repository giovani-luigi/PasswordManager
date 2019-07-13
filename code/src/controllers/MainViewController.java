package controllers;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import myid.model.DatabaseException;
import myid.model.Profile;
import myid.storage.IStoreProfiles;
import myid.view.NewProfileView;

public class MainViewController {

    private final IStoreProfiles storage;
    private Profile currentProfile = null;

    public MainViewController(IStoreProfiles storage){
        this.storage = storage;
    }
    
    public void setCurrentProfile(Profile profile){
        this.currentProfile = profile;        
    }
    
    public Profile getCurrentProfile() {
        return currentProfile;
    }
    
    private void showDatabaseError(){
        JOptionPane.showMessageDialog(null, "Erro acessando banco de dados.");
    }
    
    public ListModel getProfilesList(){
        DefaultListModel items = new DefaultListModel();
        try {
            for (String alias : storage.readAllAliases()){
                items.addElement(alias);
            }
        } catch (Exception ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            showDatabaseError();
        }
        return items;
    }

    public void addNewProfile() {
        NewProfileView view = new NewProfileView(null, true, storage);
        view.setVisible(true); // show dialog, and wait for it to close
    }
    
    public void selectionChanged(String selectedProfileAlias){
        try {
            setCurrentProfile(storage.read(selectedProfileAlias));
        } catch (DatabaseException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            showDatabaseError();
        }
    }
    
}
