import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction


fun main() {
	val javalinPort = System.getenv("JAVALIN_PORT")

	transaction(PG.db) {
		SchemaUtils.create(Projects)
		SchemaUtils.create(Dictionary)
	}
	createJavalin().start(javalinPort.toInt())
}
