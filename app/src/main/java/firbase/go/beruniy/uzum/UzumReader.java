package firbase.go.beruniy.uzum;// 13.06.2016

import android.text.TextUtils;
import firbase.go.beruniy.collection.MyArray;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class UzumReader {

    private final Scanner scanner;

    public UzumReader(Scanner Scanner) {
        this.scanner = Scanner;
    }

    public boolean isString() {
        return this.scanner.peek() == UzumToken.STRING;
    }

    public boolean isOpen() {
        return this.scanner.peek() == UzumToken.OPEN;
    }

    public boolean isClose() {
        return this.scanner.peek() == UzumToken.CLOSE;
    }

    public boolean isEOF() {
        return this.scanner.peek() == UzumToken.EOF;
    }

    public Integer readInteger() {
        String n = readString();
        if (TextUtils.isEmpty(n)) {
            return null;
        }
        return Integer.valueOf(n);
    }

    public int readInt() {
        String i = readString();
        if (TextUtils.isEmpty(i)) {
            return -1;
        }
        return Integer.parseInt(i);
    }

    public Boolean readBoolean() {
        String b = readString();
        if (TextUtils.isEmpty(b)) {
            return null;
        }
        return "1".equals(b);
    }

    public BigDecimal readBigDecimal() {
        String n = readString();
        if (TextUtils.isEmpty(n)) {
            return null;
        }
        return new BigDecimal(n);
    }

    public String readString() {
        if (isString()) {
            this.scanner.next();
            return this.scanner.val();
        }
        return null;
    }

    public <E> E readValue(UzumAdapter<E> adapter) {
        if (isOpen()) {
            this.scanner.next();
            E read = adapter.read(this);
            while (!isClose()) {
                this.skipValue();
            }
            this.scanner.next();
            return read;
        }
        return null;
    }

    public <E> MyArray<E> readArray(UzumAdapter<E> adapter) {
        if (isOpen()) {
            this.scanner.next();
            List<E> list = new ArrayList<>();
            while (!isClose()) {
                list.add(readValue(adapter));
            }
            this.scanner.next();
            return MyArray.from(list);
        }
        return null;
    }

    public void skipValue() {
        if (isString()) {
            this.scanner.next();
        } else if (isOpen()) {
            this.scanner.next();
            while (!isClose()) {
                this.skipValue();
            }
            this.scanner.next();
        }
    }
}
