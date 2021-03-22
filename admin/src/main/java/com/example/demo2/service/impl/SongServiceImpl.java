package com.example.demo2.service.impl;

import com.example.demo2.mapper.PapperMapper;
import com.example.demo2.model.Category;
import com.example.demo2.model.Paper;
import com.example.demo2.service.SongService;
import com.example.demo2.utils.Const;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
public class SongServiceImpl implements SongService {
    @Autowired
    private PapperMapper papperMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${upPath}")
    private String UpPath;

    @Value("${ESPath}")
    private String EsPath;

    //前端检查过了 这里方便起见默认返回true
    @Override
    public boolean checkFormat(MultipartFile song) {
        return true;
    }

    /**
     * 上传歌曲
     *
     * @param request
     * @param song
     * @param
     * @param songname
     * @param selection
     * @return
     */
    @Override
    public boolean addSong(HttpServletRequest request, MultipartFile song, String songname, String selection) {
        boolean isInsertSuccessful = false;
        int affectedRows = -1;
        if (song.isEmpty()) {
            return isInsertSuccessful;
        }
        String name = UUID.randomUUID().toString().replace("-", "");
        //歌曲名称需去掉.pdf的后缀
        //保存歌曲文件
        saveFile(song, name);
        Paper paper = new Paper(Integer.valueOf(selection), "/pdf/" + name, songname);
        affectedRows = papperMapper.insertOnlypaper(paper);

        if (affectedRows > 0) {
            isInsertSuccessful = true;
            sendPost(paper,"/insertPaper");
        }
        return isInsertSuccessful;
    }

    public String sendPost(Paper paper,String url) {
        String uri = EsPath + url;

        Map<String, String> jsonMap = new HashMap<>(6);
        jsonMap.put("paperId", String.valueOf(paper.getPaperId()));
        jsonMap.put("title", paper.getTitle());
        jsonMap.put("catId", String.valueOf(paper.getCatId()));
        jsonMap.put("url", paper.getUrl());

        ResponseEntity<String> apiResponse = restTemplate.postForEntity
                (
                        uri,
                        generatePostJson(jsonMap),
                        String.class
                );
        return apiResponse.getBody();
    }

    /**
     * 生成post请求的JSON请求参数
     * 请求示例:
     * {
     * "id":1,
     * "name":"张耀烽"
     * }
     *
     * @return
     */
    public HttpEntity<Map<String, String>> generatePostJson(Map<String, String> jsonMap) {

        //如果需要其它的请求头信息、都可以在这里追加
        HttpHeaders httpHeaders = new HttpHeaders();

        MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");

        httpHeaders.setContentType(type);

        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(jsonMap, httpHeaders);

        return httpEntity;
    }


    @Override
    public Integer getTotalCount(String search) {
        if (search == null)
            return papperMapper.selectCount();
        else
            return papperMapper.selectCountByName(search);
    }

    @Override
    public List<Paper> list(Integer page, Integer size, String search) {
        int offset = size * (page - 1);
        if (offset < 0)
            offset = 0;
        List<Paper> paperList = new ArrayList<>();
        if (search == null)
            paperList = papperMapper.listByPage(offset, size);
        else
            paperList = papperMapper.SearchByPage(offset, size, search);
        return paperList;
    }


    @Override
    public boolean deleteUserByName(String songid) {
        boolean isDeleteSuccessful = false;
        int affectedRows = papperMapper.deleteUserByName(songid);
        Paper paper =new Paper();
        paper.setPaperId(Integer.parseInt(songid));
        if (affectedRows > 0) {
            isDeleteSuccessful = true;
            sendPost(paper,"/deleteIndex");
        }
        return isDeleteSuccessful;
    }

    @Override
    public List<Category> selectAllCat() {
        return papperMapper.selectAllCat();
    }


    private void saveFile(MultipartFile multipartFile, String name) {
        try {
            InputStream inputStream = multipartFile.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(UpPath + name);
            try {
                int b = 0;
                while ((b = inputStream.read()) != -1) {
                    fileOutputStream.write(b);
                }
            } finally {
                inputStream.close();
                fileOutputStream.close();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
