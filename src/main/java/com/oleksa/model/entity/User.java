package com.oleksa.model.entity;

import java.util.Objects;

public final class User extends AbstractEntity<Integer> {

    private String name;
    private String password;
    private String email;
    private String fullname;
    private UserRole role;

    public User(Integer id, String name, String email, String password, UserRole role, String fullname) {
        super(id);
        setEmail(email);
        setName(name);
        setPassword(password);
        setRole(role);
        this.fullname = fullname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
    	this.name = Objects.requireNonNull(name);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
    	this.password = Objects.requireNonNull(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
    	this.email = Objects.requireNonNull(email);
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
    	this.role = Objects.requireNonNull(role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, role, fullname);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof User))
            return false;
        User other = (User) obj;
        return Objects.equals(id, other.id)
        		&& Objects.equals(name, other.name)
        		&& Objects.equals(email, other.email)
        		&& Objects.equals(password, other.password)
        		&& Objects.equals(fullname, other.fullname)
        		&& role == other.role;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("User [id=").append(id).append(", name=").append(name).append(", password=").append(password)
                .append(", email=").append(email).append(", fullname=").append(fullname).append(", role=").append(role)
                .append("]");
        return builder.toString();
    }
    
    public static final class UserBuilder {
    	
    	private Integer id;
    	private String name;
        private String password;
        private String email;
        private String fullname;
        private UserRole role = UserRole.CLIENT;
        
        public UserBuilder setId(Integer id) {
        	this.id = id;
        	return this;
        }
        
        public UserBuilder setName(String name) {
        	this.name = name;
        	return this;
        }
        
        public UserBuilder setPassword(String pass) {
        	this.password = pass;
        	return this;
        }
        
        public UserBuilder setEmail(String email) {
        	this.email = email;
        	return this;
        }
        
        public UserBuilder setFullname(String fullname) {
        	this.fullname = fullname;
        	return this;
        }
        
        public UserBuilder setRole(UserRole role) {
        	this.role = role;
        	return this;
        }
        
        public User build() {
        	return new User(id, name, email, password, role, fullname);
        }
    }

}
