import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.filter.ServerFilters
import org.http4k.lens.Query
import org.http4k.lens.string
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Http4kServer
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun FooServer(port: Int) : Http4kServer = FooApp().asServer(Jetty(port))

fun FooApp() : HttpHandler = ServerFilters.CatchLensFailure().then(
    routes(
        "/ping" bind GET to { _ : Request -> Response(OK)},
        "/foo" bind GET to { request ->
            val queryVals = Query.string().multi.defaulted("value", listOf()) (request)
            Response(OK).body("foo" + queryVals.joinToString(""))
        }
    )
)

