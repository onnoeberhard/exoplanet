package onno.exoplanet;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class OnlineDatabaseHandler {

    static final String address = "http://actio.totalh.net/test.php";

	Context _context;
	boolean internet = true;

	public OnlineDatabaseHandler(Context context) {
		_context = context;
//		ConnectionDetector cd = new ConnectionDetector(context);
//		if (!cd.isConnectingToInternet()) {
//			internet = false;
//			// Toast.makeText(context, "Connection Error!",
//			// Toast.LENGTH_SHORT).show();
//		}
	}

	public void get(WebDbUser wdu, String... params) {
		if (internet) {
			WebDbTask wdt = new WebDbTask(wdu);
			wdt.execute(params);
		} else
			wdu.gottenFromWeb(null, false, false);
	}

	private class WebDbTask extends AsyncTask<String, Void, JSONObject> {

		WebDbUser mwdu;
		String command;

		public WebDbTask(WebDbUser wdu) {
			mwdu = wdu;
		}

		protected JSONObject doInBackground(String... params) {
			command = params[0];
            if (command.equals("GET_FROM_OTHER_SITE")) {
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				list.add(new BasicNameValuePair("tag", "GET_FROM_OTHER_SITE"));
				list.add(new BasicNameValuePair("site", params[1]));
				list.add(new BasicNameValuePair("get1", params[2]));
				list.add(new BasicNameValuePair("get2", params[3]));
				JSONParser jsonParser = new JSONParser();
                JSONObject data = jsonParser.getJSONFromUrl(address, list);
                return data;
			}
			return null;
		}

		protected void onPostExecute(JSONObject data) {
			try {
				if (data != null && !data.equals("")) {
					mwdu.gottenFromWeb(data, true, (data != null));
				} else
					mwdu.gottenFromWeb(data, false, (data != null));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public interface WebDbUser {
		public void gottenFromWeb(JSONObject json, boolean good, boolean success);
	}

}