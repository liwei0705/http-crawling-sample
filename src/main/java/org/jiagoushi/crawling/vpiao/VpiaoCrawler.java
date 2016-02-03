package org.jiagoushi.crawling.vpiao;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.jiagoushi.crawling.common.Crawler;

import com.alibaba.fastjson.JSON;

public class VpiaoCrawler {
	public static void main(String[] args) {
		try {
			String r = Crawler.crawlingVpiao("http://boxoffice.wepiao.com/wepiaoProfessional/api/movieBoxOffice/0/index");
			
			VpiaoMovieData obj = JSON.parseObject(r, VpiaoMovieData.class);
			
			
			System.out.println(obj);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
