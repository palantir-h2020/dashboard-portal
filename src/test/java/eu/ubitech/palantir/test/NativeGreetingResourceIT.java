package eu.ubitech.palantir.test;

import io.quarkus.test.junit.NativeImageTest;
import org.junit.jupiter.api.Disabled;

@Disabled
@NativeImageTest
public class NativeGreetingResourceIT extends GreetingResourceTest {

  // Execute the same tests but in native mode.
}
