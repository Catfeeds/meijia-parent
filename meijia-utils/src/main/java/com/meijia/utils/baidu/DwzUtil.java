package com.meijia.utils.baidu;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import com.meijia.utils.HttpClientUtil;
import com.meijia.utils.GsonUtil;

public class DwzUtil {
	
    /**
     * 百度生成短地址Api
     * @param url 长地址
     * @return String
     * @throws MalformedURLException 
     */
    public static String dwzApi(String longUrl) throws MalformedURLException {

        String urlString = "http://dwz.cn/create.php";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("url", longUrl);
        
        String result = HttpClientUtil.post(urlString, params);
        Map<String, Object> resultMap = GsonUtil.GsonToMaps(result);
        String tinyUrl = resultMap.get("tinyurl").toString();
        return tinyUrl;
    }

 
    public static void main(String[] args) throws Exception{
        String longUrl = "http://img.51xingzheng.cn/9e8a9bf9b3f6b10fed1e86d891e8e57a?w=100&h=100";
        System.out.println(dwzApi(longUrl));
        

    }
    
    
    

}
