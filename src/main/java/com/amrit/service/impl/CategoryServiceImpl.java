package com.amrit.service.impl;

import com.amrit.dto.CategoryDto;
import com.amrit.dto.CategoryResponse;
import com.amrit.entity.Category;
import com.amrit.repository.CategoryRepository;
import com.amrit.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Boolean saveCategory(CategoryDto categoryDto) {

//        Category category = new Category();
//        category.setName(categoryDto.getName());
//        category.setDescription(categoryDto.getDescription());
//        category.setIsActive(categoryDto.getIsActive());

        Category category = mapper.map(categoryDto, Category.class);
        category.setIsDeleted(false);
        category.setCreatedBy(1);
        category.setCreatedOn(new Date());
        Category saveCategory = categoryRepo.save(category);
        if(ObjectUtils.isEmpty(saveCategory))
        {
            return false;
        }
        return true;
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = categoryRepo.findByIsDeletedFalse();

        List<CategoryDto> categoryDtoList = categories.stream().map(category -> mapper.map(category, CategoryDto.class)).toList();
        return categoryDtoList;
    }

    @Override
    public List<CategoryResponse> getActiveCategory() {
        List<Category> categories = categoryRepo.findByIsActiveTrueAndIsDeletedFalse();
        List<CategoryResponse> categoryList = categories.stream().map(category -> mapper.map(category, CategoryResponse.class)).toList();
        return categoryList;
    }

    @Override
    public CategoryDto getCategoryById(Integer id) {
        Optional<Category> findByCategory = categoryRepo.findByIdAndIsDeletedFalse(id);
        if (findByCategory.isPresent()){
            Category category = findByCategory.get();
            return mapper.map(category,CategoryDto.class);
        }
        return null;
    }

    @Override
    public Boolean deleteCategory(Integer id) {
        Optional<Category> findByCategory = categoryRepo.findById(id);

        if (findByCategory.isPresent()){
            Category category = findByCategory.get();
            category.setIsDeleted(true);
            categoryRepo.save(category);
            return true;
        }
        return false;
    }
}
