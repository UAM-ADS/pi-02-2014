package toronto;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mgaldieri
 */
public class Utils {
    
    public static String md5String(String msg) {
        String digest = null;
        
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(msg.getBytes("UTF-8"));
            
            StringBuilder sb = new StringBuilder(2*hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b&0xff));
            }
            
            digest = sb.toString();
        } catch (UnsupportedEncodingException e) {
            Logger.getGlobal().log(Level.SEVERE, null, e);
            return null;
        } catch (NoSuchAlgorithmException e) {
            Logger.getGlobal().log(Level.SEVERE, null, e);
            return null;
        }
        
        return digest;
    }
    
}
