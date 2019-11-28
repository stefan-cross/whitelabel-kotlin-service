import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.http4k.client.OkHttp
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Status
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class FooServiceEndToEndTest {

    private val client = OkHttp()
    private val server = FooServer(9090)

    @BeforeEach
    fun setup() {
        server.start()
    }

    @AfterEach
    fun shutdown() {
        server.stop()
    }

    @Test
    fun `foo server returns canned response`() {
        assertThat(client(Request(GET, "http://localhost:9090/ping")).status, equalTo(Status.OK))
    }

}

internal class FooServiceFunctionalTest() {
    private val client = FooApp()

    @Test
    fun `concats query params together`() {
        assertThat(client(Request(GET, "http://localhost:9090/foo?value=bar")).body.toString(), equalTo("foobar"))
    }
}