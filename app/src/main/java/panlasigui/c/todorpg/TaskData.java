package panlasigui.c.todorpg;

/**
 * Created by Cameron on 2/5/2016.
 */
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


}
