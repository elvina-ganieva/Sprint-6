package com.example.mvc


import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*


@Controller
class MvcController {

    @GetMapping("/app/add")
    fun addForm(person: Person): String {
        return "add-form"
    }

    @PostMapping("/app/add")
    fun addPerson(@ModelAttribute("person") person: Person, model: Model): String {
        personService.addPerson(person)
        return "add-result"
    }

    @GetMapping("/app/list")
    fun getPersonList(model: Model): String {
        model.addAttribute("list", personService.getPersonList())
        return "show-all"
    }

    @GetMapping("/app/{id}/view")
    fun getPerson(@PathVariable("id") id: String, model: Model): String {
        model.addAttribute("person", personService.getPerson(id))
        return "view-person"
    }

    @GetMapping("/app/{id}/edit")
    fun updateForm(@PathVariable("id") id: String, model: Model): String {
        model.addAttribute("person", personService.getPerson(id))
        return "edit-form"
    }

    @PostMapping("/app/{id}/edit")
    fun updatePerson(person: Person, @PathVariable("id") id: String): String {
        personService.updatePerson(person, id)
        return "edit-result"
    }

    @GetMapping("/app/{id}/delete")
    fun deletePerson(@PathVariable("id") id: String, model: Model): String {
        model.addAttribute("person", personService.getPerson(id))
        personService.deletePerson(id)
        return "delete-result"
    }
}