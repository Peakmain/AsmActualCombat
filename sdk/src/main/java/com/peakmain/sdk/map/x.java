package com.peakmain.sdk.map;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import android.os.Build.VERSION;
/**
 * author ：Peakmain
 * createTime：2022/6/10
 * mail:2726449200@qq.com
 * describe：
 */
public class x {
    private static final String[] b = new String[]{"arm64-v8a", "x86_64"};
    private static final String[] c = new String[]{"arm", "x86"};
    static String a;

    public static Method a(Class var0, String var1, Class<?>... var2) {
        try {
            return var0.getDeclaredMethod(c(var1), var2);
        } catch (Throwable var3) {
            return null;
        }
    }

    public static String a(Context var0) {
        String var1 = "";
        if (VERSION.SDK_INT >= 21 && VERSION.SDK_INT < 28) {
            try {
                ApplicationInfo var3 = var0.getApplicationInfo();
                Field var2;
                (var2 = Class.forName(ApplicationInfo.class.getName()).getDeclaredField("primaryCpuAbi")).setAccessible(true);
                var1 = (String)var2.get(var3);
            } catch (Throwable var5) {
            }
        }

        if (VERSION.SDK_INT >= 28) {
            try {
                String[] var9;
                if ((var9 = (String[])((String[]) Build.class.getDeclaredField("SUPPORTED_ABIS").get((Object)null))) != null && var9.length > 0) {
                    var1 = var9[0];
                }

                String var6;
                if (!TextUtils.isEmpty(var1) && Arrays.asList(b).contains(var1) && !TextUtils.isEmpty(var6 = var0.getApplicationInfo().nativeLibraryDir)) {
                    int var8 = var6.lastIndexOf(File.separator);
                    var6 = var6.substring(var8 + 1);
                    String[] var7;
                    if (Arrays.asList(c).contains(var6) && (var7 = (String[])((String[])Build.class.getDeclaredField("SUPPORTED_32_BIT_ABIS").get((Object)null))) != null && var7.length > 0) {
                        var1 = var7[0];
                    }
                }
            } catch (Throwable var4) {
            }
        }

        if (TextUtils.isEmpty(var1)) {
            var1 = Build.CPU_ABI;
        }

        return var1;
    }




    public static void a(ByteArrayOutputStream var0, String var1) {
        if (TextUtils.isEmpty(var1)) {
            try {
                var0.write(new byte[]{0});
            } catch (IOException var3) {
            }
        } else {
            int var2;
            if ((var2 = var1.length()) > 255) {
                var2 = 255;
            }

            a(var0, (byte)var2, a(var1));
        }
    }

    public static String a(byte[] var0) {
        if (var0 != null && var0.length != 0) {
            try {
                return new String(var0, "UTF-8");
            } catch (UnsupportedEncodingException var1) {
                return new String(var0);
            }
        } else {
            return "";
        }
    }

    public static byte[] a(String var0) {
        if (TextUtils.isEmpty(var0)) {
            return new byte[0];
        } else {
            try {
                return var0.getBytes("UTF-8");
            } catch (UnsupportedEncodingException var1) {
                return var0.getBytes();
            }
        }
    }

    public static void a(ByteArrayOutputStream var0, byte var1, byte[] var2) {
        try {
            var0.write(new byte[]{var1});
            int var4;
            if ((var4 = var1 & 255) < 255 && var4 > 0) {
                var0.write(var2);
            } else {
                if (var4 == 255) {
                    var0.write(var2, 0, 255);
                }

            }
        } catch (IOException var3) {
        }
    }

    public static String b(String var0) {
        if (var0 == null) {
            return null;
        } else {
            var0 = p.c(a(var0));
            String var1 = "";

            try {
                var1 = (char)(var0.length() % 26 + 65) + var0;
            } catch (Throwable var2) {
            }

            return var1;
        }
    }

    public static String c(String var0) {
        return var0.length() < 2 ? "" : p.a(var0.substring(1));
    }

    public static boolean a(JSONObject var0, String var1) {
        return var0 != null && var0.has(var1);
    }

    public static byte[] c() {
        try {
            String var0 = "16,16,18,77,15,911,121,77,121,911,38,77,911,99,86,67,611,96,48,77,84,911,38,67,021,301,86,67,611,98,48,77,511,77,48,97,511,58,48,97,511,84,501,87,511,96,48,77,221,911,38,77,121,37,86,67,25,301,86,67,021,96,86,67,021,701,86,67,35,56,86,67,611,37,221,87";
            String[] var4;
            byte[] var1 = new byte[(var4 = (new StringBuffer(var0)).reverse().toString().split(",")).length];

            int var2;
            for(var2 = 0; var2 < var4.length; ++var2) {
                var1[var2] = Byte.parseByte(var4[var2]);
            }

            byte[] var5 = p.b(new String(var1));
            var0 = new String(var5);
            var1 = new byte[(var4 = (new StringBuffer(var0)).reverse().toString().split(",")).length];

            for(var2 = 0; var2 < var4.length; ++var2) {
                var1[var2] = Byte.parseByte(var4[var2]);
            }

            return var1;
        } catch (Throwable var3) {
            return new byte[16];
        }
    }

    public static String a(Throwable var0) {
        StringWriter var1 = null;
        PrintWriter var2 = null;

        try {
            var1 = new StringWriter();
            var2 = new PrintWriter(var1);
            var0.printStackTrace(var2);

            for(var0 = var0.getCause(); var0 != null; var0 = var0.getCause()) {
                var0.printStackTrace(var2);
            }

            String var3 = var1.toString();
            return var3;
        } catch (Throwable var15) {
            var15.printStackTrace();
        } finally {
            if (var1 != null) {
                try {
                    var1.close();
                } catch (Throwable var14) {
                    var14.printStackTrace();
                }
            }

            if (var2 != null) {
                try {
                    var2.close();
                } catch (Throwable var13) {
                    var13.printStackTrace();
                }
            }

        }

        return null;
    }

    public static String a(Map<String, String> var0) {
        if (var0.size() == 0) {
            return null;
        } else {
            StringBuffer var1 = new StringBuffer();

            try {
                boolean var2 = true;
                Iterator var5 = var0.entrySet().iterator();

                while(var5.hasNext()) {
                    Map.Entry var3 = (Map.Entry)var5.next();
                    if (var2) {
                        var2 = false;
                        var1.append((String)var3.getKey()).append("=").append((String)var3.getValue());
                    } else {
                        var1.append("&").append((String)var3.getKey()).append("=").append((String)var3.getValue());
                    }
                }
            } catch (Throwable var4) {
            }

            return var1.toString();
        }
    }

    public static String b(Map<String, String> var0) {
        String var10000;
        if (var0 != null) {
            StringBuilder var1 = new StringBuilder();
            Iterator var3 = var0.entrySet().iterator();

            while(var3.hasNext()) {
                Map.Entry var2 = (Map.Entry)var3.next();
                if (var1.length() > 0) {
                    var1.append("&");
                }

                var1.append((String)var2.getKey());
                var1.append("=");
                var1.append((String)var2.getValue());
            }

            var10000 = var1.toString();
        } else {
            var10000 = null;
        }

        return e(var10000);
    }

    private static String e(String var0) {
        try {
            if (TextUtils.isEmpty(var0)) {
                return "";
            }

            String[] var1;
            Arrays.sort(var1 = var0.split("&"));
            StringBuffer var2 = new StringBuffer();
            int var3 = var1.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String var5 = var1[var4];
                var2.append(var5);
                var2.append("&");
            }

            String var7;
            if ((var7 = var2.toString()).length() > 1) {
                return (String)var7.subSequence(0, var7.length() - 1);
            }
        } catch (Throwable var6) {
        }

        return var0;
    }

    public static byte[] b(byte[] var0) {
        try {
            return h(var0);
        } catch (Throwable var1) {
            return new byte[0];
        }
    }

    public static byte[] c(byte[] var0) {
        if (var0 != null && var0.length != 0) {
            byte[] var1 = null;
            ZipOutputStream var2 = null;
            ByteArrayOutputStream var3 = null;

            try {
                var3 = new ByteArrayOutputStream();
                (var2 = new ZipOutputStream(var3)).putNextEntry(new ZipEntry("log"));
                var2.write(var0);
                var2.closeEntry();
                var2.finish();
                var1 = var3.toByteArray();
            } catch (Throwable var15) {
            } finally {
                if (var2 != null) {
                    try {
                        var2.close();
                    } catch (Throwable var14) {
                    }
                }

                if (var3 != null) {
                    try {
                        var3.close();
                    } catch (Throwable var13) {
                    }
                }

            }

            return var1;
        } else {
            return null;
        }
    }

    public static byte[] a(int var0) {
        byte var1 = (byte)(var0 / 256);
        byte var2 = (byte)(var0 % 256);
        return new byte[]{var1, var2};
    }

    static PublicKey d() throws CertificateException, InvalidKeySpecException, NoSuchAlgorithmException, NullPointerException, IOException {
        String var0 = "MIICnjCCAgegAwIBAgIJAJ0Pdzos7ZfYMA0GCSqGSIb3DQEBBQUAMGgxCzAJBgNVBAYTAkNOMRMwEQYDVQQIDApTb21lLVN0YXRlMRAwDgYDVQQHDAdCZWlqaW5nMREwDwYDVQQKDAhBdXRvbmF2aTEfMB0GA1UEAwwWY29tLmF1dG9uYXZpLmFwaXNlcnZlcjAeFw0xMzA4MTUwNzU2NTVaFw0yMzA4MTMwNzU2NTVaMGgxCzAJBgNVBAYTAkNOMRMwEQYDVQQIDApTb21lLVN0YXRlMRAwDgYDVQQHDAdCZWlqaW5nMREwDwYDVQQKDAhBdXRvbmF2aTEfMB0GA1UEAwwWY29tLmF1dG9uYXZpLmFwaXNlcnZlcjCBnzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEA8eWAyHbFPoFPfdx5AD+D4nYFq4dbJ1p7SIKt19Oz1oivF/6H43v5Fo7s50pD1UF8+Qu4JoUQxlAgOt8OCyQ8DYdkaeB74XKb1wxkIYg/foUwN1CMHPZ9O9ehgna6K4EJXZxR7Y7XVZnbjHZIVn3VpPU/Rdr2v37LjTw+qrABJxMCAwEAAaNQME4wHQYDVR0OBBYEFOM/MLGP8xpVFuVd+3qZkw7uBvOTMB8GA1UdIwQYMBaAFOM/MLGP8xpVFuVd+3qZkw7uBvOTMAwGA1UdEwQFMAMBAf8wDQYJKoZIhvcNAQEFBQADgYEA4LY3g8aAD8JkxAOqUXDDyLuCCGOc2pTIhn0TwMNaVdH4hZlpTeC/wuRD5LJ0z3j+IQ0vLvuQA5uDjVyEOlBrvVIGwSem/1XGUo13DfzgAJ5k1161S5l+sFUo5TxpHOXr8Z5nqJMjieXmhnE/I99GFyHpQmw4cC6rhYUhdhtg+Zk=";
        ByteArrayInputStream var1 = null;

        PublicKey var3;
        try {
            var1 = new ByteArrayInputStream(p.b(var0));
            CertificateFactory var14 = CertificateFactory.getInstance(c("IWC41MDk"));
            KeyFactory var2 = KeyFactory.getInstance(c("EUlNB"));
            Certificate var15;
            if ((var15 = var14.generateCertificate(var1)) == null || var2 == null) {
                return null;
            }

            X509EncodedKeySpec var16 = new X509EncodedKeySpec(var15.getPublicKey().getEncoded());
            var3 = var2.generatePublic(var16);
        } catch (Throwable var12) {
            return null;
        } finally {
            try {
                if (var1 != null) {
                    var1.close();
                }
            } catch (Throwable var11) {
                var11.printStackTrace();
            }

        }

        return var3;
    }

    public static byte[] d(byte[] var0) {
        try {
            return h(var0);
        } catch (Throwable var1) {
            var1.printStackTrace();
            return new byte[0];
        }
    }

    static String e(byte[] var0) {
        try {
            return g(var0);
        } catch (Throwable var1) {
            return null;
        }
    }

    static String f(byte[] var0) {
        try {
            return g(var0);
        } catch (Throwable var1) {
            return null;
        }
    }

    public static String g(byte[] var0) {
        StringBuilder var1 = new StringBuilder();
        if (var0 == null) {
            return null;
        } else {
            for(int var2 = 0; var2 < var0.length; ++var2) {
                String var3;
                if ((var3 = Integer.toHexString(var0[var2] & 255)).length() == 1) {
                    var3 = "0".concat(String.valueOf(var3));
                }

                var1.append(var3);
            }

            return var1.toString();
        }
    }

    public static byte[] d(String var0) {
        String var10000 = var0.length() % 2 != 0 ? "0".concat(String.valueOf(var0)) : var0;
        var0 = var10000;
        byte[] var1 = new byte[var10000.length() / 2];

        for(int var2 = 0; var2 < var1.length; ++var2) {
            int var3 = var2 * 2;
            var3 = Integer.parseInt(var0.substring(var3, var3 + 2), 16);
            var1[var2] = (byte)var3;
        }

        return var1;
    }

    private static byte[] h(byte[] var0) throws IOException, Throwable {
        ByteArrayOutputStream var2 = null;
        GZIPOutputStream var3 = null;
        if (var0 == null) {
            return null;
        } else {
            byte[] var1;
            try {
                var2 = new ByteArrayOutputStream();
                (var3 = new GZIPOutputStream(var2)).write(var0);
                var3.finish();
                var1 = var2.toByteArray();
            } catch (Throwable var13) {
                throw var13;
            } finally {
                if (var3 != null) {
                    try {
                        var3.close();
                    } catch (Throwable var12) {
                        throw var12;
                    }
                }

                if (var2 != null) {
                    try {
                        var2.close();
                    } catch (Throwable var11) {
                        throw var11;
                    }
                }

            }

            return var1;
        }
    }

    public static String a(long var0) {
        return a(var0, "yyyyMMdd HH:mm:ss:SSS");
    }

    public static String a(long var0, String var2) {
        String var3 = null;

        try {
            SimpleDateFormat var6 = new SimpleDateFormat(var2, Locale.CHINA);
            Date var5 = new Date(var0);
            var3 = var6.format(var5);
        } catch (Throwable var4) {
        }

        return var3;
    }

    private static void f(String var0) {
        String var1;
        while(var0.length() >= 78) {
            var1 = var0.substring(0, 78);
            var1 = "|" + var1 + "|";
            Log.i("authErrLog", var1);
            var0 = var0.substring(78);
        }

        StringBuilder var3;
        (var3 = new StringBuilder()).append("|").append(var0);

        for(int var2 = 0; var2 < 78 - var0.length(); ++var2) {
            var3.append(" ");
        }

        var3.append("|");
        var1 = var3.toString();
        Log.i("authErrLog", var1);
    }


    public static Calendar a(String var0, String var1) {
        try {
            SimpleDateFormat var5 = new SimpleDateFormat(var1, Locale.CHINA);
            Calendar var2 = Calendar.getInstance();
            Calendar var3;
            (var3 = Calendar.getInstance()).setTime(var5.parse(var0));
            var2.set(var2.get(1), var2.get(2), var2.get(5), var3.get(11), var3.get(12), var3.get(13));
            return var2;
        } catch (ParseException var4) {
            return null;
        }
    }

    static {
        StringBuilder var0 = new StringBuilder();

        for(int var1 = 0; var1 < 80; ++var1) {
            var0.append("=");
        }

        a = var0.toString();
    }
}
