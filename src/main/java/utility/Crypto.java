package main.java.utility;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Crypto {
    private final String pepper = "*63&^mVLC(#";
    private final String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

    private static class CryptoHolder {
        private static final Crypto HOLDER_INSTANCE = new Crypto();
    }

    public static Crypto getInstance() {
        return CryptoHolder.HOLDER_INSTANCE;
    }

    public String getHashCodeSHA224(String password, String salt) {
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-224");
            byte[] messageDigest = md.digest((this.pepper + password + salt).getBytes(StandardCharsets.UTF_8));
            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder hashValue = new StringBuilder(no.toString(16));
            while (hashValue.length() < 32) {
                hashValue.insert(0, "0");
            }
            return hashValue.toString();

        } catch (NoSuchAlgorithmException exception) {
            return null;
        }
    }
    public String getSalt(int size) {
        StringBuilder sb = new StringBuilder(size);
        for (int i=0; i<size; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }
}
