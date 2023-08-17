package com.bovada.flashlights.lib;

import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;


public interface GameMainView extends ILoadingView {
    //void replaceFragment(Fragment fragment);

    void setActionBarTitle(String title);

    boolean isUnlocked();

    //void openBrowser(Category category);

    @Nullable
    TabLayout getTabs();


    boolean showRefresh();

    void homeScreen();

    void resolveCamera();

    boolean isResolved();

}
