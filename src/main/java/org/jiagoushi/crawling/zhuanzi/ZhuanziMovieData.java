package org.jiagoushi.crawling.zhuanzi;

import lombok.Data;

@Data
public class ZhuanziMovieData {
	String name;
	long todayBox;//当日票房 单位分
	long totalBox;//累计票房 单位分
	int todayShowCnt;//当日场次
	int todaySeatCnt;//当日人次
}
