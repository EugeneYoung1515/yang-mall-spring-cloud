package com.ywcjxf.mall.web.controller;


import com.ywcjxf.mall.serviceinterface.IndexAdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.ywcjxf.mall.constant.FileLocation.AD_IMAGE;

@Controller
public class IndexAdImageController {

    private IndexAdService indexAdService;

    private static final Logger logger = LoggerFactory.getLogger(IndexAdImageController.class);

    @RequestMapping("/uploadimage")
    public String getImageUploadPage(){
        return "c_uploadImagePage";
    }

    @PostMapping("/image/upload")
    public String imageUpload(MultipartFile file, RedirectAttributes redirectAttributes){
        if(!file.isEmpty()) {
            //这下面的部分要不要放到业务层里

            String uuid = UUID.randomUUID().toString().replace("-", "");
            String fileName = file.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            File file1 = new File(AD_IMAGE + uuid + suffix);//这里硬编码了
            //要新建 adimage 不然下面的transferTo会报异常
            try {
                file.transferTo(file1);

                indexAdService.insertImage(uuid+suffix);

                redirectAttributes.addFlashAttribute("msg","上传成功");
                return "redirect:/image/upload/result";
            }catch (IOException ex){
                //ex.printStackTrace();

                logger.error("file transfer"+"_"+ex.getMessage(),ex);

                //redirectAttributes.addFlashAttribute("msg","上传上传失败，请重新上传");
                //return "redirect:/uploadimage";
            }
        }
        redirectAttributes.addFlashAttribute("msg","上传失败，请重新上传");
        return "redirect:/uploadimage";
    }

    @RequestMapping("/image/upload/result")
    public String uploadImageResult(){
        return "c_uploadImageResult";
    }

    @Autowired
    public void setIndexAdService(IndexAdService indexAdService) {
        this.indexAdService = indexAdService;
    }
}
