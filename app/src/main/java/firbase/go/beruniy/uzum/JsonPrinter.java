package firbase.go.beruniy.uzum;// 13.06.2016

import android.util.JsonWriter;

import java.io.IOException;

import firbase.go.beruniy.utils.Util;

class JsonPrinter implements Printer {

    private final JsonWriter writer;

    public JsonPrinter(JsonWriter writer) {
        this.writer = writer;
    }

    @Override
    public void printOpen() {
        try {
            writer.beginArray();
        } catch (IOException e) {
            throw new UzumException(e);
        }
    }

    @Override
    public void printClose() {
        try {
            writer.endArray();
        } catch (IOException e) {
            throw new UzumException(e);
        }
    }

    @Override
    public void print(String str) {
        try {
            writer.value(Util.nvl(str));
        } catch (IOException e) {
            throw new UzumException(e);
        }
    }
}
