package firbase.go.beruniy.uzum;// 13.06.2016

import java.math.BigDecimal;

import firbase.go.beruniy.collection.MyArray;

public final class UzumWriter {

    private final Printer printer;

    public UzumWriter(Printer printer) {
        this.printer = printer;
    }

    public void write(int number) {
        write(String.valueOf(number));
    }

    public void write(boolean bol) {
        write(bol ? "1" : "0");
    }

    public void write(BigDecimal number) {
        if (number == null) {
            throw new UzumException("BigDecimal number == null");
        }
        write(number.toPlainString());
    }

    public void write(String str) {
        this.printer.print(str);
    }

    public <E> void write(E item, UzumAdapter<E> adapter) {
        this.printer.printOpen();
        adapter.write(this, item);
        this.printer.printClose();
    }

    public <E> void write(MyArray<E> items, UzumAdapter<E> adapter) {
        this.printer.printOpen();
        for (E item : items) {
            write(item, adapter);
        }
        this.printer.printClose();
    }
}
