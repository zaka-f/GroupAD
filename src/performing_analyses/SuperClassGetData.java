package performing_analyses;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import mainUI.MainUI;

public class SuperClassGetData {
	
	//returns the country code
	protected String setCountryCode(String country) {	
		switch (country) {
		case "Brazil":
			return "bra";
		case "Canada":
			return "can";
		case "China":
			return "chn";
		case "France":
			return "fra";
		case "USA":
			return "usa";
		default:
			throw new IllegalArgumentException("Unknown analysis " + country);
		}	
    }
	
	// returns the URL for the query when given the start year, end year , country code , and the analis id.
	protected String setURL(String country, String startYear, String endYear, String analisCode) {
		String urlString = String.format(
				"http://api.worldbank.org/v2/country/%s/indicator/%s?date=%s:%s&format=json", country,
				analisCode, startYear, endYear);

		return urlString;
	}
	
	// Returns the start year selected
	protected String updateStartYear() {
		return (String) MainUI.getFromList().getSelectedItem();
	}
	
	public String getResult() {
		return null;
	}
	
	//returns the end year selected
	protected String updateEndYear() {
		return (String) MainUI.getToList().getSelectedItem();
	}

    //retreives the data from the online bank
	protected JsonArray retreiveData(String urlString) {
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
				return new JsonParser().parse(inline).getAsJsonArray();
	
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block e.printStackTrace();
		}
		return null;
	}

	
	
}
