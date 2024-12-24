package com.amrit.service;

import com.amrit.dto.NotesDto;
import com.amrit.exception.ResourceNotFoundException;

import java.util.List;

public interface NotesService {

    public Boolean saveNotes(NotesDto notesDto) throws ResourceNotFoundException;

    public List<NotesDto> getAllNotes();

}
