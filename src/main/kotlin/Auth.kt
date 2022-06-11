import io.javalin.core.security.Role
import io.javalin.http.Context
import io.javalin.http.Handler
import org.mindrot.jbcrypt.BCrypt

enum class ApiRole : Role { USER_READ, USER_WRITE, USER_DELETE }

object Auth {
	fun accessManager(handler: Handler, ctx: Context, permittedRoles: Set<Role>) {
		println(ctx.header("Authorization"))
		if (!ctx.basicAuthCredentialsExist()) {
			ctx.header("WWW-Authenticate", "Basic realm=\"glossar api\"")
			ctx.status(401)
			return
		}

		when {
			ctx.userRoles.any { it in permittedRoles } -> handler.handle(ctx)
			else -> ctx.status(403)
		}
	}

	private val Context.userRoles: Set<ApiRole>
		get() = this.basicAuthCredentials().let { (username, password) ->
			val hashedPassword = users[username] ?: return emptySet()
			val authorized = BCrypt.checkpw(password, hashedPassword)
			if (!authorized) emptySet() else userRoleMap.getOrElse(username) { emptySet() }
		}

	private val users = mapOf(
		"patintern" to "\$2a\$10\$Z4l4D7TIja.M/hbfIX0ivObhr0fcclRyt1kv3cNI9n4QisTe6j/bu",
		"admin" to "\$2a\$10\$b38/h2YKCa6qWKrdJrqQb.HxpM.6z83MR456HpcV2Oz9kqBuZ8voK"
	)
	private val userRoleMap = mapOf(
		"patintern" to setOf(ApiRole.USER_READ, ApiRole.USER_WRITE),
		"admin" to setOf(ApiRole.USER_READ, ApiRole.USER_WRITE, ApiRole.USER_DELETE)
	)
}
