package ru.fil.linerProgramProblem

import java.io.File


class WorkServices {

    fun work() {
        val file =
            File("/Users/filippkusakin/IdeaProjects/linerProgramProblem/src/main/resources/test3.csv").inputStream()
        val data: ArrayList<List<Int>> = arrayListOf()
        file.bufferedReader().lines().forEach { line ->
            data.add(line.split(";").map { it.toInt() })
        }
        printInputData(data)
        calculateData(data)
    }

    private fun calculateData(data: ArrayList<List<Int>>) {
        val dataPromezList = mutableListOf<DataPromez>()
        val dataPromez = DataPromez(data[0])
        var indexData = 2
        var maxData = getMaxData(data[1], data[indexData])
        dataPromez.dataX = maxData.map {
            data[0][it.first]
        }
        dataPromez.maxF = maxData.map { it.second!! }
        dataPromezList.add(dataPromez)
        indexData++
        var newDataPromez: DataPromez
        while (indexData < data.size - 1) {
            newDataPromez = DataPromez(data[0])
            maxData = getMaxData(dataPromez.maxF!!, data[indexData])
            newDataPromez.dataX = maxData.map {
                data[0][it.first]
            }
            newDataPromez.maxF = maxData.map { it.second!! }
            dataPromezList.add(newDataPromez)
            indexData++
        }
        var indexData2 = 2
        dataPromezList.forEach {
            printPromex(it, indexData2)
            indexData2 += 1
        }
        val max = lastCalculate(dataPromezList[dataPromezList.size - 1].maxF!!, data[indexData])
        printResult(dataPromezList, max, data[0], data.size - 1)
    }

    private fun printResult(
        dataPromezList: MutableList<DataPromez>,
        max: Pair<Int, Int?>,
        epsil: List<Int>,
        iter: Int
    ) {
        var iterX = iter
        var result = epsil[epsil.size - 1]
        println("---------------Result !---------------")
        println("Zmax = ${max.second}")
        println("X*$iterX($result) = ${epsil[max.first - 1]}")
        result -= epsil[max.first - 1]
        iterX--
        dataPromezList.reversed().forEach { dataPromez ->
            val s = dataPromez.dataX!![dataPromez.epsil.indexOf(result)]
            println("X*$iterX($result) = $s")
            result -= s
            iterX--
        }
        println("X*$iterX($result) = $result")
    }

    private fun lastCalculate(maxF: List<Int>, lastX: List<Int>): Pair<Int, Int?> {
        var iterLastX = lastX.size
        val res = maxF.map { it ->
            iterLastX--
            it + lastX[iterLastX]
        }
        return Pair(res.indexOf(res.maxOrNull()), res.maxOrNull())
    }

    private fun printPromex(dataPromez: DataPromez, indexData: Int) {
        println("---------------Calculate ${indexData - 1}---------------")
        println("epsilon = ${dataPromez.epsil}")
        println("F(X${indexData}) = ${dataPromez.maxF}")
        println("X${indexData}* = ${dataPromez.dataX}")
    }

    private fun getMaxData(firstList: List<Int>, secondList: List<Int>): List<Pair<Int, Int?>> {
        val data: ArrayList<List<Int>> = arrayListOf()
        var iter = 1
        while (iter <= firstList.size) {
            val fData = firstList.subList(0, iter)
            val sData = secondList.subList(0, iter)
            var fIter = fData.size
            data.add(sData.map { it ->
                fIter--
                fData[fIter] + it
            })
            iter++
        }
        return data.map {
            val maxValue = it.maxOrNull()
            Pair(it.indexOf(maxValue), maxValue)
        }

    }

    private fun printInputData(dataList: ArrayList<List<Int>>) {
        dataList.forEach {
            it.forEach { data ->
                print("$data ")
            }
            println()
        }
    }

}

data class DataPromez(
    var epsil: List<Int>,
    var maxF: List<Int>? = null,
    var dataX: List<Int>? = null
)