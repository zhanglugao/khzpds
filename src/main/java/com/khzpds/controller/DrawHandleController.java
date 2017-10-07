package com.khzpds.controller;

import com.khzpds.base.BaseController;
import com.khzpds.base.SystemConfig;
import com.khzpds.service.UserCompletionItemApplyService;
import com.khzpds.util.ImageHelper;
import com.khzpds.vo.UserCompletionItemApplyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 处理上传的图片
 */
@RequestMapping("/drawHandle")
@Controller
public class DrawHandleController extends BaseController {

    @Autowired
    private UserCompletionItemApplyService userCompletionItemApplyService;

    @RequestMapping("/rotateImage")
    public void rotateImage(String id,String degree, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", "1");
        UserCompletionItemApplyInfo applyInfo = userCompletionItemApplyService.findById(id);
        if (applyInfo == null || applyInfo.getFilePath() == null) {
            result.put("error_desc", "图片文件不存在");
            this.writeJson(response, result);
            return;
        }
        String filePath = SystemConfig.getUploadDir() + applyInfo.getFilePath();
        try {
            ImageHelper.rotateImage(filePath, Integer.parseInt(degree), filePath);
            result.put("status", "0");
            this.writeJson(response, result);
        } catch (IOException e) {
            e.printStackTrace();
            result.put("error_desc", "图片文件不存在");
            this.writeJson(response, result);
        }
    }
}
