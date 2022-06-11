import io.javalin.http.Context
import io.javalin.plugin.openapi.annotations.OpenApi
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.util.*

object ProjectApi {
/* TODO:
	app.get("/read_project/:project_id") {}
	app.delete("/delete_project") {}
	*/

	fun getDocs(ctx: Context) {
		ctx.html("<a href=\"/swagger-ui\">Dokumentation</a>")
	}

	@OpenApi(
		tags = ["Project"]
	)
	fun readAll(ctx: Context) {
		val projectList = arrayListOf<ProjectEntity>()
		transaction(PG.db) {
			Projects.selectAll().forEach {
				projectList.add(
					ProjectEntity(
						it[Projects.projectTitle],
						it[Projects.projectId],
						it[Projects.wrikeProjectId],
						it[Projects.gitlabProjectId]
					)
				)
			}
			ctx.json(projectList)
		}
	}

	@OpenApi(
		tags = ["Project"]
	)
	fun create(ctx: Context) {
		val entity: ProjectEntityCreate = ctx.bodyAsClass(ProjectEntityCreate::class.java)
		transaction(PG.db) {
			Projects.insert { projctx ->
				projctx[projectTitle] = entity.projectTitle
				projctx[projectId] = UUID.randomUUID().toString()
				projctx[wrikeProjectId] = entity.wrikeProjectId
				projctx[gitlabProjectId] = entity.gitlabProjectId
			}
		}
	}


	@OpenApi(
		tags = ["Project"]
	)
	fun update(ctx: Context) {
		val entity: ProjectEntityUpdate = ctx.bodyAsClass(ProjectEntityUpdate::class.java)
		transaction(PG.db) {
			Projects.update({ Projects.projectId eq entity.projectId }) {
				it[projectTitle] = entity.projectTitle
				it[wrikeProjectId] = entity.wrikeProjectId
				it[gitlabProjectId] = entity.gitlabProjectId
			}
		}
	}

	@OpenApi(
		tags = ["Project"]
	)
	fun delete(ctx: Context) {
		val entityToDelete: ProjectEntityDelete = ctx.bodyAsClass(ProjectEntityDelete::class.java)
		transaction(PG.db) {
			Projects.deleteWhere { Projects.projectId eq entityToDelete.projectId }
			Dictionary.deleteWhere { Dictionary.projectId eq entityToDelete.projectId }
		}
	}
}
