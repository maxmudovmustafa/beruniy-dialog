package firbase.go.beruniy.variable

/**
 * Created by MrShoxruxbek on 05,July,2020
 */
data class Tuple4(
    val first: Any,
    val second: Any,
    val third: Any,
    val fourth: Any
) {
    @Override
    fun stringValue() = "Tuple4($first, $second, $third, $fourth)"

}