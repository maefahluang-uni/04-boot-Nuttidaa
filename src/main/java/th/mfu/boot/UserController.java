package th.mfu.boot;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    public static Map<String, User> users = new HashMap<String, User>();

   @PostMapping("/users")
    public ResponseEntity<String> registerUser( User user) {
      //TODO
      if (users.containsKey(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("User with username '" + user.getUsername() + "' already exists");
        }
        
        // Add user to HashMap
        users.put(user.getUsername(), user);
        
        // Return 201 CREATED with success message
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User '" + user.getUsername() + "' registered successfully");
    
    }

    @GetMapping("/users")
    public ResponseEntity<Collection<User>> list() {
        //TODO
        return ResponseEntity.ok(users.values());
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<User> getUser(String username) {
        //TODO
        User user = users.get(username);
        
        // Check if user exists
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        // Return user with 200 OK status
        return ResponseEntity.ok(user);
    }
    
}
