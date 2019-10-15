package firbase.go.beruniy.util;// 24.06.2016

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class IOUtil {

    public static final String CODE_PAGE = "UTF8";

    public static String readToEnd(BufferedReader reader) {
        try {
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                total.append(line);
            }
            return total.toString();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String readToEnd(InputStream inputStream, String codePage) {
        try {
            return readToEnd(new BufferedReader(new InputStreamReader(inputStream, codePage), 4096));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readToEnd(InputStream inputStream) {
        return readToEnd(inputStream, CODE_PAGE);
    }


    public static String getMimiType(String type) {
        switch (type) {
            case "doc":
                return "application/msword";
            case "docx":
                return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "xls":
                return "application/vnd.ms-excel";
            case "xlsx":
                return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "ppt":
                return "application/vnd.ms-powerpoint";
            case "pptx":
                return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
            case "mp4":
                return "video/mp4";
            case "mpeg":
                return "video/mpeg";
            case "ogg":
                return "video/ogg";
            case "quicktime":
                return "video/quicktime";
            case "gif":
                return "image/gif";
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "svg":
                return "image/svg+xml";
            default:
                return "application/" + type;
        }
    }

}
