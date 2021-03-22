package com.example.demo2.service;

import com.example.demo2.model.Category;
import com.example.demo2.model.Paper;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SongService {
    boolean checkFormat(MultipartFile song);

    boolean addSong(HttpServletRequest request, MultipartFile song, String songname, String selection);

    Integer getTotalCount(String search);

    List<Paper> list(Integer page, Integer size, String search);

    boolean deleteUserByName(String songid);

    List<Category> selectAllCat();
}
