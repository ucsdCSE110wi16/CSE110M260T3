package panlasigui.c.todorpg;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
//import android.view.LayoutInflater;

/**
 * Wow what a cool program
 */
public class FormDialog extends DialogFragment {

    private EditText taskName;
    private EditText taskDescription;
    private Spinner categorySpinner;
    private RatingBar difficultyBar;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.form, null);

        taskName = (EditText) view.findViewById(R.id.taskName);
        taskDescription = (EditText) view.findViewById(R.id.taskDescription);
        categorySpinner = (Spinner) view.findViewById(R.id.taskCategorySpinner);
        difficultyBar = (RatingBar) view.findViewById(R.id.difficultyBar);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.skills_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        categorySpinner.setAdapter(adapter);



        // Get the layout inflater


        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        ((TaskPage)getActivity()).createTask(
                                taskName.getText().toString(),
                                taskDescription.getText().toString(),
                                categorySpinner.getSelectedItem().toString(),
                                difficultyBar.getRating());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FormDialog.this.getDialog().cancel();
                    }
                });


        return builder.create();
    }
}

