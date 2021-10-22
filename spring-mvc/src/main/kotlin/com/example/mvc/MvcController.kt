package com.example.mvc

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse


@Controller
@RequestMapping("/app")
class MvcController {

    @GetMapping("/add")
    fun addForm(person: Person, response: HttpServletResponse): String {
        if (response.getHeader("cookie") == "allowed") {
            return "add-form"
        }
        else {
            println("there")
            return "redirect:/login"
        }
    }

    @PostMapping("/add")
    fun addPerson(@ModelAttribute("person") person: Person, model: Model, response: HttpServletResponse): String {
        if (response.getHeader("cookie") == "allowed") {
            personService.addPerson(person)
            return "add-result"
        } else
            return "redirect:/login"
    }

    @GetMapping("/list")
    fun getPersonList(model: Model, response: HttpServletResponse): String {
        if (response.getHeader("cookie") == "allowed") {
            model.addAttribute("list", personService.getPersonList())
            return "show-all"
        } else
            return "redirect:/login"
    }

    @GetMapping("/{id}/view")
    fun getPerson(@PathVariable("id") id: String, model: Model, response: HttpServletResponse): String {
        if (response.getHeader("cookie") == "allowed") {
            model.addAttribute("person", personService.getPerson(id))
            return "view-person"
        } else
            return "redirect:/login"
    }

    @GetMapping("/{id}/edit")
    fun updateForm(@PathVariable("id") id: String, model: Model, response: HttpServletResponse): String {
        if (response.getHeader("cookie") == "allowed") {
            model.addAttribute("person", personService.getPerson(id))
            return "edit-form"
        } else
            return "redirect:/login"
    }

    @PostMapping("/{id}/edit")
    fun updatePerson(@ModelAttribute("person") person: Person, @PathVariable("id") id: String, response: HttpServletResponse): String {
        if (response.getHeader("cookie") == "allowed") {
            personService.updatePerson(person, id)
            return "edit-result"
        } else
            return "redirect:/login"
    }

    @GetMapping("/{id}/delete")
    fun deletePerson(@PathVariable("id") id: String, model: Model, response: HttpServletResponse): String {
        if (response.getHeader("cookie") == "allowed") {
            model.addAttribute("person", personService.getPerson(id))
            personService.deletePerson(id)
            return "delete-result"
        } else
            return "redirect:/login"
    }
}