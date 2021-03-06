package com.shopme.admin.category;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.shopme.common.entity.Category;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)

public class CategoryServiceTests {
@MockBean
private CategoryRepository catRepo;

@InjectMocks
private CategoryService service ;

@Test
public void testCheckUniqueInNewModeReturnDuplicateName() {
	Integer id = null;
    String  name ="Computers";
    String alias ="abc";
    Category category = new Category(id ,name ,alias);
    Mockito.when(catRepo.findByName(name)).thenReturn(category);
    Mockito.when(catRepo.findByAlias(alias)).thenReturn(null);
    String result = service.checkUnique(id, name, alias);
    assertThat(result).isEqualTo("DuplicateName");
    
	
}
     @Test
    public void testCheckUniqueInNewModeReturnDuplicateAlias() {
	Integer id = null;
    String  name ="nameAbc";
    String alias ="Electronics";
    Category category = new Category(id ,name ,alias);
    Mockito.when(catRepo.findByName(name)).thenReturn(null);
    Mockito.when(catRepo.findByAlias(alias)).thenReturn(category);
    String result = service.checkUnique(id, name, alias);
    assertThat(result).isEqualTo("DuplicateAlias");
    
	
}
   @Test
    public void testCheckUniqueInNewModeReturnDuplicateOk() {
	Integer id = null;
    String  name ="nameAbc";
    String alias ="Computers";
    Category category = new Category(id ,name ,alias);
    Mockito.when(catRepo.findByName(name)).thenReturn(null);
    Mockito.when(catRepo.findByAlias(alias)).thenReturn(null);
    String result = service.checkUnique(id, name, alias);
    assertThat(result).isEqualTo("Ok");
    
	
}
   @Test
   public void testCheckUniqueInEditModeReturnDuplicateName() {
   	Integer id = 1;
       String  name ="Computers";
       String alias ="abc";
       Category category = new Category(2 ,name ,alias);
       Mockito.when(catRepo.findByName(name)).thenReturn(category);
       Mockito.when(catRepo.findByAlias(alias)).thenReturn(null);
       String result = service.checkUnique(id, name, alias);
       assertThat(result).isEqualTo("DuplicateName");
       
   	
   }
   @Test
   public void testCheckUniqueInEditModeReturnDuplicateAlias() {
	Integer id = 1;
   String  name ="nameAbc";
   String alias ="Electronics";
   Category category = new Category(2 ,name ,alias);
   Mockito.when(catRepo.findByName(name)).thenReturn(null);
   Mockito.when(catRepo.findByAlias(alias)).thenReturn(category);
   String result = service.checkUnique(id, name, alias);
   assertThat(result).isEqualTo("DuplicateAlias");
   
	
}
   @Test
   public void testCheckUniqueInEditModeReturnDuplicateOk() {
	Integer id = 1;
   String  name ="nameAbc";
   String alias ="Computers";
   
   Category category = new Category(id ,name ,alias);
   Mockito.when(catRepo.findByName(name)).thenReturn(null);
   Mockito.when(catRepo.findByAlias(alias)).thenReturn(category);
   String result = service.checkUnique(id, name, alias);
   assertThat(result).isEqualTo("Ok");
   
	
}
}
