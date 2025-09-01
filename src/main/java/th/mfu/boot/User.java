package th.mfu.boot;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    @JsonProperty("username")
    private String username;
    
    @JsonProperty("displayname")
    private String displayname;
    
    @JsonProperty("email")
    private String email;
    public User() {
    }
    
    // Constructor with all parameters
    public User(String username, String displayname, String email) {
        this.username = username;
        this.displayname = displayname;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", displayname='" + displayname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
    
    // equals method (optional but recommended)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return username != null ? username.equals(user.username) : user.username == null;
    }
    
    // hashCode method (optional but recommended)
    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }
}