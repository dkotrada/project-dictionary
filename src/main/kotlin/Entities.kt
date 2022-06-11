data class ProjectEntity(
	val projectTitle: String,
	val projectId: String,
	val wrikeProjectId: Int,
	val gitlabProjectId: Int
)

data class ProjectEntityCreate(
	val projectTitle: String,
	val wrikeProjectId: Int,
	val gitlabProjectId: Int
)

data class ProjectEntityUpdate(
	val projectId: String,
	val projectTitle: String,
	val wrikeProjectId: Int,
	val gitlabProjectId: Int
)

data class ProjectEntityDelete(
	val projectId: String
)

data class DictEntityDelete(
	val projectId: String,
	val id: Int
)

data class DictEntity(
	val projectId: String,
	val word: String,
	val autor: String,
	val status: String,
	val type: String,
	val description: String,
	val structure: String,
	val example: String,
	val context: String,
	val synonyms: String,
	val references: String
)

data class DictEntityModify(
	val id: Int,
	val projectId: String,
	val word: String,
	val autor: String,
	val status: String,
	val type: String,
	val description: String,
	val structure: String,
	val example: String,
	val context: String,
	val synonyms: String,
	val references: String
)
