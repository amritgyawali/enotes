package com.amrit.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.amrit.entity.FileDetails;
import com.amrit.exception.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import com.amrit.dto.NotesDto;

public interface NotesService {

    public Boolean saveNotes(String notes,MultipartFile file) throws Exception;

    public List<NotesDto> getAllNotes();

    byte[] downloadFile(FileDetails fileDetails) throws ResourceNotFoundException, IOException;

    FileDetails getFileDetails(Integer id) throws ResourceNotFoundException;
}
