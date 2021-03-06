package panlasigui.c.todorpg;

import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import panlasigui.c.todorpg.Activities.LoginActivity;
import panlasigui.c.todorpg.Activities.TaskPage;
import panlasigui.c.todorpg.Classes.TaskData;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.core.Is.is;


/**
 * Created by tqian on 3/11/16.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EditTaskTest {
    private String inputUsername;
    private String inputPassword;
    private String taskName;
    private String taskDesc;

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);
    @Before
    public void initInput() {
        this.inputUsername = "EditTaskTest@test.com";
        this.inputPassword = "EditTask";
        taskName = "Editing Task";
        taskDesc = "This was edited!";
    }

    @Test
    public void testCreatingTask() {
        //login first
        onView(withId(R.id.email)).perform(typeText(inputUsername));
        onView(withId(R.id.password)).perform(typeText(inputPassword), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button)).perform(click());
        while(TaskPage.taskList == null) {}
        int size = TaskPage.taskList.size();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //edit task
        onData(anything()).inAdapterView(withId(R.id.lvItems)).atPosition(0).perform(click());
        onView(withId(R.id.taskName)).perform(clearText(), typeText(this.taskName));
        onView(withId(R.id.taskDescription)).perform(clearText(),
                typeText(this.taskDesc), ViewActions.closeSoftKeyboard());
        onView(withId(android.R.id.button1)).perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TaskData data = TaskPage.taskList.get(0);
        assertThat(data.getName(), is(this.taskName));
        assertThat(data.getDescription(), is(this.taskDesc));

        onData(anything()).inAdapterView(withId(R.id.lvItems)).atPosition(0).perform(click());
        onView(withId(R.id.taskName)).perform(clearText(), typeText("reverted"));
        onView(withId(R.id.taskDescription)).perform(clearText(),
                typeText("reverted"), ViewActions.closeSoftKeyboard());
        onView(withId(android.R.id.button1)).perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        data = TaskPage.taskList.get(0);
        assertThat(data.getName(), is("reverted"));
        assertThat(data.getDescription(), is("reverted"));
    }
}
