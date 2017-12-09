package onno.exoplanet;

import android.os.Bundle;

public class HtmlActivtiy extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        setContentView(R.layout.activity_html);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return NAVDRAWER_ITEM_2;
    }

}
