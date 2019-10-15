package firbase.go.beruniy.uzum;// 13.06.2016

import java.util.ArrayList;
import java.util.List;

import firbase.go.beruniy.collection.MyArray;

public abstract class UzumAdapter<E> {

    public abstract E read(UzumReader in);

    public abstract void write(UzumWriter out, E val);

    public UzumAdapter<MyArray<E>> toArray() {
        final UzumAdapter<E> that = this;
        return new UzumAdapter<MyArray<E>>() {
            @Override
            public MyArray<E> read(UzumReader in) {
                List<E> list = new ArrayList<>();
                while (!in.isClose()) list.add(in.readValue(that));
                return MyArray.from(list);
            }

            @Override
            public void write(UzumWriter out, MyArray<E> val) {
                for (E item : val) out.write(item, that);
            }
        };
    }

    public static final UzumAdapter<String> STRING = new UzumAdapter<String>() {
        @Override
        public String read(UzumReader in) {
            return in.readString();
        }

        @Override
        public void write(UzumWriter out, String val) {
            out.write(val);
        }
    };

    public static final UzumAdapter<MyArray<String>> STRING_ARRAY = new UzumAdapter<MyArray<String>>() {
        @Override
        public MyArray<String> read(UzumReader in) {
            List<String> list = new ArrayList<>();
            while (!in.isClose()) list.add(in.readString());
            return MyArray.from(list);
        }

        @Override
        public void write(UzumWriter out, MyArray<String> val) {
            for (String item : val) out.write(item);
        }
    };

}
