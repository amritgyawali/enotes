package com.amrit.service.impl;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amrit.dto.NotesDto;
import com.amrit.dto.NotesDto.CategoryDto;
import com.amrit.entity.Category;
import com.amrit.entity.FileDetails;
import com.amrit.entity.Notes;
import com.amrit.exception.ResourceNotFoundException;
import com.amrit.repository.CategoryRepository;
import com.amrit.repository.FileRepository;
import com.amrit.repository.NotesRepository;
import com.amrit.service.NotesService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NotesServiceImpl implements NotesService {

    @Autowired
    private NotesRepository notesRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CategoryRepository categoryRepo;

    @Value("${file.upload.path}")
    private String uploadpath;

    @Autowired
    private FileRepository fileRepo;

    @Override
    public Boolean saveNotes(String notes, MultipartFile file) throws Exception {

        ObjectMapper ob = new ObjectMapper();
        NotesDto notesDto = ob.readValue(notes, NotesDto.class);

        // category validation
        checkCategoryExist(notesDto.getCategory());

        Notes notesMap = mapper.map(notesDto, Notes.class);

        FileDetails fileDtls = saveFileDetails(file);

        if (!ObjectUtils.isEmpty(fileDtls)) {
            notesMap.setFileDetails(fileDtls);
        } else {
            notesMap.setFileDetails(null);
        }

        Notes saveNotes = notesRepo.save(notesMap);
        if (!ObjectUtils.isEmpty(saveNotes)) {
            return true;
        }
        return false;
    }

    private FileDetails saveFileDetails(MultipartFile file) throws IOException {

        if (!ObjectUtils.isEmpty(file) && !file.isEmpty()) {

            String originalFilename = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(originalFilename);

            List<String> extensionAllow = Arrays.asList("pdf", "xlsx", "jpg", "png", "docx");
            if (!extensionAllow.contains(extension)) {
                throw new IllegalArgumentException("invalid file format ! Upload only .pdf , .xlsx,.jpg");
            }

            String rndString = UUID.randomUUID().toString();
            String uploadfileName = rndString + "." + extension; // sdfsafbhkljsf.pdf

            File saveFile = new File(uploadpath);
            if (!saveFile.exists()) {
                saveFile.mkdir();
            }
            // path : enotesapiservice/notes/java.pdf
            String storePath = uploadpath.concat(uploadfileName);

            // upload file
            long upload = Files.copy(file.getInputStream(), Paths.get(storePath));
            if (upload != 0) {
                FileDetails fileDtls = new FileDetails();
                fileDtls.setOriginalFileName(originalFilename);
                fileDtls.setDisplayFileName(getDisplayName(originalFilename));
                fileDtls.setUploadFileName(uploadfileName);
                fileDtls.setFileSize(file.getSize());
                fileDtls.setPath(storePath);
                FileDetails saveFileDtls = fileRepo.save(fileDtls);
                return saveFileDtls;
            }
        }

        return null;
    }

    private String getDisplayName(String originalFilename) {
        // java_programming_tutorials.pdf
        // java_prog.pdf
        String extension = FilenameUtils.getExtension(originalFilename);
        String fileName = FilenameUtils.removeExtension(originalFilename);

        if (fileName.length() > 8) {
            fileName = fileName.substring(0, 7);
        }
        fileName = fileName + "." + extension;
        return fileName;
    }

    private void checkCategoryExist(CategoryDto category) throws Exception {
        categoryRepo.findById(category.getId()).orElseThrow(() -> new ResourceNotFoundException("category id invalid"));
    }

    @Override
    public List<NotesDto> getAllNotes() {
        return notesRepo.findAll().stream().map(note -> mapper.map(note, NotesDto.class)).toList();
    }

    @Override
    public byte[] downloadFile(FileDetails fileDetails) throws ResourceNotFoundException, IOException {
        InputStream io= new FileInputStream(fileDetails.getPath());
        return StreamUtils.copyToByteArray(io);
    }

    @Override
    public FileDetails getFileDetails(Integer id) throws ResourceNotFoundException {
        FileDetails fileDetails = fileRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("file is not available"));

        return fileDetails;
    }

}
