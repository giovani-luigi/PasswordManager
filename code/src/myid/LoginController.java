/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package myid;

import javax.swing.JOptionPane;
import myid.cryptography.Hashing;
import myid.storage.MasterKeyFile;

public class LoginController {

    private static final int MAX_PWD_LENGTH = 50;
    private static final int MIN_PWD_LENGTH = 4;
    
    public boolean isRegistered(){
        return !new MasterKeyFile().isEmpty();
    }
    
    public boolean login(String masterKey){
        MasterKeyFile file = new MasterKeyFile();
        return Hashing.verifyPassword(masterKey, file.getPasswordHash());
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
        
        //save to a hash file
        MasterKeyFile mkf = new MasterKeyFile();
        mkf.storePasswordHash(masterKey);
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
