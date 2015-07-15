package org.jiagoushi.crawling;

import org.jiagoushi.crawling.jiwei.JiweiCrawler;

/**
 * 网页抓取Sample：抓取中纪委曝光的典型案件
 * @author wei.li<liwei.contact@gmail.com>
 *
 */
public class JiweiMain {
	public static void main(String[] args) {
		new JiweiCrawler().excute();
	}
}
