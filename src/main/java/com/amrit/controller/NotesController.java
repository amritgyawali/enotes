package com.amrit.controller;

import com.amrit.dto.NotesDto;
import com.amrit.exception.ResourceNotFoundException;
import com.amrit.service.NotesService;
import com.amrit.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {

    @Autowired
    private NotesService notesService;

    @PostMapping("/")
    public ResponseEntity<?> saveNotes(@RequestBody NotesDto notesDto) throws ResourceNotFoundException {
        Boolean saveNotes= notesService.saveNotes(notesDto);
        if (saveNotes){
            return CommonUtil.createBuildResponseMessage("notes saved success", HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Notes not saved", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllNotes()
    {
        List<NotesDto> notes= notesService.getAllNotes();
        if (CollectionUtils.isEmpty(notes)) {
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
    }
}
