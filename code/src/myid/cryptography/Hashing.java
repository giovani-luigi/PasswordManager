package myid.cryptography;

import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
*  This class provides Hashing through Password-Based Key Derivation Function 2
*  (a.k.a. PBKDF2)
*
*  ----------------------------------
*  Why hashing, and what hash to use?
*  ----------------------------------
* 
*  Storing the passwords as plain text is obviously a bad idea. If anyone steals
*  the database, all data is vulnerable. A hashing algorithm is a one-way
*  function that maps some data (e.g. a password) to a value. 
*   Important properites of a hash function:
*  - It is one-way, i.e. the original value cannot be obtained from the hash
*    - This is important so an attacker would not be able to obtain the original
*      password that was used to produce that hash.
*  - If two hashes match means that the value used to produce them are equal,
*    despite the fact that we don't know which value is that.
*    - This is essential for login. We don't know the user password and it is
*      not stored anywhere. But we still can check if the hash of his input 
*      matches the stored hash (which means he entered the correct password)
* 
*  Hash algorithm:
* 
*   Password-Based Key Derivation Function 2 (PBKDF2) is a safe algorithm used 
*   for password hashing. 
* 
*   Algorithms like MD5 and SHA1 have been proven to be vulnerable to collision 
*   attacks and can be rainbow tabled easily (when they see if you hash is the 
*   same in their database of common passwords). 
* 
*  ----------------------------------
*  What is a salt and why to use it?
*  ----------------------------------
*
*  A salt is usually a random string that you add at the end of all your
*  passwords when you hash them. Using a salt means if someone gets your 
*  database, they can not check the hashes for common passwords. Checking 
*  the database is called using a rainbow table. 
*  You should always use a salt when hashing!
* 
*/
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
