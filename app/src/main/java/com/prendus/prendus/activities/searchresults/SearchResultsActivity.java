package com.prendus.prendus.activities.searchresults;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.prendus.prendus.R;
import com.prendus.prendus.activities.IPrendusActivity;
import com.prendus.prendus.activities.search.SearchActivity;
import com.prendus.prendus.utilities.Utilities;

/**
 * Created by mcrowder65 on 3/23/17.
 */

public class SearchResultsActivity extends AppCompatActivity implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener, IPrendusActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Abstract to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        Utilities.hideSpinner(this);
    }
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }



    @Override
    public void dropDownMenuClicked(MenuItem item) {
        View menuItemView = findViewById(R.id.menu);
        PopupMenu popup = new PopupMenu(SearchResultsActivity.this, menuItemView);
        Utilities.populatePopup(popup);
        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
        final SearchResultsActivity self = this;
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Utilities.navigate(item, self, null);
                return true;
            }
        });
        popup.show();//showing popup menu
    }

    @Override
    public void searchClicked(MenuItem item) {
        Utilities.goToActivity(SearchActivity.class, this);
    }

    @Override
    public ProgressBar getSpinner() {
        ProgressBar spinner = (ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        return spinner;
    }
}
