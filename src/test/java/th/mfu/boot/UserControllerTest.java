package th.mfu.boot;

import th.mfu.boot.UserController;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserControllerTest {

    private UserController controller = new UserController();

    @BeforeEach
    public void setUp() {
        // Clear the static HashMap before each test to ensure test isolation
        UserController.users.clear();
    }

    @Test
    public void testRegisterUser() {
        // Arrange
        User request = new User();
        request.setUsername("john");
        request.setDisplayname("John Doe");
        request.setEmail("john@example.com");

        // Act
        ResponseEntity<String> response = controller.registerUser(request);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode()); // Should be 201 CREATED
        assertTrue(response.getBody().contains("registered successfully"));

        // Verify user was actually stored
        ResponseEntity<User> user = controller.getUser("john");
        assertEquals(HttpStatus.OK, user.getStatusCode());
        assertEquals("john", user.getBody().getUsername());
        assertEquals("john@example.com", user.getBody().getEmail());
    }

    @Test
    public void testRegisterDuplicateUser() {
        // Arrange
        User request1 = new User();
        request1.setUsername("brian");
        request1.setDisplayname("Brian Smith");
        request1.setEmail("brian@example.com");

        User request2 = new User();
        request2.setUsername("brian"); // Same username
        request2.setDisplayname("Brian Jones");
        request2.setEmail("brian2@example.com");

        // Act
        ResponseEntity<String> response1 = controller.registerUser(request1);
        ResponseEntity<String> response2 = controller.registerUser(request2);

        // Assert
        assertEquals(HttpStatus.CREATED, response1.getStatusCode()); // First registration should succeed
        assertTrue(response1.getBody().contains("registered successfully"));

        assertEquals(HttpStatus.CONFLICT, response2.getStatusCode()); // Second should conflict
        assertTrue(response2.getBody().contains("already exists"));
    }

    @Test
    public void testGetUser() {
        // Arrange - Register a user first
        User request1 = new User();
        request1.setUsername("alice");
        request1.setDisplayname("Alice Johnson");
        request1.setEmail("alice@example.com");
        controller.registerUser(request1);

        // Act & Assert - Get existing user
        ResponseEntity<User> user = controller.getUser("alice");
        assertEquals(HttpStatus.OK, user.getStatusCode());
        assertNotNull(user.getBody());
        assertEquals("alice", user.getBody().getUsername());
        assertEquals("alice@example.com", user.getBody().getEmail());

        // Act & Assert - Get unknown user
        ResponseEntity<User> unknownUser = controller.getUser("unknown");
        assertEquals(HttpStatus.NOT_FOUND, unknownUser.getStatusCode());
    }

    @Test
    public void testListUsers() {
        // Arrange - Register multiple users
        User user1 = new User();
        user1.setUsername("user1");
        user1.setDisplayname("User One");
        user1.setEmail("user1@example.com");

        User user2 = new User();
        user2.setUsername("user2");
        user2.setDisplayname("User Two");
        user2.setEmail("user2@example.com");

        controller.registerUser(user1);
        controller.registerUser(user2);

        // Act
        ResponseEntity<Collection<User>> response = controller.list();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testListEmptyUsers() {
        // Act - List users when no users are registered
        ResponseEntity<Collection<User>> response = controller.list();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().size());
    }
}