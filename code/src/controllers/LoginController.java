package controllers;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import myid.cryptography.Hashing;
import myid.storage.IStoreProfiles;

public class LoginController {

    private static final int MAX_PWD_LENGTH = 50;
    private static final int MIN_PWD_LENGTH = 4;
    
    private final IStoreProfiles storage;
    
    public LoginController(IStoreProfiles storage){
        this.storage = storage;
    }
    
    public boolean isRegistered(){
        try {
            return storage.hasPassword();
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean login(String masterKey) throws Exception{
        return Hashing.verifyPassword(masterKey, storage.getPasswordHash());
    }
    
    /**
     * Prompt the user to register a key to be used as master key
     */
    public void register(){
        
        //request user to input a master key
        String masterKey;
        do{
            masterKey = JOptionPane.showInputDialog("Digite a senha mestre para criptografar os dados:");
            if (masterKey == null) return;
        } while(!isValid(masterKey) );
        
        try {
            storage.setPasswordHash(masterKey);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar senha no banco de dados.");
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    /**
     * Check whether the master key meet the minimum requirements
     * @param masterKey the intended key
     * @return TRUE when the password is valid to be used
     */
    private boolean isValid(String masterKey){
        //check minimum length
        if (masterKey == null) return false;
        return (masterKey.length() >= MIN_PWD_LENGTH) && (masterKey.length() <= MAX_PWD_LENGTH);
    }
    
}
