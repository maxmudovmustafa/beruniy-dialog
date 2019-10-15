package firbase.go.beruniy.uzum;// 13.06.2016

import android.os.Parcel;
import android.util.JsonReader;
import android.util.JsonWriter;

import java.io.Closeable;
import java.io.StringReader;
import java.io.StringWriter;

import firbase.go.beruniy.utils.Util;

public final class Uzum {

    public static <E> byte[] toBytes(E val, UzumAdapter<E> adapter) {
        if (val == null || adapter == null) {
            throw new UzumException("NULL pointer");
        }
        Parcel source = Parcel.obtain();
        try {
            UzumWriter writer = new UzumWriter(new ParcelPrinter(source));
            writer.write(val, adapter);
            return source.marshall();
        } finally {
            source.recycle();
        }
    }

    public static byte[] toBytes(String json) {
        if (json == null) {
            throw new UzumException("NULL pointer");
        }
        JsonReader jr = new JsonReader(new StringReader(json));
        Parcel source = Parcel.obtain();
        try {
            pipe(new JsonScanner(jr), new ParcelPrinter(source));
            return source.marshall();
        } finally {
            source.recycle();
            closeIt(jr);
        }
    }

    public static <E> String toJson(E val, UzumAdapter<E> adapter) {
        if (val == null || adapter == null) {
            throw new UzumException("NULL pointer");
        }
        StringWriter s = new StringWriter();
        JsonWriter jw = new JsonWriter(s);
        try {
            UzumWriter writer = new UzumWriter(new JsonPrinter(jw));
            writer.write(val, adapter);
            return s.toString();
        } finally {
            closeIt(jw);
        }
    }

    public static String toJson(byte[] bytes) {
        if (bytes == null) {
            throw new UzumException("NULL pointer");
        }
        Parcel source = Parcel.obtain();
        source.unmarshall(bytes, 0, bytes.length);
        source.setDataPosition(0);

        StringWriter s = new StringWriter();
        JsonWriter jw = new JsonWriter(s);
        try {
            try {
                pipe(new ParcelScanner(source), new JsonPrinter(jw));
                return s.toString();
            } finally {
                source.recycle();
                closeIt(jw);
            }
        } catch (Exception e) {
            System.out.println(Util.extractStackTrace(e));
            throw e;
        }
    }

    public static <E> E toValue(String json, UzumAdapter<E> adapter) {
        if (json == null || adapter == null) {
            throw new UzumException("NULL pointer");
        }
        JsonReader jr = new JsonReader(new StringReader(json));
        try {
            UzumReader reader = new UzumReader(new JsonScanner(jr));
            return reader.readValue(adapter);
        } finally {
            closeIt(jr);
        }
    }
    public static <E> E toValue(byte[] bytes, UzumAdapter<E> adapter) {
        if (bytes == null || adapter == null) {
            throw new UzumException("NULL pointer");
        }
        Parcel source = Parcel.obtain();
        try {
            source.unmarshall(bytes, 0, bytes.length);
            source.setDataPosition(0);
            UzumReader reader = new UzumReader(new ParcelScanner(source));
            return reader.readValue(adapter);
        } finally {
            source.recycle();
        }
    }

    private static void pipe(Scanner scanner, Printer printer) {
        int token;
        while ((token = scanner.next()) != UzumToken.EOF) {
            switch (token) {
                case UzumToken.STRING:
                    printer.print(scanner.val());
                    break;
                case UzumToken.OPEN:
                    printer.printOpen();
                    break;
                case UzumToken.CLOSE:
                    printer.printClose();
                    break;
                default:
                    throw new UzumException("invalid token " + token);
            }
        }
    }

    private static void closeIt(Closeable closeable) {
        try {
            closeable.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static <E> UzumParcellable toParcellable(E val, UzumAdapter<E> adapter) {
        return new UzumParcellable(toBytes(val, adapter));
    }

    public static <E> E toValue(UzumParcellable parcellable, UzumAdapter<E> adapter) {
        return toValue(parcellable.bytes, adapter);
    }

    private Uzum() {
    }
}
