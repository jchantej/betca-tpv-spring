package es.upm.miw;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import es.upm.miw.controllers.AllMiwControllersIntegrationTests;
import es.upm.miw.documents.core.AllMiwDocumentsCoreTests;
import es.upm.miw.repositories.core.AllMiwRepositoriesCoreIntegrationTests;
import es.upm.miw.resources.AllMiwResourcesFunctionalTests;
import es.upm.miw.services.AllMiwServicesIntegrationTests;
import es.upm.miw.utils.AllMiwUtilsTests;

@RunWith(Suite.class)
@SuiteClasses({
    AllMiwDocumentsCoreTests.class,
    AllMiwUtilsTests.class,
    
    AllMiwControllersIntegrationTests.class,
    AllMiwRepositoriesCoreIntegrationTests.class,
    AllMiwServicesIntegrationTests.class,
    
    AllMiwResourcesFunctionalTests.class,
})
public class AllMiwTests {
}
