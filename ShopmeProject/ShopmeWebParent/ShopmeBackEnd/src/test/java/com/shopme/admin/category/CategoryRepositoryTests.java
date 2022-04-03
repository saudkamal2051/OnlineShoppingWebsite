package com.shopme.admin.category;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Category;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTests {

	@Autowired
	private CategoryRepository catRepo;
	
	@Test
	public void testCreateRootCategory() {
		Category category = new Category("Electronics");
	Category savedCategory =	catRepo.save(category);
	assertThat(savedCategory.getId()).isGreaterThan(0);
	}
	@Test
	public void testCreateSubCategory() {
		Category parent = new Category(1);
		Category laptops =new Category("Laptops", parent);
		Category components =new Category("Computer Components", parent);
		catRepo.saveAll(List.of(laptops ,components));
	}
	@Test
	public void testFindByName() {
		String name ="Computers";
		Category category =catRepo.findByName(name);
		assertThat(category).isNotNull();
		assertThat(category.getName()).isEqualTo(name);
	}
	@Test
	public void  testFindByAlias() {
		String alias ="Electronics";
		Category category =catRepo.findByAlias(alias);
		assertThat(category).isNotNull();
		assertThat(category.getAlias()).isEqualTo(alias);
	}
}
