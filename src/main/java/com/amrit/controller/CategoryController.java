package com.amrit.controller;

import com.amrit.dto.CategoryDto;
import com.amrit.dto.CategoryResponse;
import com.amrit.entity.Category;
import com.amrit.exception.ResourceNotFoundException;
import com.amrit.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto){
        Boolean saveCategory = categoryService.saveCategory(categoryDto);
        if(saveCategory){
            return new ResponseEntity<>("saved success", HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllCategory(){
        List<CategoryDto> allCategory = categoryService.getAllCategory();
        if(CollectionUtils.isEmpty(allCategory)){
            return ResponseEntity.noContent().build();
        }
        else{
            return new ResponseEntity<>(allCategory, HttpStatus.OK);
        }
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveCategory(){
        List<CategoryResponse> allCategory = categoryService.getActiveCategory();
        if(CollectionUtils.isEmpty(allCategory)){
            return ResponseEntity.noContent().build();
        }
        else{
            return new ResponseEntity<>(allCategory, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id) throws Exception{
//        try{
//            CategoryDto categoryDto = categoryService.getCategoryById(id);
//            if (ObjectUtils.isEmpty(categoryDto))
//            {
//                return new ResponseEntity<>("Category not found with id =  "+id,HttpStatus.NOT_FOUND);
//            }
//            return new ResponseEntity<>(categoryDto,HttpStatus.OK);
//        }
//        catch (ResourceNotFoundException d){
//            return new ResponseEntity<>(d.getMessage(),HttpStatus.NOT_FOUND);
//        }catch (Exception e){
//            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
//        }

        CategoryDto categoryDto = categoryService.getCategoryById(id);
        if (ObjectUtils.isEmpty(categoryDto))
        {
            return new ResponseEntity<>("Internal Server Error"+id,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categoryDto,HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id){
        Boolean deleted = categoryService.deleteCategory(id);
        if (deleted)
        {
            return new ResponseEntity<>("Category deleted successfully for id =  "+id,HttpStatus.OK);
        }
        return new ResponseEntity<>("category not deleted",HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
