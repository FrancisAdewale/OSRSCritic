package com.example.osrscritic

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class IndexActivityTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(IndexActivity::class.java)

    @Test
    fun indexShouldDisplay() {

        onView(withId(R.id.index_layout)).check(matches(isDisplayed()))
    }

    @Test
    fun otherEmailBtnWorks() {
        onView(withId(R.id.other_email_btn)).check(matches(isClickable()))
    }

    @Test
    fun otherEmailBtnGoesNext() {
        onView(withId(R.id.other_email_btn)).perform(click())
    }

    @Test
    fun googleSignInWorks() {
        onView(withId(R.id.google_sign_in)).perform(click())
    }


}