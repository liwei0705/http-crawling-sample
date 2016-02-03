package org.jiagoushi.crawling.vpiao;

import java.util.List;

import lombok.Data;

@Data
public class VpiaoMovieData {
	// "accurateNationalBoxOffice": 51389288.11,全国票房
	double accurateNationalBoxOffice;

	List<MovieBoxOffice> movieBoxOffices;

	@Data
	public static class MovieBoxOffice {

		// "movieId": 5855,
		int movieId;

		// "movieName": "功夫熊猫3",
		String movieName;

		// "productAvgPerson": 11,场均人次
		int productAvgPerson;

		// "productBoxOffice": "3293.9",票房 万
		double productBoxOffice;

		// "productBoxOfficeRate": "64.10",票房占比
		double productBoxOfficeRate;

		// "productScheduleRate": "48.74",排片占比
		double productScheduleRate;

		// "productShowTimes": 67407,场次
		long productShowTimes;

		// "productTicketSeatRate": "6.42",上座率
		double productTicketSeatRate;

		// "productTickets": 746862,
		long productTickets;

		// "productTotalBoxOffice": "46420.0",
		double productTotalBoxOffice;
	}

}


//{
//"accurateNationalBoxOffice": 51389288.11,
//"dataUpdateLog": {
//    "dataForDate": "2016-02-02T00:00:00",
//    "dataTime": "15:40",
//    "dataUpdateTime": "2016-02-02T15:40:01",
//    "frequency": 20,
//    "reportName": "movie_box_office"
//},
//"error": 0,
//"errorMessage": "成功",
//"movie": {
//    "completeSpell": null,
//    "director": null,
//    "directorEnglish": null,
//    "movieHot": 0,
//    "movieId": 0,
//    "movieNameEnglish": null,
//    "name": null,
//    "pictureUrl": null,
//    "releaseDate": null,
//    "releaseYear": null,
//    "score": 0,
//    "shortSpell": null,
//    "starring": null,
//    "starringEnglish": null,
//    "version": null,
//    "wantCount": 0
//},
//"movieBoxOffices": [
//    {
//        "boxOffice": "2475.1",
//        "boxOfficeCateye": "3003.6",
//        "boxOfficeCateyeRate": 0,
//        "boxOfficeCbo": "1922.1",
//        "boxOfficeCboRate": 0,
//        "boxOfficeForecast": "3293.9",
//        "boxOfficeRate": "59.88",
//        "boxOfficeRateCateye": "65.48",
//        "boxOfficeRateCbo": "48.06",
//        "boxOfficeRateForecast": "64.10",
//        "boxOfficeRateNew": 0,
//        "movieBoxOfficeId": 14445,
//        "movieId": 5855,
//        "movieName": "功夫熊猫3",
//        "movieNameEnglish": "Kung Fu Panda 3",
//        "pictureUrl": "http://static.image.wxmovie.com/movie/2016/1/8_0/201601081038563641.jpg",
//        "productAvgPerson": 11,
//        "productBoxOffice": "3293.9",
//        "productBoxOfficeEnglish": "32.94M",
//        "productBoxOfficeRate": "64.10",
//        "productScheduleRate": "48.74",
//        "productShowTimes": 67407,
//        "productTicketSeatRate": "6.42",
//        "productTickets": 746862,
//        "productTicketsEnglish": "0.75M",
//        "productTotalBoxOffice": "46420.0",
//        "productTotalBoxOfficeEnglish": "464.20M",
//        "releaseDate": "2016-01-29T00:00:00",
//        "scheduleDate": "2016-02-02T00:00:00",
//        "scheduleRate": "48.74",
//        "scheduleRateCateye": "0.00",
//        "shortRemark": "世界终归是属于萌货和吃货的",
//        "showDateWeek": 2,
//        "showDays": 5,
//        "ticketSeatRate": "6.42",
//        "ticketSeatRateCateye": "0.00",
//        "totalBoxOffice": "34613.7",
//        "totalBoxOfficeCateye": "46312.0",
//        "totalBoxOfficeCbo": "44821.7",
//        "totalBoxOfficeForecast": "39344.7",
//        "week": 5,
//        "weekDay": "星期二",
//        "zzbBoxOffice": "0.0",
//        "zzbBoxOfficeForecast": "3293.9",
//        "zzbBoxOfficeRate": "0.00",
//        "zzbBoxOfficeRateForecast": "64.10",
//        "zzbScheduleRate": "0.00",
//        "zzbTicketSeatRate": "0.00",
//        "zzbTotalBoxOffice": "42897.2",
//        "zzbTotalBoxOfficeForecast": "46420.0"
//    },
//],
//"movieFilter": {
//    "cinemaId": 0,
//    "cinemaName": null,
//    "cityId": [
//        0
//    ],
//    "endDate": null,
//    "exportDate": null,
//    "exportEndDate": null,
//    "exportStartDate": null,
//    "hotType": null,
//    "locality": null,
//    "movieId": 0,
//    "nationwide": true,
//    "periodType": 0,
//    "scheduleDate": "2016-02-02T00:00:00",
//    "startDate": null,
//    "theaterChainId": 0,
//    "theaterChainName": null,
//    "year": 0
//},
//"movieSituations": [],
//"nationalBoxOffice": "5138.9",
//"nationalBoxOfficeEnglish": "51.39M",
//"paging": {
//    "page": 1,
//    "pageSize": 10,
//    "pages": 9,
//    "start": 0,
//    "total": 84
//},
//"totalShowTimes": 0,
//"totalTickets": 0
//}
