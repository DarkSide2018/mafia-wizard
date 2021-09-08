import org.junit.Assert
import org.junit.Test

class TestingTakeIf {
    @Test
    fun testing(){
        var testing:String? = "before"
        var afterEmpty:String? = "po"
        testing = afterEmpty?.takeIf { it != "pod"}
        Assert.assertEquals("po",testing)
        testing = afterEmpty?.takeIf { it != "po"}
        Assert.assertNull(testing)
    }
}