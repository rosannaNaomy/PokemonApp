package com.np.pokemonapp

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

//used in test classes that require coroutines, regular tests don't have a main dispatcher,
// coroutine main dispatcher relies on the main looper, which is only available in a real app scenario, like launching the app,
// but inside the test source and running tests on jvm, that app environment is not available and there is no access to the main dispatcher
//We need to define our own junit rule
@ExperimentalCoroutinesApi
class MainCoroutineRule(
    private val dispatcher: CoroutineDispatcher = TestCoroutineDispatcher()
) : TestWatcher(), TestCoroutineScope by TestCoroutineScope(dispatcher) {

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        cleanupTestCoroutines()
        Dispatchers.resetMain()
    }

}