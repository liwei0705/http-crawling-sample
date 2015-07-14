package org.jiagoushi.crawling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import lombok.Data;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import com.alibaba.fastjson.JSON;

public class Main {

	public static void main(String[] args) {
		try {
			String strJson = crawling("http://piaofang.maoyan.com/history/date/box.json?date=2015-07-14&cnt=10");

			MaoyanBox obj = JSON.parseObject(strJson, MaoyanBox.class);

			List<MaoyanBoxData> datas = obj.getData();
			for(MaoyanBoxData data : datas){
				System.out.println(data);
			}
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Data
	static class MaoyanBox {
		long pubDate;
		String queryDate;
		double totalBox;
		List<MaoyanBoxData> data;
	}

	@Data
	static class MaoyanBoxData {
		double boxRate;			// "42.80",
		double dailyBoxOffice;	// "1409.0" 
		int movieId;			// 246397,
		String movieName;		// "西游记之大圣归来",
		int releaseDay;			// 5,
		double showRate;		// "20.39",
		boolean showZero;		// false,
		long sumBoxOffice;		// "14871"
	}

	public static String crawling(String url) throws ClientProtocolException, IOException {

		// 创建HttpClient实例
		HttpClient httpclient = HttpClientBuilder.create().build();
		// 创建Get方法实例
		HttpGet httpgets = new HttpGet(url);

		httpgets.addHeader("User-Agent",
				"Mozilla/5.0 (iPhone; CPU iPhone OS 8_4 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Mobile/12H143 Mtime_iPhone_Showtime_Hybird/9.1.2(WebView Width 320 Height 510) (Device iPhone6,2)");

		HttpResponse response = httpclient.execute(httpgets);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream instreams = entity.getContent();
			String str = convertStreamToString(instreams);
			System.out.println("TEST URL: " + url);
			System.out.println(str);

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
