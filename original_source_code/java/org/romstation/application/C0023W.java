package org.romstation.application;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

/* JADX INFO: renamed from: org.romstation.application.W */
/* JADX INFO: compiled from: Security.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/W.class */
public class C0023W {

    /* JADX INFO: renamed from: a */
    private static final String f28a = "AES/CBC/PKCS5Padding";

    /* JADX INFO: renamed from: b */
    private static final String f29b = "AES";

    /* JADX INFO: renamed from: c */
    private static final byte[] f30c = {75, 107, 118, 107, 104, 106, 52, 51, 100, 51, 102, 80, 114, 53, 104, 103};

    /* JADX INFO: renamed from: a */
    public static String m65a(String string) {
        String result = null;
        try {
            Cipher cipher = Cipher.getInstance(f28a);
            cipher.init(1, new SecretKeySpec(f30c, f29b));
            byte[] bytes = cipher.doFinal(string.getBytes());
            byte[] iv = cipher.getIV();
            ByteBuffer buffer = ByteBuffer.allocate(iv.length + bytes.length);
            buffer.put(iv);
            buffer.put(bytes);
            result = Base64.getEncoder().encodeToString(buffer.array());
        } catch (Exception exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
        }
        return result;
    }

    /* JADX INFO: renamed from: b */
    public static String m66b(String string) {
        String result = null;
        try {
            Cipher cipher = Cipher.getInstance(f28a);
            ByteBuffer buffer = ByteBuffer.wrap(Base64.getDecoder().decode(string));
            cipher.init(2, new SecretKeySpec(f30c, f29b), new IvParameterSpec(buffer.array(), 0, cipher.getBlockSize()));
            byte[] output = cipher.doFinal(buffer.array(), cipher.getBlockSize(), buffer.capacity() - cipher.getBlockSize());
            result = new String(output);
        } catch (Exception exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
        }
        return result;
    }

    /* JADX INFO: renamed from: a */
    public static String m67a(byte[] bytes) {
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            return new HexBinaryAdapter().marshal(md.digest(bytes)).toLowerCase();
        } catch (NoSuchAlgorithmException exception) {
            RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
            return null;
        }
    }
}
