package com.alpidi.security.sevices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alpidi.exception.FileStorageException;
import com.alpidi.exception.MyFileNotFoundException;
import com.alpidi.repository.ListingPropertiesRepository;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;


import javax.annotation.PostConstruct;
import org.apache.commons.io.FilenameUtils;
@Service
public class FileStorageService {
	
	@Value("")
   private Path fileStorageLocation;
    
    @Value("${upload.path}")
    private String uploadPath;
    @Autowired
    ListingPropertiesRepository listingPropertiesRepository;
    @PostConstruct
    public void init() {
    	 this.fileStorageLocation = Paths.get(uploadPath)
                 .toAbsolutePath().normalize();

         try {
             Files.createDirectories(this.fileStorageLocation);
         } catch (Exception ex) {
             throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
         }
    }
    
public Boolean createDir(Path location) {
	 try {
         Files.createDirectories(location);
         return true;
     } catch (Exception ex) {
         throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
         
     }
}
    public String storeFile(MultipartFile file,String listingid,String shopid) {
        
    	Date date = new Date();
		
        String fileName = StringUtils.cleanPath(shopid +"_"+ listingid +"_"+ date.getTime() + '.' + FilenameUtils.getExtension(file.getOriginalFilename()));
        try {
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            String location = uploadPath + File.separator + shopid +File.separator + listingid;
            
            this.fileStorageLocation = Paths.get(location).toAbsolutePath().normalize();

            try {            	
            	this.createDir(this.fileStorageLocation);            	
            }
            catch(Exception ex) {
            	
            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.createFile(targetLocation);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName, String shopid, String listingid) {
        try {
        	
        	  this.fileStorageLocation = Paths.get(uploadPath + File.separator + shopid +File.separator + listingid)
                      .toAbsolutePath().normalize();
        	
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }
}
