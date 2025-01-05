package com.amrit.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import com.amrit.dto.FavouriteNoteDto;
import com.amrit.dto.NotesResponse;
import com.amrit.entity.FileDetails;
import com.amrit.entity.Notes;
import com.amrit.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.amrit.dto.NotesDto;
import com.amrit.service.NotesService;
import com.amrit.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {

    @Autowired
    private NotesService notesService;

    @PostMapping("/")
    public ResponseEntity<?> saveNotes(@RequestParam String notes,
                                       @RequestParam(required = false) MultipartFile file) throws Exception {

        Boolean saveNotes = notesService.saveNotes(notes,file);
        if (saveNotes) {
            return CommonUtil.createBuildResponseMessage("Notes saved success", HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Notes not saved", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws IOException, ResourceNotFoundException {
        FileDetails fileDetails = notesService.getFileDetails(id);
        byte[] data = notesService.downloadFile(fileDetails);
        HttpHeaders headers = new HttpHeaders();
        String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentDispositionFormData("attachment",fileDetails.getOriginalFileName());
        return ResponseEntity.ok().headers(headers).body(data);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllNotes() {
        List<NotesDto> notes = notesService.getAllNotes();
        if (CollectionUtils.isEmpty(notes)) {
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
    }

    @GetMapping("/user-notes")
    public ResponseEntity<?> getAllNotesByUser(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                               @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Integer userId = 2;
        NotesResponse notes = notesService.getAllNotesByUser(userId, pageNo, pageSize);
        return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception {
        notesService.softDeleteNotes(id);
        return CommonUtil.createBuildResponseMessage("deleted successfully",HttpStatus.OK);
    }

    @GetMapping("/restore/{id}")
    public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception {
        notesService.restoreDeleteNotes(id);
        return CommonUtil.createBuildResponseMessage("restored successfully",HttpStatus.OK);
    }

    @GetMapping("/recycle-bin")
    public ResponseEntity<?> userRecycleBin() throws Exception {
        Integer userId=2;
        List<NotesDto> notes = notesService.getUserRecycleBinNotes(userId);
        if(CollectionUtils.isEmpty(notes)){
            return CommonUtil.createBuildResponseMessage("notes not available in recycle-bin",HttpStatus.OK);
        }
        return CommonUtil.createBuildResponse(notes,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception {
        notesService.hardDeleteNotes(id);
        return CommonUtil.createBuildResponseMessage("deleted successfully",HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteRecycleBin() throws Exception {
        int userId = 2;
        notesService.deleteRecycleBin(userId);
        return CommonUtil.createBuildResponseMessage("deleted all recyclebin successfully",HttpStatus.OK);
    }

    @GetMapping("/fav/{noteId}")
    public ResponseEntity<?> favoriteNote(@PathVariable Integer noteId) throws Exception {
        notesService.favoriteNotes(noteId);
        return CommonUtil.createBuildResponseMessage("Notes added Favorite", HttpStatus.CREATED);
    }

    @DeleteMapping("/un-fav/{favNotId}")
    public ResponseEntity<?> unFavoriteNote(@PathVariable Integer favNotId) throws Exception {
        notesService.unFavoriteNotes(favNotId);
        return CommonUtil.createBuildResponseMessage("Remove Favorite", HttpStatus.OK);
    }

    @GetMapping("/fav-note")
    public ResponseEntity<?> getUserfavoriteNote() throws Exception {

        List<FavouriteNoteDto> userFavoriteNotes = notesService.getUserFavoriteNotes();
        if (CollectionUtils.isEmpty(userFavoriteNotes)) {
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(userFavoriteNotes, HttpStatus.OK);
    }

    @GetMapping("/copy-note/{noteId}")
    public ResponseEntity<?> copyNote(@PathVariable Integer noteId) throws Exception {
        notesService.copyNote(noteId);
        return CommonUtil.createBuildResponseMessage("Notes copied successfully", HttpStatus.CREATED);
    }
}
