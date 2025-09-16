package com.migualador.cocktails

import androidx.compose.ui.test.ComposeTimeoutException
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.Navigation.findNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.migualador.cocktails.presentation.MainActivity
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @Before
    fun setUp() {
        // navigates to home screen
        composeTestRule.activityRule.scenario.onActivity {
            findNavController(it, R.id.navHostFragment).navigate(R.id.action_splashFragment_to_homeFragment)
        }

        composeTestRule.waitForIdle()
    }

    @Test
    fun home_screen_displays_loading_indicator_then_hides_it() {

        try {
            composeTestRule.onNodeWithTag("loading_indicator").assertIsDisplayed()

            composeTestRule.waitUntil(timeoutMillis = 10000) {
                composeTestRule.onAllNodesWithTag("loading_indicator").fetchSemanticsNodes()
                    .isEmpty()
            }

        } catch (e: ComposeTimeoutException) {
            throw AssertionError("Loading indicator did not disappear within 10 seconds. Consider also the network state.", e)
        }
    }

    @Test
    fun after_loading_data_home_screen_shows_list_of_alcoholic_cocktails() {

        try {
            composeTestRule.waitUntil(timeoutMillis = 10000) {
                composeTestRule.onAllNodesWithTag("loading_indicator").fetchSemanticsNodes()
                    .isEmpty()
            }

            composeTestRule.onNodeWithTag("alcoholic_cocktails_list").assertIsDisplayed()

        } catch (e: ComposeTimeoutException) {
            throw AssertionError("Loading indicator did not disappear within 10 seconds. Consider also the network state.", e)
        }
    }

    @Test
    fun after_loading_data_home_screen_list_of_alcoholic_cocktails_has_any_item() {

        try {
            composeTestRule.waitUntil(timeoutMillis = 10000) {
                composeTestRule.onAllNodesWithTag("loading_indicator").fetchSemanticsNodes()
                    .isEmpty()
            }

            val items = composeTestRule.onAllNodesWithTag("cocktail_item").fetchSemanticsNodes()
            assert(items.isNotEmpty())

        } catch (e: ComposeTimeoutException) {
            throw AssertionError("Loading indicator did not disappear within 10 seconds. Consider also the network state.", e)
        }
    }

}