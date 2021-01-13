package org.jiagoushi.crawling.common;

import java.io.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class Crawler {
	public static String crawling(String url) throws ClientProtocolException, IOException {

		// 创建HttpClient实例
		HttpClient httpclient = HttpClientBuilder.create().build();
		// 创建Get方法实例
		HttpGet httpgets = new HttpGet(url);

		httpgets.addHeader("User-Agent",
				"Mozilla/5.0 (iPhone; CPU iPhone OS 8_4 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Mobile/12H143 Mtime_iPhone_Showtime_Hybird/9.1.2(WebView Width 320 Height 510) (Device iPhone6,2)");

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

	/**
	 * 从网络上下载图片（代码来自于网络）
	 */
	public static void crawlPicture(String url, String dirPath,
									   String filePath) {

		DefaultHttpClient httpclient = new DefaultHttpClient();

		HttpGet httpget = new HttpGet(url);

		httpget
				.setHeader(
						"User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.79 Safari/537.1");
		httpget
				.setHeader("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");

		try {
			HttpResponse resp = httpclient.execute(httpget);
			if (HttpStatus.SC_OK == resp.getStatusLine().getStatusCode()) {
				HttpEntity entity = resp.getEntity();

				InputStream in = entity.getContent();

				savePicToDisk(in, dirPath, filePath);

				System.out.println("保存图片 "+filePath+" 成功....");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

	/**
	 * 将图片写到 硬盘指定目录下
	 * @param in
	 * @param dirPath
	 * @param filePath
	 */
	private static void savePicToDisk(InputStream in, String dirPath,
									  String filePath) {

		try {
			File dir = new File(dirPath);
			if (dir == null || !dir.exists()) {
				dir.mkdirs();
			}

			//文件真实路径
			String realPath = dirPath.concat(filePath);
			File file = new File(realPath);
			if (file == null || !file.exists()) {
				file.createNewFile();
			}

			FileOutputStream fos = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len = 0;
			while ((len = in.read(buf)) != -1) {
				fos.write(buf, 0, len);
			}
			fos.flush();
			fos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
