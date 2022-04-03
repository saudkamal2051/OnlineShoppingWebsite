package com.shopme.admin.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class ProductRepositoryTests {
@Autowired
private ProductRepository repo;
@Autowired
private TestEntityManager entityManager;
   @Test
   public void testCreateProduct() {
	   Brand brand = entityManager.find(Brand.class,38);
	   Category category = entityManager.find(Category.class, 6);
	   Product product = new Product();
	   product.setName("Acer Aspire Desktop");
	   product.setAlias("Acer_Aspire_Desktop");
	   product.setShortDescription("Short description of Acer Aspire Desktop");
	   product.setFullDescription("Full description of Acer Aspire Desktop");
	   product.setBrand(brand);
	   product.setCategory(category);
	   product.setPrice(678);
	   product.setCost(600);
	   product.setEnabled(true);
	   product.setInStock(true);
	   product.setCreatedTime(new Date());
	   product.setUpdatedTime(new Date());
	   Product savedProduct =   repo.save(product);
	   assertThat(savedProduct).isNotNull();
	   assertThat(savedProduct.getId()).isGreaterThan(0);
	      
   }
   
     @Test
     public void testListAllProduct() {
    Iterable<Product>iterableProduct=	 repo.findAll();
    iterableProduct.forEach(System.out ::println);
     }
     @Test
     public void testGetProduct() {
    	 Integer id =2;
    Product product =	 repo.findById(id).get();
    System.out.println(product);
    assertThat(product).isNotNull();
     }
     @Test
       public void testUpdateProduct() {
    	 Integer id =1;
    Product product =	 repo.findById(id).get();
    product.setPrice(499);
    repo.save(product);
    Product updatedProduct = entityManager.find(Product.class, id);
    assertThat(updatedProduct.getPrice()).isEqualTo(499);
     }
     
     @Test
     public void testDeleteProduct() {
    	 Integer id = 20;
    	 repo.deleteById(id);
    Optional<Product>result =	 repo.findById(id);
    assertThat(!result.isPresent());
     }
     
     @Test
     public void testSaveProductWithImages() {
    	 Integer productId = 1;
    Product product =	 repo.findById(productId).get();
    product.setMainImage("main image.jpg");
    product.addExtraImage("extra image 1.png");
    product.addExtraImage("extra image_2.png");
    product.addExtraImage("extra-image 3.png");
   Product savedProduct = repo.save(product);
   assertThat(savedProduct.getImages().size()).isEqualTo(3);
     }
     
     @Test
     public void testSaveProductWithDetails() {
    	 Integer productId = 1;
    	 Product product = repo.findById(productId).get();
    	 product.addDetails("Device Memory", "129GB");
    	 product.addDetails("Cpu Model", "MediaTek");
    	 product.addDetails("Os", "Android 10");
    	 Product savedProduct = repo.save(product);
    	 assertThat(savedProduct.getDetails()).isNotEmpty();
     }
}
