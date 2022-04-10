fun main(args: Array<String>) {
    val mather = Mather()
    val postfix = mather.toPostfix("15/(7-(1+1))*3-(2+(1+1))*15/(7-(200+1))*3-(2+(1+1))*(15/(7-(1+1))*3-(2+(1+1))+15/(7-(1+1))*3-(2+(1+1)))")
    val result = mather.calculatePostfix(postfix)
    println(result)

}