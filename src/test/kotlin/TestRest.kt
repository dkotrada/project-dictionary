package de.dieterkonrad

import DictEntity
import DictEntityDelete
import DictEntityModify
import ProjectEntityCreate
import ProjectEntityDelete
import ProjectEntityUpdate
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import createJavalin
import io.restassured.RestAssured
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.junit.jupiter.api.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestRest {
	// Erstelle Json aus Kotlin `data class`
	private val mapper = jacksonObjectMapper()
	private val projectId = "51973927-1013-4bae-b6c3-417054e68a63"
	private val entityId = 30

	/**
	 * Project Tests
	 */
	@Test
	fun testReadAllProjects() {
		When {
			get("/read_all_projects")
		} Then {
			statusCode(200)
		}
	}

	@Test
	fun testCreateProject() {
		Given {
			contentType("application/json")
			body(
				mapper.writeValueAsString(
					ProjectEntityCreate("Neues Test", 4567, 3456)
				)
			)
		} When {
			post("/create_project")
		} Then {
			statusCode(200)
		}
	}

	@Test
	fun testUpdateProject() {
		Given {
			contentType("application/json")
			body(
				mapper.writeValueAsString(
					ProjectEntityUpdate(projectId, "Projekt Titel Update Nochmal", 23422, 3455)
				)
			)
		} When {
			post("/update_project")
		} Then {
			statusCode(200)
		}
	}

	//@Test
	fun testDeleteProject() {
		Given {
			contentType("application/json")
			body(
				mapper.writeValueAsString(
					ProjectEntityDelete(
						projectId
					)
				)
			)
		} When {
			delete("/delete_project")
		} Then {
			statusCode(200)
		}
	}

	/**
	 * Dictionary Tests
	 */

	@Test
	fun testCreateDictionaryEntity() {
		Given {
			contentType("application/json")
			body(
				mapper.writeValueAsString(
					DictEntity(
						projectId,
						"Superman",
						"Deiter Konrad",
						"eingereicht",
						"Musterman",
						"Eine natürliche Person, die Bestellungen aufgeben kann",
						"Name, Geburtsdatum, Telefon, Link zu Swagger API",
						"Fred Feuerstein, 25 Juni 1904, 02355 345 789",
						"Kundenverwaltung",
						"Person",
						"Issue 305, Userstory 4, Wrike 34"
					)
				)
			)
		} When {
			post("/create_dictionary_entity")
		} Then {
			statusCode(200)
		}
	}

	//@Test
	fun testDeleteDictionaryEntity() {
		Given {
			contentType("application/json")
			body(
				mapper.writeValueAsString(
					DictEntityDelete(
						projectId,
						19
					)
				)
			)
		} When {
			post("/delete_dictionary_entity")
		} Then {
			statusCode(200)
		}
	}

	@Test
	fun testUpdateDictionaryEntity() {
		Given {
			contentType("application/json")
			body(
				mapper.writeValueAsString(
					DictEntityModify(
						entityId,
						projectId,
						"Superman",
						"Sao Paolo",
						"überprüft",
						"Stadt",
						"Eine natürliche Person, die Bestellungen aufgeben kann",
						"Name, Geburtsdatum, Telefon, Link zu Swagger API",
						"Fred Feuerstein, 25 Juni 1904, 02355 345 789",
						"Kundenverwaltung",
						"Person",
						"Issue 305, Userstory 4, Wrike 34"
					)
				)
			)
		} When {
			post("/update_dictionary_entity")
		} Then {
			statusCode(200)
		}
	}


	@Test
	fun testReadAllDictionaryEntities() {
		When {
			get("/read_all_dictionary_entities")
		} Then {
			statusCode(200)
		}
	}


	// Konfiguration für die Tests
	private val port = 7007
	private val app = createJavalin()

	@BeforeAll
	fun startServer() {
		app.start(port)
	}

	@AfterAll
	fun stopSever() {
		app.stop()
	}

	// Benutze JUnit für die allgemeine Konfiguration
	@BeforeEach
	fun init() {
		RestAssured.port = port
	}
}
