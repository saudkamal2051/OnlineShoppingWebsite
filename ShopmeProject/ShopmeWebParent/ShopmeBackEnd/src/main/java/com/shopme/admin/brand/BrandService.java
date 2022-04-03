package com.shopme.admin.brand;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Brand;

@Service
public class BrandService {
	public static final int BRANDS_PER_PAGE =10;

	@Autowired
	private BrandRepository repo;
	
	public List<Brand> listAll(){
		return (List<Brand>) repo.findAll();
	}
	
	public Page<Brand>listByPage(int pageNum ,String sortField ,String SortDir,String keyword){
		Sort sort =Sort.by(sortField);
		sort  = SortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum - 1, BRANDS_PER_PAGE, sort);
		if(keyword != null) {
			return repo.findAll(keyword, pageable);
		}
		return repo.findAll(pageable);
	}
	
	public Brand  save(Brand brand) {
		return repo.save(brand);
	}
	public Brand get(Integer id) throws BrandNotFoundException {
		try {
			return repo.findById(id).get();
		}catch(NoSuchElementException ex) {
			throw new BrandNotFoundException("could not find any brand with id: +id");
		}
	}
	
}
