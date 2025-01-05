package com.amrit.dto;

import com.amrit.entity.Notes;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FavouriteNoteDto {
     private Integer id;

     private NotesDto note;

     private Integer userId;
}
