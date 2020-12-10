package com.example.recycler004

class Person(firstName: String, lastName: String, yearOfBirth: Int) {

    // Similar to Static members
    companion object {

        val item = "Some Value"

    }

    val fullName = "$firstName $lastName"
    var age: Int

    init {
        age = 2018 - yearOfBirth
    }
}


fun main() {

    println("Hello World")

    val names = listOf("Anne", "Peter", "Jeff")
    for (name in names) {
        println(name)
    }
}