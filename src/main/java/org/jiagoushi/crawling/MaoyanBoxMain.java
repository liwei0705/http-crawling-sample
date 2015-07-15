package org.jiagoushi.crawling;

import java.io.IOException;
import java.util.List;

import lombok.Data;
import org.apache.http.client.ClientProtocolException;
import org.jiagoushi.crawling.common.Crawler;

import com.alibaba.fastjson.JSON;

/**
 * 抓取JSON的Sample（抓取猫眼票房数据）
 *
 */
public class MaoyanBoxMain {

	public static void main(String[] args) {
		try {
			String strJson = Crawler.crawling("http://piaofang.maoyan.com/history/date/box.json?date=2015-07-14&cnt=10");

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


}
