package com.example.mvc

import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.ConcurrentHashMap
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/api")
class RestController {

    @PostMapping("/add")
    fun addPerson(@RequestBody person: Person) {
        personService.addPerson(person)
    }

    @GetMapping("/list")
    fun getPersonList(response: HttpServletResponse,
                      @RequestParam(required = false) name: String?,
                      @RequestParam(required = false) address: String?): Map<String, Person>? {
        if (response.getHeader("cookie") == "allowed") {
            return personService.getPersonList(name, address)
        } else
            return null
    }

    @GetMapping("/{id}/view")
    fun getPerson(@PathVariable("id") id: String, response: HttpServletResponse): Person? {
        if (response.getHeader("cookie") == "allowed") {
            return personService.getPerson(id)
        } else
            return null
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