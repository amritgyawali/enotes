package com.amrit.service.impl;

import com.amrit.dto.NotesDto;
import com.amrit.entity.Notes;
import com.amrit.exception.ResourceNotFoundException;
import com.amrit.repository.CategoryRepository;
import com.amrit.repository.NotesRepository;
import com.amrit.service.NotesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;


@Service
public class NotesServiceImpl implements NotesService {
    @Autowired
    private NotesRepository notesRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CategoryRepository categoryRepo;

    @Override
    public Boolean saveNotes(NotesDto notesDto) throws ResourceNotFoundException {
        // category validation notes
        checkCategoryExist(notesDto.getCategory());
        Notes notes = mapper.map(notesDto, Notes.class);
        Notes saveNotes = notesRepo.save(notes);
        return !ObjectUtils.isEmpty(saveNotes);
    }

    private void checkCategoryExist(NotesDto.CategoryDto category) throws ResourceNotFoundException {
        categoryRepo.findById(category.getId())
                .orElseThrow(()-> new ResourceNotFoundException("category id invalid"));
    }

    @Override
    public List<NotesDto> getAllNotes() {
       return notesRepo.findAll().stream()
                .map(note -> mapper.map(note, NotesDto.class)).toList();
    }
}
