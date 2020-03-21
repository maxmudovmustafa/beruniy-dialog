package firbase.go.beruniy.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class holds generic type objects similar to usual {@link Arrays} and
 * implements {@link Iterable} interface. The instance of this class is created
 * automatically when static function {@link MyArray#from(Object[])} is executed.
 * Objects cannot be added (removed, filtered etc.) directly to (from) that instance
 * of {@link MyArray}. New objects are added (removed, filtered etc.)
 * to (from) the new instance of {@link MyArray}.
 */

@SuppressWarnings("unchecked")
public class MyArray<E> implements Iterable<E> {

    private final Object[] data;
    private Map<Object, E> map;
    private MyMapper<?, ?> keyMap;

    /**
     * The constructor is private so the new instance of the object {@link MyArray}
     * cannot be created directly from outside the class. Only {@link MyArray#EMPTY} and
     * {@link MyArray#fromPrivate(Object[])} static functions can call the constructor.
     *
     * @param data object(s) that should be added
     */
    private MyArray(Object[] data) {
        this.data = data;
    }

    /**
     * function returns the object (of generic type) in {@link MyArray} from specified position
     *
     * @param index position of the object in {@link MyArray#data}
     * @return specified generic type in the position of {@param index}
     * @throws ArrayIndexOutOfBoundsException exception when the {@param index}
     *                                        is incorrect
     */
    public E get(int index) {
        return (E) this.data[index];
    }

    /**
     * Converts all objects to the {@link String} using {@link String#valueOf(int)} function
     * and returns new {@link CharSequence[]}
     *
     * @return char value of objects in {@link MyArray}
     */
    public CharSequence[] toCharSequenceArray() {
        CharSequence[] r = new CharSequence[data.length];
        for (int i = 0; i < data.length; i++) {
            r[i] = String.valueOf(data[i]);
        }
        return r;
    }

    /**
     * Clones (copies) the {@link MyArray#data}
     *
     * @return new array of specified generic type
     */
    public E[] toArray() {
        return (E[]) this.data.clone();
    }

    /**
     * Creates a new string from objects in {@link MyArray}. This function is recommended
     * to use only when the objects inside are type of {@link CharSequence} or objects
     * that overrides {@link Object#toString()} function.
     * Otherwise, the hex value of objects will be returned.
     * <p>
     * Examples:
     * <blockquote><pre>
     * MyArray<Person> items  = MyArray.<Person>from (
     * new Person("1","John", MyArray.<String>from("+777777777777","+888888888888")),
     * new Person("2","Mike", MyArray.<String>from("+666666666666")));
     * StringBuilder sb = new StringBuilder();
     * for (Person person : items){
     * sb.append(person.personId).append(" ").append(person.name).append(" ");
     * person.phoneNumbers.mkString(sb, "[\"","\",\"","\"]");
     * sb.append("\n");
     * }
     * String result = sb.toString()
     * Returns:
     * 1 John ["+777777777777","+888888888888"]
     * 2 Mike ["+666666666666"]
     * </pre></blockquote>
     *
     * @param sb     existing {@link StringBuilder} that takes the result of the function
     * @param prefix first character of the {@link String}
     * @param infix  separator between the objects ({@link CharSequence})
     * @param suffix the last character of the {@link String}
     * @return new {@link StringBuilder} or given {@param sb} with concatenated strings
     */
    public StringBuilder mkString(StringBuilder sb, String prefix, String infix, String suffix) {
        sb.append(prefix);

        switch (data.length) {
            case 0:
                break;
            case 1:
                sb.append(data[0]);
                break;
            default:
                sb.append(data[0]);
                for (int i = 1; i < data.length; i++) {
                    sb.append(infix);
                    sb.append(data[i]);
                }
                break;
        }

        sb.append(suffix);

        return sb;
    }

    /**
     * Creates a new string from objects in {@link MyArray}. This function is recommended
     * to use only when the objects inside are type of {@link CharSequence} or objects
     * that overrides {@link Object#toString()} function.
     * Otherwise, the hex value of objects will be returned.
     * Unlike {@link MyArray#mkString(StringBuilder, String, String, String)}, new
     * {@link StringBuilder} object is created in this function.
     *
     * @param prefix first character of the {@link String}
     * @param infix  separator between the objects ({@link CharSequence})
     * @param suffix the last character of the {@link String}
     * @return new {@link StringBuilder} object with concatenated strings
     */
    public String mkString(String prefix, String infix, String suffix) {
        return mkString(new StringBuilder(), prefix, infix, suffix).toString();
    }

    /**
     * Creates a new string from objects in {@link MyArray}. This function is recommended
     * to use only when the objects inside are type of {@link CharSequence} or objects
     * that overrides {@link Object#toString()} function.
     * Otherwise, the hex value of objects will be returned.
     * The prefix and suffix are empty strings "" by default.
     *
     * @param infix separator between the objects ({@link CharSequence}).
     * @return new {@link StringBuilder} object with concatenated strings.
     */
    public String mkString(String infix) {
        return mkString("", infix, "");
    }

    /**
     * Creates new empty object {@link MyArray} (Singleton).
     */
    private static final Object EMPTY = new MyArray<>(new Object[0]);

    /**
     * Returns empty {@link MyArray}.
     */
    public static <R> MyArray<R> emptyArray() {
        return (MyArray<R>) EMPTY;
    }

    /**
     * The function that returns new object {@link MyArray} with given elements
     * in it. This function is private and is called by {@link MyArray#from} methods.
     *
     * @param elements array of objects that is being added.
     * @return {@link MyArray} with given objects inside.
     */
    private static <T> MyArray<T> fromPrivate(Object[] elements) {
        if (elements.length == 0) {
            return emptyArray();
        }
        return new MyArray<T>(elements);
    }

    /**
     * Gets elements and passes to {@link MyArray#fromPrivate(Object[])} method.
     *
     * @param elements objects that are being added.
     * @return {@link MyArray} object with given objects inside.
     */
    public static <T> MyArray<T> from(T... elements) {
        return fromPrivate(elements);
    }

    /**
     * Gets elements as {@link List} and passes to {@link MyArray#fromPrivate(Object[])} method
     * converting them to array.
     *
     * @param elements list of objects that are being added.
     * @return {@link MyArray} object with given objects inside.
     */
    public static <T> MyArray<T> from(List<T> elements) {
        return fromPrivate(elements.toArray());
    }

    /**
     * Gets elements as {@link Set} and passes to {@link MyArray#fromPrivate(Object[])} method
     * converting them to array.
     *
     * @param elements set of objects that are being added.
     * @return {@link MyArray} object with given objects inside.
     */
    public static <T> MyArray<T> from(Set<T> elements) {
        return fromPrivate(elements.toArray());
    }

    /**
     * Checks given {@link MyArray} and returns:
     * if object equals to null - empty {@link MyArray};
     * if not null - object itself.
     * The function is used to avoid {@link NullPointerException}.
     *
     * @param arr object that should be checked whether null or not.
     * @return {@link MyArray#EMPTY} or {@param arr}.
     */
    public static <T> MyArray<T> nvl(MyArray<T> arr) {
        if (arr == null) {
            return MyArray.emptyArray();
        }
        return arr;
    }

    /**
     * The function that returns the objects of {@link MyArray} as a {@link List}.
     *
     * @return {@link MyArray#data} as {@link List}.
     */
    public List<E> asList() {
        return Arrays.asList(toArray());
    }

    /**
     * The function that returns the objects of {@link MyArray} as a {@link Set}.
     *
     * @return {@link MyArray#data} as {@link Set}.
     */
    public Set<E> asSet() {
        Set<E> r = new HashSet<>();
        for (Object o : data) {
            r.add((E) o);
        }
        return r;
    }

    @Override
    public java.util.Iterator<E> iterator() {
        return new java.util.Iterator<E>() {

            int index = 0;

            @Override
            public boolean hasNext() {
                return index < data.length;
            }

            @Override
            public E next() {
                return (E) data[index++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("remove() method is not supported");
            }
        };
    }

    /**
     * Casts the object to its parent class.
     */
    public <F> MyArray<F> toSuper() {
        return (MyArray<F>) this;
    }

    /**
     * Filters out the object inside the {@link MyArray} then creates and returns new
     * instance of {@link MyArray} with left elements. If the {@link MyArray#data} is
     * empty or {@param predicate} is true for all objects inside {@link MyArray}
     * returns current instance without changes.
     *
     * @param predicate condition for filtering objects.
     * @return {@link MyArray} with elements left after filter.
     */
    public MyArray<E> filter(MyPredicate<E> predicate) {
        if (data.length == 0) {
            return this;
        }
        if (predicate == MyPredicate.True()) {
            return this;
        }
        ArrayList<E> r = new ArrayList<>();
        for (Object aData : data) {
            E x = (E) aData;
            if (predicate.apply(x)) {
                r.add(x);
            }
        }
        return from(r);
    }

    /**
     * checks {@link MyArray} for null objects.
     *
     * @return true if there is (are) null object(s) and false if not.
     */
    public boolean hasNull() {
        for (Object aData : data) {
            if (aData == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * throws exception if {@link MyArray#hasNull()} function returns true.
     */
    public void checkNotNull() {
        if (hasNull()) {
            throw new RuntimeException("Array contains null element.");
        }
    }

    /**
     * creates new {@link MyArray} filtering from null objects. if {@link MyArray#data}
     * is empty or has not nulls, returns current instance.
     *
     * @return current {@link MyArray} object if it is empty or does not contain nulls;
     * new filtered instance of {@link MyArray} if it contains nulls.
     */
    public MyArray<E> filterNotNull() {
        if (data.length == 0) {
            return this;
        }
        if (!hasNull()) {
            return this;
        }
        ArrayList<E> r = new ArrayList<>();
        for (Object aData : data) {
            E x = (E) aData;
            if (x != null) {
                r.add(x);
            }
        }
        return from(r);
    }

    /**
     * outputs the first object index whose criteria matches the given predicate. If
     * there is no such object, returns -1.
     *
     * @param predicate search criterion
     * @return index of found object; -1 if object not found.
     */
    public int findFirstPosition(MyPredicate<E> predicate) {
        for (int i = 0; i < data.length; i++) {
            if (predicate.apply((E) data[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * returns the index of the object similarly to {@link MyArray#findFirstPosition(MyPredicate)},
     * mapping the key object with the identifier of another one using {@link MyMapper}.
     *
     * @param key    identifier of the object that is being looked for.
     * @param mapper KEY_ADAPTER for mapping the objects.
     * @return index of found object; -1 if object not found.
     */
    public <K> int findPosition(final K key, final MyMapper<E, K> mapper) {
        return findFirstPosition(new MyPredicate<E>() {
            @Override
            public boolean apply(E e) {
                return mapper.apply(e).equals(key);
            }
        });
    }

    /**
     * gets first object that matches the requirement ({@param predicate}).
     * Outputs null in case no object was found according to set criterion.
     *
     * @param predicate search criterion
     * @return first met generic type object corresponding to the predicate;
     * null if nothing found.
     */
    public E findFirst(MyPredicate<E> predicate) {
        int pos = findFirstPosition(predicate);
        if (pos > -1) {
            return (E) data[pos];
        }
        return null;
    }


    /**
     * checks whether the object that matches the requirement ({@param predicate}) exists
     * or not.
     *
     * @param predicate search criterion.
     * @return true if object was found; false otherwise.
     */
    public boolean contains(MyPredicate<E> predicate) {
        return findFirstPosition(predicate) > -1;
    }

    /**
     * returns new {@link MyArray} instance consisting of transformed objects from
     * specific type to another type.
     *
     * @param mapper mapper of two type of objects
     * @return empty {@link MyArray} if {@link MyArray#data} is empty;
     * new {@link MyArray} instance with obtained(converted) objects.
     */
    public <R> MyArray<R> map(MyMapper<E, R> mapper) {
        if (data.length == 0) {
            return emptyArray();
        }
        Object[] r = new Object[data.length];
        for (int i = 0; i < data.length; i++) {
            r[i] = mapper.apply((E) data[i]);
        }
        return fromPrivate(r);
    }

    /**
     * creates new {@link MyArray} instance with the elements set from parameters (sub-objects) of
     * the objects inside the collection.
     * <p>
     * <p>For example, take {@code MyArray<Person>}. Using
     * {@code flatMap(new MyFlatMapper<Person, String>...)}function, we can create
     * new {@code MyArray<String>} collection taking necessary data from inside the
     * {@code Person} object (e.g. name of the person)</p>.
     *
     * @param flatMapper mapper between two objects.
     * @return empty {@link MyArray} if {@link MyArray#data} is empty;
     * new instance of {@link MyArray} with obtained objects.
     */
    public <R> MyArray<R> flatMap(MyFlatMapper<E, R> flatMapper) {
        if (data.length == 0) {
            return emptyArray();
        }
        List<R> r = new ArrayList<>();
        for (Object aData : data) {
            MyArray<R> es = flatMapper.apply((E) aData);
            for (R s : es) {
                r.add(s);
            }
        }
        return from(r);
    }

    private synchronized <K> void makeKeyMap(MyMapper<E, K> mapper) {
        if (map == null) {
            keyMap = mapper;
            map = new HashMap<>();
            for (Object aData : data) {
                E d = (E) aData;
                Object key = mapper.apply(d);
                if (map.containsKey(key)) {
                    throw new RuntimeException("MyArray key duplicate found key adapter=" +
                            mapper.getClass().getName() + " key=" + key);
                } else {
                    map.put(key, d);
                }
            }
        }
    }

    /**
     * returns the object from {@link MyArray}, key of which matches the given
     * parameter {@param key}. This method reduces the time spent on
     * search, on account of hashing the objects.
     *
     * @param key    key of the object (in [key, value] pair) that is being searched.
     * @param mapper mapper for two objects
     * @return the object from {@link MyArray}
     */
    public <K> E find(K key, MyMapper<E, K> mapper) {
        if (data.length == 0) {
            return null;
        }
        if (map == null) {
            makeKeyMap(mapper);
        }
        if (keyMap != mapper) {
            throw new IllegalArgumentException("Not proper key adapter used." +
                    keyMap.getClass().getName());
        }
        return map.get(key);
    }

    /**
     * checks the uniqueness of the keys generating [key, value] pairs of the objects
     * in {@link MyArray}.
     *
     * @param mapper mapper
     */
    public <K> void checkUniqueness(MyMapper<E, K> mapper) {
        if (data.length != 0 && map == null) {
            makeKeyMap(mapper);
        }
    }

    /**
     * checks certain elements for existence. Given parameters are passed to
     * {@link MyArray#find(Object, MyMapper)} method. If the object was not found,
     * the method returns false.
     *
     * @param key    the unique key of the object that is being searched.
     * @param mapper mapper.
     * @return true if exists; false otherwise.
     */
    public <K> boolean contains(K key, MyMapper<E, K> mapper) {
        return find(key, mapper) != null;
    }

    /**
     * the function that sorts elements and outputs the result
     * as a new {@link MyArray}
     *
     * @param comparator the parameter that compares objects in array
     * @return new {@link MyArray} instance consisting of sorted elements.
     */
    @SuppressWarnings("SuspiciousSystemArraycopy")
    public MyArray<E> sort(java.util.Comparator<E> comparator) {
        E[] r = (E[]) new Object[data.length];
        System.arraycopy(data, 0, r, 0, data.length);
        Arrays.sort(r, comparator);
        return fromPrivate(r);
    }

    /**
     * checks whether {@link MyArray} holds elements or not.
     *
     * @return false if yes; true otherwise.
     */
    public boolean isEmpty() {
        return this.data.length == 0;
    }

    /**
     * checks whether {@link MyArray} holds elements or not.
     *
     * @return true if yes; false if not.
     */
    public boolean nonEmpty() {
        return !isEmpty();
    }

    /**
     * returns the number of elements in {@link MyArray}.
     *
     * @return number of elements in collection.
     */
    public int size() {
        return data.length;
    }

    /**
     * creates new {@link MyArray} adding the given {@param element}
     * object to the first position.
     *
     * @param element the object to be added.
     * @return new instance of {@link MyArray} with given element at the first position.
     */
    public MyArray<E> prepend(E element) {
        Object[] newData = new Object[data.length + 1];
        newData[0] = element;
        System.arraycopy(data, 0, newData, 1, data.length);
        return fromPrivate(newData);
    }

    /**
     * creates new {@link MyArray} adding the given {@param element}
     * object to the end.
     *
     * @param element the object to be added.
     * @return new instance of {@link MyArray} with given element at the last position.
     */
    public MyArray<E> append(E element) {
        Object[] newData = new Object[data.length + 1];
        System.arraycopy(data, 0, newData, 0, data.length);
        newData[data.length] = element;
        return fromPrivate(newData);
    }

    /**
     * creates new {@link MyArray} connecting the given instance
     * to the end of current instance of {@link MyArray}.
     *
     * @param that the collection of objects to be added.
     * @return new instance of {@link MyArray} with given collection of elements
     * at the last position.
     */
    public MyArray<E> append(MyArray<E> that) {
        if (this.isEmpty()) {
            return that;
        }
        if (that.isEmpty()) {
            return this;
        }
        Object[] newData = new Object[this.data.length + that.data.length];
        System.arraycopy(this.data, 0, newData, 0, this.data.length);
        System.arraycopy(that.data, 0, newData, this.data.length, that.data.length);
        return fromPrivate(newData);
    }

    /**
     * unifies the current {@link MyArray} with other instance (given one {@param that}) using
     * specific (given) mapper.
     *
     * @param that       another instance of {@link MyArray} that is being combined.
     * @param keyAdapter mapper.
     * @return new instance consisting of joint objects from two {@link MyArray}s.
     */
    public <K> MyArray<E> union(MyArray<E> that, MyMapper<E, K> keyAdapter) {
        List<E> result = null;
        for (Object o : that.data) {
            E el = (E) o;
            K key = keyAdapter.apply(el);
            if (!contains(key, keyAdapter)) {
                if (result == null) {
                    result = new ArrayList<>(this.asList());
                }
                result.add(el);
            }
        }
        if (result == null) {
            return this;
        } else {
            return MyArray.from(result);
        }
    }

    /**
     * unifies the current {@link MyArray} with other instance (given one {@param that}).
     *
     * @param that another instance of {@link MyArray} that is being combined with the current.
     * @return new instance consisting of joint objects from two {@link MyArray}s.
     * @see MyArray#union(MyArray, MyMapper)
     */
    public MyArray<E> union(MyArray<E> that) {
        return union(that, MyMapper.<E>identity());
    }

}