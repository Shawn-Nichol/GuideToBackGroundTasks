package com.example.guidetobackgroundtasks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.guidetobackgroundtasks.ExampleThread.ExampleThreadingFragment;
import com.example.guidetobackgroundtasks.MultipleThreads.ThreadingFragment;
import com.example.guidetobackgroundtasks.Runnable.RunnableFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar mToolbar;
    NavigationView mNavView;
    DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadToolbar();
        loadNavDrawer();
    }

    private void loadToolbar() {
        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
    }

    private void loadNavDrawer() {
        mDrawerLayout = findViewById(R.id.main_nav_drawer);
        mNavView = findViewById(R.id.main_nav_view);
        mNavView.setNavigationItemSelectedListener(MainActivity.this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.close_drawer, R.string.open_drawer);

        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.nav_drawer_1:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,
                        new ExampleThreadingFragment()).commit();
                break;
            case R.id.nav_drawer_2:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,
                        new RunnableFragment()).commit();
                break;

        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
