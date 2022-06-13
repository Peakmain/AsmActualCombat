package com.peakmain.sdk.map;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * author ：Peakmain
 * createTime：2022/6/10
 * mail:2726449200@qq.com
 * describe：
 */
public final class p {
    private static final char[] a = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    private static final byte[] b = new byte[128];

    public static byte[] a(byte[] var0) throws CertificateException, InvalidKeySpecException, NoSuchAlgorithmException, NullPointerException, IOException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        KeyGenerator var1;
        if ((var1 = KeyGenerator.getInstance(x.c("EQUVT"))) == null) {
            return null;
        } else {
            var1.init(256);
            byte[] var3 = var1.generateKey().getEncoded();
            PublicKey var2;
            if ((var2 = x.d()) == null) {
                return null;
            } else {
                byte[] var4 = a(var3, (Key)var2);
                var0 = a(var3, var0);
                var3 = new byte[var4.length + var0.length];
                System.arraycopy(var4, 0, var3, 0, var4.length);
                System.arraycopy(var0, 0, var3, var4.length, var0.length);
                return var3;
            }
        }
    }

    public static String b(byte[] var0) {
        try {
            return d(var0);
        } catch (Throwable var1) {
            return null;
        }
    }

    public static String a(String var0) {
        return x.a(b(var0));
    }

    public static String c(byte[] var0) {
        try {
            return d(var0);
        } catch (Throwable var1) {
            var1.printStackTrace();
            return null;
        }
    }

    private static byte[] a(byte[] var0, byte[] var1) {
        try {
            return c(var0, var1, x.c());
        } catch (Throwable var2) {
            return null;
        }
    }

    public static byte[] a(byte[] var0, byte[] var1, byte[] var2) throws Exception {
        IvParameterSpec var5 = new IvParameterSpec(var2);
        SecretKeySpec var4 = new SecretKeySpec(var0, x.c("EQUVT"));
        Cipher var3;
        (var3 = Cipher.getInstance(x.c("CQUVTL0NCQy9QS0NTNVBhZGRpbmc"))).init(2, var4, var5);
        return var3.doFinal(var1);
    }

    public static byte[] b(byte[] var0, byte[] var1, byte[] var2) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        return c(var0, var1, var2);
    }

    private static byte[] c(byte[] var0, byte[] var1, byte[] var2) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        IvParameterSpec var6 = new IvParameterSpec(var2);
        SecretKeySpec var5 = new SecretKeySpec(var0, x.c("EQUVT"));
        Cipher var3 = Cipher.getInstance(x.c("CQUVTL0NCQy9QS0NTNVBhZGRpbmc"));

        try {
            var3.init(1, var5, var6);
        } catch (InvalidAlgorithmParameterException var4) {
            var4.printStackTrace();
        }

        return var3.doFinal(var1);
    }

    static byte[] a(byte[] var0, Key var1) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher var2;
        (var2 = Cipher.getInstance(x.c("CUlNBL0VDQi9QS0NTMVBhZGRpbmc"))).init(1, var1);
        return var2.doFinal(var0);
    }

    public static byte[] b(String var0) {
        if (var0 == null) {
            return new byte[0];
        } else {
            byte[] var6;
            int var1 = (var6 = x.a(var0)).length;
            ByteArrayOutputStream var2 = new ByteArrayOutputStream(var1);
            int var3 = 0;

            while(var3 < var1) {
                byte var4;
                do {
                    var4 = b[var6[var3++]];
                } while(var3 < var1 && var4 == -1);

                if (var4 == -1) {
                    break;
                }

                byte var5;
                do {
                    var5 = b[var6[var3++]];
                } while(var3 < var1 && var5 == -1);

                if (var5 == -1) {
                    break;
                }

                var2.write(var4 << 2 | (var5 & 48) >>> 4);

                do {
                    if (var3 == var1) {
                        return var2.toByteArray();
                    }

                    if ((var4 = var6[var3++]) == 61) {
                        return var2.toByteArray();
                    }

                    var4 = b[var4];
                } while(var3 < var1 && var4 == -1);

                if (var4 == -1) {
                    break;
                }

                var2.write((var5 & 15) << 4 | (var4 & 60) >>> 2);

                do {
                    if (var3 == var1) {
                        return var2.toByteArray();
                    }

                    if ((var5 = var6[var3++]) == 61) {
                        return var2.toByteArray();
                    }

                    var5 = b[var5];
                } while(var3 < var1 && var5 == -1);

                if (var5 == -1) {
                    break;
                }

                var2.write((var4 & 3) << 6 | var5);
            }

            return var2.toByteArray();
        }
    }

    private static String d(byte[] var0) {
        StringBuffer var1 = new StringBuffer();
        int var2 = var0.length;
        int var3 = 0;

        while(var3 < var2) {
            int var4 = var0[var3++] & 255;
            if (var3 == var2) {
                var1.append(a[var4 >>> 2]);
                var1.append(a[(var4 & 3) << 4]);
                var1.append("==");
                break;
            }

            int var5 = var0[var3++] & 255;
            if (var3 == var2) {
                var1.append(a[var4 >>> 2]);
                var1.append(a[(var4 & 3) << 4 | (var5 & 240) >>> 4]);
                var1.append(a[(var5 & 15) << 2]);
                var1.append("=");
                break;
            }

            int var6 = var0[var3++] & 255;
            var1.append(a[var4 >>> 2]);
            var1.append(a[(var4 & 3) << 4 | (var5 & 240) >>> 4]);
            var1.append(a[(var5 & 15) << 2 | (var6 & 192) >>> 6]);
            var1.append(a[var6 & 63]);
        }

        return var1.toString();
    }

    static {
        int var0;
        for(var0 = 0; var0 < 128; ++var0) {
            b[var0] = -1;
        }

        for(var0 = 65; var0 <= 90; ++var0) {
            b[var0] = (byte)(var0 - 65);
        }

        for(var0 = 97; var0 <= 122; ++var0) {
            b[var0] = (byte)(var0 - 97 + 26);
        }

        for(var0 = 48; var0 <= 57; ++var0) {
            b[var0] = (byte)(var0 - 48 + 52);
        }

        b[43] = 62;
        b[47] = 63;
    }
}
