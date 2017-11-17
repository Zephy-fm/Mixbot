package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class CurrencyRateHandler {

	private URL currencyUrl;
	private JSONObject data;
	private JSONObject currencyData;

	private String[] commands =
			{"Update", "AED", "AFN", "ALL", "AMD", "ANG", "AOA", "ARS", "AUD", "AWG", "AZN", "BAM", "BBD", "BDT", "BGN", "BHD", "BIF", "BMD", "BND", "BOB", "BRL", "BSD", "BTC", "BTN", "BWP", "BYN", "BYR", "BZD", "CAD", "CDF", "CHF", "CLF", "CLP", "CNY", "COP", "CRC", "CUC", "CUP", "CVE", "CZK", "DJF", "DKK", "DOP", "DZD", "EGP", "ERN", "ETB", "EUR", "FJD", "FKP", "GBP", "GEL", "GGP", "GHS", "GIP", "GMD", "GNF", "GTQ", "GYD", "HKD", "HNL", "HRK", "HTG", "HUF", "IDR", "ILS", "IMP", "INR", "IQD", "IRR", "ISK", "JEP", "JMD", "JOD", "JPY", "KES", "KGS", "KHR", "KMF", "KPW", "KRW", "KWD", "KYD", "KZT", "LAK", "LBP", "LKR", "LRD", "LSL", "LTL", "LVL", "LYD", "MAD", "MDL", "MGA", "MKD", "MMK", "MNT", "MOP", "MRO", "MUR", "MVR", "MWK", "MXN", "MYR", "MZN", "NAD", "NGN", "NIO", "NOK", "NPR", "NZD", "OMR", "PAB", "PEN", "PGK", "PHP", "PKR", "PLN", "PYG", "QAR", "RON", "RSD", "RUB", "RWF", "SAR", "SBD", "SCR", "SDG", "SEK", "SGD", "SHP", "SSL", "SOS", "SRD", "STD", "SVC", "SYP", "SZL", "THB", "TJS", "TMT", "TND", "TOP", "TRY", "TTD", "TWD", "TZS", "UAH", "UGX", "USD", "UYU", "UZS", "VEF", "VND", "VUV", "WST", "XAF", "XAG", "XAU", "XCD", "XDR", "XOF", "XPF", "YER", "ZAR", "ZMK", "ZMW", "ZWL"};

	public CurrencyRateHandler() {
		try {
			this.currencyUrl = new URL("http://www.apilayer.net/api/live?access_key=78b518a61fada8b526d81f46a1b04921&format=1");
		} catch (MalformedURLException e) {
			System.out.println("Bad url inside CurrencyRateHandler:");
			e.printStackTrace();
		}
		this.reloadData();
	}

	public String reloadData() {
		try {
			String json = this.readUrl();
			this.data = new JSONObject(json);
		} catch (JSONException je) {
			System.out.println("Problem loading json:");
			je.printStackTrace();
			this.data = null;
		}

		if (this.data == null) {
			System.out.println("The data in the json was null...");
			return "Could not update the data...";
		}
		this.currencyData = this.data.getJSONObject("quotes");
		return "Currency data successfully updated!";
	}

	public String getCurrencyRate(String currencyType) {
		if (currencyType == null) {
			throw new IllegalArgumentException("currencyType cannot be null");
		}
		try {
			String currencyTypeCapitalized = currencyType.toUpperCase();
			String formattedCurrencyType = "USD" + currencyTypeCapitalized;

			double rate = this.currencyData.getDouble(formattedCurrencyType);
			String convertedRate = String.valueOf(rate);
			int decimalIndexRate = convertedRate.indexOf(".");
			int convertedRateSize = convertedRate.substring(decimalIndexRate).length();
			if (convertedRateSize > 6) {
				convertedRateSize = 6;
			}

			Double amountInUSD = 1 / rate;
			String convertedRateUSD = String.valueOf(amountInUSD);
			int decimalIndexRateUSD = convertedRateUSD.indexOf(".");
			int convertedRateSizeUSD = convertedRateUSD.substring(decimalIndexRateUSD).length();
			if (convertedRateSizeUSD > 6) {
				convertedRateSizeUSD = 6;
			}

			String USDtoCurrency = String.format("$1 USD -> %." + convertedRateSize + "f " + currencyTypeCapitalized, rate);

			String CurrencyToUSD = "wasn't calculated... :thinking:";
			if (amountInUSD < 0.02) {
				CurrencyToUSD = String.format("1 " + currencyTypeCapitalized + " -> $%." + convertedRateSizeUSD + "f USD", amountInUSD);
			} else {
				CurrencyToUSD = String.format("1 " + currencyTypeCapitalized + " -> $%.2f USD", amountInUSD);
			}
			return CurrencyToUSD + " || " + USDtoCurrency;
		} catch (JSONException npe) {
			return "No currency matching \"" + currencyType + "\"";
		}
	}

	public string getCurrencyConversion(String currencyToConvert) {
		this.reloadData();

		String conversionArgs[] = currencyToConvert.split("\\s");
		String amount = conversionArgs[0];
		String currencyOrigin = conversionArgs[1];
		String currencyDestination = conversionArgs[2];

		if (!amount.matches("[\\x00-\\x20]*[+-]?(((((\\p{Digit}+)(\\.)?((\\p{Digit}+)?)([eE][+-]?(\\p{Digit}+))?)|(\\.((\\p{Digit}+))([eE][+-]?(\\p{Digit}+))?)|(((0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+)))[pP][+-]?(\\p{Digit}+)))[fFdD]?))[\\x00-\\x20]*")) {
			throw new IllegalArgumentException("currency amount must be a number");
		}
		try {
			Double USDrateOfOriginCurrency = this.currencyData.getDouble("USD" + currencyOrigin.toUpperCase());
			Double USDrateOfDestinationCurrency = this.currencyData.getDouble("USD" + currencyDestination.toUpperCase());

			Double rateFromOriginToDestination = USDrateOfDestinationCurrency / USDrateOfOriginCurrency;
			Double convertedAmount = Double.parseDouble(amount) * rateFromOriginToDestination;

			return "1 " + currencyOrigin.toUpperCase() + " -> " rateFromOriginToDestination + " " + currencyDestination.toUpperCase()
			  			+ " || " + amount + " " + currencyOrigin.toUpperCase() + " -> " convertedAmount + " " + currencyDestination.toUpperCase();
		} catch (JSONException npe) {
			return "bad currency type";
		}
	}

	public String getAllCommands() {
		String returnMessage = "List of commands for the command !currency [parameter] : [";
		for (String current : this.commands) {
			returnMessage += current + ", ";
		}
		int index = returnMessage.length();
		returnMessage = returnMessage.substring(0, index - 2);
		returnMessage += "]";
		return returnMessage;
	}

	private String readUrl() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(this.currencyUrl.openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1)
	            buffer.append(chars, 0, read);

	        return buffer.toString();
		} catch (Exception e) {
			System.out.println("Something went wrong in CurrencyRateHandler::readUrl");
		} finally {
	        if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					System.out.println("Reader could not close:");
					e.printStackTrace();
				}
	    }
		return null;
	}

}
