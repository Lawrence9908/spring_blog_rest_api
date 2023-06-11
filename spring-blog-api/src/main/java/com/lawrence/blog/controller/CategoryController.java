package com.lawrence.blog.controller;

import com.lawrence.blog.payload.CategoryDto;
import com.lawrence.blog.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    // Add new category end point
    //http://localhost:8080/api/categories/
    @PostMapping()
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto savedCategory =  categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    //Get all categories end point
    //http://localhost:8080/api/categories
    @GetMapping()
    public ResponseEntity<List<CategoryDto>> getCategories(){
        List<CategoryDto> categoriesList = categoryService.getAllCategories();
        return new ResponseEntity<>(categoriesList, HttpStatus.OK);
    }

    //Get a category of the given categoryId end point
    //http://localhost:8080/api/categories/1
    @GetMapping("{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Long categoryId){
        CategoryDto category  = categoryService.getCategory(categoryId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
    //Delete the category of the give id end point
    //http://localhost:8080/api/categories/1
    @DeleteMapping("{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>("The have been successfully deleted", HttpStatus.OK);
    }
    //Update category of the given id with the given values end point
    //http://localhost:8080/api/categories/2
    @PutMapping("{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable Long categoryId){
        CategoryDto updateCategory = categoryService.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(updateCategory, HttpStatus.OK);
    }


}
