package com.lawrence.blog.services.impl;

import com.lawrence.blog.entity.Category;
import com.lawrence.blog.exceptions.ResourceNotFoundException;
import com.lawrence.blog.payload.CategoryDto;
import com.lawrence.blog.repository.CategoryRepository;
import com.lawrence.blog.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    //Injecting the categoryRepository into CategoryService
    //Using constructor annotation
    // We are not using @Autowired since we have only one constructor
    private CategoryRepository categoryRepo;

    public CategoryServiceImpl(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }
    //Method to add a new category into the database
    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setDescription(categoryDto.getDescription());
        category.setName(categoryDto.getName());

        Category savedCategory = categoryRepo.save(category);
        CategoryDto result = new CategoryDto();
        result.setDescription(savedCategory.getDescription());
        result.setName(savedCategory.getName());
        result.setId(savedCategory.getId());
        return result;
    }
   //Method to get a category tha matches the given id
    @Override
    public CategoryDto getCategory(Long categoryId) {
        Category category  = categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","id" , categoryId));
        //Convert category into Dto
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setDescription(category.getDescription());
        categoryDto.setName(category.getName());
        categoryDto.setId(category.getId());

        return categoryDto ;
    }
    //Method to get all categories
    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories  = categoryRepo.findAll();
        //goes over a list of categoryEntity on convert each category entity
        // into category dto and return a list of category dto
        return categories.stream().map(category -> mapEntityTOdTO(category)).collect(Collectors.toList());
    }
   //Method to update the category of the given id
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
        //Checking if the category with the given id do exists
        Category category  = categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "id", categoryId));
        //Setting new value to be updated
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setId(categoryDto.getId());
        //Saving categoryEntity of the updated Entity into the db
        Category updatedCategory = categoryRepo.save(category);
        //Converting category Entity to Dto
        return mapEntityTOdTO(updatedCategory);
    }
    //Method to delete category of the given id
    @Override
    public void deleteCategory(Long categoryId) {
        Category category  = categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "id", categoryId));
        categoryRepo.deleteById(categoryId);
    }
    //Helper method to convert CategoryEntity to CategoryDto
    private CategoryDto mapEntityTOdTO(Category category){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setDescription(category.getDescription());
        categoryDto.setName(category.getName());
        categoryDto.setId(category.getId());
        return  categoryDto;
    }

   //Helper method to convert CategoryDto to CategoryEntity
    private Category mapDtoToEntity(CategoryDto categoryDto){
        Category category = new Category();
        category.setDescription(categoryDto.getDescription());
        category.setName(categoryDto.getName());
        return category;
    }
}
