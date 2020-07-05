package firbase.go.beruniy.view_setup;


public class DialogError extends RuntimeException {

    public DialogError() {
    }

    public DialogError(String detailMessage) {
        super(detailMessage);
    }

    public DialogError(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public DialogError(Throwable throwable) {
        super(throwable);
    }

    public static DialogError Unsupported() {
        return new DialogError("Unsupported");
    }

    public static DialogError NullPointer() {
        return new DialogError("NULL pointer");
    }

    public static void checkNull(Object... args) {
        for (Object a : args) {
            if (a == null) {
                throw NullPointer();
            }
        }
    }

    public static DialogError Required() {
        return new DialogError("Required");
    }

}
