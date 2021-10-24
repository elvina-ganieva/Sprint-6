package com.example.pleasework

import com.example.pleasework.dao.Person
import com.example.pleasework.services.PersonService
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestControllerTest {
    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    lateinit var personService: PersonService

    private val headers = HttpHeaders()


    @BeforeAll
    fun setUp() {
        headers.add("Cookie", getCookie())
        personService.addPerson(Person("Elvina", "Kazan"))
        personService.addPerson(Person("Till", "Berlin"))
    }

    private fun getCookie(): String? {
        val map: MultiValueMap<String, String> = LinkedMultiValueMap()
        map.set("username", "12345")
        map.set("password", "12345")
        val resp: ResponseEntity<String> = restTemplate
            .postForEntity("http://localhost:${port}/login",
            HttpEntity(map, headers), String::class.java)
        return resp.headers["Set-Cookie"]!![0]
    }

    private fun url(s: String): String {
        return "http://localhost:${port}/api/${s}"
    }

    @Test
    fun `should add a person`() {
        //        restTemplate.postForObject(url("add"), HttpEntity(Person("Christoph", "Berlin"), headers), Person::class.java)
        restTemplate.exchange(url("add"),
            HttpMethod.POST, HttpEntity<Person>(Person("Christoph", "Berlin"), headers),
            Person::class.java)
        Assertions.assertEquals("Christoph", personService.getPerson("3")!!.name)
    }

    @Test
    fun `should get persons list`() {
        val resp = restTemplate.exchange(url("list"), HttpMethod.GET, HttpEntity(null, headers),
            object : ParameterizedTypeReference<Map<String, Person>>() {})
        val respBody = resp.body
        Assertions.assertEquals("Elvina", respBody?.get("1")!!.name)
    }

    @Test
    fun `should view a person`() {
        val resp: ResponseEntity<Person> = restTemplate.exchange(url("1/view"),
        HttpMethod.GET, HttpEntity<Person>(null, headers),
            Person::class.java)
        Assertions.assertEquals("Elvina", resp.body!!.name)
    }

    @Test
    fun `should update a person`() {
        val id = 1
        val entity = HttpEntity<Person>(Person("Elvina", "Moscow"), headers)
        val resp = restTemplate.exchange(
            url("${id}/edit"), HttpMethod.POST,
            entity, Person::class.java, id
        )
        Assertions.assertEquals("Moscow", resp.body!!.address)
    }

    @Test
    fun `should delete a person`() {
        val id = 1
        val size = personService.getPersonList(null, null).size
        val resp = restTemplate.exchange(
            url("${id}/delete"),
            HttpMethod.DELETE, HttpEntity(null, headers), Person::class.java, id
        )
        Assertions.assertTrue(resp.body!!.name == "Elvina")
        Assertions.assertTrue(size - personService.getPersonList(null, null).size == 1)
    }
}