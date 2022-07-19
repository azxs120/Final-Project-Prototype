package com.example.prototype.ui.Call_Handling;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.prototype.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.ongoingCallsTab, R.string.handlingCallsTab, R.string.closedCallsTab};
    private final Context mContext;
    private String userMobileNumber, userIdentity;

    public SectionsPagerAdapter(Context context, FragmentManager fm, String userMobileNumber, String userIdentity) {
        super(fm);
        mContext = context;
        this.userMobileNumber = userMobileNumber;
        this.userIdentity = userIdentity;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                //fragment = OngoingCallsTab.newInstance(userMobileNumber, userIdentity);
                fragment = new OngoingCallsTab();
                break;
            case 1:
                fragment = ClosedCallsTab.newInstance(userMobileNumber, userIdentity);

                //fragment = new HandlingCallsTab();
                break;
            case 2:
                fragment = HandlingCallsTab.newInstance(userMobileNumber, userIdentity);

                //fragment = new ClosedCallsTab();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return TAB_TITLES.length;
    }
}