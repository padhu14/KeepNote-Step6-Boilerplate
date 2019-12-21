package com.stackroute.keepnote.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.service.CategoryService;

/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
public class CategoryController {

	/*
	 * Autowiring should be implemented for the CategoryService. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword
	 */
	@Autowired
	private CategoryService categoryService;

	/*
	 * Define a handler method which will create a category by reading the
	 * Serialized category object from request body and save the category in
	 * database. Please note that the careatorId has to be unique.This handler
	 * method should return any one of the status messages basis on different
	 * situations: 1. 201(CREATED - In case of successful creation of the category
	 * 2. 409(CONFLICT) - In case of duplicate categoryId
	 *
	 * 
	 * This handler method should map to the URL "/api/v1/category" using HTTP POST
	 * method".
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/api/v1/category")
	public ResponseEntity<?> createCategory(@RequestBody Category category) {
		try {
			Category category2 = categoryService.createCategory(category);
			if (category2 != null) {
				return new ResponseEntity<Category>(category2, HttpStatus.CREATED);
			}

			return new ResponseEntity<String>("Creation Category Failed", HttpStatus.CONFLICT);

		} catch (Exception e) {
			return new ResponseEntity<String>("Creation reminder Failed", HttpStatus.CONFLICT);
		}
	}

	/*
	 * Define a handler method which will delete a category from a database.
	 * 
	 * This handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the category deleted successfully from
	 * database. 2. 404(NOT FOUND) - If the category with specified categoryId is
	 * not found.
	 * 
	 * This handler method should map to the URL "/api/v1/category/{id}" using HTTP
	 * Delete method" where "id" should be replaced by a valid categoryId without {}
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/api/v1/category/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable String id) {
		try {

			if (categoryService.deleteCategory(id)) {
				return new ResponseEntity<String>("Category Deleted", HttpStatus.OK);
			}
			return new ResponseEntity<String>("Category Not Found", HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * Define a handler method which will update a specific category by reading the
	 * Serialized object from request body and save the updated category details in
	 * database. This handler method should return any one of the status messages
	 * basis on different situations: 1. 200(OK) - If the category updated
	 * successfully. 2. 404(NOT FOUND) - If the category with specified categoryId
	 * is not found. This handler method should map to the URL
	 * "/api/v1/category/{id}" using HTTP PUT method.
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/api/v1/category/{id}")
	public ResponseEntity<?> updateCategory(@RequestBody Category category, @PathVariable String id) {
		try {
			Category category2 = categoryService.updateCategory(category, id);
			if (category2 == null) {
				return new ResponseEntity<String>("Category Not Found", HttpStatus.CONFLICT);
			}
			return new ResponseEntity<Category>(category2, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}

	/*
	 * Define a handler method which will get us the category by a userId.
	 * 
	 * This handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the category found successfully.
	 * 
	 * 
	 * This handler method should map to the URL "/api/v1/category" using HTTP GET
	 * method
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/api/v1/category/{id}")
	public ResponseEntity<?> getCategoryById(@PathVariable String id) {
		try {
			Category category = categoryService.getCategoryById(id);
			
			return new ResponseEntity<Category>(category, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}
