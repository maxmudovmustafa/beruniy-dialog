package firbase.go.beruniy.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.MessageDigest;

public final class Util {

    public static <A> A nvl(A v, A d) {
        return v != null ? v : d;
    }

    public static String nvl(String v) {
        return nvl(v, "");
    }

    final protected static char[] hexArray = "0123456789abcdef".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String calcSHA(byte b[]) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(b);
        return bytesToHex(md.digest());
    }

    public static String extractStackTrace(Throwable ex) {
        StringWriter result = new StringWriter();
        PrintWriter writer = new PrintWriter(result);
        ex.printStackTrace(writer);
        writer.close();
        return result.toString();
    }

    public static final Command NOOP = new Command() {
        @Override
        public void apply() {

        }
    };

}
