package com.gladunalexander;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OnlineStoreApplicationTests {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Test
	public void contextLoads() {

		Assert.assertTrue(passwordEncoder.matches("password", "$2a$12$wHn7qx6SBR5pJV0NW7pyH.OuIMbner8TsfCwRD5aTtFzu1SRxjPDe"));
	}

}
