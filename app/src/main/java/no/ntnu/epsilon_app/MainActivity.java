package no.ntnu.epsilon_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.IOException;

import javax.sql.DataSource;

import no.ntnu.epsilon_app.api.RetrofitClientInstance;
import no.ntnu.epsilon_app.data.Image;
import no.ntnu.epsilon_app.data.ImageParser;
import no.ntnu.epsilon_app.data.LoginDataSource;
import no.ntnu.epsilon_app.data.LoginRepository;
import no.ntnu.epsilon_app.data.User;
import no.ntnu.epsilon_app.data.UserParser;
import no.ntnu.epsilon_app.data.UserViewModel;
import no.ntnu.epsilon_app.ui.about_us.AboutUsViewModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_newsfeed, R.id.nav_gallery, R.id.nav_faq, R.id.nav_ask_question, R.id.nav_about_us)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
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
            case R.id.action_logout:
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
                break;

        case R.id.changePassword:
            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.nav_change_pwd);
            break;

        }
        return super.onOptionsItemSelected(item);
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


}
