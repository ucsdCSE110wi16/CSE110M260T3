package panlasigui.c.todorpg;

import android.content.Context;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Cameron on 2/6/2016.
 */
public class ItemsAdapter<T> extends ArrayAdapter<TaskData> {

    public ItemsAdapter(Context context, ArrayList<TaskData> tasks) {
        super(context, 0, tasks);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        TaskData task = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task, parent, false);

        }

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

}
