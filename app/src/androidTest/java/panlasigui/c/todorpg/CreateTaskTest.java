package panlasigui.c.todorpg;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.ClipData;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import java.util.ArrayList;

import panlasigui.c.todorpg.Activities.LoginActivity;
import panlasigui.c.todorpg.Activities.TaskPage;
import panlasigui.c.todorpg.Classes.TaskData;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.core.Is.is;


/**
 * Created by tqian on 3/11/16.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CreateTaskTest {
    private String inputUsername;
    private String inputPassword;
    private String taskName;
    private String taskDesc;
    private String taskCat;
    private float taskDif;

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);
    @Before
    public void initInput() {
        this.inputUsername = "CreateTaskTest@tester.com";
        this.inputPassword = "CreateTask";
        taskName = "Testing Task";
        taskDesc = "This is a test!";
        taskCat = "Fitness";
        taskDif = (float)3.0;
    }

    @Test
    public void testCreatingTask() {
        //login first
        onView(withId(R.id.email)).perform(typeText(inputUsername));
        onView(withId(R.id.password)).perform(typeText(inputPassword), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button)).perform(click());
        while(TaskPage.taskList == null) {}
        int size = TaskPage.taskList.size();

        //create task
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.taskName)).perform(typeText(this.taskName));
        onView(withId(R.id.taskDescription)).perform(typeText(this.taskDesc), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.taskCategorySpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(taskCat))).perform(click()); //THIS LINE
        onView(withId(R.id.difficultyBar)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());

        while(TaskPage.taskList.size() == size) {}
        TaskData data = TaskPage.taskList.get(0);
        assertThat(data.getName(), is(this.taskName));
        assertThat(data.getDescription(), is(this.taskDesc));
        assertThat(data.getCategory(), is(this.taskCat));
        assertThat(data.getDifficulty(), is(this.taskDif));
    }
}
