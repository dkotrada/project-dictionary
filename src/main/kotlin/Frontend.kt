import io.javalin.http.Context
import kotlinx.html.*
import kotlinx.html.stream.appendHTML

object Frontend {
	fun getRoot(ctx: Context) {
		ctx.html(page)
	}
}

val page = buildString {
	appendHTML().html {
		head {
			title { +"Projectlist" }
			link( rel = "stylesheet", href = "css/tailwind.min.css")
		}
		body {
			div(classes = "max-w-sm mx-auto flex p-6 mt-6 bg-white rounded-lg shadow-xl") {
				div(classes = "flex-shrink-0") {
					img(alt = "ChitChat Logo", classes = "h-12 w-12", src = "/assets/logo.svg")
				}
				div(classes = "ml-6 pt-1") {
					h4(classes = "font-mono text-xl text-gray-900 leading-tight") {
						+"ChitChat"
					}
					p(classes = "text-base text-gray-600 leading-normal") {
						+"You have a new message!"
					}
					a("/") { +"Go Back" }
				}
			}
		}
	}
}
