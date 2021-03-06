package es.upm.miw.documents.core;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Document
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private String mobile;

    private Date registrationDate;

    private String username;

    private String password;

    private Boolean active;

    private String email;

    private String dni;

    private String address;

    private Role[] roles;

    private Token token;

    public User() {
        this.registrationDate = new Date();
        this.active = true;
    }

    public User(String mobile, String username, String password, String dni, String address, String email) {
        this();
        this.mobile = mobile;
        this.username = username;
        this.dni = dni;
        this.address = address;
        this.email = email;
        this.setPassword(password);
        this.roles = new Role[] {Role.CUSTOMER};
    }

    public User(String mobile, String username, String password) {
        this(mobile, username, password, null, null, null);
    }

    public String getId() {
        return id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null) {
            this.password = UUID.randomUUID().toString();
        } else {
            this.password = new BCryptPasswordEncoder().encode(password);
        }
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Role[] getRoles() {
        return roles;
    }

    public void setRoles(Role[] roles) {
        this.roles = roles;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    @Override
    public int hashCode() {
        return this.mobile.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return mobile.equals(((User) obj).mobile);
    }

    @Override
    public String toString() {
        String date = "null";
        if (registrationDate != null) {
            date = new SimpleDateFormat("dd-MMM-yyyy").format(registrationDate.getTime());
        }
        return "User [mobile=" + mobile + ", username=" + username + ", password=" + password + ", active=" + active + ", email=" + email
                + ", dni=" + dni + ", address=" + address + ", registrationDate=" + date + ", roles=" + java.util.Arrays.toString(roles)
                + ", token=" + token + "]";
    }

}
