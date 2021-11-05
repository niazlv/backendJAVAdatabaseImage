package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import org.apache.commons.io.FileUtils;

@Controller
public class FileUpload {
    @RequestMapping(value="/upload", method=RequestMethod.GET)
    public @ResponseBody String provideUploadInfo() {
        return "Вы можете загружать файл с использованием того же URL.";
    }

    @RequestMapping(value="/upload", method= RequestMethod.POST)
    public @ResponseBody
    String handleFileUpload(@RequestParam("file") MultipartFile file) {
        String[] path;
        String name = file.getOriginalFilename();
        System.out.println(name);
        path = name.split("\\.");

        if (path[path.length - 1].equals("jpg") || path[path.length - 1].equals("jpeg")) {
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    //BufferedOutputStream stream =
                    //        new BufferedOutputStream(new FileOutputStream(new File(name + "-uploaded")));
                    File files = new File(name);
                    FileUtils.writeByteArrayToFile(files, bytes);

                    int id = SQL.SQLadd(name, files);
                    files.delete();
                    //stream.write(bytes);
                    //stream.close();
                    return "" + id;
                } catch (Exception e) {
                    return "err";
                }
            } else {
                return "file is null";
            }
        }
        else
            return "file not *.jpg || *.jpeg";
    }
}
