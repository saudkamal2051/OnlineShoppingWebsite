package com.shopme.admin.category;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.FileUploadUtil;
import com.shopme.common.entity.Category;
import com.shopme.common.exception.CategoryNotFoundException;

@Controller
public class CategoryController {

	@Autowired
	private CategoryService service;
	
	@GetMapping("/categories")
	public String listFirstPage(@Param("sortDir") String sortDir , Model model) {
		return listByPage(1, sortDir, null ,model);
	}
	
	@GetMapping("/categories/page/{pageNum}")
	public String listByPage(@PathVariable(name ="pageNum") Integer pageNum 
			,@Param("sortDir")String sortDir ,@Param("keyword")String keyword, Model model) {

	if(sortDir == null || sortDir.isEmpty()) {
		sortDir ="asc";
	}
	CategoryPageInfo pageInfo = new CategoryPageInfo();
List<Category>listCategories =  service.listByPage(pageInfo, pageNum ,sortDir ,keyword);


long startCount = (pageNum - 1) * CategoryService.ROOT_CATEGORIES_PER_PAGE + 1;
long endCount = startCount + CategoryService.ROOT_CATEGORIES_PER_PAGE - 1;
if(endCount > pageInfo.getTotalElements()) {
	endCount = pageInfo.getTotalElements();
}

String reverseSortDir =sortDir.equals("asc") ? "desc" :"asc";
model.addAttribute("totalPages", pageInfo.getTotalPages());
model.addAttribute("totalItems", pageInfo.getTotalElements());
model.addAttribute("currentPage", pageNum);
model.addAttribute("sortField", "name");
model.addAttribute("sortField", sortDir);
model.addAttribute("keyword", keyword);
model.addAttribute("startCount", startCount);
model.addAttribute("endCount", endCount);

model.addAttribute("listCategories", listCategories);
model.addAttribute("reverseSortDir", reverseSortDir);
return "categories/categories";
}
	
	@GetMapping("/categories/new")
	public String newCategory(Model model) {
	List<Category>listCategories =	service.listCategoriesUsedInForm();
		model.addAttribute("listCategories", listCategories);
		model.addAttribute("category", new Category());
		model.addAttribute("pageTitle", "Create new Category");
		return "categories/category_form";
	}
	
	@PostMapping("/categories/save")
	public String saveCategory(Category category ,@RequestParam("fileImage") MultipartFile
			multipartFile ,RedirectAttributes ra) throws IOException {
		 if(!multipartFile.isEmpty()) {
    		
		String fileName =StringUtils.cleanPath(multipartFile.getOriginalFilename());
		category.setImage(fileName);
		Category saveCategory =service.save(category);
		String uploadDir ="../category-images/" + saveCategory.getId();
		FileUploadUtil.cleanDir(uploadDir);
		FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		 }else {
			 service.save(category);
		 }
		ra.addFlashAttribute("message", "The category has been saved successfully");
		return "redirect:/categories";
				
	}
	
	@GetMapping("/categories/edit/{id}")
    public String editCategory(@PathVariable(name="id") 
    Integer id ,Model model ,RedirectAttributes ra) {
   	 try {
   		 Category category = service.get(id);
   	List<Category>listCategories =	 service.listCategoriesUsedInForm();
   	
     	model.addAttribute("category", category);
   		model.addAttribute("listCategories", listCategories);
   	     
   		 model.addAttribute("pageTitle", "Edit category(Id:" + id + ")");
   		 
   		 
   		 return "categories/category_form";
   		 
   		 }catch(CategoryNotFoundException ex) {
   			 ra.addFlashAttribute("message", ex.getMessage());
   			 return "redirect:/categories";
   			
   		 }
   	 }
}
