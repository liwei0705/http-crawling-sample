package org.jiagoushi.crawling.zhuanzi;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.jiagoushi.crawling.common.Crawler;
import org.jiagoushi.crawling.jiwei.CaseData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ZhuanziBoxCrawler {
	
	public static void main(String[] args) {
		new ZhuanziBoxCrawler().excute();
	}
	
	public void excute() {
		try {
			crawling("http://111.205.151.7/movies/2016-01-26");

			System.out.println("完毕");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static final int NAME = 0;
	private static final int TODAY_BOX= 1;
	private static final int TODAY_SHOWCNT = 2;
	
	
	public HashMap<String, ZhuanziMovieData> crawling(String url) throws ClientProtocolException, IOException {
		String strContent = Crawler.crawling(url);
		Document doc = Jsoup.parse(strContent);
		Elements trElms = doc.select("table.tlist_a").select("tbody").select("tr");
		HashMap<String, ZhuanziMovieData> map = new HashMap<String, ZhuanziMovieData>();

		for (int i = 0; i < trElms.size(); i++) {
			ZhuanziMovieData d = new ZhuanziMovieData();
			//<tr>
			//<td>星球大战：原力觉醒</td>
			//<td>871<i>76,157</i></td>
			//<td>17,623场<i>249,972</i></td>
			//<td>343<i>39.39%</i></td>
			//<td>528<i>60.61%</i></td>
			//</tr>
			
			Element trElm = trElms.get(i);
			Elements tdElms = trElm.select("td");
			
			Element tdName = tdElms.get(NAME);
			d.setName(tdName.ownText());
			
			Element tdBox = tdElms.get(TODAY_BOX);
			d.setTodayBox(toNum(tdBox.ownText())*1000000);
			d.setTotalBox(toNum(tdBox.select("i").get(0).ownText())*1000000);
			

			Element tdShowCnt = tdElms.get(TODAY_SHOWCNT);
			d.setTodayShowCnt((int) toNum(tdShowCnt.ownText()));
			d.setTodaySeatCnt((int) toNum(tdShowCnt.select("i").get(0).ownText()));

			map.put(d.getName(), d);
		}
		
		System.out.println(map);
		return map;
	}
	
	private long toNum(String str){
		String numStr = str.replace(",", "").replace("场", "");
		try{
			return Long.valueOf(numStr);
		}catch(Exception ex){
			return 0l;
		}
	}
}
