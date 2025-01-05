package com.amrit.scheduler;

import com.amrit.dto.NotesDto;
import com.amrit.entity.Notes;
import com.amrit.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class NotesScheduler {

    @Autowired
    private NotesRepository notesRepo;


    @Scheduled(cron = "0 0 0 * * *")
    public void deleteNotesScheduler(){
        System.out.println("hello");
        LocalDateTime cutoff = LocalDateTime.now().minusDays(7);
        List<Notes> deleteNotes = notesRepo.findAllByIsDeletedAndDeletedOnBefore(true,cutoff);
        if(!deleteNotes.isEmpty()){
            notesRepo.deleteAll(deleteNotes);
        }
    }
}
