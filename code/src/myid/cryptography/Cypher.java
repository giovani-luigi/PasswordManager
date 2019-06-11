package myid.cryptography;

/**
 * Provides common implementations for all the different cyphers.
 * @author Giovani
 */
public abstract class Cypher  {

    protected final String password;
    
    public Cypher(String password){
        this.password = password;
    }

    /**
     * Encrypt data using the provided password
     * @param data The data to encrypt
     * @return The encrypted data
     */
    public abstract String Encrypt(String data);
    
    /**
     * Decrypt data using the provided password 
     * @param data The encrypted data to be decrypted
     * @return The decrypted data
     */
    public abstract String Decrypt(String data);
    
}