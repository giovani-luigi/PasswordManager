package myid.controllers;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import myid.model.DatabaseException;
import myid.model.Profile;
import myid.storage.IStoreProfiles;
import myid.view.EditProfileView;
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
    
    public ListModel getAllProfileAliases(){
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
        NewProfileView view = new NewProfileView(storage);
        view.setVisible(true); // show dialog, and wait for it to close
    }
    
    public void editCurrentProfile(){
        if (currentProfile == null) return;
        EditProfileView view = new EditProfileView(storage, currentProfile);
        view.setVisible(true);
        try {
            // after the dialog closes, reload the current profile in case of any change
            setCurrentProfile(storage.read(getCurrentProfile().getAlias()));
        } catch (DatabaseException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            showDatabaseError();
        }
    }
    
    public void selectProfile(String profileAlias){
        try {
            setCurrentProfile(storage.read(profileAlias));
        } catch (DatabaseException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            showDatabaseError();
        }
    }
    
    public void eraseCurrentProfile(){
        if (currentProfile==null) return;
        // ask confirmation
        if (JOptionPane.showConfirmDialog(null, 
                "Tem certeza que deseja apagar o perfil '" + currentProfile.getAlias() + "' ?"
                , "Confirme", JOptionPane.YES_NO_OPTION) == 1) return;
        try {
            storage.remove(currentProfile);
            currentProfile = null;
            JOptionPane.showMessageDialog(null, "Perfil apagado.");
        } catch (DatabaseException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            showDatabaseError();
        }
    }
    
}
