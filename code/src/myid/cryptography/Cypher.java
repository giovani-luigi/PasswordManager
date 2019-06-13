package myid.cryptography;

/**
 * Provides common implementations for all the different cyphers.
 * @author Giovani
 */
public abstract class Cypher  {

    protected String password = null;
            
    protected Cypher(String password){
        this.password = password;
    }
    
    protected Cypher(){}

    /**
     * Encrypt data using the provided password
     * @param data The data to encrypt
     * @return The encrypted data, or null when the password is not set, or an error happens
     */
    public abstract String Encrypt(String data);
    
    /**
     * Decrypt data using the provided password 
     * @param data The encrypted data to be decrypted
     * @return The decrypted data, or null when the password is not set, or an error happens
     */
    public abstract String Decrypt(String data);
    
}