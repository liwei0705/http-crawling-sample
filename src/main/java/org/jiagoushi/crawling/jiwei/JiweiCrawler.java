package org.jiagoushi.crawling.jiwei;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.http.client.ClientProtocolException;
import org.jiagoushi.crawling.common.Crawler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JiweiCrawler {
	private List<String[]> areaList;

	private List<CaseData> caseList;

	public JiweiCrawler() {
		caseList = new ArrayList<CaseData>();
		areaList = new ArrayList<String[]>();

		areaList.add(new String[] { "http://www.ccdi.gov.cn/special/bgtzt/bj_bgt/", "北京", "" });
		areaList.add(new String[] { "http://www.ccdi.gov.cn/special/bgtzt/tj_bgt/", "天津", "" });
		areaList.add(new String[] { "http://www.ccdi.gov.cn/special/bgtzt/hb_bgt/", "河北", "" });
		areaList.add(new String[] { "http://www.ccdi.gov.cn/special/bgtzt/sx_bgt/", "山西", "" });
		areaList.add(new String[] { "http://www.ccdi.gov.cn/special/bgtzt/nmg_bgt/", "内蒙古", "" });
		areaList.add(new String[] { "http://www.ccdi.gov.cn/special/bgtzt/ln_bgt/", "辽宁", "" });
		areaList.add(new String[] { "http://www.ccdi.gov.cn/special/bgtzt/jl_bgt/", "吉林", "" });
		areaList.add(new String[] { "http://www.ccdi.gov.cn/special/bgtzt/hlj_bgt/", "黑龙江", "" });
		areaList.add(new String[] { "http://www.ccdi.gov.cn/special/bgtzt/sh_bgt/", "上海", "" });
		areaList.add(new String[] { "http://www.ccdi.gov.cn/special/bgtzt/js_bgt/", "江苏", "" });
		areaList.add(new String[] { "http://www.ccdi.gov.cn/special/bgtzt/zj_bgt/", "浙江", "" });
		areaList.add(new String[] { "http://www.ccdi.gov.cn/special/bgtzt/ah_bgt/", "安徽", "" });
		areaList.add(new String[] { "http://www.ccdi.gov.cn/special/bgtzt/fj_bgt/", "福建", "" });
		areaList.add(new String[] { "http://www.ccdi.gov.cn/special/bgtzt/jx_bgt/", "江西", "" });
		areaList.add(new String[] { "http://www.ccdi.gov.cn/special/bgtzt/sd_bgt/", "山东", "" });
		areaList.add(new String[] { "http://www.ccdi.gov.cn/special/bgtzt/hn_bgt/", "河南", "" });
		areaList.add(new String[] { "http://www.ccdi.gov.cn/special/bgtzt/hubei_bgt/", "湖北", "" });
		areaList.add(new String[] { "http://www.ccdi.gov.cn/special/bgtzt/hunan_bgt/", "湖南", "" });
		areaList.add(new String[] { "http://www.ccdi.gov.cn/special/bgtzt/gd_bgt/", "广东", "" });
		areaList.add(new String[] { "http://www.ccdi.gov.cn/special/bgtzt/gx_bgt/", "广西", "" });
		areaList.add(new String[] { "http://www.ccdi.gov.cn/special/bgtzt/hainan_bgt/", "海南", "" });
		areaList.add(new String[] { "http://www.ccdi.gov.cn/special/bgtzt/cq_bgt/", "重庆", "" });
		areaList.add(new String[] { "http://www.ccdi.gov.cn/special/bgtzt/sc_bgt/", "四川", "" });
		areaList.add(new String[] { "http://www.ccdi.gov.cn/special/bgtzt/gz_bgt/", "贵州", "" });
		areaList.add(new String[] { "http://www.ccdi.gov.cn/special/bgtzt/yn_bgt/", "云南", "" });
		areaList.add(new String[] { "http://www.ccdi.gov.cn/special/bgtzt/xz_bgt/", "西藏", "" });
		areaList.add(new String[] { "http://www.ccdi.gov.cn/special/bgtzt/shanxi_bgt/", "陕西", "" });
		areaList.add(new String[] { "http://www.ccdi.gov.cn/special/bgtzt/gs_bgt/", "甘肃", "" });
		areaList.add(new String[] { "http://www.ccdi.gov.cn/special/bgtzt/qh_bgt/", "青海", "" });
		areaList.add(new String[] { "http://www.ccdi.gov.cn/special/bgtzt/nx_bgt/", "宁夏", "" });
		areaList.add(new String[] { "http://www.ccdi.gov.cn/special/bgtzt/xj_bgt/", "新疆", "" });
		areaList.add(new String[] { "http://www.ccdi.gov.cn/special/bgtzt/bt_bgt/", "新疆生产建设兵团", "" });
		areaList.add(new String[] { "http://www.ccdi.gov.cn/special/bgtzt/zyhgjjj/", "中央和国家机关 国企 金融机构", "" });

	}

	public void excute() {
		try {
			for (String[] area : areaList) {
				crawlingArea(area[0], area[1], area[2]);
			}

			writeCsvFile("C:/曝光信息抓取结果.csv");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得一个省份的数据
	 * 
	 * @param url
	 * @param areaName
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private void crawlingArea(String url, String areaName, String areaId) throws ClientProtocolException, IOException {
		String strContent = Crawler.crawling(url);
		crawlingOnePage(areaId, areaName, url, strContent);

		for (int i = 1; i < 20; i++) {
			String urlPage = url + "index_" + i + ".html";
			strContent = Crawler.crawling(urlPage);
			if (null == strContent || strContent.length() == 0) {
				break;
			}
			crawlingOnePage(areaId, areaName, url, strContent);
		}
	}

	/**
	 * 获取该省份一页中的数据
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	private void crawlingOnePage(String areaId, String areaName, String baseUrl, String contentOfPage) throws ClientProtocolException, IOException {

		Document doc = Jsoup.parse(contentOfPage);
		Elements elms = doc.select("li.fixed");
		for (int i = 0; i < elms.size(); i++) {
			Element elm = elms.get(i);
			String strDate = elm.select("span").text();
			String strHref = elm.select("a").attr("href");
			// String strTitle = elm.select("a").attr("title");

			String url = baseUrl + strHref.replace("./", "");

			crawlingOneNotice(areaId, areaName, strDate, url);
		}

	}

	/**
	 * 获取一次通报中的所有Case
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	private void crawlingOneNotice(String areaId, String areaName, String date, String url) throws ClientProtocolException, IOException {
		String strContent = Crawler.crawling(url);
		Document doc = Jsoup.parse(strContent);
		Elements elms = doc.select("div.TRS_Editor").select("p");
		for (int i = 0; i < elms.size(); i++) {
			String content = elms.get(i).text();

			CaseData data = new CaseData();
			data.setAreaId(areaId);
			data.setAreaName(areaName);
			data.setDate(date);
			data.setContent(content);

			this.caseList.add(data);
		}
	}

	private static final Object[] FILE_HEADER = { "序号", "地域编号", "地域", "曝光时间", "曝光内容" };

	public void writeCsvFile(String fileName) throws UnsupportedEncodingException, FileNotFoundException {

		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(fileName, false), "GBK");
		BufferedWriter fileWriter = new BufferedWriter(osw);

		CSVPrinter csvFilePrinter = null;

		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator('\n');

		try {
			csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
			csvFilePrinter.printRecord(FILE_HEADER);

			int no = 1;

			for (CaseData data : caseList) {
				List<String> dataRecord = new ArrayList<String>();

				// "序号", "地域编号","地域","曝光时间", "曝光内容"
				dataRecord.add(String.valueOf(no));
				no++;
				dataRecord.add(String.valueOf(data.getAreaId()));
				dataRecord.add(String.valueOf(data.getAreaName()));
				dataRecord.add(String.valueOf(data.getDate()));
				dataRecord.add(String.valueOf(data.getContent()));

				csvFilePrinter.printRecord(dataRecord);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();

				csvFilePrinter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
