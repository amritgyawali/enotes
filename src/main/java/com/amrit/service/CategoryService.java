package com.amrit.service;


import com.amrit.dto.CategoryDto;
import com.amrit.dto.CategoryResponse;
import com.amrit.entity.Category;

import java.util.List;

public interface CategoryService {

    public Boolean saveCategory(CategoryDto categoryDto);
    public List<CategoryDto> getAllCategory();
    public List<CategoryResponse> getActiveCategory();
}
