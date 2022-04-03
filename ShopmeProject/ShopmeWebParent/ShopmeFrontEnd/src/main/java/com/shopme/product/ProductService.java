package com.shopme.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Product;
import com.shopme.common.exception.ProductNotFoundException;

@Service
public class ProductService {
  public static final int PRODUCTS_PER_PAGE = 10;
	
	@Autowired
	private ProductRepository repo;
	public Page<Product> listByCategory(int pageNum ,int categoryId){
		String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
		Pageable pageable = PageRequest.of(pageNum - 1 ,PRODUCTS_PER_PAGE);
		return repo.listByCategory(categoryId, categoryIdMatch, pageable);
	}
	
	public Product getProduct(String alias) throws ProductNotFoundException {
	Product product =	repo.findByAlias(alias);
	if(product == null) {
		throw new ProductNotFoundException("Coul not found any product with alias" + alias);
	}
	return product;
	}
}
