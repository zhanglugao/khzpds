package com.khzpds.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.khzpds.base.BaseController;
import com.khzpds.base.SystemConfig;
import com.khzpds.util.UUIDUtil;

@Controller
@RequestMapping("/productUpload")
public class ProductUploadController extends BaseController{
	
	@RequestMapping("/toPage")
	public ModelAndView toPage(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView(getRootPath(request)+"/video/video_upload2");
	}
	
	/***
	 * 文件上传 
	 * @param chunk
	 * @param chunks
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/fileUpload")
	public void upload(String uploadType,Long size,Integer chunk,String guid,Integer chunks,@RequestParam MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String,Object> result=new HashMap<String, Object>();
		if(StringUtils.isBlank(guid)){
			guid=UUIDUtil.getUUID();
		}
		//上传就行了
		Calendar c=Calendar.getInstance();
		String basePath=SystemConfig.getUploadDir()+File.separator+uploadType+File.separator+c.get(Calendar.YEAR)+(c.get(Calendar.MONTH)+1)+File.separator;
		boolean res=mkdir(basePath);
		if(!res){
			result.put("status", "1");
			result.put("error_desc", "目标文件夹不存在");
			this.writeJson(response,result);
			return;
		}
		String destFile=null;
		destFile=basePath+guid+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),file.getOriginalFilename().length());
		boolean f=uploadFile(file.getInputStream(), destFile, file.getSize(), guid, false, 0, 0,0);
		if(f){
			result.put("status", "0");
			result.put("filePath", URLEncoder.encode(destFile, "utf-8"));
			this.writeJson(response,result);
			return;
		}else{
			result.put("status", "1");
			result.put("error_desc", "上传过程出错");
			this.writeJson(response,result);
			return;
		}
	}
	
	private boolean mkdir(String path){
		File pathFile=new File(path);
		if(!pathFile.exists()){
			boolean res=pathFile.mkdirs();
			if(!res){
				return false;
			}
		}
		return true;
	}
	
	
	private boolean uploadFile(InputStream input,String destFile,long fileSize,String uuid,boolean ifChunk,int chunkNum,int totalChunk,long totalSize){
		try {
			//得到输入输出流
			FileOutputStream fos=new FileOutputStream(destFile);
			//缓冲流
			BufferedInputStream bufis=new BufferedInputStream(input);
			BufferedOutputStream bufos=new BufferedOutputStream(fos);
			//对于1G以上的文件，复制时byte[]数组的大小越大越快，对于小于1G的文件，byte[]数组
			//的大小在5M左右最快，今本人测试，比Windows的复制最多要快40秒
			//后面加上根据要复制的文件大小 动态调整缓冲区的大小 以提高复制速度
			int num=5;
			if(fileSize>1024*1024*1024){
				num=150;
			}
			byte[] by=new byte[1024*1024*num];//缓冲区大小 设为5M
			int len;  
	        boolean flag=true;  
	        while(flag)  
	        {  
				len=bufis.read(by);  
				if(len==-1)  
				{  
				    flag=false;  
				    continue;  
				}  
				bufos.write(by,0,len);  
				bufos.flush();
	        }  
	        bufos.close();  
	        bufis.close();  
            return true;
		} catch (IOException e) {
            return false;
		}
	}
}
