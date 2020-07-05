package firbase.go.beruniy.view_setup.listener

interface CommandFacade<T> : CommandDialogFacade<T> {
    fun getIcon(value: T): Any?
}