package panlasigui.c.todorpg;

/**
 * Created by Cameron on 2/5/2016.
 * Edited by Theodorus on 2/13/2016.
 */

import java.util.Comparator;

public class TaskData {

    private String name;
    private String description;
    private String category;
    private float difficulty;


    // constructor for if there is no description
    /*
    public TaskData (String name,  int difficulty, String category) {

        this.name = name;
        this.difficulty = difficulty;
        this.category = category;

    }
    */
    public TaskData (String name, String description, String category, float difficulty ) {

        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.category = category;

    }
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public float getDifficulty() {
        return difficulty;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDifficulty(float difficulty) {
        this.difficulty = difficulty;
    }

    public static Comparator<TaskData> compTaskName = new Comparator<TaskData>() {
        public int compare(TaskData task1, TaskData task2){
            String task1Name = task1.getName().toUpperCase();
            String task2Name = task2.getName().toUpperCase();

            return task1Name.compareTo(task2Name);
        }
    };

    public static Comparator<TaskData> compTaskDiff = new Comparator<TaskData>() {
        public int compare(TaskData task1, TaskData task2){
            float task1Diff = task1.getDifficulty();
            float task2Diff = task2.getDifficulty();

            if(task1Diff < task2Diff)
                return -1;
            if(task1Diff < task2Diff)
                return 1;
            return 0;
        }
    };

    public static Comparator<TaskData> compTaskCat = new Comparator<TaskData>() {
        public int compare(TaskData task1, TaskData task2){
            String task1Cat = task1.getCategory().toUpperCase();
            String task2Cat = task2.getCategory().toUpperCase();

            return task1Cat.compareTo(task2Cat);
        }
    };



}
