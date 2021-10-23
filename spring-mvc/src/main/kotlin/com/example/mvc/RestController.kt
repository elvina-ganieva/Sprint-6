package com.example.mvc

import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class RestController {

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
    fun updatePerson(@RequestBody person: Person, @PathVariable("id") id: String) {
        personService.updatePerson(person, id)
    }

    @DeleteMapping("/{id}/delete")
    fun deletePerson(@PathVariable("id") id: String) {
        personService.deletePerson(id)
    }
}