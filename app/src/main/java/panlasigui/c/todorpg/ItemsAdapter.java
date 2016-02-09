package panlasigui.c.todorpg;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Cameron on 2/6/2016.
 * For CSE 110 Project
 */
public class ItemsAdapter<T> extends ArrayAdapter<TaskData> {

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

        TextView taskName = (TextView) convertView.findViewById(R.id.taskInstanceName);
        TextView taskDescription = (TextView) convertView.findViewById(R.id.taskInstanceDesc);
        TextView taskCategory = (TextView) convertView.findViewById(R.id.taskInstanceCategory);
        RatingBar taskDifficulty = (RatingBar) convertView.findViewById(R.id.taskInstanceDiff);

        taskName.setText(task.getName());
        taskDescription.setText(task.getDescription());
        taskCategory.setText(task.getCategory());
        taskDifficulty.setRating(task.getDifficulty());

        return convertView;
    }

    private void confirmRemove(Context c, final int pos, String s) {
        new AlertDialog.Builder(c)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("CONFIRM")
                .setMessage("Did you " + s + " this task?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        remove(getItem(pos));
                        notifyDataSetChanged();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

}
