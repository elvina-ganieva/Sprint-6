package com.example.mvc

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class RestController {

    @Autowired
    lateinit var personService: PersonService

    @PostMapping("/add")
    fun addPerson(@RequestBody person: Person) {
        personService.addPerson(person)
    }

    @GetMapping("/list")
    fun getPersonList(
        @RequestParam(name = "name", required = false) name: String?,
        @RequestParam(name = "address", required = false) address: String?
    ): Map<String, Person>? {
        return personService.getPersonList(name, address)
    }

    @GetMapping("/{id}/view")
    fun getPerson(@PathVariable("id") id: String): Person? {
        return personService.getPerson(id)
    }

    @PostMapping("/{id}/edit")
    fun updatePerson(@RequestBody person: Person, @PathVariable("id") id: String): Person? {
        personService.updatePerson(person, id)
        return personService.getPerson(id)
    }

    @DeleteMapping("/{id}/delete")
    fun deletePerson(@PathVariable("id") id: String): Person? {
        return personService.deletePerson(id)
    }
}