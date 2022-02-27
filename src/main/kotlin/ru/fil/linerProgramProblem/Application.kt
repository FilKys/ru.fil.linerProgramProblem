package ru.fil.linerProgramProblem

import ru.fil.linerProgramProblem.services.WorkServices

val workServices = WorkServices()

fun main() {
    println("Start application!!")
    workServices.work()
    println("Stop application!!")
}