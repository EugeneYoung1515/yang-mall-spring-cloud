package com.ywcjxf.mall.web.controller;

import com.ywcjxf.mall.serviceinterface.ItemService;
import net.coobird.thumbnailator.Thumbnails;
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
import java.util.UUID;

import static com.ywcjxf.mall.constant.FileLocation.*;

@Controller
public class ItemImageController {
    private ItemService itemService;

    private static final Logger logger = LoggerFactory.getLogger(ItemImageController.class);

    @RequestMapping("/uploaditemimage")
    public String getImageUploadPage(){
        return "c_uploadItemImagePage";
    }

    @PostMapping("/image/item/upload")
    public String ItemImageUploadAndCreateHtml(MultipartFile file, RedirectAttributes redirectAttributes){
        if(!file.isEmpty()) {
            //这下面的部分要不要放到业务层里

            String uuid = UUID.randomUUID().toString().replace("-", "");
            String fileName = file.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            File file1 = new File(ITEM_IMAGE_FULL + uuid + suffix);//这里硬编码了
            File file2 = new File(ITEM_IMAGE_360 + uuid + suffix);//这里硬编码了
            File file3 = new File(ITEM_IMAGE_240 + uuid + suffix);//这里硬编码了
            File file4 = new File(ITEM_IMAGE_60 + uuid + suffix);//这里硬编码了
            try {
                file.transferTo(file1);
                Thumbnails.of(file1).size(360,360).toFile(file2);
                Thumbnails.of(file1).size(240,240).toFile(file3);
                Thumbnails.of(file1).size(60,60).toFile(file4);

                Integer itemId = itemService.getItemIdByNullOrderByIdLimitOne();
                itemService.insertItemImage(itemId,uuid+suffix);

                redirectAttributes.addFlashAttribute("msg","上传成功");
                redirectAttributes.addFlashAttribute("itemId",itemId);
                return "redirect:/imageanditem/result";
            }catch (IOException ex){
                //ex.printStackTrace();

                logger.error(ex.getMessage(),ex);

                //redirectAttributes.addFlashAttribute("msg","上传上传失败，请重新上传");
                //return "redirect:/uploadimage";
            }
        }
        redirectAttributes.addFlashAttribute("msg","上传失败，请重新上传");
        return "redirect:/uploaditemimage";

    }

    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }
}
