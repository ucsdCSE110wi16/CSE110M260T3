package panlasigui.c.todorpg;

/**
 * Created by vishu on 2/12/2016.
 */
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.Normalizer;
import java.util.ArrayList;

public class Login_Page extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener  {
    //Constants
    private final static int MIN_PASSWORD_SIZE = 5;

    public static Account curAccount;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        Firebase.setAndroidContext(this);
        Firebase myFirebaseRef = new Firebase("https://todorpg.firebaseio.com/");


    }





    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}
