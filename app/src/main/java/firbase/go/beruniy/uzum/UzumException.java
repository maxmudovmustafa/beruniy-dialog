package firbase.go.beruniy.uzum;// 14.06.2016

public class UzumException extends RuntimeException {
    public UzumException() {
    }

    public UzumException(String detailMessage) {
        super(detailMessage);
    }

    public UzumException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public UzumException(Throwable throwable) {
        super(throwable);
    }

    public static UzumException Unsupported() {
        return new UzumException("Unsupported");
    }

    public static UzumException NullPointer() {
        return new UzumException("NULL pointer");
    }
}
