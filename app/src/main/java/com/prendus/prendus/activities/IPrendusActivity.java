package com.prendus.prendus.activities;

import android.view.MenuItem;
import android.widget.ProgressBar;

/**
 * Created by mcrowder65 on 3/22/17.
 */

public interface IPrendusActivity {
    void dropDownMenuClicked(MenuItem item);
    void searchClicked(MenuItem item);
    ProgressBar getSpinner();
}
