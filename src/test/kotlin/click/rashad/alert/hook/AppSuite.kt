package click.rashad.alert.hook

import org.junit.Assert.assertEquals
import org.junit.Test

class AppSuite {

    init {
        println("AppSuite!")
    }

    @Test
    fun printTest() {
        assertEquals("Hello, Alert Hook!", "Hello, Alert Hook!")
    }
}
