package panlasigui.c.todorpg.Activities;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import panlasigui.c.todorpg.Classes.Account;
import panlasigui.c.todorpg.Classes.TaskData;
import panlasigui.c.todorpg.Fragments.FormDialog;
import panlasigui.c.todorpg.ItemsAdapter;
import panlasigui.c.todorpg.R;


public class TaskPage extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, ItemsAdapter.ItemsAdapterCallback {

    public static ArrayList<TaskData> taskList;
    public static ItemsAdapter<TaskData> itemsAdapter;
    public static String userID;
    public static Account account;
    ListView lvItems;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    /*
    private EditText date;
    private EditText time;
    private DatePickerDialog datePicker;
    private TimePickerDialog timePicker;
    private SimpleDateFormat dateFormat;
    */

    private TextView avInt;
    private TextView avFit;
    private TextView avHea;
    private TextView avCha;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_page);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        Firebase.setAndroidContext(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        date = (EditText) findViewById(R.id.editDate);
        time = (EditText) findViewById(R.id.editTime);
        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {}
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {}
        });
        setupListViewListener();
        */

        Intent i =getIntent();
        account =  i.getParcelableExtra("Account");

        userID = account.getUserID();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptNewTask();
            }
        });

        avInt = (TextView) findViewById(R.id.avatarInt);
        avFit = (TextView) findViewById(R.id.avatarFit);
        avHea = (TextView) findViewById(R.id.avatarHea);
        avCha = (TextView) findViewById(R.id.avatarCha);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (slideOffset == 0) {
                    // drawer closed
                    updateDrawerLevels();
                    invalidateOptionsMenu();
                } else if (slideOffset != 0) {
                    // started opening
                    updateDrawerLevels();
                    invalidateOptionsMenu();
                }
                super.onDrawerSlide(drawerView, slideOffset);
            }
        };

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //ListView Customization
        lvItems = (ListView) findViewById(R.id.lvItems);
        //int[] colors = {0, 0xFFFF0000, 0}; // red for the example
        int[] colors = {0, 0, 0};
        lvItems.setDivider(new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors));
        lvItems.setDividerHeight(5);

        taskList = new ArrayList<>();
        //If returning user read DB for info.

        itemsAdapter = new ItemsAdapter<>(this, taskList);
        itemsAdapter.setCallback(this);
        lvItems.setAdapter(itemsAdapter);

        if( account.isNew() == 0) {
            Firebase ref = new Firebase("https://todorpg.firebaseio.com/Users/" + TaskPage.userID+"/Tasks");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    int numTasks = TaskPage.account.getNumTasks();
                    for(int i = 0; i<numTasks; i++)
                    {
                        TaskData task = new TaskData();
                        String strTask = String.valueOf(i+1);
                        task.setName((String) snapshot.child("Task " + strTask).child("name").getValue());
                        task.setDescription((String) snapshot.child("Task " + strTask).child("description").getValue());
                        task.setCategory((String) snapshot.child("Task " + strTask).child("category").getValue());
                        double diff = (double) snapshot.child("Task " + strTask).child("difficulty").getValue();
                        task.setDifficulty((float) diff) ;
                        TaskPage.taskList.add(task);
                    }
                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {
                }
            });
        }
        itemsAdapter.notifyDataSetChanged();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onBackPressed() {
       /*
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.task_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_sortByName) {
            Collections.sort(taskList, TaskData.compTaskName);
            itemsAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Tasks have been sorted by Name", Toast.LENGTH_SHORT).show();

            return true;
        }

        if (id == R.id.action_sortByDiff) {
            Collections.sort(taskList, TaskData.compTaskDiff);
            itemsAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Tasks have been sorted by Difficulty", Toast.LENGTH_SHORT).show();

            return true;
        }

        if (id == R.id.action_sortByCat) {
            Collections.sort(taskList, TaskData.compTaskCat);
            itemsAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Tasks have been sorted by Category", Toast.LENGTH_SHORT).show();

            return true;
        }

        if (id == R.id.action_sortByTime) {
            Toast.makeText(this, "Tasks have been sorted by Time", Toast.LENGTH_SHORT).show();
            //call sort
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

        public boolean onNavigationItemSelected(MenuItem item) {
            // Handle navigation view item clicks here.
            int id = item.getItemId();

           if (id == R.id.nav_stats) {
                startActivity(new Intent(TaskPage.this, stats.class));
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

            drawer.clearFocus();
            drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void updateDrawerLevels() {
        if (avInt != null && avFit != null && avHea != null && avCha != null) {
            avInt.setText("Intelligence Level " + account.getIntelligenceXP() / 15);
            avFit.setText("Fitness Level " + account.getFitnessXP() / 15);
            avHea.setText("Health Level " + account.getHealthXP() / 15);
            avCha.setText("Charisma Level " + account.getCharismaXP() / 15);
        }
    }

    public void promptNewTask() {
        DialogFragment newFragment = new FormDialog();
        newFragment.setCancelable(false);
        newFragment.show(getFragmentManager(), "Create Task");

    }

    public void editTask(Bundle b) {
        FormDialog newFragment = new FormDialog();
        newFragment.setArguments(b);
        newFragment.setCancelable(false);
        newFragment.show(getFragmentManager(), "Edit Task");
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "TaskPage Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://panlasigui.c.todorpg/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "TaskPage Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://panlasigui.c.todorpg/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
