package myid.cryptography;

import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hashing {
    
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 512;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
    private static final int SALT_LENGTH = 20;    
    
    public static byte[] generateSalt(int size){
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[size];
        random.nextBytes(bytes);
        return bytes;
    }
    
    /**
     * Creates a HASH (one-way encryption) from a password so we can store it safely in the database.
     * @param password the plain text password to hash
     * @param salt the salt to use
     * @return the hash for the given password
     */
    public static String hashPassword(String password, byte[] salt){
         char[] pwdBytes = password.toCharArray();
         PBEKeySpec spec = new PBEKeySpec(pwdBytes, salt, ITERATIONS, KEY_LENGTH);
         try{
            
            SecretKeyFactory fac = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] securePassword = fac.generateSecret(spec).getEncoded();
             
            //prepend salt to the crypted data, so we can return all in one single string
            byte[] buffer = new byte[salt.length + securePassword.length];
            System.arraycopy(salt, 0, buffer, 0, salt.length);
            System.arraycopy(securePassword, 0, buffer, salt.length, securePassword.length);

            //encode crypted bytes into a string with only printable characters (Base64)
            return Base64.getEncoder().encodeToString(buffer);
            
         }catch (NoSuchAlgorithmException | InvalidKeySpecException ex){
             System.err.println(ex);
             return null;
         }finally{
             spec.clearPassword();
         }
    }

    /**
     * Creates a HASH (one-way encryption) from a password so we can store it safely in the database. A random salt is used
     * @param password the plain text password to hash
     * @return the hash for the given password
     */
    public static String hashPassword(String password){
        return hashPassword(password, generateSalt(SALT_LENGTH));
    }
    
    /**
     * Test an input value against an existing key and return whether they 
     * generate the same HASH or not, i.e. if the password is correct
     * @param input the plain-text input data to test
     * @param key the existing hash key to test against
     * @return a value indicating if the password match the hash key
     */
    public static boolean verifyPassword(String input, String key){
        
        //first test if our key is bigger than the salt, so it can contain it
        if (key.length() < SALT_LENGTH) throw new IllegalArgumentException("The key length is incorrect.");
        
        //first extract the salt from the existing key:
        ByteBuffer buffer = ByteBuffer.wrap(Base64.getDecoder().decode(key));
        byte[] saltBytes = new byte[SALT_LENGTH];
        buffer.get(saltBytes, 0, saltBytes.length); // read the first block of bytes which is the salt
        byte[] keyBytes = new byte[buffer.capacity() - saltBytes.length];
        buffer.get(keyBytes); // read next block of bytes
        
        //now encrypt the input
        String cryptedInput = hashPassword(input, saltBytes);
        // handle possible errors
        if (cryptedInput == null) return false;
        
        //check if they match
        return cryptedInput.equals(key);
    }

}
