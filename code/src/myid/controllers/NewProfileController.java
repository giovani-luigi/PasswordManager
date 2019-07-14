package myid.controllers;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import myid.model.DatabaseException;
import myid.model.Profile;
import myid.storage.IStoreProfiles;
import myid.model.BadValueException;

public class NewProfileController {

    // <editor-fold defaultstate="collapsed" desc="Private attributes">
    
    private final IStoreProfiles storage;
    
    private String alias = "";
    private String password = "";
    private String user = "";
    private String url = "";
    
    // </editor-fold>
        
    // <editor-fold defaultstate="collapsed" desc="Setters/Getters">
     
    public void setAlias(String alias){
        this.alias = alias;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructor">
    
    public NewProfileController(IStoreProfiles storage){
        this.storage = storage;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Creates a profile using the data from the fields in the view
     * @return the new profile
     * @exception BadValueException thrown when a value fails the validation rules
     */
    private Profile createProfile() throws BadValueException{
        Profile p = new Profile();
        p.setAlias(alias);
        p.setPwd(password);
        p.setUrl(url);
        p.setUser(user);
        return p;
    }
    
    public boolean saveProfile(){
        try {
            storage.add(createProfile());
            return true;
        } catch (DatabaseException ex) {
            Logger.getLogger(NewProfileController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao inserir no banco de dados");
        } catch (BadValueException ex) {
            Logger.getLogger(NewProfileController.class.getName()).log(Level.INFO, null, ex);
            JOptionPane.showMessageDialog(null, "Dado inválido: " + ex.getMessage());
        }
        return false;
    }
    
    // </editor-fold>
    
}
