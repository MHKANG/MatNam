package com.POM.MatNam.File.Controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.POM.MatNam.File.Payload.UploadFile;
import com.POM.MatNam.File.Service.FileUploadDownloadService;

import lombok.extern.java.Log;
@Log
@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("feature/upload")
public class FileUploadController {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private FileUploadDownloadService service;
    
    @GetMapping("/uploadFiles")
    public Iterable<UploadFile> getUploadFileList(){
        return service.getFileList();
    }
    
    @GetMapping("/uploadFile/{id}")
    public Optional<UploadFile> getUploadFile(@PathVariable int id){
        return service.getUploadFile(id);
    }
    @PostMapping("/uploadFile")
    public UploadFile uploadFile(@RequestParam("file") MultipartFile file) {
        UploadFile uploadFile = service.storeFile(file);
        
        return uploadFile;
    }
    
    @PostMapping("/uploadMultipleFiles")
    public List<UploadFile> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files){
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }
    
    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request){
         // Load file as Resource
        Resource resource = service.loadFileAsResource(fileName);
 
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
 
        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
 
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    
//    @RequestMapping("/uploadFile")
//	public void forGues() {
//		log.info("file==========================");
//	}

}
