package panlasigui.c.todorpg.Classes;

import android.widget.ProgressBar;

import panlasigui.c.todorpg.R;

public class statNode{
    public String stat;
    public int level;
    public int currExp;
    public int maxExp;

    public statNode(String stat, int level, int maxExp, int currExp) {
        this.stat = stat;
        this.level = level;
        this.maxExp = maxExp;
        this.currExp = currExp;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCurrExp() {
        return currExp;
    }

    public void setCurrExp(int currExp) {
        this.currExp = currExp;
    }

    public int getMaxExp() {
        return maxExp;
    }

    public void setMaxExp(int maxExp) {
        this.maxExp = maxExp;
    }
}
