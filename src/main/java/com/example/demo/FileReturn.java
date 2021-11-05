package com.example.demo;

import javafx.scene.image.Image;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.security.URIParameter;

@Controller
public class FileReturn {
    @RequestMapping(value="/download", method= RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public @ResponseBody
    HttpEntity<byte[]> provideUploadInfo(
        @RequestParam() Integer id) {
            try {
                InputStream is = SQL.SQLget(id);
                byte[] image = IOUtils.toByteArray(is);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG); //or what ever type it is
                headers.setContentLength(image.length);
                return new HttpEntity<byte[]>(image,headers);
            } catch (IOException | NullPointerException e) {
                return new HttpEntity<byte[]>("error".getBytes());
            }
            //return null;
    }
}
