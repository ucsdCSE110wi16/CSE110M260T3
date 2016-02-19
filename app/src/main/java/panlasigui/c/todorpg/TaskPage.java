package panlasigui.c.todorpg;

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
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TaskPage extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, ItemsAdapter.ItemsAdapterCallback {

    public static ArrayList<TaskData> taskList;
    public static ItemsAdapter<TaskData> itemsAdapter;
    public static String userID;
    ListView lvItems;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //setupListViewListener();

        Intent i =getIntent();
        Account account = (Account) i.getParcelableExtra("Account");

        userID = account.getUserID();
        System.out.println(userID);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptNewTask();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //ListView Customization
        lvItems = (ListView) findViewById(R.id.lvItems);
        //int[] colors = {0, 0xFFFF0000, 0}; // red for the example
        int[] colors = {0, 0, 0};
        lvItems.setDivider(new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors));
        lvItems.setDividerHeight(50);

        taskList = new ArrayList<>();
        itemsAdapter = new ItemsAdapter<>(this, taskList);
        itemsAdapter.setCallback(this);
        lvItems.setAdapter(itemsAdapter);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        if (id == R.id.action_settings) {
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inventory) {
            // Handle the camera action
        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(TaskPage.this, Profile.class));
        } else if (id == R.id.nav_store) {
            startActivity(new Intent(TaskPage.this, Store.class));
        } else if (id == R.id.nav_stats) {
            startActivity(new Intent(TaskPage.this, Skills.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    /*
    private void setupListViewListener() {
        lvItems.setOnItemClickListener(
                new ListView.OnItemClickListener() {


                    button1.setOnClickListener(this);
                    button2.setOnClickListener(this);

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    }

                    public boolean onClickFail(ItemsAdapter<TaskData> adapter,
                                               View item, int pos, long id) {
                        taskList.remove(pos);
                        adapter.notifyDataSetChanged();
                        return true;

                    }

                    public boolean onClickComplete(ItemsAdapter<TaskData> adapter,
                                                   View item, int pos, long id) {
                        taskList.remove(pos);
                        adapter.notifyDataSetChanged();
                        return true;

                    }

                }


        );


    }
*/

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
