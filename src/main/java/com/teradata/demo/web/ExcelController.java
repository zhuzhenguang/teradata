package com.teradata.demo.web;

import com.teradata.demo.service.ExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Excel
 * Created by zhu on 14-1-20.
 */
public class ExcelController {
    private static Logger logger = LoggerFactory.getLogger(ExcelController.class);

    private ExcelService excelService;

    @RequestMapping("/excel/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        logger.info("File {} upload successfully!", file.getOriginalFilename());
        //excelService.parse(file.getInputStream());
        return "success";
    }

    @RequestMapping("init")
    public String init() {
        return "init";
    }

    public void setExcelService(ExcelService excelService) {
        this.excelService = excelService;
    }
}
