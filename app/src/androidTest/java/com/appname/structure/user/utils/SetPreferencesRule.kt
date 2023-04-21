

package com.appname.structure.user.utils

/**
 * Rule to be used in tests that sets the preferences needed for avoiding onboarding flows,
 * resetting filters, etc.
class SetPreferencesRule : TestWatcher() {

    @InstallIn(SingletonComponent::class)
    @EntryPoint
    interface SetPreferencesRuleEntryPoint {
        fun preferenceStorage(): PreferenceStorage
        @ApplicationScope
        fun applicationScope(): CoroutineScope
    }

    override fun starting(description: Description?) {
        super.starting(description)

        EntryPointAccessors.fromApplication(
            ApplicationProvider.getApplicationContext(),
            SetPreferencesRuleEntryPoint::class.java
        ).preferenceStorage().apply {
            runTest {
              userLoggedIn(true)
            }
        }
    }

    override fun finished(description: Description) {
        // At the end of every test, cancel the application scope
        // So DataStore is closed
        EntryPointAccessors.fromApplication(
            ApplicationProvider.getApplicationContext(),
            SetPreferencesRuleEntryPoint::class.java
        ).applicationScope().cancel()
        super.finished(description)
    }
}*/
