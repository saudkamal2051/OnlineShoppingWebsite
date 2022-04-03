package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {
	@Autowired
	private RoleRepository roleRepo;
	
	
	@Test
	public void testCreateFirstRole() {
		Role roleAdmin = new Role("Admin" ,"Manage Everthing");
	Role savedRole=	    roleRepo.save(roleAdmin);
	assertThat(savedRole.getId()).isGreaterThan(0);
	}
        @Test
          public void testCreateRestRole() {
	  Role roleSalesPerson =new Role("SalesPerson", "Manage product price , customer"  + " " + " shipping orders and sales report");
	  Role roleEditor = new Role("Editor" ,"Manage categories brands  ,products article and menus");
	  Role roleShipper = new Role("shipper", "view products ,view order and update order status");
	  Role roleAssistant = new Role("Assistant" ,"Manage questions and reviews");
	  
	  roleRepo.saveAll(List.of(roleSalesPerson ,roleEditor,roleAssistant,roleShipper));
  }
}
