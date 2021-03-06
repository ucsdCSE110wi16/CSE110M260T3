package panlasigui.c.todorpg.Classes;

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

    public TaskData (String name, String description, String category, float difficulty) {
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.category = category;
    }
    public TaskData() {}

    public String getName() {
        return name;
    }

    public String getDescription() { return description; }

    public String getCategory() { return category; }

    public float getDifficulty() { return difficulty; }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDifficulty(float difficulty) { this.difficulty = difficulty; }

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

    public static Comparator<TaskData> compTaskTime = new Comparator<TaskData>() {
        public int compare(TaskData task1, TaskData task2){
            /*String task1Date = task1.getDate();
            String task2Date = task2.getDate();

            int datecmp = task1Date.compareToIgnoreCase(task2Date);
            if (datecmp < 0) return -1;
            else if (datecmp > 0) return 1;
            else {
                return task1.getTime().compareToIgnoreCase(task2.getTime());
            }*/

            String task1Name = task1.getName().toUpperCase();
            String task2Name = task2.getName().toUpperCase();

            return task1Name.compareTo(task2Name);
        }
    };
}
