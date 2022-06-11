import io.javalin.http.Context
import io.javalin.plugin.openapi.annotations.OpenApi
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

object DictionaryApi {

	/**
	 * TODO: übrigen requests

	// Lese nur die Entities die zum Projekt gehören.
	app.get("/read_project_dictionary_entities") {}

	app.delete("/delete_dictionary_entity") {}
	 */
	@OpenApi(
		tags = ["Dictionary"]
	)
	fun create(ctx: Context) {
		val entity: DictEntity = ctx.bodyAsClass(DictEntity::class.java)
		transaction(PG.db) {
			Dictionary.insert { dict ->
				dict[projectId] = entity.projectId
				dict[word] = entity.word
				dict[autor] = entity.autor
				dict[status] = entity.status
				dict[type] = entity.type
				dict[description] = entity.description
				dict[structure] = entity.structure
				dict[example] = entity.example
				dict[context] = entity.context
				dict[synonyms] = entity.synonyms
				dict[references] = entity.references
			}
		}
	}


	@OpenApi(
		tags = ["Dictionary"]
	)
	fun update(ctx: Context) {
		val entity: DictEntityModify = ctx.bodyAsClass(DictEntityModify::class.java)
		transaction(PG.db) {
			Dictionary.update({ Dictionary.id eq entity.id }) {
				it[id] = entity.id
				it[projectId] = entity.projectId
				it[word] = entity.word
				it[autor] = entity.autor
				it[status] = entity.status
				it[type] = entity.type
				it[description] = entity.description
				it[structure] = entity.structure
				it[example] = entity.example
				it[context] = entity.context
				it[synonyms] = entity.synonyms
				it[references] = entity.references
			}
		}
	}

	@OpenApi(
		tags = ["Dictionary"]
	)
	fun read(ctx: Context) {
		TODO("Noch nicht nötig")
	}

	@OpenApi(
		tags = ["Dictionary"]
	)
	fun delete(ctx: Context) {
		val entityToDelete: DictEntityDelete = ctx.bodyAsClass(DictEntityDelete::class.java)
		transaction(PG.db) {
			Dictionary.deleteWhere { Dictionary.id eq entityToDelete.id }
		}
		ctx.json(entityToDelete.id)
	}

	@OpenApi(
		tags = ["Dictionary"]
	)
	fun readAll(ctx: Context) {
		val dictList = arrayListOf<DictEntityModify>()
		transaction(PG.db) {
			Dictionary.selectAll().forEach {
				dictList.add(
					DictEntityModify(
						it[Dictionary.id],
						it[Dictionary.projectId],
						it[Dictionary.word],
						it[Dictionary.autor],
						it[Dictionary.status],
						it[Dictionary.type],
						it[Dictionary.description],
						it[Dictionary.structure],
						it[Dictionary.example],
						it[Dictionary.context],
						it[Dictionary.synonyms],
						it[Dictionary.references]
					)
				)
				ctx.json(dictList)
			}
		}
	}
}
