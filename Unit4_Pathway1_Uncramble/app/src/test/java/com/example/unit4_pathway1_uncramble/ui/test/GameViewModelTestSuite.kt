package com.example.unit4_pathway1_uncramble.ui.test

import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * Test suite that runs all GameViewModel related tests.
 * This provides a convenient way to run all tests together.
 */
@RunWith(Suite::class)
@Suite.SuiteClasses(
    GameViewModelTest::class,
    GameViewModelPerformanceTest::class
)
class GameViewModelTestSuite