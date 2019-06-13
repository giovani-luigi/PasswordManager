package myid.cryptography;


public class CypherByPass extends Cypher {

    @Override
    public String Encrypt(String data) {
        return data;
    }

    @Override
    public String Decrypt(String data) {
        return data;
    }

}
