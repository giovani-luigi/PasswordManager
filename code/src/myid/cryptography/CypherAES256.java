package myid.cryptography;

import java.nio.ByteBuffer;
import java.security.AlgorithmParameters;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import myid.storage.SQLiteStorage;

public class CypherAES256 extends Cypher {

    private final byte[] salt;
    
    private static final int SALT_LENGTH = 20;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int ITERATIONS = 1024;
    private static final int KEY_LENGTH = 256;
    
    public CypherAES256(String password){
        super(password);
        salt = Hashing.generateSalt(SALT_LENGTH);
    }
    
    @Override
    public String Encrypt(String data) {
        if (password == null) return null; // if no password is set, return null
        try{
            //Derive the key
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(),salt, ITERATIONS, KEY_LENGTH);
            SecretKey secretKey = factory.generateSecret(spec);
            SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

            //initialize AES
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secret);        
            AlgorithmParameters params = cipher.getParameters();
            byte[] ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();

            //encrypt the data
            byte[] encryptedTextBytes = cipher.doFinal(data.getBytes("UTF-8"));

            //prepend salt and IV to the crypted data, so we can return all in one single string
            byte[] buffer = new byte[salt.length + ivBytes.length + encryptedTextBytes.length];
            System.arraycopy(salt, 0, buffer, 0, salt.length);
            System.arraycopy(ivBytes, 0, buffer, salt.length, ivBytes.length);
            System.arraycopy(encryptedTextBytes, 0, buffer, salt.length + ivBytes.length, encryptedTextBytes.length);

            //encode using Base so they turn into printable characters
            return Base64.getEncoder().encodeToString(buffer);
        }catch(Exception e){
            Logger.getLogger(SQLiteStorage.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    @Override
    public String Decrypt(String data) {
        if (password == null) return null; // if no password is set, return null
        try{
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
           
            //strip off the salt and iv
            ByteBuffer buffer = ByteBuffer.wrap(Base64.getDecoder().decode(data));
            byte[] saltBytes = new byte[SALT_LENGTH];
            buffer.get(saltBytes, 0, saltBytes.length);
            byte[] ivBytes = new byte[cipher.getBlockSize()];
            buffer.get(ivBytes, 0, ivBytes.length);
            byte[] encryptedTextBytes = new byte[buffer.capacity() - saltBytes.length - ivBytes.length];
            buffer.get(encryptedTextBytes);

            // Deriving the key
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, ITERATIONS, KEY_LENGTH);
            SecretKey secretKey = factory.generateSecret(spec);
            SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes));
            
            return new String(cipher.doFinal(encryptedTextBytes));
        }catch(Exception e){
            Logger.getLogger(SQLiteStorage.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
        
    }
    
}
