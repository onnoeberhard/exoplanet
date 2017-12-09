package onno.exoplanet;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class FrontFragment extends Fragment {

    public Context mContext;
    ProgressBar loading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity().getApplicationContext();
    }

    ViewGroup root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        root = (ViewGroup) inflater.inflate(R.layout.frontfragment, container, false);
        loading = (ProgressBar) root.findViewById(R.id.loading);
        final OnlineDatabaseHandler dbo = new OnlineDatabaseHandler(mContext);
        loading.setVisibility(View.VISIBLE);
        dbo.get(new OnlineDatabaseHandler.WebDbUser() {
                    @Override
                    public void gottenFromWeb(JSONObject json, boolean good, boolean success) {
                        String data = "";
                        loading.setVisibility(View.GONE);
                        try {
                            data = json.getString("data");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        int counter = -1;
                        for (int i = 0; i < data.length(); i++) {
                            if (data.charAt(i) == '\n') {
                                counter++;
                            }
                        }
                        int counter2 = 0;
                        String word = "......";
                        String d = data.toLowerCase();
                        for (int i = 0; i < d.length(); i++) {
                            word = String.valueOf(word.charAt(1)) +String.valueOf( word.charAt(2)) +
                                    String.valueOf(word.charAt(3)) + String.valueOf(word.charAt(4)) +
                                    String.valueOf(word.charAt(5)) + String.valueOf(d.charAt(i));
                            if (word.equals("kepler")) {
                                counter2++;
                            }
                        }
                        int counter3 = 0;
                        int counter4 = 0;
                        String word2 = "......";
                        for (int i = 0; i < d.length(); i++) {
                            word2 = String.valueOf(word2.charAt(1)) +String.valueOf( word2.charAt(2)) +
                                    String.valueOf(word2.charAt(3)) + String.valueOf(word2.charAt(4)) +
                                    String.valueOf(word2.charAt(5)) + String.valueOf(d.charAt(i));
                            if (word2.equals("transi")) {
                                counter3++;
                            }
                            if (word2.equals("radial")) {
                                counter4++;
                            }
                        }
                        float transit = ((float)counter3 / (float)counter) * 100;
                        float radial = ((float)counter4 / (float)counter) * 100;
                        DecimalFormat df = new DecimalFormat();
                        df.setMaximumFractionDigits(2);
                        TextView tv = (TextView) root.findViewById(R.id.text);
                        tv.setText("Entdeckte Exoplaneten:\n   " + Integer.toString(counter) +
                                "\n\nVon Kepler:\n   " + Integer.toString(counter2) +
                                "\n\nBeobachtungsmethoden:\n" +
                                "   Transit:\n       " + df.format(transit) + "%\n" +
                                "   Radialgeschwindigkeit:\n" +
                                "       " + df.format(radial) + "%");
                    }
                }, "GET_FROM_OTHER_SITE", "http://exoplanetarchive.ipac.caltech.edu/cgi-bin/nstedAPI/nph-nstedAPI", "table=exoplanets",
                "select=pl_hostname,pl_discmethod");
        return root;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = getActivity().getApplicationContext();
    }

}
