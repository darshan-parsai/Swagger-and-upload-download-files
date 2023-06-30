package com.example.join.service;

import com.example.join.dto.Input;
import com.example.join.entity.User;
import jakarta.servlet.http.HttpServletResponse;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveData(Input input);

    List<User> getAllUser() throws Exception;

    Optional<User> getUserById(Integer id);

//    List<String>
    void downloadFiles(List<String> filePath, HttpServletResponse response);
}
