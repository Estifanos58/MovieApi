package com.movieflix.movieApi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
        // get name of the file
        String fileName = file.getOriginalFilename();
        logger.info("Uploading file: {}", fileName);

        // get the file path
        String filePath = path + File.separator + fileName;

        // create directory if not exists
        File f = new File(path);
        if (!f.exists()) {
            f.mkdir(); // or use f.mkdirs() to create parent directories
            logger.info("Created directory: {}", path);
        }

        // copy the file or upload file to the path
        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        logger.info("File uploaded to: {}", filePath);
        return fileName;
    }

    @Override
    public InputStream getResourceFile(String path, String filename) throws FileNotFoundException {
        String filePath = path + File.separator + filename;

        // Add logging for resource retrieval
        logger.info("Fetching file: {}", filePath);
        return new FileInputStream(filePath);
    }
}






//package com.movieflix.movieApi.service;
//
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//
//@Service
//public class FileServiceImpl implements FileService{
//    @Override
//    public String uploadFile(String path, MultipartFile file) throws IOException {
//
//        // get name of the file
//        String fileName = file.getOriginalFilename();
//
//        // get the file path
//        String filePath = path + File.separator + fileName;
//
//        // create file object
//        File f = new File(path);
//        if(!f.exists()){
//            f.mkdir();
//        }
//
//        // copy the file or upload file to the path
//        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
//
//
//        return fileName;
//    }
//
//    @Override
//    public InputStream getResourceFile(String path, String filename) throws FileNotFoundException {
//        String filePath = path + File.separator + filename;
//
//        return new FileInputStream(filePath);
//    }
//}
