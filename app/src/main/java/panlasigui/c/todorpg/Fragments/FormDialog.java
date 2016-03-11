package panlasigui.c.todorpg.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import panlasigui.c.todorpg.Activities.TaskPage;
import panlasigui.c.todorpg.Classes.TaskData;
import panlasigui.c.todorpg.R;

/**
 * Wow what a cool program
 */
public class FormDialog extends DialogFragment {

    private TextView formTitle;
    private EditText taskName;
    private EditText taskDescription;
    private Spinner categorySpinner;
    private RatingBar difficultyBar;
    private ArrayAdapter<CharSequence> adapter;
    private boolean edit = false;
    private int pos;
    private EditText date;
    private EditText time;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.form, null);

        formTitle = (TextView) view.findViewById(R.id.createTaskLabel);
        taskName = (EditText) view.findViewById(R.id.taskName);
        taskDescription = (EditText) view.findViewById(R.id.taskDescription);
        categorySpinner = (Spinner) view.findViewById(R.id.taskCategorySpinner);
        difficultyBar = (RatingBar) view.findViewById(R.id.difficultyBar);
        date = (EditText) getView().findViewById(R.id.editDate);
        time = (EditText) getView().findViewById(R.id.editTime);

        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.skills_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        categorySpinner.setAdapter(adapter);

        //edit tasks
        Bundle b = getArguments();
        if (b != null) {
            edit = true;
            formTitle.setText("Edit Task");
            taskName.setText(b.getString("tN"));
            taskDescription.setText(b.getString("tD"));
            categorySpinner.setSelection(adapter.getPosition(b.getString("tC")));
            difficultyBar.setRating(b.getFloat("diff"));
            date.setText(b.getString("date"));
            time.setText(b.getString("time"));
            pos = b.getInt("pos");
        }

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        final AlertDialog d = builder.setView(view)
        // Add action buttons

                .setPositiveButton(R.string.check, null)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .create();

        d.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {

                Button b = d.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        String name = taskName.getText().toString();
                        String desc = taskDescription.getText().toString();
                        String cat = categorySpinner.getSelectedItem().toString();
                        float diff = difficultyBar.getRating();
                        String datestr = date.getText().toString();
                        String timestr = time.getText().toString();

                        SimpleDateFormat format = new SimpleDateFormat("DD-MM-YY");
                        try {
                            Date d = format.parse(datestr);
                            datestr = d.toString();
                        } catch (ParseException e) {
                            datestr = "";
                        }

                        SimpleDateFormat format2 = new SimpleDateFormat("HH-MM");
                        try {
                            Date t = format2.parse(timestr);
                            timestr = t.toString();
                        } catch (ParseException e) {
                            timestr = "";
                        }

                        if (name.equals("")) {
                            Toast.makeText(getActivity(), "Task Name Required", Toast.LENGTH_SHORT).show();
                        } else if (cat.equals("Category")) {
                            Toast.makeText(getActivity(), "Category Required", Toast.LENGTH_SHORT).show();
                        } else if (diff == 0) {
                            Toast.makeText(getActivity(), "Difficulty Required", Toast.LENGTH_SHORT).show();
                        } else if (datestr.equals("")) {
                            Toast.makeText(getActivity(), "Invalid Date Format", Toast.LENGTH_SHORT).show();
                        } else if (timestr.equals("")) {
                            Toast.makeText(getActivity(), "Invalid Time Format", Toast.LENGTH_SHORT).show();
                        } else {
                            TaskData task = new TaskData(name, desc, cat, diff, datestr, timestr); // returns float
                            if (!edit) {
                                TaskPage.itemsAdapter.add(task); //array is in TaskPage?
                            } else {
                                TaskPage.taskList.set(pos, task);
                                //Edit DB
                                String posDBstr = String.valueOf(pos +1);
                                Firebase ref = new Firebase("https://todorpg.firebaseio.com/Users/"+TaskPage.userID+"/Tasks/Task "+posDBstr);
                                ref.child("name").setValue(task.getName());
                                ref.child("description").setValue(task.getDescription());
                                ref.child("category").setValue(task.getCategory());
                                ref.child("difficulty").setValue(task.getDifficulty());
                                TaskPage.itemsAdapter.notifyDataSetChanged();
                            }
                            d.dismiss();
                        }
                    }
                });
            }
        });

        return d;
    }
}

