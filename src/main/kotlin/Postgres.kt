import org.jetbrains.exposed.sql.Database


object PG {
	// Vielelicht dotenv oder das hier https://medium.com/better-programming/do-you-even-try-functional-error-handling-in-kotlin-ad562b3b394f
	private val host = System.getenv("POSTGRES_HOST")
	private val port = System.getenv("POSTGRES_PORT")
	private val dbName = System.getenv("POSTGRES_DB")
	private val url = "jdbc:postgresql://${host}:${port}/${dbName}"
	private val user = System.getenv("POSTGRES_USER")
	private val password = System.getenv("POSTGRES_PASSWORD")
	val db by lazy {
		Database.connect(
			url = url,
			driver = "org.postgresql.Driver",
			user = user,
			password = password
		)
	}
}
