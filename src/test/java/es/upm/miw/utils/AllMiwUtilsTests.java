package es.upm.miw.utils;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    BarcodeTest.class,
    EncryptingTest.class
})
public class AllMiwUtilsTests {

}
