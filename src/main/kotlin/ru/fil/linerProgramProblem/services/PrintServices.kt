package ru.fil.linerProgramProblem.services

open class PrintServices {

    fun printStr(str: String) = print(str)

    fun printlnStr(str: String) = println(str)

    fun printFunction(function: Function) {
        printlnStr("Function:")
        var factorsAndX = buildEquation(function.xMap!!)
        if (factorsAndX.first() == '+') {
            factorsAndX = factorsAndX.removeRange(0, 1)
        }
        val factorsC = if (function.c!! > 0) "+" else ""
        println("${factorsAndX.replace("1*", "")}$factorsC${function.c} -> ${function.extr}")
    }

    fun printConfines(factorsX: MutableMap<Int, Int>, inequality: Pair<String, Int>) {
        var factorsAndX = buildEquation(factorsX)
        if (factorsAndX.first() == '+') {
            factorsAndX = factorsAndX.removeRange(0, 1)
        }
        printlnStr("${factorsAndX.replace("1*", "")} ${inequality.first} ${inequality.second}")
    }

    private fun buildEquation(factorsX: MutableMap<Int, Int>): String {
        var factorsAndX = ""
        factorsX.forEach { (index, value) ->
            factorsAndX = if (value > 0) {
                "$factorsAndX+$value"
            } else if (value < 0) {
                "$factorsAndX$value"
            } else {
                factorsAndX
            }
            factorsAndX = "${factorsAndX}*X$index"
        }
        return factorsAndX
    }

    fun printMenu() {
        printlnStr("${EMenu.START.point}. ${EMenu.START.description}")
        printlnStr("${EMenu.EXIT.point}. ${EMenu.EXIT.description}")
        printStr("Введите пункт меню: ")
    }
}

enum class EMenu(val point: Int, val description: String) {
    START(1, "Начать вычисления"),
    EXIT(0, "Выход")
}