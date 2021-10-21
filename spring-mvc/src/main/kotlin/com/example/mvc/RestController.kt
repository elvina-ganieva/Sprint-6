package com.example.mvc

import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.ConcurrentHashMap

@RestController
@RequestMapping("/api")
class RestController {

    @PostMapping("/add")
    fun addPerson(@RequestBody person: Person) {
        personService.addPerson(person)
    }

    @GetMapping("/list")
    fun getPersonList(): ConcurrentHashMap<String, Person> = personService.getPersonList()

    @GetMapping("/{id}/view")
    fun getPerson(@PathVariable("id") id: String) = personService.getPerson(id)

    @PostMapping("/{id}/edit")
    fun updatePerson(@RequestBody person: Person, @PathVariable("id") id: String) {
        personService.updatePerson(person, id)
    }

    @DeleteMapping("/{id}/delete")
    fun deletePerson(@PathVariable("id") id: String) {
        personService.deletePerson(id)
    }
}