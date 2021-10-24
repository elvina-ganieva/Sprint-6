package com.example.mvc


import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service()
class PersonService {

    fun getPersonList(name: String?, address: String?): Map<String, Person> {
        if (name != null && address != null)
            return persons.filter { name == it.value.name && address == it.value.address }
        else if (name == null && address != null)
            return persons.filter { address == it.value.address }
        else if (name != null && address == null)
            return persons.filter { name == it.value.name }
        else
            return persons.toMap()
    }


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

    fun deletePerson(id: String): Person? {
        return persons.remove(id)
    }

    companion object {
        private val persons = ConcurrentHashMap<String, Person>()
        private var id = 1
    }
}
