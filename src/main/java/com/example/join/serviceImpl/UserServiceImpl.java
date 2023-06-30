package com.example.join.serviceImpl;

import com.example.join.dto.Input;
import com.example.join.entity.User;
import com.example.join.exception.UserNotFoundException;
import com.example.join.repository.UserRepo;
import com.example.join.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public User saveData(Input input) {

        return userRepo.save(input.getUser());
    }

    @Override
    public List<User> getAllUser() throws Exception {
        List<User> users = new ArrayList<>();
        users = userRepo.findAll();
        if (users.isEmpty()){
            throw new Exception("No users found");
        }
        return users;
    }

    @Override
    public Optional<User> getUserById(Integer id) {
        if (!userRepo.existsById(id)){
           throw new UserNotFoundException("User not found with the Id : "+id);
        }
        return userRepo.findById(id);
    }

    @Override
//    public List<String>
    public void downloadFiles(List<String> filePath, HttpServletResponse response) {
        response.setContentType("application/zip"); // zip archive format
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
                .filename("download.zip", StandardCharsets.UTF_8)
                .build()
                .toString());


        // Archiving multiple files and responding to the client
        try(ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream())){
            for (String file : filePath) {
                Path path = Path.of(file);
                System.out.println("path "+path+" file name "+path.getFileName().toString());
                try (InputStream inputStream = Files.newInputStream(path)) {
                    zipOutputStream.putNextEntry(new ZipEntry(path.getFileName().toString()));
                    StreamUtils.copy(inputStream, zipOutputStream);
                    zipOutputStream.flush();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<String> fileNames = new ArrayList<>();
        for (String file : filePath) {
            Path path = Path.of(file);
           fileNames.add(path.getFileName().toString());
        }
        System.out.println("::::::::::::"+fileNames);
//        return fileNames;
    }
}
