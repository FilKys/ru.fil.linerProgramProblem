package ru.fil.linerProgramProblem


class WorkServices : PrintServices() {

    private var countX: Int? = null
    private var countConfines: Int? = null
    private val allConfines: MutableMap<MutableMap<Int, Int>, Pair<String, Int>> = mutableMapOf()
    private var function = Function()

    fun work() {
        var pointMenu: Int? = null
        while (pointMenu != EMenu.EXIT.point) {
            printMenu()
            pointMenu = readlnOrNull()?.toIntOrNull()
            if (pointMenu == EMenu.START.point) {
                initCountX()
                initCountConfines()
                initConfines()
                initFunction()

                printFunction(function)
                printlnStr("Confines:")
                allConfines.forEach { (x, y) ->
                    printConfines(x, y)
                }
            } else {
                printlnStr("Введите сущесствущий пункт меню")
            }
        }
        printlnStr("Завершение приложения")
    }

    private fun initFunction() {
        function.xMap = initFactorsForX()

        var const: Int? = null
        while (const == null) {
            printStr("Введите С: ")
            const = readlnOrNull()?.toIntOrNull()
        }
        function.c = const

        var extr: String? = null
        while (extr == null) {
            printStr("Введите Extr (min or max): ")
            extr = readlnOrNull()?.trim()?.lowercase()
            extr = if (extr == ETypeExtr.MAX.name.lowercase() || extr == ETypeExtr.MIN.name.lowercase()) extr
            else null
        }
        function.extr = extr
    }

    private fun initConfines() {
        var iterConfines = 0
        while (iterConfines < countConfines!!) {
            allConfines[initFactorsForX()] = initInequality()
            iterConfines++
        }
        initInequalityForX()
    }

    private fun initInequalityForX() {
        var iterIndex = 1
        while (iterIndex <= countX!!) {
            var inequality: String? = null
            while (inequality == null) {
                printStr("Орграничения для X$iterIndex (<= 0 или >= 0) (Пример: >=): ")
                inequality = readlnOrNull()?.trim()
                inequality = if (inequality == ETypeInequality.LESS_OR_EQUALS.inequality ||
                    inequality == ETypeInequality.MORE_OR_EQUALS.inequality
                ) inequality
                else null
            }
            allConfines[mutableMapOf(Pair(iterIndex, 1))] = Pair(inequality, 0)
            iterIndex++
        }

    }

    private fun initInequality(): Pair<String, Int> {
        var inequality: Pair<String, Int>? = null
        while (inequality == null) {
            printStr("Введите (<=,>=,=) и число для этого неравенства (Пример: <= 4): ")
            inequality = validData(readlnOrNull()?.trim())
        }
        return inequality
    }

    private fun validData(inputData: String?): Pair<String, Int>? =
        if (inputData == null) {
            null
        } else {
            val parseInput = inputData.split(" ")
            if (parseInput.size < 2 || parseInput.size > 2) {
                null
            } else {
                var inequality: String? = null
                ETypeInequality.values().forEach { eType ->
                    if (eType.inequality == parseInput[0]) {
                        inequality = eType.inequality
                        return@forEach
                    }
                }
                val value = parseInput[1].toIntOrNull()
                if (inequality != null && value != null) {
                    Pair(inequality!!, value)
                } else {
                    null
                }
            }
        }

    private fun initFactorsForX(): MutableMap<Int, Int> {
        var factor: Int? = null
        var index = 1
        val confinesX: MutableMap<Int, Int> = mutableMapOf()
        while (index <= countX!!) {
            while (factor == null) {
                printStr("Введите множитель для X$index: ")
                factor = readlnOrNull()?.toIntOrNull()
            }
            confinesX[index] = factor
            index++
            factor = null
        }
        return confinesX
    }

    private fun initCountConfines() {
        while (countConfines == null || countConfines!! < 0) {
            printStr("Кол-во ограничений: ")
            countConfines = readlnOrNull()?.toIntOrNull()
        }
    }

    private fun initCountX() {
        while (countX == null || countX!! < 0) {
            printStr("Кол-во переменных: ")
            countX = readlnOrNull()?.toIntOrNull()
        }
    }


}

data class Function(
    var xMap: MutableMap<Int, Int>? = null,
    var c: Int? = null,
    var extr: String? = null
)

enum class ETypeExtr {
    MIN,
    MAX
}

enum class ETypeInequality(val inequality: String) {
    EQUALS("="),
    MORE_OR_EQUALS(">="),
    LESS_OR_EQUALS("<=")
}