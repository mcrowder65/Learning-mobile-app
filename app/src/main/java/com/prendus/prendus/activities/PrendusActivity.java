package com.prendus.prendus.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.prendus.prendus.R;
import com.prendus.prendus.activities.search.SearchActivity;
import com.prendus.prendus.activities.takequiz.TakeQuizActivity;
import com.prendus.prendus.utilities.Utilities;

/**
 * Created by mcrowder65 on 3/22/17.
 */

public abstract class PrendusActivity extends AppCompatActivity {
    // Menu icons are inflated just as they were with actionbar

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void dropDownMenuClicked(MenuItem item) {
        View menuItemView = findViewById(R.id.menu);
        PopupMenu popup = new PopupMenu(PrendusActivity.this, menuItemView);
        Utilities.populatePopup(popup);
        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
        final PrendusActivity self = this;
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Utilities.navigate(item, self, null);
                return true;
            }
        });
        popup.show();//showing popup menu
    }


    public void searchClicked(MenuItem item) {
        Utilities.goToActivity(SearchActivity.class, this);
    }


    public ProgressBar getSpinner() {
        ProgressBar spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        return spinner;
    }

}
