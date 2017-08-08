package com.gladunalexander.persistence;

import com.gladunalexander.backend.persistence.domain.Role;
import com.gladunalexander.backend.persistence.domain.User;
import com.gladunalexander.backend.service.UserService;
import com.gladunalexander.enums.RolesEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created Alexander Gladun
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Before
    public void init(){
        Assert.assertNotNull(userService);
    }

    @Test
    public void saveUser(){
        userService.createUser(createUser());
        User user = userService.findByUsername("daniel333");
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getId());
        Assert.assertTrue(user.getRoles().contains(new Role(RolesEnum.USER)));
        Assert.assertTrue(passwordEncoder.matches("daniel333", user.getPassword()));
    }

    @Test
    public void deleteUser(){
        userService.delete(3);
        User user = userService.findById(3);
        Assert.assertNull(user);
    }

    @Test
    public void testDuplicateExists(){
        User user = createUser();
        Assert.assertFalse("This user doesnt't exists", userService.duplicatesExists(user));

        user.setUsername("admin");
        Assert.assertTrue(userService.duplicatesExists(user));
    }

    private User createUser(){
        User user = new User();
        user.setFirstName("Daniel");
        user.setLastName("Pierce");
        user.setUsername("daniel333");
        user.setPassword("daniel333");
        user.setEmail("daniel333@gmail.com");
        return user;
    }
}
