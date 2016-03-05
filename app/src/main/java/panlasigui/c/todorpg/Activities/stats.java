package panlasigui.c.todorpg.Activities;

import android.app.Activity;
import android.os.Bundle;
/**
 * created by Evan on 2/12/16
 */
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.logging.LogRecord;

import panlasigui.c.todorpg.Classes.statNode;
import panlasigui.c.todorpg.R;
import android.os.Handler;

import com.firebase.client.Firebase;


public class stats extends AppCompatActivity {

    protected static statNode intel = new statNode("intel", (int) (TaskPage.account.getIntelligenceXP() / 15), 15,  TaskPage.account.getIntelligenceXP() % 15);
    protected static statNode fit = new statNode("fit", (int) (TaskPage.account.getFitnessXP() / 15), 15,  TaskPage.account.getFitnessXP() % 15);
    protected static statNode hp = new statNode("hp", (int) (TaskPage.account.getHealthXP() / 15), 15,  TaskPage.account.getHealthXP() % 15);
    protected static statNode charm = new statNode("charm", (int) (TaskPage.account.getCharismaXP() / 15), 15,  TaskPage.account.getCharismaXP() % 15);
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public static statNode getIntel() {
        return intel;
    }

    public static statNode getFit() {
        return fit;
    }

    public static statNode getHp() {
        return hp;
    }

    public static statNode getCharm() {return charm; }





    public static void updateStat(String stat, float amount){

        Firebase ref = new Firebase("https://todorpg.firebaseio.com/Users/"+ TaskPage.userID);

        if(stat.equals("Intelligence")) {
            intel.setCurrExp(intel.getCurrExp() + (int) (amount * 2));
            if (intel.getCurrExp() >= intel.getMaxExp()) {
                intel.setCurrExp(intel.getCurrExp() - intel.getMaxExp());
                intel.setLevel(intel.getLevel() + 1);
            }


           TaskPage.account.setIntelligenceXP(intel.getCurrExp() + (intel.getLevel() * 15));
            ref.child("intelligenceXP").setValue(intel.getCurrExp() + (intel.getLevel() * 15));
           //Calculate total and update account in TaskPage
        }
        else if(stat.equals("Fitness")) {
            fit.setCurrExp(fit.getCurrExp() + (int) (amount * 2));
            if (fit.getCurrExp() >= fit.getMaxExp()) {
                fit.setCurrExp(fit.getCurrExp() - fit.getMaxExp());
                fit.setLevel(fit.getLevel() + 1);
            }
            TaskPage.account.setFitnessXP(fit.getCurrExp() + (fit.getLevel() * 15));
            ref.child("FitnessXP").setValue(fit.getCurrExp() + (fit.getLevel() * 15));
        }
        else if(stat.equals("Health")) {
            hp.setCurrExp(hp.getCurrExp() + (int) (amount * 2));
            if (hp.getCurrExp() >= hp.getMaxExp()) {
                hp.setCurrExp(hp.getCurrExp() - hp.getMaxExp());
                hp.setLevel(hp.getLevel() + 1);
            }
            TaskPage.account.setHealthXP(hp.getCurrExp() + (hp.getLevel() * 15));
            ref.child("HealthXP").setValue(hp.getCurrExp() + (hp.getLevel() * 15));
        }
        else if(stat.equals("Charisma")) {
            charm.setCurrExp(charm.getCurrExp() + (int) (amount * 2));
            if (charm.getCurrExp() >= charm.getMaxExp()) {
                charm.setCurrExp(charm.getCurrExp() - charm.getMaxExp());
                charm.setLevel(charm.getLevel() + 1);
            }
            TaskPage.account.setCharismaXP(charm.getCurrExp() + (charm.getLevel() * 15));
            ref.child("CharismaXP").setValue(charm.getCurrExp() + (charm.getLevel() * 15));
        }
        else{
            System.out.println("something went wrong with stats.updateStat");
        }
    }

}

