package firbase.go.beruniy.variable

/**
 * Created by MrShoxruxbek on 05,July,2020
 */
data class Tuple3(
    val first: Any,
    val second: Any,
    val third: Any
) {
    @Override
    fun stringValue() = "Tuple3($first, $second, $third)"

}