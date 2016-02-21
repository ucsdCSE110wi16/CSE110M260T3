package panlasigui.c.todorpg;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vishu on 2/12/2016.
 */
public class Account implements Parcelable{

    public Account(String user, String pass, String ID, int isNew, int numTasks)
   {
       this.setUsername(user);
       this.setPassword(pass);
       this.setUserID(ID);
       this.isNew = isNew;
       taskList = new ArrayList<>();
       this.numTasks = numTasks;
   }
    /* Fields relevant to an account */
    private static String username;
    private static String password;
    private static String userID;
    private ArrayList<TaskData> taskList;
    private int isNew;
    private int numTasks;

    public int getNumTasks() {
        return numTasks;
    }

    public void setNumTasks(int numTasks) {
        this.numTasks = numTasks;
    }

    //Getters and Setters
    public int isNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Account.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Account.password = password;
    }

    public ArrayList<TaskData> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<TaskData> taskList) {
        this.taskList = taskList;
    }
    public static String getUserID() {
        return userID;
    }

    public static void setUserID(String userID) {
        Account.userID = userID;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
       dest.writeString(this.getUserID());
        dest.writeInt(this.isNew());
        dest.writeInt(this.numTasks);

    }

    protected Account(Parcel in)
    {
        this.setUserID(in.readString());
        this.setIsNew(in.readInt());
        this.setNumTasks(in.readInt());
    }
    public static final Parcelable.Creator<Account> CREATOR = new Parcelable.Creator<Account>() {
        public Account createFromParcel(Parcel source) {
            return new Account(source);
        }

        public Account[] newArray(int size) {
            return new Account[size];
        }
    };
}