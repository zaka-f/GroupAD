package performinganalyses;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import javax.management.loading.PrivateClassLoader;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import mainUI.MainUI;

public class AverageForestArea extends SuperClassGetData {

	private String country;
	private String yearStart;
	private String yearEnd;
	private String urlString;
	private JsonArray data;

	AverageForestArea(String country) {
		this.country = country;
		this.country = setCountryCode(country);
		updateYears();
		data = retreiveData(urlString);
		setURL();
//		this.country = country;
//		setCountry();
//		updateYears();
//		setURL();
	}

//	private void retreiveData() {
//		try {
//			URL url = new URL(urlString);
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.setRequestMethod("GET");
//			conn.connect();
//			int responsecode = conn.getResponseCode();
//// IF THE RESPONSE IS 200 OK GET THE LINE WITH THE RESULTS
//			if (responsecode == 200) {
//				String inline = "";
//				Scanner sc = new Scanner(url.openStream());
//				while (sc.hasNext()) {
//					inline += sc.nextLine();
//				}
//				sc.close();
//// PROCESS THE JSON AS ONE LINE
//				data = new JsonParser().parse(inline).getAsJsonArray();
//				int size = data.size();
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block e.printStackTrace();
//		}
//	}
//
//	private void setCountry() {
//		this.country = setCountryCode(this.country);
//	}
//
	private void updateYears() {
//		this.yearStart = (String) MainUI.getFromList().getSelectedItem();
//		this.yearEnd = (String) MainUI.getToList().getSelectedItem();
		this.yearStart = "2000";
		this.yearEnd= "2001";
	}

	private void setURL() {
		String urlString = String.format(
				"http://api.worldbank.org/v2/country/%s/indicator/AG.LND.FRST.ZS?date=%s:%s&format=json", country,
				yearStart, yearEnd);

		System.out.println(urlString);
	}

	public static void main(String[] args) {
		new AverageForestArea("Canada");
		String urlString = String.format(
				"http://api.worldbank.org/v2/country/%s/indicator/SP.POP.TOTL?date=2000:2001&format=json", "can");

		System.out.println(urlString);

		int populationForYear = 0;
		int cummulativePopulation = 0;

		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responsecode = conn.getResponseCode();
// IF THE RESPONSE IS 200 OK GET THE LINE WITH THE RESULTS
			if (responsecode == 200) {
				String inline = "";
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) {
					inline += sc.nextLine();
				}
				sc.close();
// PROCESS THE JSON AS ONE LINE
				JsonArray jsonArray = new JsonParser().parse(inline).getAsJsonArray();
				int size = jsonArray.size();
				int sizeOfResults = jsonArray.get(1).getAsJsonArray().size();
				int year = 0;
				for (int i = 0; i < sizeOfResults; i++) {
// GET FOR EACH ENTRY THE YEAR FROM THE �date� FIELD
					year = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("date").getAsInt();
					// CHECK IF THERE IS A VALUE FOR THE POPULATION FOR A
					// GIVEN YEAR
					if (jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").isJsonNull())
						populationForYear = 0;
					else
						// GET THE POPULATION FOR THE GIVEN YEAR FROM THE
						// �value� FIELD
						populationForYear = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value")
								.getAsInt();

					System.out.println("Population for : " + year + " is " + populationForYear);
					cummulativePopulation = cummulativePopulation + populationForYear;
				}
				System.out.println(
						"The average population over the selected year is " + cummulativePopulation / sizeOfResults);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block e.printStackTrace();
		}

		return;
	}

}
