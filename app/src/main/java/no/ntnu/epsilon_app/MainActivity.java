package no.ntnu.epsilon_app;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import no.ntnu.epsilon_app.data.LoginDataSource;
import no.ntnu.epsilon_app.data.LoginRepository;
import no.ntnu.epsilon_app.tools.EpsilonFacebookIntent;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),
                R.drawable.ic_settings);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        final ImageButton imageButton = findViewById(R.id.facebookLinkDrawer);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_newsfeed, R.id.nav_calendar, R.id.nav_faq, R.id.nav_ask_question, R.id.nav_about_us)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(EpsilonFacebookIntent.newFaceBookIntent(getPackageManager()));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_button:
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.nav_settings);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void logout() {
        LoginRepository loginRepository = LoginRepository.getInstance(new LoginDataSource());
        if (loginRepository.isLoggedIn()) {
            loginRepository.logout();
        }
        final SharedUserPrefs sharedUserPrefs = new SharedUserPrefs(this);
        sharedUserPrefs.editor.clear();
        sharedUserPrefs.editor.commit();

        Intent mainIntent = new Intent(MainActivity.this, LoginActivity.class);
        MainActivity.this.startActivity(mainIntent);
        MainActivity.this.finish();
    }

    public void goToSplashScreen() {
        LoginRepository loginRepository = LoginRepository.getInstance(new LoginDataSource());
        if (loginRepository.isLoggedIn()) {
            loginRepository.logout();
        }
        Intent mainIntent = new Intent(MainActivity.this, SplashActivity.class);
        MainActivity.this.startActivity(mainIntent);
        MainActivity.this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.nav_newsfeed);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    private void onFailure() {
    }


}
