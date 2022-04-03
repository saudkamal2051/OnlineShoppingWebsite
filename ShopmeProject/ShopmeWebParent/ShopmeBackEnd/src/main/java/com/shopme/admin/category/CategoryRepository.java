package com.shopme.admin.category;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopme.common.entity.Category;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {
    @Query("SELECT c FROM  Category c WHERE c.parent.id is Null")
	public List<Category>findRootCategories(Sort sort);
    
    
    @Query("SELECT c FROM  Category c WHERE c.parent.id is Null")
 	public Page<Category>findRootCategories(Pageable pageable);
    
    @Query("SELECT c FROM  Category c WHERE c.name LIKE %?1%")
    public Page<Category> search(String keyword ,Pageable pageable);
    
    public Category findByName(String name);
    public Category findByAlias(String alias);
    
}
