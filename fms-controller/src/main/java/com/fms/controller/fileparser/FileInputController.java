package com.fms.controller.fileparser;

import com.fms.domain.filemanage.Control;
import org.springframework.beans.factory.annotation.Autowired;
import com.fms.domain.filemanage.FileInput;
import com.fms.service.filemanage.ControlService;
import com.fms.service.filemanage.FileInputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/fileInput")
public class FileInputController {
    @Autowired
    private FileInputService fileInputService;
    @Autowired
    private Environment env;

    @RequestMapping("getList")
    public Object getList(Map<String, Object> params) {
        return fileInputService.getList(params);
    }


    @RequestMapping("get")
    public Object get(String id) {
        return fileInputService.get((Long.parseLong(id)));
    }

    @RequestMapping("add")
    public void add(@RequestParam Map<String, Object> params) {
        String ip= (String)params.get("ip");
        String userName= (String)  params.get("userName");
        String password= (String)  params.get("password");
        String port= (String)  params.get("port");
        String path= (String)   params.get("path");
        FileInput fileInput=new FileInput();
        fileInput.setIp(ip);
        fileInput.setUserName(userName);
        fileInput.setPassword(password);
        fileInput.setPort(port);
        fileInput.setPath(path);
        fileInputService.add(fileInput);
    }


    @RequestMapping("update")
    public void update(@RequestParam Map<String, Object> params) {
        String id = (String)params.get("id");
        String ip= (String)params.get("ip");
        String userName= (String)  params.get("userName");
        String password= (String)  params.get("password");
        String port= (String)  params.get("port");
        String path= (String)   params.get("path");
        FileInput fileInput=fileInputService.queryId(Long.parseLong(id));
        fileInput.setIp(ip);
        fileInput.setUserName(userName);
        fileInput.setPassword(password);
        fileInput.setPort(port);
        fileInput.setPath(path);
        fileInputService.update(fileInput);
    }
    @RequestMapping("delete")
    public void delete(String id) {
        fileInputService.delete(Long.parseLong(id));
    }

}
