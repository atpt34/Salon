package com.oleksa.model.entity;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.oleksa.model.entity.User.UserBuilder;

public class UserTest {

	public static final User USER 
		= new User(1, "oleksii", "atpt34@gmail.com", "123456", UserRole.ADMIN, "Oleksa T. V.");
	public static final User SAME_USER
		= new User(1, "oleksii", "atpt34@gmail.com", "123456", UserRole.ADMIN, "Oleksa T. V.");
	public static final User THIRD_SAME_USER
		= new User(1, "oleksii", "atpt34@gmail.com", "123456", UserRole.ADMIN, "Oleksa T. V.");
	public static final User USER_NULLS
		= new User(null, "oleksii", "atpt34@gmail.com", "123456", UserRole.ADMIN, null);
	public static final User SAME_USER_NULLS
		= new User(null, "oleksii", "atpt34@gmail.com", "123456", UserRole.ADMIN, null);
	public static final User THIRD_SAME_USER_NULLS
	= new User(null, "oleksii", "atpt34@gmail.com", "123456", UserRole.ADMIN, null);
	public static final User OTHER_USER_ID
		= new User(2, "oleksa", "atpt34@gmail.com", "123456", UserRole.ADMIN, "Oleksa T. V.");
	public static final User OTHER_USER_ID2
		= new User(null, "oleksa", "atpt34@gmail.com", "123456", UserRole.ADMIN, "Oleksa T. V.");
	public static final User OTHER_USER_NAME
		= new User(null, "oleksa", "atpt34@gmail.com", "123456", UserRole.ADMIN, "Oleksa T. V.");
	public static final User OTHER_USER_EMAIL
		= new User(null, "oleksa", "atpt34@gmail.com", "123456", UserRole.ADMIN, "Oleksa T. V.");
	public static final User OTHER_USER_PASS
		= new User(null, "oleksa", "atpt34@gmail.com", "12345", UserRole.ADMIN, "Oleksa T. V.");
	public static final User OTHER_USER_ROLE
		= new User(null, "oleksa", "atpt34@gmail.com", "123456", UserRole.MASTER, "Oleksa T. V.");
	public static final User OTHER_USER_FULLNAME
		= new User(null, "oleksa", "atpt34@gmail.com", "123456", UserRole.ADMIN, "Oleksa O. V.");
	public static final User OTHER_USER_FULLNAME2
		= new User(null, "oleksa", "atpt34@gmail.com", "123456", UserRole.ADMIN, null);
	public static final User OTHER_USER
		= new User(null, "maxim", "maxim2018@gmail.com", "654321", UserRole.CLIENT, "Maximets N. V.");
	public static final User NULL_USER = null;
	
	@Before
    public void setUp() {
		
	}
	
	@Test
    public void testContract() {
		assertTrue(USER.equals(SAME_USER) && USER.hashCode() == SAME_USER.hashCode());
		assertTrue(USER_NULLS.equals(SAME_USER_NULLS) && USER_NULLS.hashCode() == SAME_USER_NULLS.hashCode());
	}
	
	@Test
    public void testNotEqualsOtherUsers() {
        assertFalse(USER.equals(OTHER_USER));
        assertFalse(USER.equals(USER_NULLS));
    }
	
	@Test
    public void testNotEqualsOtherId() {
        assertFalse(USER.equals(OTHER_USER_ID));
        assertFalse(USER.equals(OTHER_USER_ID2));
    }
	
	@Test
    public void testNotEqualsOtherName() {
        assertFalse(USER.equals(OTHER_USER_NAME));
    }
	
	@Test
    public void testNotEqualsOtherEmail() {
        assertFalse(USER.equals(OTHER_USER_EMAIL));
    }
	
	@Test
    public void testNotEqualsOtherPassword() {
        assertFalse(USER.equals(OTHER_USER_PASS));
    }
	
	@Test
    public void testNotEqualsOtherRole() {
        assertFalse(USER.equals(OTHER_USER_ROLE));
    }
	
	@Test
    public void testNotEqualsOtherFullname() {
        assertFalse(USER.equals(OTHER_USER_FULLNAME));
        assertFalse(USER.equals(OTHER_USER_FULLNAME2));
    }
	
	@Test
    public void testEqualsNull() {
		assertFalse(USER.equals(NULL_USER));
		assertFalse(USER.equals(null));
		assertFalse(SAME_USER.equals(null));
		assertFalse(OTHER_USER.equals(null));
	}
	
	@Test
    public void testEqualsReflexive() {
		assertTrue(USER.equals(USER));
        assertTrue(SAME_USER.equals(SAME_USER));
        assertTrue(OTHER_USER.equals(OTHER_USER));
	}
	
	@Test
	public void testEqualsSymmetry() {
        assertTrue(USER.equals(SAME_USER));
        assertTrue(SAME_USER.equals(USER));
    }
	
	@Test
	public void testEqualsTransitivity() {
        assertTrue(USER.equals(SAME_USER) && SAME_USER.equals(THIRD_SAME_USER) && USER.equals(THIRD_SAME_USER));
        assertTrue(THIRD_SAME_USER.equals(USER) && THIRD_SAME_USER.equals(SAME_USER) && SAME_USER.equals(USER));
        assertTrue(USER_NULLS.equals(SAME_USER_NULLS) && SAME_USER_NULLS.equals(THIRD_SAME_USER_NULLS)
        		&& USER_NULLS.equals(THIRD_SAME_USER_NULLS));
        assertTrue(THIRD_SAME_USER_NULLS.equals(USER_NULLS) && THIRD_SAME_USER_NULLS.equals(SAME_USER_NULLS)
        		&& SAME_USER_NULLS.equals(USER_NULLS));
    }
	
	@Test
    public void testJavaContractHashCodeConsistency() {
        int hashCode = USER.hashCode();
        String getName = USER.getName();
        String email = USER.getEmail();
        Object fullname = USER.getFullname();
        Integer id = USER.getId();
        String password = USER.getPassword();
        UserRole role = USER.getRole();
        int newHashCode = USER.hashCode();
        assertTrue(hashCode == newHashCode);
    }
	
	@Test
    public void testJavaContractEqualsConsistency() {
		assertFalse(USER.equals(OTHER_USER));
		String getName = USER.getName();
        String email = USER.getEmail();
        Object fullname = USER.getFullname();
        Integer id = USER.getId();
        String password = USER.getPassword();
        UserRole role = USER.getRole();
        int newHashCode = USER.hashCode();
		assertFalse(USER.equals(OTHER_USER));
	}
	
	@Test (expected = NullPointerException.class)
	public void testNullName() {
		User user = new User(null, null, "atpt34@gmail.com", "123456", UserRole.ADMIN, "Oleksa T. V.");
		fail();
	}
	
	@Test (expected = NullPointerException.class)
	public void testNullEmail() {
		User user = new User(null, "oleksa", null, "123456", UserRole.ADMIN, "Oleksa T. V.");
		fail();
	}
	
	@Test (expected = NullPointerException.class)
	public void testNullPassword() {
		User user
		= new User(null, "oleksii", "atpt34@gmail.com", null, UserRole.ADMIN, "Oleksa T. V.");
		fail();
	}
	
	@Test (expected = NullPointerException.class)
	public void testNullRole() {
		User user 
		= new User(null, "oleksii", "atpt34@gmail.com", "123456", null, "Oleksa T. V.");
		fail();
	}
    
	@Test
	public void testUserBuilder() {
		Integer id = null;
		String name = "lipovets";
		String email = "hello@meta.ua";
		String password = "1234";
		UserRole role = UserRole.MASTER;
		String fullname = "Lipovets F. A.";
		User user = new User(id, name, email, password, role, fullname);
		User built = new UserBuilder().setId(id)
				.setName(name).setEmail(email).setPassword(password)
				.setRole(role).setFullname(fullname).build();
		assertEquals(user, built);
    }
}
