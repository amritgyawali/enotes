package com.amrit.service;

import java.io.IOException;
import java.util.List;

import com.amrit.dto.FavouriteNoteDto;
import com.amrit.dto.NotesResponse;
import com.amrit.entity.FavouriteNote;
import com.amrit.entity.FileDetails;
import com.amrit.exception.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import com.amrit.dto.NotesDto;

public interface NotesService {

    public Boolean saveNotes(String notes,MultipartFile file) throws Exception;

    public List<NotesDto> getAllNotes();

    byte[] downloadFile(FileDetails fileDetails) throws ResourceNotFoundException, IOException;

    FileDetails getFileDetails(Integer id) throws ResourceNotFoundException;

    NotesResponse getAllNotesByUser(Integer userId,Integer pageNo,Integer pageSize);

    void softDeleteNotes(Integer id) throws Exception;

    void restoreDeleteNotes(Integer id) throws Exception;

   List<NotesDto> getUserRecycleBinNotes(Integer userId);

    void hardDeleteNotes(Integer id) throws Exception;

    void deleteRecycleBin(int userId);
}
