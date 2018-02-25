package es.upm.miw.repositories.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.documents.core.Token;
import es.upm.miw.documents.core.User;
import es.upm.miw.repositories.core.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class UserRepositoryIT {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @Before
    public void seedDb() {
        this.user = new User("666001000", "666001000", "666001000");
        this.userRepository.save(user);
    }

    @Test
    public void testFindUser() {
        User userBd = userRepository.findByMobile("666001000");
        assertNotNull(userBd);
        assertEquals("666001000", userBd.getUsername());
    }

    @Test
    public void testFindUserNameByMobile() {
        User userBd = userRepository.findUsernameByMobile("666001000");
        assertNotNull(userBd);
        assertEquals("666001000", userBd.getUsername());
        assertNull(userBd.getPassword());
    }

    @Test
    public void testFindByTokenValue() {
        Token token = new Token();
        this.user.setToken(token);
        userRepository.save(this.user);
        assertEquals(user, userRepository.findByTokenValue(token.getValue()));
    }
    
    @Test
    public void testFindCustomerAll() {
        System.out.println(userRepository.findCustomerAll());
    }
    
    @After
    public void delete() {
        this.userRepository.delete(user);
    }

}
