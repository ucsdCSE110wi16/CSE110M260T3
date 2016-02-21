package panlasigui.c.todorpg;

import android.os.Bundle;
/**
 * created by Evan on 2/12/16
 */
import android.app.Application;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;



public class stats extends AppCompatActivity {

    protected static statNode intel = new statNode("intel", 0, 5, 0);
    protected static statNode fit = new statNode ("fit", 0, 5, 0);
    protected static statNode hp = new statNode ("hp", 0, 5, 0);
    protected static statNode charm = new statNode ("charm", 0,5 ,0);
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

    public static statNode getCharm() {
        return charm;
    }

    protected static void updateStat(String stat, float amount){
        if(stat.equals("Intelligence")) {
            intel.setCurrExp(intel.getCurrExp() + (int) amount * 2);
            if (intel.getCurrExp() >= intel.getMaxExp()) {
                intel.setCurrExp(intel.getCurrExp() - intel.getMaxExp());
                intel.setLevel(intel.getLevel() + 1);
            }
        }
        else if(stat.equals("Fitness")) {
            fit.setCurrExp(fit.getCurrExp() + (int) amount * 2);
            if (fit.getCurrExp() >= fit.getMaxExp()) {
                fit.setCurrExp(fit.getCurrExp() - fit.getMaxExp());
                fit.setLevel(fit.getLevel() + 1);
            }
        }
        else if(stat.equals("Health")) {
            hp.setCurrExp(hp.getCurrExp() + (int) amount * 2);
            if (hp.getCurrExp() >= hp.getMaxExp()) {
                hp.setCurrExp(hp.getCurrExp() - hp.getMaxExp());
                hp.setLevel(hp.getLevel() + 1);
            }
        }
        else if(stat.equals("Charisma")) {
            charm.setCurrExp(charm.getCurrExp() + (int) amount * 2);
            if (charm.getCurrExp() >= charm.getMaxExp()) {
                charm.setCurrExp(charm.getCurrExp() - charm.getMaxExp());
                charm.setLevel(charm.getLevel() + 1);
            }
        }
        else{
            System.out.println("something went wrong with stats.updateStat");
        }
    }

}

