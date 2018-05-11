package com.oleksa.model.dao;

import org.junit.*;

import com.oleksa.model.dao.impl.UserDaoImpl;
import com.oleksa.model.entity.User;
import com.oleksa.model.entity.UserRole;
import com.oleksa.model.exception.NotUniqueEmailException;
import com.oleksa.model.exception.NotUniqueNameException;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;

public class UserDaoImplTest {

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS user_t (" + 
            "    us_name varchar(15) NOT NULL, "
            + "  us_id serial NOT NULL," + 
            "    us_password varchar(64) NOT NULL," + 
            "    us_role varchar(15) NOT NULL," + 
            "    us_email varchar(320) NOT NULL," + 
            "    us_full_name varchar(255) NOT NULL" + 
            ");" +  
            "CREATE UNIQUE INDEX IF NOT EXISTS \"us_email_un\" ON user_t (us_email);"
            + "CREATE UNIQUE INDEX IF NOT EXISTS \"us_name_un\" ON user_t (us_name);";

    private static final String INSERT_DB1 = "INSERT INTO user_t(us_id, us_name, us_password, us_role, us_email, us_full_name)"
            + " VALUES(1, 'atpt34', '123', 'admin', 'atpt34@gmail.com', 'Oleksa Tretik');";
    private static final String INSERT_DB2 = "INSERT INTO user_t(us_id, us_name, us_password, us_role, us_email, us_full_name)"
            + " VALUES(2, 'maximl', '123', 'client', 'maxim24@gmail.com', 'Maxim Lubovets');";
    private static final String INSERT_DB3 = "INSERT INTO user_t(us_id, us_name, us_password, us_role, us_email, us_full_name)"
            + " VALUES(3, 'vityas', '123', 'master', 'vitya34@gmail.com', 'Vitya Skripnick');";
    private static final String INSERT_DB4 = "INSERT INTO user_t(us_id, us_name, us_password, us_role, us_email, us_full_name)"
            + " VALUES(4, 'olgapo', '345', 'master', 'olga34@gmail.com', 'Olga Polevik');";

    private static final String TRUNC_TABLE = "TRUNCATE TABLE user_t;";

    private DataSource dataSource;
    private UserDaoImpl userDao;
    
    @Before
    public void setUp() throws SQLException, NamingException {
       JdbcDataSource ds = new JdbcDataSource();
       ds.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=PostgreSQL");
       dataSource = ds;
       userDao = new UserDaoImpl(dataSource);
       
       try (Connection conn = dataSource.getConnection();
               Statement statement = conn.createStatement()) {
           statement.execute(CREATE_TABLE);
           statement.execute(INSERT_DB1);
           statement.execute(INSERT_DB2);
           statement.execute(INSERT_DB3);
       }
    }
    
    @After
    public void tearDown() throws SQLException {
        try (Connection conn = dataSource.getConnection();
                Statement statement = conn.createStatement()) {
            statement.execute(TRUNC_TABLE);
        }
    }
    
    @Test
    public void testFindAll() {
        List<User> findAll = userDao.findAll();
        assertEquals(3, findAll.size());
        assertEquals(findAll.get(0).getRole(), UserRole.ADMIN);
        assertEquals(findAll.get(1).getRole(), UserRole.CLIENT);
        assertEquals(findAll.get(2).getRole(), UserRole.MASTER);
    }
    
    @Test
    public void testFindByEmail() {
        String email = "atpt34@gmail.com";
        Optional<User> user = userDao.findByEmail(email);
        
        assertTrue(user.isPresent());
        assertEquals(UserRole.ADMIN, user.get().getRole());
        assertEquals(1, (int) user.get().getId());
    }
    
    @Test
    public void testFindByName()  {
        String name = "atpt34";
        Optional<User> user = userDao.findByName(name);
        
        assertTrue(user.isPresent());
        assertEquals(UserRole.ADMIN, user.get().getRole());
        assertEquals(1, (int) user.get().getId());
    }
    
    @Test
    public void testFindById() {
        int id = 3;
        Optional<User> user = userDao.findById(id);
        
        assertTrue(user.isPresent());
        assertEquals(UserRole.MASTER, user.get().getRole());
        assertEquals(id, (int) user.get().getId());
    }
    
    @Test
    public void testFindByNotExistedId() {
        int id = -1;
        Optional<User> user = userDao.findById(id);

        assertTrue(!user.isPresent());
    }
    
    @Test
    public void testDeleteById() throws SQLException {
        try (Connection conn = dataSource.getConnection();
                Statement statement = conn.createStatement()) {
            statement.execute(INSERT_DB4);
        }
        int id = 4;
        assertEquals("olgapo", userDao.findById(id).get().getName());
        userDao.deleteById(id);
        assertEquals(Optional.empty(), userDao.findById(id));
    }
    
    @Test
    public void testDeleteByNonExistedId() {
        int id = -1;
        userDao.deleteById(id);
        assertEquals(Optional.empty(), userDao.findById(id));
    }
    
    @Test
    public void testCreate() {
        User user = new User(0, "lexatrt", "lexa@meta.ua", "lexameta", UserRole.ADMIN, "Leha Tretik");
        try {
            User create = userDao.create(user);
            assertNotNull(create);
            assertEquals(user.getName(), create.getName());
            userDao.deleteById(create.getId());
        } catch (NotUniqueNameException e) {
            fail();
        } catch (NotUniqueEmailException e) {
            fail();
        }
    }
    
    @Test
    public void testCreateNotUniqueName() {
        User user = new User(0, "atpt34", "lexa@meta.ua", "lexameta", UserRole.ADMIN, "Leha Tretik");
        try {
            userDao.create(user);
            fail();
        } catch (NotUniqueNameException e) {
            assertNotNull(e);
        } catch (NotUniqueEmailException e) {
            fail();
        }
    }
    
    @Test
    public void testCreateNotUniqueEmail() {
        User user = new User(0, "oleksii", "atpt34@gmail.com", "lexameta", UserRole.ADMIN, "Leha Tretik");
        try {
            userDao.create(user);
            fail();
        } catch (NotUniqueNameException e) {
            fail();
        } catch (NotUniqueEmailException e) {
            assertNotNull(e);
        }
    }
    
    @Test
    public void testUpdate() {
        Optional<User> optUser = userDao.findById(1);
        User user = optUser.get();
        user.setName("user_hello");
        user.setPassword("user_world");
        try {
            User update = userDao.update(user);
            assertNotNull(update);
            assertEquals(user, update);
            assertEquals(user.getName(), update.getName());
            assertEquals(user.getPassword(), update.getPassword());
        } catch (NotUniqueNameException e) {
            fail();
        } catch (NotUniqueEmailException e) {
            fail();
        }
    }
    
    @Test(expected = NotUniqueNameException.class)
    public void testUpdateNotUniqueName() throws Throwable {
        Optional<User> optUser = userDao.findById(3);
        User user = optUser.get();
        user.setName("atpt34");
        userDao.update(user);
        fail();
    }
    
    @Test(expected = NotUniqueEmailException.class)
    public void testUpdateNotUniqueEmail() throws Throwable {
        Optional<User> optUser = userDao.findById(3);
        User user = optUser.get();
        user.setEmail("atpt34@gmail.com");
        userDao.update(user);
        fail();
    }
}
