package panlasigui.c.todorpg;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.Handler;

import com.firebase.client.Firebase;

import java.util.ArrayList;

import panlasigui.c.todorpg.Activities.TaskPage;
import panlasigui.c.todorpg.Activities.stats;
import panlasigui.c.todorpg.Classes.TaskData;

/**
 * Created by Cameron on 2/6/2016.
 * For CSE 110 Project
 */
public class ItemsAdapter<T> extends ArrayAdapter<TaskData> {

    private ItemsAdapterCallback callback;

    public ItemsAdapter(Context context, ArrayList<TaskData> tasks) {
        super(context, 0, tasks);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        TaskData task = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task, parent, false);
        }

        Button buttonFail = (Button) convertView.findViewById(R.id.buttonFailTask);
        Button buttonComplete = (Button) convertView.findViewById(R.id.buttonCompleteTask);
        //final TextView noTasks = (TextView) convertView.findViewById(R.id.noTasks);

        buttonFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmRemove(getContext(), position, "fail");
                /*
                if(getCount() == 0 ) noTasks.setVisibility(View.VISIBLE);
                else noTasks.setVisibility(View.GONE);
                */
            }

        });

        buttonComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmRemove(getContext(), position, "complete");
                /*
                if(getCount() == 0 ) noTasks.setVisibility(View.VISIBLE);
                else noTasks.setVisibility(View.GONE);
                */
            }

        });

        final LinearLayout taskBackground = (LinearLayout) convertView.findViewById(R.id.taskBackground);
        final TextView taskName = (TextView) convertView.findViewById(R.id.taskInstanceName);
        final TextView taskDescription = (TextView) convertView.findViewById(R.id.taskInstanceDesc);
        final TextView taskCategory = (TextView) convertView.findViewById(R.id.taskInstanceCategory);
        final RatingBar taskDifficulty = (RatingBar) convertView.findViewById(R.id.taskInstanceDiff);
        final ImageView categoryImage = (ImageView) convertView.findViewById(R.id.categoryIcon);
        //final TextView intel_exp = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.content_stats, parent, false).findViewById(R.id.a1);
        ProgressBar intel_exp = (ProgressBar) LayoutInflater.from(getContext()).inflate(R.layout.content_stats, parent, false).findViewById(R.id.progressBar);
        System.out.println("the progress is " + ((TaskPage.account.getIntelligenceXP() % 15) * 100) / 15);
        intel_exp.setMax(70);
        intel_exp.setProgress(70);
        //((TaskPage.account.getIntelligenceXP() % 10) * 100) / 10)
        //intel_exp.setVisibility(View.GONE);
        System.out.println("the progress is after: " + ((TaskPage.account.getIntelligenceXP() % 15) * 100) / 15);

        taskName.setText(task.getName());
        taskDescription.setText(task.getDescription());
        taskCategory.setText(task.getCategory());
        taskDifficulty.setRating(task.getDifficulty());

        switch (taskCategory.getText().toString()) {
            case "Health":
                categoryImage.setImageResource(R.drawable.health);
                taskBackground.setBackgroundResource(R.drawable.health_background);
                break;
            case "Fitness":
                categoryImage.setImageResource(R.drawable.str);
                taskBackground.setBackgroundResource(R.drawable.str_background);
                break;
            case "Intelligence":
                categoryImage.setImageResource(R.drawable.magic);
                taskBackground.setBackgroundResource(R.drawable.magic_background);
                break;
            case "Charisma":
                categoryImage.setImageResource(R.drawable.smile);
                taskBackground.setBackgroundResource(R.drawable.charisma_background);
                break;
        }


        taskBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    Bundle b = new Bundle();
                    b.putString("tN", taskName.getText().toString());
                    b.putString("tD", taskDescription.getText().toString());
                    b.putString("tC", taskCategory.getText().toString());
                    b.putFloat("diff", taskDifficulty.getRating());
                    b.putInt("pos", position);
                    callback.editTask(b);
                }
            }
        });

        return convertView;
    }

    @Override
    public void add(TaskData object) {

        String countStr = String.valueOf(this.getCount() + 1);
        super.add(object);
        //Add to DB

        Firebase ref = new Firebase("https://todorpg.firebaseio.com/Users/"+ TaskPage.userID);
        ref.child("numTasks").setValue(this.getCount());
        ref.child("Task " + countStr);
        ref = new Firebase("https://todorpg.firebaseio.com/Users/"+TaskPage.userID+"/Tasks/Task "+countStr);
        ref.child("name").setValue(object.getName());
        ref.child("description").setValue(object.getDescription());
        ref.child("category").setValue(object.getCategory());
        ref.child("difficulty").setValue(object.getDifficulty());

    }

    @Override
    public void remove(TaskData object) {
        int remove = this.getPosition(object);//0 index remove spot.

        //Remove from  DB

        Firebase ref = new Firebase("https://todorpg.firebaseio.com/Users/"+TaskPage.userID+"/Tasks");

        //Renumber and rename
        for (int i = 0; i<this.getCount();i++)
        {
            String str = String.valueOf(i);
            String strMinus = String.valueOf(i-1);
            TaskData task;
            if(i>remove) {
                //Copy
                task = this.getItem(i);
                ref.child("Task "+str).child("name").setValue(task.getName());
                ref.child("Task "+str).child("description").setValue(task.getDescription());
                ref.child("Task "+str).child("category").setValue(task.getCategory());
                ref.child("Task "+str).child("difficulty").setValue(task.getDifficulty());
            }
        }


        ref = new Firebase("https://todorpg.firebaseio.com/Users/"+TaskPage.userID);
        String last = String.valueOf(this.getCount());
        ref.child("Tasks").child("Task " +last).setValue(null);
        super.remove(object);
        ref.child("Tasks");
        ref.child("numTasks").setValue(this.getCount());
    }

    private void confirmRemove(Context c, final int pos, String s) {
        new AlertDialog.Builder(c)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("CONFIRM")
                .setMessage("Did you " + s + " this task?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        stats.updateStat(getItem(pos).getCategory(), getItem(pos).getDifficulty());
                        remove(getItem(pos));
                        notifyDataSetChanged();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    public void setCallback(ItemsAdapterCallback callback){
        this.callback = callback;
    }

    public interface ItemsAdapterCallback {
        void editTask(Bundle b);
    }


}
