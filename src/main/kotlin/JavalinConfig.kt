import cc.vileda.openapi.dsl.info
import cc.vileda.openapi.dsl.openapiDsl
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder
import io.javalin.core.security.SecurityUtil
import io.javalin.plugin.openapi.OpenApiOptions
import io.javalin.plugin.openapi.OpenApiPlugin
import io.javalin.plugin.openapi.ui.SwaggerOptions

data class ErrorResponse(
	val title: String,
	val status: Int,
	val type: String,
	val details: Map<String, String>?
)

fun createJavalin(): Javalin {
	val app = Javalin.create {
		it.registerPlugin(configureOpenApiPlugin())
		it.defaultContentType = "application/json"
		it.enableCorsForAllOrigins()
		it.accessManager(Auth::accessManager)
		it.addStaticFiles("public")

	}

	// ApiBuilder
	// Später könnte das benutzt werden: https://javalin.io/documentation#handler-groups
	app.routes {
		ApiBuilder.path("/") {
			ApiBuilder.get(Frontend::getRoot, SecurityUtil.roles(ApiRole.USER_READ))
		}
		ApiBuilder.path("/docs") {
			ApiBuilder.get(ProjectApi::getDocs, SecurityUtil.roles(ApiRole.USER_READ))
		}
		ApiBuilder.path("/read_all_projects") {
			ApiBuilder.get(ProjectApi::readAll, SecurityUtil.roles(ApiRole.USER_READ))
		}
		ApiBuilder.path("/create_project") {
			ApiBuilder.post(ProjectApi::create, SecurityUtil.roles(ApiRole.USER_WRITE))
		}
		ApiBuilder.path("/update_project") {
			ApiBuilder.post(ProjectApi::update, SecurityUtil.roles(ApiRole.USER_WRITE))
		}
		ApiBuilder.path("/delete_project") {
			ApiBuilder.delete(ProjectApi::delete, SecurityUtil.roles(ApiRole.USER_DELETE))
		}
		ApiBuilder.path("/create_dictionary_entity") {
			ApiBuilder.post(DictionaryApi::create, SecurityUtil.roles(ApiRole.USER_WRITE))
		}
		ApiBuilder.path("/update_dictionary_entity") {
			ApiBuilder.post(DictionaryApi::update, SecurityUtil.roles(ApiRole.USER_WRITE))
		}
		ApiBuilder.path("/read_dictionary_entity") {
			ApiBuilder.get(DictionaryApi::read, SecurityUtil.roles(ApiRole.USER_READ))
		}
		ApiBuilder.path("/delete_dictionary_entity") {
			ApiBuilder.delete(DictionaryApi::delete, SecurityUtil.roles(ApiRole.USER_DELETE))
		}
		ApiBuilder.path("/read_all_dictionary_entities") {
			ApiBuilder.get(DictionaryApi::readAll, SecurityUtil.roles(ApiRole.USER_READ))
		}
	}
	return app
}

fun configureOpenApiPlugin() = OpenApiPlugin(
	OpenApiOptions {
		openapiDsl {
			info {
				title = "Dictionary API"
				description = "Glossar für Projekte bei Pat Adams"
				version = "0.0.0"
			}
		}
	}.apply {
		path("/swagger-docs") // endpoint for OpenAPI json
		swagger(SwaggerOptions("/swagger-ui")) // endpoint for swagger-ui
		defaultDocumentation { doc ->
			// Standartrückgabewert wenn etwas schief gelaufen ist.
			doc.json("404", ErrorResponse::class.java)
		}
	}
)
