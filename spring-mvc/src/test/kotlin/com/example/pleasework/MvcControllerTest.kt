package com.example.pleasework

import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
class MvcControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc


    @BeforeEach
    fun setUp() {
        mockMvc.perform(
            post("/app/add")
                .param("name", "Elvina")
                .param("address", "Kazan")
        )
        mockMvc.perform(
            post("/app/add")
                .param("name", "Till")
                .param("address", "Berlin")
        )
    }

    @Test
    fun `should add a person`() {
        mockMvc.perform(get("/app/add"))
            .andExpect(status().isOk)
            .andExpect(view().name("add-form"))
        mockMvc.perform(
            post("/app/add")
                .param("name", "Christoph")
                .param("address", "Berlin")
        )
            .andExpect(status().isOk)
            .andExpect(view().name("add-result"))
            .andExpect(model().attributeExists("person"))
            .andExpect(content().string(containsString("Christoph")))

    }

    @Test
    fun `should edit a person`() {
        mockMvc.perform(
            post("/app/1/edit")
                .param("name", "Elvina")
                .param("address", "Moscow")
        )
            .andExpect(status().isOk)
            .andExpect(view().name("edit-result"))
            .andExpect(model().attributeExists("person"))
            .andExpect(content().string(containsString("Moscow")))
    }


    @Test
    fun `should get persons list`() {
        mockMvc.perform(get("/app/list"))
            .andExpect(status().isOk)
            .andExpect(view().name("show-all"))
            .andExpect(model().attributeExists("list"))
            .andExpect(content().string(containsString("Elvina")))
    }

    @Test
    fun `should view a person`() {
        mockMvc.perform(get("/app/2/view"))
            .andExpect(status().isOk)
            .andExpect(view().name("view-person"))
            .andExpect(model().attributeExists("person"))
            .andExpect(content().string(containsString("Till")))
    }

    @Test
    fun `should delete a person`() {
        mockMvc.perform(get("/app/1/delete"))
            .andExpect(status().isOk)
            .andExpect(view().name("delete-result"))
    }
}