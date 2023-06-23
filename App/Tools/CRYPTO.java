package App.Tools;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class CRYPTO {
    
    public static String generarIdentificador() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    private static SecretKeySpec generarClaveAES(String clave) throws Exception {
        byte[] claveEnBytes = clave.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(claveEnBytes, "AES");
    }

    public static String encriptarAES(String mensaje, String clave) throws Exception {
        SecretKeySpec secretKey = generarClaveAES(clave);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] bytesEncriptados = cipher.doFinal(mensaje.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(bytesEncriptados);
    }

    public static String desencriptarAES(String mensajeEncriptado, String clave) throws Exception {
        SecretKeySpec secretKey = generarClaveAES(clave);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] bytesDesencriptados = cipher.doFinal(Base64.getDecoder().decode(mensajeEncriptado));
        return new String(bytesDesencriptados, StandardCharsets.UTF_8);
    }
}
