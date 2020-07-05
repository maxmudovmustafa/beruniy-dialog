package firbase.go.beruniy.view_setup.listener

interface CommandDialogFacade<T> {
    fun getName(value: T): CharSequence
    fun apply(value: T)
}