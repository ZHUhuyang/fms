package com.fms.controller.fileparser;

import com.fms.domain.filemanage.Control;
import com.fms.domain.filemanage.FileParserJar;
import com.fms.domain.filemanage.FileType;
import com.fms.service.filemanage.ControlService;
import com.handu.apollo.utils.CollectionUtil;
import com.handu.apollo.utils.ExtUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;

@RestController
@RequestMapping("/control")
public class ControlController {
    @Autowired
    private ControlService controlService;
    @Autowired
    private Environment env;

    @RequestMapping("getList")
    public Object getList(Map<String, Object> params) {
        return controlService.getList(params);
    }

  /*  @RequestMapping("add")
    public void add(Control control) {
        switch(control.getType()){
            case "网络":
                control.setRemark("net");
                break;
            case "硬件":
                control.setRemark("hardware");
                break;
            case "区块":
                control.setRemark("block");
                break;
        }
        controlService.add(control);
    }*/


    @RequestMapping("add")
    public void add(@RequestParam String name, @RequestParam String type, @RequestParam("listFile") MultipartFile picture, HttpServletRequest request, HttpServletResponse response) {
        Control control = new Control();
        control.setType(type);
        control.setName(name);
        String path = request.getSession().getServletContext().getRealPath("/upload");
        control.setLocation(path);
        File filePath = new File(path);
        System.out.println("文件的保存路径：" + path);
        if (!filePath.exists() && !filePath.isDirectory()) {
            System.out.println("目录不存在，创建目录:" + filePath);
            filePath.mkdir();
        }
        //获取原始文件名称(包含格式)
        String originalFileName = picture.getOriginalFilename();
        System.out.println("原始文件名称：" + originalFileName);
        //在指定路径下创建一个文件
        File targetFile = new File(path, originalFileName);

        //将文件保存到服务器指定位置
        try {
            picture.transferTo(targetFile);
            System.out.println("上传成功");
            //将文件在服务器的存储路径返回
        } catch (IOException e) {
            System.out.println("上传失败");
            e.printStackTrace();
        }
    }
}
