package com.example.join.controller;

import com.example.join.dto.Input;
import com.example.join.entity.User;
import com.example.join.service.ProductService;
import com.example.join.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/save-user")
    public User saveWithProduct(@RequestBody Input input){
        return userService.saveData(input);
    }

    @GetMapping("/get-all-user")
    public ResponseEntity<List<User>> getAllUser() throws Exception {
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }

    @GetMapping("/get-user-by-id")
    public Optional<User> getUserById(@RequestParam Integer id){

        return userService.getUserById(id);
    }

    @GetMapping("/download-multi-file")
    public void downloadFiles(@RequestParam List<String> filePath, HttpServletResponse response){
        System.out.println("Hii");
        userService.downloadFiles(filePath, response);
    }

    @PostMapping("/upload-multi-file")
    public String uploadFiles(@RequestParam MultipartFile[] files){
        return  userService.uploadFiles(files);
    }

    @GetMapping ("/download-by-url")
    public String downloadByUrl(@RequestParam List<String> imageUrls){
        return userService.downloadByUrl(imageUrls);
    }
}
