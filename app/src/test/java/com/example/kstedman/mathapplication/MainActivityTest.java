package com.example.kstedman.mathapplication;

import android.content.Intent;
import android.os.Build;
import android.widget.TextView;

import com.example.kstedman.mathapplication.ui.MainActivity;
import com.example.kstedman.mathapplication.ui.SolveActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static junit.framework.Assert.assertTrue;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)

public class MainActivityTest {
    private MainActivity activity;

    @Before
    public void setup() {
        activity = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void validateTextViewContent() {
        TextView appNameTitle = (TextView) activity.findViewById(R.id.pageTitle);
        assertTrue("SOLVR".equals(appNameTitle.getText().toString()));
    }

    @Test
    public void validateSolvrButtonContent() {
        TextView appNameTitle = (TextView) activity.findViewById(R.id.solveButton);
        assertTrue("Solvr".equals(appNameTitle.getText().toString()));
    }

    @Test
    public void validateContactButtonContent() {
        TextView appNameTitle = (TextView) activity.findViewById(R.id.contactbutton);
        assertTrue("Contact".equals(appNameTitle.getText().toString()));
    }

    @Test
    public void validateConvertButtonContent() {
        TextView appNameTitle = (TextView) activity.findViewById(R.id.convertButton);
        assertTrue("Convert".equals(appNameTitle.getText().toString()));
    }

    @Test
    public void validateAboutButtonContent() {
        TextView appNameTitle = (TextView) activity.findViewById(R.id.aboutButton);
        assertTrue("About".equals(appNameTitle.getText().toString()));
    }

    @Test
    public void solveActivityStarted() {
        activity.findViewById(R.id.solveEquationButton).performClick();
        Intent expectedIntent = new Intent(activity, SolveActivity.class);
        ShadowActivity shadowActivity = org.robolectric.Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    @Test
    public void aboutActivityStarted() {
        activity.findViewById(R.id.aboutButton).performClick();
        Intent expectedIntent = new Intent(activity, SolveActivity.class);
        ShadowActivity shadowActivity = org.robolectric.Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    @Test
    public void contactActivityStarted() {
        activity.findViewById(R.id.contactButton).performClick();
        Intent expectedIntent = new Intent(activity, SolveActivity.class);
        ShadowActivity shadowActivity = org.robolectric.Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    @Test
    public void convertActivityStarted() {
        activity.findViewById(R.id.convertButton).performClick();
        Intent expectedIntent = new Intent(activity, SolveActivity.class);
        ShadowActivity shadowActivity = org.robolectric.Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }
}