package firbase.go.beruniy.uzum;// 13.06.2016

import android.util.JsonReader;
import android.util.JsonToken;
import firbase.go.beruniy.utils.L_Log;

import java.io.IOException;

class JsonScanner implements Scanner {

    private final JsonReader reader;
    private String val;

    public JsonScanner(JsonReader reader) {
        this.reader = reader;
    }

    @Override
    public String val() {
        return this.val;
    }

    @Override
    public int next() {
        this.val = null;
        try {
            JsonToken token = this.reader.peek();
            switch (token) {
                case STRING:
                    this.val = reader.nextString();
                    return UzumToken.STRING;
                case BEGIN_ARRAY:
                    reader.beginArray();
                    return UzumToken.OPEN;
                case END_ARRAY:
                    reader.endArray();
                    return UzumToken.CLOSE;
                case END_DOCUMENT:
                    return UzumToken.EOF;
                default:
                    throw new UzumException("Invalid token in JsonScanner = " + token);
            }
        } catch (Exception e) {
            throw new UzumException(e);
        }
    }

    @Override
    public int peek() {
        try {
            JsonToken token = this.reader.peek();
            switch (token) {
                case STRING:
                    return UzumToken.STRING;
                case BEGIN_ARRAY:
                    return UzumToken.OPEN;
                case END_ARRAY:
                    return UzumToken.CLOSE;
                case END_DOCUMENT:
                    return UzumToken.EOF;
                default:
                    new L_Log().log("Invalid token in JsonScanner = " + token);
            }
        } catch (IOException e) {
            throw new UzumException(e);
        }
        return 0;
    }
}
