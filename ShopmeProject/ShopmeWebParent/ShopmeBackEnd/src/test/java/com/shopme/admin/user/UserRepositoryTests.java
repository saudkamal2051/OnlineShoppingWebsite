package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	@Autowired
	
	private UserRepository userRepo;
	
	@Autowired
	private EntityManager entityManager;
	
	@Test
	public void testCreateNewUSerWithOneRole() {
	Role roleAdmin =	entityManager.find(Role.class, 1);
	User userKamal = new User("kamalSaud232@gmail.com","kamal2020","kamal","saud");
	userKamal.addRole(roleAdmin);
	User savedUser =userRepo.save(userKamal);
	assertThat(savedUser.getId()).isGreaterThan(0);
	
	
	
		
	}
	@Test
	public void testCreateNewUserWithTwoRole() {
		User userRavi = new User("ravi3433@gmail.com","ravi2020","ravi","kumar");
		Role roleEditor =new Role(3);
		Role roleAssistant = new Role(4);
		userRavi.addRole(roleEditor);
		userRavi.addRole(roleAssistant);
	User savedUser =	userRepo.save(userRavi);
	assertThat(savedUser.getId()).isGreaterThan(0);
	
	}
	@Test
	public void testGetUserByEmail() {
		String email ="kamalSaud232@gmail.com";
	User user =	userRepo.getUserByEmail(email);
	assertThat(user).isNotNull();
	}
	@Test
	public void testCountById() {
		Integer id =100;
		Long countById = userRepo.countById(id);
		assertThat(countById).isNotNull().isGreaterThan(0);
	}
  @Test
  public void testListFirstPage() {
	  int pageNumber=1;
	  int pageSize =4;
	   Pageable pageable = PageRequest.of(pageNumber, pageSize);
	   Page<User>  page =  userRepo.findAll(pageable);
         List<User>listUsers =	page.getContent();
         listUsers.forEach(user -> System.out.println(user));
         assertThat(listUsers.size()).isEqualTo(pageSize);

  }
  @Test
  public void testSearchUSer() {
	  String keyword ="bruce";
	  int pageNumber=1;
	  int pageSize =4;
	   Pageable pageable = PageRequest.of(pageNumber, pageSize);
	   Page<User>  page =  userRepo.findAll(keyword ,pageable);
         List<User>listUsers =	page.getContent();
         listUsers.forEach(user -> System.out.println(user));
         assertThat(listUsers.size()).isGreaterThan(0);
  }
}
