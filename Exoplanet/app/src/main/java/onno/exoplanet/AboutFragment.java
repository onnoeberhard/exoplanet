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

public class AboutFragment extends Fragment {

    public Context mContext;

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
        root = (ViewGroup) inflater.inflate(R.layout.aboutfragment, container, false);
        TextView t15 = (TextView) root.findViewById(R.id.t15);
        LocalDatabaseHandler dbl = new LocalDatabaseHandler(mContext);
        if(dbl.getStuffValue("15").equals("DOES NOT EXIST")) {
            dbl.setStuffValue("15", "true");
            dbl.close();
        } else
            t15.setVisibility(View.GONE);
        return root;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = getActivity().getApplicationContext();
    }

}
