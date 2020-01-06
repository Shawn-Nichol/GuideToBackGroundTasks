package com.example.guidetobackgroundtasks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.guidetobackgroundtasks.AsyncTask.AsyncTaskFragment;
import com.example.guidetobackgroundtasks.ExampleThread.ExampleThreadingFragment;
import com.example.guidetobackgroundtasks.Handler.HandlerFragment;
import com.example.guidetobackgroundtasks.IntentService.IntentServiceFragment;
import com.example.guidetobackgroundtasks.JobScheduler.JobSchedulerFragment;
import com.example.guidetobackgroundtasks.Looper.LooperFragment;
import com.example.guidetobackgroundtasks.MultipleThreads.MultipleThreadsFragment;
import com.example.guidetobackgroundtasks.Runnable.RunnableFragment;
import com.example.guidetobackgroundtasks.ThreadPool.ThreadPoolFragment;
import com.example.guidetobackgroundtasks.WorkManager.WorkManagerFragment;
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
            case R.id.nav_drawer_3:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,
                        new LooperFragment()).commit();
                break;
            case R.id.nav_drawer_4:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,
                        new HandlerFragment()).commit();
                break;
            case R.id.nav_drawer_5:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,
                        new MultipleThreadsFragment()).commit();
                break;
            case R.id.nav_drawer_6:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,
                        new ThreadPoolFragment()).commit();
                break;
            case R.id.nav_drawer_7:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,
                        new AsyncTaskFragment()).commit();
                break;
            case R.id.nav_drawer_8:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,
                        new IntentServiceFragment()).commit();
                break;
            case R.id.nav_drawer_9:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,
                        new JobSchedulerFragment()).commit();
                break;
            case R.id.nav_drawer_10:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,
                        new WorkManagerFragment()).commit();
                break;

        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
