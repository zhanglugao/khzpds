package com.khzpds.base;
 
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
/**
 * 
* @ClassName: AntiSqlInterceptor 
* @Description: TODO(特殊字符转义过滤器，目前只是在查询操作时将参数中的'_'转为'\_','%'转为'\%',后期需要加在其他类型语句上时，如update语句请在注解上加入update，
* 如若是更多字符转义操作，请修改modifyLikeSql方法) 
* @author 鲁高
* @date 2016年1月21日 下午1:13:31 
*
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class }) })
public class AntiSqlInterceptor implements Interceptor {
 
    private Properties properties;
 
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        Configuration configuration = mappedStatement.getConfiguration();
        Object returnValue = null;
        //long start = System.currentTimeMillis();
        String sql = getSql(configuration, boundSql);
        returnValue = invocation.proceed();
        /*long end = System.currentTimeMillis();
        long time = (end - start);
        if (time > 1) {
            String sql = getSql(configuration, boundSql, sqlId, time);
            System.err.println(sql);
        }*/
        return returnValue;
    }
 
    public static String getSql(Configuration configuration, BoundSql boundSql) {
        String sql = showSql(configuration, boundSql);
        return sql;
    }
 
    private static String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } 
        else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } 
        else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }
 
        }
        return value;
    }
 
    public static String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        sql=modifyLikeSql(sql,parameterObject);
        /*if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
 
            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    }
                }
            }
        }*/
        return sql;
    }
 
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
 
    public void setProperties(Properties properties0) {
        this.properties = properties0;
    }
    
    public static String modifyLikeSql_todo(String sql,Object parameterObject)
    {
        if(parameterObject instanceof PageParameter){
            if(!sql.toLowerCase().contains("like"))
                return sql;
             //sql=" and OPER_REMARK LIKE '%' || #{operRemark} || '%'  \n " +"and OPER_U_NAME LIKE #{operUName} || '%' ";
            //原始表达式：\s\w+\sLIKE\s('%'\s\|{2})?\s*(#\{\w+\})\s*(\|{2}\s*'%')
            String reg="\\s\\w+\\sLIKE\\s*('%'\\s*\\|{2}\\s*)?(#\\{\\w+\\})(\\s*\\|{2}\\s*'%')?";//"order\\s+by\\s+.+"
            Pattern pattern = Pattern.compile(reg,Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(sql);
            
            List<String> replaceEscape=new ArrayList<String>();
            List<String> replaceFiled=new ArrayList<String>();
            
            while(matcher.find()){
                replaceEscape.add(matcher.group());
                 int n = matcher.groupCount();  
                 for (int i = 0; i <= n; i++)
                 {  
                    String  output = matcher.group(i);  
                    if(2==i&&output!=null)
                    {
                        replaceFiled.add(output.trim());
                    }
                 }  
               }

            //sql = matcher.replaceAll(reg+" 1111");
            
            for(String s:replaceEscape)
            {
                sql=sql.replace(s, s+" ESCAPE '/' ");
            }
            //修改参数
            HashMap<String,String> paramMab=(HashMap<String, String>)((PageParameter)parameterObject).getSearch();
            for(String s:replaceFiled)
            {
                String key=s.replace("#{", "").replace("}", "");
                String val =paramMab.get(key);
                if(val!=null &&val instanceof String&&(val.toString().contains("%")||val.toString().contains("_")))
                {
                    val=val.toString().replaceAll("%", "/%").replaceAll("_", "/_");
                    paramMab.put(key.toString(), val);
                }           
            }   
            return sql;   
        }
        else{
            return sql;         
        }
        
    }
    
    public static String modifyLikeSql(String sql,Object parameterObject)  
    {  
        if(parameterObject instanceof HashMap){  
        }
        else if(parameterObject instanceof String){
        	parameterObject=parameterObject.toString().replaceAll("%", "\\\\%").replaceAll("_", "\\\\_"); 
        }
        else{  
            return sql;           
        }  
        if(!sql.toLowerCase().contains("like"))  
            return sql;  
         //sql=" and OPER_REMARK LIKE '%' || #{operRemark} || '%'  \n " +"and OPER_U_NAME LIKE #{operUName} || '%' ";  
        //原始表达式：\s\w+\sLIKE\s('%'\s\|{2})?\s*(#\{\w+\})\s*(\|{2}\s*'%')  
        /*String reg="\\s\\w+\\sLIKE\\s*('%'\\s*\\|{2}\\s*)?(#\\{\\w+\\})(\\s*\\|{2}\\s*'%')?";//"order\\s+by\\s+.+"  
        Pattern pattern = Pattern.compile(reg,Pattern.CASE_INSENSITIVE);  
        Matcher matcher = pattern.matcher(sql);  
          
        List<String> replaceEscape=new ArrayList<String>();  
        List<String> replaceFiled=new ArrayList<String>();  
          
        while(matcher.find()){  
            replaceEscape.add(matcher.group());  
             int n = matcher.groupCount();    
             for (int i = 0; i <= n; i++)  
             {    
                String  output = matcher.group(i);    
                if(2==i&&output!=null)  
                {  
                    replaceFiled.add(output.trim());  
                }  
             }    
           }  
  
        //sql = matcher.replaceAll(reg+" 1111");  
          
        for(String s:replaceEscape)  
        {  
            sql=sql.replace(s, s+" ESCAPE '/' ");  
        }  */
        //修改参数  
        HashMap<String,Object> paramMab=(HashMap)parameterObject;  
        for(Entry<String,Object> entry:paramMab.entrySet())  
        {  
            //sql=sql.replace(s, " ? ");  
            // #{operUName} -->operUName  
            String key=entry.getKey();  
            Object val =paramMab.get(key);  
            if(val!=null &&val instanceof String&&(val.toString().contains("%")||val.toString().contains("_")))  
            {  
                val=antiSql(val.toString());
                paramMab.put(key.toString(), val);  
            }             
        }     
        return sql;     
    }  
    
    public static String antiSql(String sql){
    	sql=sql.replaceAll("%", "\\\\%");
    	if(sql.startsWith("_")){
    		sql="\\_"+sql.substring(1);
    	}
    	return sql;
    }
}