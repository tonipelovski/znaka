package InterpretatorTests;

import com.znaka.Exceptions.EvaluatorException;
import com.znaka.Interpretator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class InterpretatorTests {
    @Disabled
    @Test
    public void BasicTest() throws IOException {
        Interpretator interpretator = new Interpretator("TestResources/General/ErrorFile.zk", false);
        Assertions.assertThrows(EvaluatorException.class, interpretator::run);
        interpretator.run();
        Assertions.assertEquals(1,1);
    }

    @Disabled
    @Test
    public void CustomTest() throws IOException {
        Interpretator interpretator = new Interpretator("TestResources/General/CustomFile.zk", false);
        Assertions.assertThrows(EvaluatorException.class, interpretator::run);
        interpretator.run();
        Assertions.assertEquals(1,1);
    }
}
