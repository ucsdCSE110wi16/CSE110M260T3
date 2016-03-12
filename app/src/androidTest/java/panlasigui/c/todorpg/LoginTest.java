package panlasigui.c.todorpg;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import panlasigui.c.todorpg.Activities.LoginActivity;
import panlasigui.c.todorpg.Activities.TaskPage;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginTest {
    private String inputUsername;
    private String inputPassword;

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);

    @Before
    public void initInput() {
        // Specify a valid string.
        this.inputUsername = "LoginTest@tester.com";
        this.inputPassword = "LoginTest";
    }

    @Test
    public void changeText_sameActivity() {
        // Type text and then press the button.
        onView(withId(R.id.email)).perform(typeText(inputUsername));
        onView(withId(R.id.password)).perform(typeText(inputPassword), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button)).perform(click());

        while (TaskPage.account == null) {}
        int xp = TaskPage.account.getCharismaXP() + TaskPage.account.getFitnessXP()
                  + TaskPage.account.getHealthXP() + TaskPage.account.getIntelligenceXP();

        assertThat(xp, is(10));
    }
}
