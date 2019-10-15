package firbase.go.beruniy.collection;

public abstract class MyPredicate<E> {

    public abstract boolean apply(E e);

    public <T> MyPredicate<T> map(final MyMapper<T, E> mapper) {
        final MyPredicate<E> self = this;
        return new MyPredicate<T>() {
            @Override
            public boolean apply(T t) {
                return self.apply(mapper.apply(t));
            }
        };
    }

    public MyPredicate<E> and(final MyPredicate<E> that) {
        if (that == null) {
            return this;
        }
        final MyPredicate<E> self = this;
        return new MyPredicate<E>() {
            @Override
            public boolean apply(E element) {
                return self.apply(element) && that.apply(element);
            }
        };
    }

    public MyPredicate<E> or(final MyPredicate<E> that) {
        if (that == null) {
            return this;
        }
        final MyPredicate<E> self = this;
        return new MyPredicate<E>() {
            @Override
            public boolean apply(E element) {
                return self.apply(element) || that.apply(element);
            }
        };
    }

    public MyPredicate<E> negate() {
        final MyPredicate<E> self = this;
        return new MyPredicate<E>() {
            @Override
            public boolean apply(E element) {
                return !self.apply(element);
            }
        };
    }

    @SuppressWarnings("unchecked")
    public static <T> MyPredicate<T> True() {
        return (MyPredicate<T>) TRUE;
    }

    @SuppressWarnings("unchecked")
    public static <T> MyPredicate<T> False() {
        return (MyPredicate<T>) FALSE;
    }

    private static final MyPredicate<Object> TRUE = new MyPredicate<Object>() {
        @Override
        public boolean apply(Object o) {
            return true;
        }

        @Override
        public <T> MyPredicate<T> map(MyMapper<T, Object> mapper) {
            return MyPredicate.True();
        }

        @Override
        public MyPredicate<Object> and(MyPredicate<Object> that) {
            if (that == null) {
                return this;
            } else {
                return that;
            }
        }

        @Override
        public MyPredicate<Object> or(MyPredicate<Object> that) {
            return this;
        }

        @Override
        public MyPredicate<Object> negate() {
            return MyPredicate.False();
        }
    };

    private static final MyPredicate<Object> FALSE = new MyPredicate<Object>() {
        @Override
        public boolean apply(Object o) {
            return false;
        }

        @Override
        public <T> MyPredicate<T> map(MyMapper<T, Object> mapper) {
            return MyPredicate.False();
        }

        @Override
        public MyPredicate<Object> and(MyPredicate<Object> that) {
            return this;
        }

        @Override
        public MyPredicate<Object> or(MyPredicate<Object> that) {
            if (that == null) {
                return this;
            } else {
                return that;
            }
        }

        @Override
        public MyPredicate<Object> negate() {
            return MyPredicate.True();
        }
    };

    public static <T, E> MyPredicate<T> cover(final MyPredicate<E> predicate, final MyMapper<T, E> mapper) {
        if (predicate != null) {
            return new MyPredicate<T>() {
                @Override
                public boolean apply(T t) {
                    return predicate.apply(mapper.apply(t));
                }
            };
        }
        return null;
    }

    public static int compare(int lhs, int rhs) {
        return lhs < rhs ? -1 : (lhs == rhs ? 0 : 1);
    }

    public static int compare(long lhs, long rhs) {
        return lhs < rhs ? -1 : (lhs == rhs ? 0 : 1);
    }

    public static boolean equals(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }

}
