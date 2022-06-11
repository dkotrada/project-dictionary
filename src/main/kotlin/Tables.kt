import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Projects : Table() {
	val projectTitle: Column<String> = varchar("project_title", 150)
	val projectId: Column<String> = varchar("project_id", 37).primaryKey()
	val wrikeProjectId: Column<Int> = integer("wrike_project_id")
	val gitlabProjectId: Column<Int> = integer("gitlab_project_id")
}

object Dictionary : Table() {
	val id: Column<Int> = integer("id").autoIncrement().primaryKey()

	// References bedeutet in SQL Sprache den "foreign key"
	val projectId: Column<String> = varchar("project_id", 37).references(Projects.projectId)
	val word: Column<String> = varchar("word", 45)
	val autor: Column<String> = varchar("autor", 45)
	val status: Column<String> = varchar("status", 45)
	val type: Column<String> = varchar("type", 45)
	val description: Column<String> = text("description")
	val structure: Column<String> = text("structure")
	val example: Column<String> = text("example")
	val context: Column<String> = varchar("context", 150)
	val synonyms: Column<String> = varchar("synonyms", 150)
	val references: Column<String> = text("references")
}
