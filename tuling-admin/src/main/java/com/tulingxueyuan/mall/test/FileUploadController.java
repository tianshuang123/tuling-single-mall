package com.tulingxueyuan.mall.test;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @title FileUploadController
 * @date 2023/9/30 16:33
 * @author ts happy boy
 * @description TODO
 */

/**
 * @BelongsProject: tuling-single-mall
 * @BelongsPackage: com.tulingxueyuan.mall.test
 * @Author: ts  happyBoy
 * @CreateTime: 2023-09-30  16:33
 * @Description: TODO
 * @Version: 1.0
 */



@RestController
public class FileUploadController {
    //POST请求接口为/upload
    @PostMapping(value = "/upload",produces = "application/json;charset=utf-8")
    //@RequestParam指向前端input file的name,加入HttpServletRequest请求
    public String upload(@RequestParam("uploadFile") MultipartFile[] multipartFiles, HttpServletRequest request){
        //设置当前日期
        String uploaddate= new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //设置文件上传保存文件路径：保存在项目运行目录下的uploadFile文件夹+当前日期
        String savepath = request.getSession().getServletContext().getRealPath("/uploadFile/")+uploaddate;
        //创建文件夹,当文件夹不存在时，创建文件夹
        File folder = new File(savepath);
        if(!folder.isDirectory()){
            folder.mkdir();
        }
        //建立一个listmap的返回参数
        List<Map<String,Object>> listmap =new ArrayList<>();
        //建立一个循环分别接收多文件
        for(MultipartFile file:multipartFiles){
            //重命名上传的文件,为避免重复,我们使用UUID对文件分别进行命名
            String oldname=file.getOriginalFilename();//getOriginalFilename()获取文件名带后缀
            //UUID去掉中间的"-",并将原文件后缀名加入新文件
            String newname= UUID.randomUUID().toString().replace("-","")
                    +oldname.substring(oldname.lastIndexOf("."));
            //建立每一个文件上传的返回参数
            Map<String,Object> map=new HashMap<>();
            //文件保存操作
            try {
                file.transferTo(new File(folder,newname));
                //建立新文件路径,在前端可以直接访问如http://localhost:8080/uploadFile/2021-07-16/新文件名(带后缀)
                String filepath=request.getScheme()+"://"+request.getServerName()+":"+
                        request.getServerPort()+"/uploadFile/"+uploaddate+"/"+newname;
                //写入返回参数
                map.put("[oldname]",oldname);
                map.put("[newname]",newname);
                map.put("[filepath]",filepath);
                map.put("[result]","成功!");
                listmap.add(map);
            }catch (IOException ex){
                //操作失败报错并写入返回参数
                ex.printStackTrace();
                map.put("[oldname]",oldname);
                map.put("[newname]",newname);
                map.put("[filepath]","");
                map.put("[result]","失败!");
            }
        }
        //将执行结果返回
        return listmap.toString();
    }
}



