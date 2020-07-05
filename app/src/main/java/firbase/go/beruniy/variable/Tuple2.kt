package firbase.go.beruniy.variable

data class Tuple2(
    val first: Any,
    val second: Any
) {

    @Override
    fun stringValue() = "Tuple2($first, $second)"

}