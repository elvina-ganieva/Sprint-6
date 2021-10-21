package com.example.mvc


import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class PersonService {

    fun getPersonList(): ConcurrentHashMap<String, Person> = persons

    fun getPerson(id: String): Person? = persons[id]

    fun addPerson(person: Person) {
        person.id = id
        persons[id.toString()] = person
        id++
    }

    fun updatePerson(person: Person, id: String) {
        person.id = id.toInt()
        persons[id] = person
    }

    fun deletePerson(id: String) {
        persons.remove(id)
    }

    companion object {
        private val persons = ConcurrentHashMap<String, Person>()
        private var id = 1
    }
}

val personService = PersonService()
