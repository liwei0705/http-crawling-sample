package org.jiagoushi.crawling.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class Crawler {
	public static String crawlingVpiao(String strUrl) throws ClientProtocolException, IOException {
        /**   
         * 首先要和URL下的URLConnection对话。 URLConnection可以很容易的从URL得到。比如： // Using   
         *  java.net.URL and //java.net.URLConnection   
         *   
         *  使用页面发送请求的正常流程：在页面http://www.faircanton.com/message/loginlytebox.asp中输入用户名和密码，然后按登录， 
         *  跳转到页面http://www.faircanton.com/message/check.asp进行验证 
         *  验证的的结果返回到另一个页面 
         *   
         *  使用java程序发送请求的流程：使用URLConnection向http://www.faircanton.com/message/check.asp发送请求 
         *  并传递两个参数：用户名和密码 
         *  然后用程序获取验证结果 
         */    
        URL url = new URL(strUrl);     
        URLConnection connection = url.openConnection();     
        /**   
         * 然后把连接设为输出模式。URLConnection通常作为输入来使用，比如下载一个Web页。   
         * 通过把URLConnection设为输出，你可以把数据向你个Web页传送。下面是如何做：   
         */    
        connection.setDoOutput(true);     
        /**   
         * 最后，为了得到OutputStream，简单起见，把它约束在Writer并且放入POST信息中，例如： ...   
         */    
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "8859_1");     
        out.write("pushToken=xxxxxx"); //向页面传递数据。post的关键所在！     
        
        

        
        
        
        
//        Accept	
//        application/json, text/javascript
       
//        Accept-Encoding	
//        gzip, deflate
//        Accept-Language	
//        zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3
//        Connection	
//        keep-alive
//        Content-Length	
//        88
//        Content-Type	
//        application/json
//        Host	
//        boxoffice.wepiao.com
//        Origin	
//        http://piaofang.wepiao.com
//        Referer	
//        http://piaofang.wepiao.com/?from=message&isappinstalled=0
//        User-Agent	
//        Mozilla/5.0 (Windows NT 6.1; WOW64; rv:44.0) Gecko/20100101 Firefox/44.0
        
        
        
        
        
        
        
        
        
        // remember to clean up     
        out.flush();     
        out.close();     
        /**   
         * 这样就可以发送一个看起来象这样的POST：    
         * POST /jobsearch/jobsearch.cgi HTTP 1.0 ACCEPT:   
         * text/plain Content-type: application/x-www-form-urlencoded   
         * Content-length: 99 username=bob password=someword   
         */    
        // 一旦发送成功，用以下方法就可以得到服务器的回应：     
//        String sCurrentLine;     
//        String sTotalString;     
//        sCurrentLine = "";     
//        sTotalString = "";     
        InputStream l_urlStream;     
        l_urlStream = connection.getInputStream();   
        
        return convertStreamToString(l_urlStream);
//        // 传说中的三层包装阿！     
//        BufferedReader l_reader = new BufferedReader(new InputStreamReader(     
//                l_urlStream));     
//        while ((sCurrentLine = l_reader.readLine()) != null) {     
//            sTotalString += sCurrentLine + "/r/n";     
//    
//        }     
//        System.out.println(sTotalString);     
//        
//        
//        return sTotalString;

	}
	
	
	
	public static String crawling(String url) throws ClientProtocolException, IOException {

		// 创建HttpClient实例
		HttpClient httpclient = HttpClientBuilder.create().build();
		// 创建Get方法实例
		HttpGet httpgets = new HttpGet(url);

		httpgets.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");

		HttpResponse response = httpclient.execute(httpgets);
		if(response.getStatusLine().getStatusCode() == 404){
			return "";
		}
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream instreams = entity.getContent();
			String str = convertStreamToString(instreams);
//			System.out.println("TEST URL: " + url);
//			System.out.println(str);

			httpgets.abort();
			return str;
		}

		return "";

	}
	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
