package controllers;

import myid.cryptography.CypherAES256;
import myid.view.*;

public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //request login (to validate the master key)
        LoginView lv = new LoginView(null, true);
        lv.setVisible(true); //show dialog and wait for it to close
                
        //if we are not authenticated after the user closed the form, exit application
        if (!lv.isAuthenticated()) System.exit(0);
        
        MainView mv = new MainView(new CypherAES256(lv.getPassword()));
        lv.dispose(); // get rid of the login view, as we won't use it anymore

        //show main window
        mv.setVisible(true);
    }    
}