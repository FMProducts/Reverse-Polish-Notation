import java.util.*
import kotlin.math.pow


class Mather{
    private val operationPriority = mapOf(
        "(" to 0,
        "+" to 1,
        "-" to 1,
        "*" to 2,
        "/" to 2,
        "^" to 3,
        "~" to 4
    )
    private val symbolsStack = Stack<String>()

    fun toPostfix(expressionString: String): String {
        val postfixExpr = mutableListOf<String>()
        val expression = divideExpression(expressionString)
        expression.forEach { item ->
            if (item.toIntOrNull() != null){
                postfixExpr.add(item)
            }
            else if(item == "("){
                symbolsStack.add(item)
            }
            else if(item == ")"){
                while (symbolsStack.size > 0 && symbolsStack.peek() != "("){
                    postfixExpr.add(symbolsStack.pop())
                }
                symbolsStack.pop()
            }
            else if (operationPriority.containsKey(item)){
                while (symbolsStack.size > 0 &&
                    (operationPriority.getValue(symbolsStack.peek()) >= operationPriority.getValue(item))){
                    postfixExpr.add(symbolsStack.pop())
                }
                symbolsStack.push(item)
            }
        }
        symbolsStack.reversed().forEach {
            postfixExpr.add(it)
        }
        var result = ""
        postfixExpr.forEach {
            result += " $it"
        }
        return result
    }

    fun calculatePostfix(expressionString: String): Double {
        val expression = divideExpression(expressionString)
        val localStack = Stack<Double>()
        expression.forEach { item ->
            if (item.toDoubleOrNull() != null){
                localStack.push(item.toDouble())
            }
            else if(operationPriority.containsKey(item)){
                val second = if(localStack.size > 0) localStack.pop() else 0.0
                val first = if(localStack.size > 0) localStack.pop() else 0.0
                localStack.push(calculate(item , first, second))
            }
        }
        return localStack.pop()
    }

    private fun divideExpression(expression: String) : List<String>{
        val result = mutableListOf<String>()
        val expressionItem = StringBuilder("")
        expression.forEach {
            if (Character.isDigit(it)) expressionItem.append(it)
            else {
                if (expressionItem.isNotEmpty()){
                    result.add(expressionItem.toString())
                    expressionItem.clear()
                }
                if (!Character.isSpaceChar(it)) result.add(it.toString())
            }
        }
        if (expressionItem.isNotEmpty()) result.add(expressionItem.toString())
        return result.toList()
    }
    private fun calculate(operation: String, first: Double, second: Double) : Double {
        return when(operation){
            "+" -> first + second
            "-" -> first - second
            "*" -> first * second
            "/" -> first / second
            "^" -> first.pow(second)
            else -> 0.0
        }
    }
}