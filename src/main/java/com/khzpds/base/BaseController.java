package com.khzpds.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.khzpds.util.UUIDUtil;


public class BaseController {
	private static final Log log = LogFactory.getLog(BaseController.class);
	protected void writeJson(HttpServletResponse response, Object object)  {
		try {
			String json = JSON.toJSONString(object, SerializerFeature.WriteDateUseDateFormat);

			// log.info("转换后的JSON字符串：" + json);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(json);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
	}
	protected void writeString(HttpServletResponse response, String object){
		try {
		// log.info("转换后的JSON字符串：" + json);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(object);
		response.getWriter().flush();
		response.getWriter().close();
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
	}

	/**
	 * 获取分页参数
	 * 
	 * @param request
	 * @return
	 */
	public PageParameter getPageParameter(HttpServletRequest request) {
		PageParameter pageParameter = new PageParameter();
		if(request.getParameter("rows")!=null){
			pageParameter.setPageSize(Integer.parseInt(request.getParameter("rows")));
		}
		if(request.getParameter("page")!=null){
			pageParameter.setCurrentPage(Integer.parseInt(request.getParameter("page")));	
		}
		if(request.getParameter("sort")!=null){
			pageParameter.setOrderField(request.getParameter("sort"));
		}
		if(request.getParameter("order")!=null){
			pageParameter.setOrderType(request.getParameter("order"));
		}
		return pageParameter;
	}
	
	/**
	 * 获取分页参数
	 * 
	 * @param request
	 * @return
	 */
	public PageParameter getPageParameter2(HttpServletRequest request) {
		PageParameter page=new PageParameter();
		String pageSize=request.getParameter("page_size");
		String currentPage=request.getParameter("current_page");
		if(pageSize==null){
			page.setPageSize(5);
		}else{
			page.setPageSize(Integer.parseInt(pageSize));
		}
		if(currentPage==null){
			page.setCurrentPage(1);
		}else{
			page.setCurrentPage(Integer.parseInt(currentPage));
		}
		return page;
	}

//	protected int getPlatformId(HttpServletRequest request) {
//		return 1;
//	}
//	
//	protected String getCurrentUserId(HttpServletRequest request) {
//		return "woshi";
//	}
	protected SessionInfo getCurrentSessionInfo(HttpServletRequest request) {
	  //TODO:获取session 中的 key �?currentUser，并转换�?SessionInfo
		SessionInfo sessionInfo=(SessionInfo)request.getSession().getAttribute(BusinessConfig.USER_SESSION_KEY);
	    return sessionInfo; 
    }
	
	protected void setCurrentSessionInfo(HttpServletRequest request) {
	    //TODO:登录后设置sessionInfo，并存入 session�?
    }
	
	protected String getRootPath(HttpServletRequest request) {
		request.setAttribute("userName", this.getCurrentSessionInfo(request).getUserName());
	    return "";
	}

	/**
	 * 文件上传
	 * 
	 * @param multiparFile
	 * @return
	 * @throws IOException
	 */
	protected String fileupload(MultipartFile multiparFile, String path)  {
		String result = "";
		try{
    		File filepaht1 = new File(path);
    		if (!filepaht1.exists()) {
    			filepaht1.mkdirs();
    			log.info("==============================文件夹已经创建！！！！！�?===========================");
    		}
    		if (!multiparFile.isEmpty()) {
    		    String fileName = multiparFile.getOriginalFilename();
    		    int lastNum = fileName.lastIndexOf(".");
    		    String houzhui = fileName.substring(lastNum, fileName.length());
    		    result=UUIDUtil.getUUID()+houzhui;
    			String filePath = path + result;
    			File file = new File(filePath);
    			FileCopyUtils.copy(multiparFile.getBytes(), file);
    			log.info("============================文件已经上传完成！！！！�?==============================");
    			//result = filePath;
    		}
		}
		catch(Exception ex){
			log.info("================================文件上传失败！！！！！！==================================");
		    return "";
		}
		return result;
	}
	
	//用于解决日期类型装载到javabean出错的问�?
	 @InitBinder  
    protected void initBinder(HttpServletRequest request,  
                                  ServletRequestDataBinder binder) throws Exception {  
        //对于�?��转换为Date类型的属性，使用DateEditor进行处理  
        binder.registerCustomEditor(Date.class, new DateEditor());  
    } 
	
	public Object getBean(HttpServletRequest request,String beanName){
	    WebApplicationContext wc=  RequestContextUtils.getWebApplicationContext(request);
	    return wc.getBean(beanName);
	}
	
	public String download(String fileName,String folder, HttpServletRequest request,HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName="+ fileName);
        try {
            String root_path = request.getSession().getServletContext().getRealPath("/");
            String path = root_path + "WEB-INF"+File.separator+"views"+File.separator+"default"+File.separator+folder+File.separator+fileName;
//            String path = SystemConfig.getUploadDir()+ File.separator+folder+File.separator+fileName;
            InputStream inputStream = new FileInputStream(new File(path));
            OutputStream os = response.getOutputStream();
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }
             // 这里主要关闭�?
            os.close();
    
            inputStream.close();
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(),e);
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
        return null;
    }

}
