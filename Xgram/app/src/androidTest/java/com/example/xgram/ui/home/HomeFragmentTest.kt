package com.example.xgram.ui.home

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.supportsInputMethods
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.xgram.MainActivity
import org.hamcrest.CoreMatchers.allOf
import com.example.xgram.R
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class HomeFragmentTest {

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun searchUsername() {
        myName()
    }

    private fun myName() {
        onView(withId(R.id.searchBar))
            .perform(click())
        onView(
            allOf(
                supportsInputMethods(), isDescendantOfA(
                    withId(
                        R.id
                            .searchView
                    )
                )
            )
        )
            .perform(typeText("David4rr"))
            .perform(pressImeActionButton())
    }
}