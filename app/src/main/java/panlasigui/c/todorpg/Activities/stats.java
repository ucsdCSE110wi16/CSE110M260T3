package panlasigui.c.todorpg.Activities;

import android.os.Bundle;
/**
 * created by Evan on 2/12/16
 */
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import panlasigui.c.todorpg.Classes.statNode;
import panlasigui.c.todorpg.R;
import android.os.Handler;
import android.widget.TextView;

import com.firebase.client.Firebase;

import org.w3c.dom.Text;


public class stats extends AppCompatActivity {

    protected static statNode intel = new statNode("intel", (int) (TaskPage.account.getIntelligenceXP() / 15), 15,  TaskPage.account.getIntelligenceXP() % 15);
    protected static statNode fit = new statNode("fit", (int) (TaskPage.account.getFitnessXP() / 15), 15,  TaskPage.account.getFitnessXP() % 15);
    protected static statNode hp = new statNode("hp", (int) (TaskPage.account.getHealthXP() / 15), 15,  TaskPage.account.getHealthXP() % 15);
    protected static statNode charm = new statNode("charm", (int) (TaskPage.account.getCharismaXP() / 15), 15,  TaskPage.account.getCharismaXP() % 15);

    public static statNode getIntel() {
        return intel;
    }

    public static statNode getFit() {
        return fit;
    }

    public static statNode getHp() { return hp; }

    public static statNode getCharm() { return charm; }

    static ProgressBar progIntelligence;
    static ProgressBar progFitness;
    static ProgressBar progHealth;
    static ProgressBar progCharisma;

    static TextView intLevel;
    static TextView fitLevel;
    static TextView heaLevel;
    static TextView chaLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stats.super.onBackPressed();
            }
        });

        progIntelligence = (ProgressBar) findViewById(R.id.progressBar);
        progIntelligence.setProgress(intel.getCurrExp() * 100 / 15);
        progFitness = (ProgressBar) findViewById(R.id.progressBar2);
        progFitness.setProgress(fit.getCurrExp() * 100 / 15);
        progHealth = (ProgressBar) findViewById(R.id.progressBar3);
        progHealth.setProgress(hp.getCurrExp() * 100 / 15);
        progCharisma = (ProgressBar) findViewById(R.id.progressBar4);
        progCharisma.setProgress(charm.getCurrExp() * 100 / 15);

        intLevel = (TextView) findViewById(R.id.intLabel);
        intLevel.setText("Intelligence Level " + intel.getLevel());
        fitLevel = (TextView) findViewById(R.id.fitLabel);
        fitLevel.setText("Fitness Level " + fit.getLevel());
        heaLevel = (TextView) findViewById(R.id.heaLabel);
        heaLevel.setText("Health Level " + hp.getLevel());
        chaLevel = (TextView) findViewById(R.id.chaLabel);
        chaLevel.setText("Charisma Level " + charm.getLevel());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

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
        }
        else if(stat.equals("Fitness")) {
            fit.setCurrExp(fit.getCurrExp() + (int) (amount * 2));
            if (fit.getCurrExp() >= fit.getMaxExp()) {
                fit.setCurrExp(fit.getCurrExp() - fit.getMaxExp());
                fit.setLevel(fit.getLevel() + 1);
            }
            TaskPage.account.setFitnessXP(fit.getCurrExp() + (fit.getLevel() * 15));
            ref.child("fitnessXP").setValue(fit.getCurrExp() + (fit.getLevel() * 15));
        }
        else if(stat.equals("Health")) {
            hp.setCurrExp(hp.getCurrExp() + (int) (amount * 2));
            if (hp.getCurrExp() >= hp.getMaxExp()) {
                hp.setCurrExp(hp.getCurrExp() - hp.getMaxExp());
                hp.setLevel(hp.getLevel() + 1);
            }
            TaskPage.account.setHealthXP(hp.getCurrExp() + (hp.getLevel() * 15));
            ref.child("healthXP").setValue(hp.getCurrExp() + (hp.getLevel() * 15));
        }
        else if(stat.equals("Charisma")) {
            charm.setCurrExp(charm.getCurrExp() + (int) (amount * 2));
            if (charm.getCurrExp() >= charm.getMaxExp()) {
                charm.setCurrExp(charm.getCurrExp() - charm.getMaxExp());
                charm.setLevel(charm.getLevel() + 1);
            }
            TaskPage.account.setCharismaXP(charm.getCurrExp() + (charm.getLevel() * 15));
            ref.child("charismaXP").setValue(charm.getCurrExp() + (charm.getLevel() * 15));
        }
        else{
            System.out.println("something went wrong with stats.updateStat");
        }
    }
}

