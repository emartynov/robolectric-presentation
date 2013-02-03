package com.ebuddy;

import com.google.inject.Injector;
import com.google.inject.util.Modules;
import com.xtremelabs.robolectric.Robolectric;
import org.junit.runners.model.InitializationError;
import roboguice.RoboGuice;
import roboguice.inject.ContextScope;
import roboguice.test.RobolectricRoboTestRunner;

public class InjectingTestRunner extends RobolectricRoboTestRunner
{
    public InjectingTestRunner ( Class<?> testClass ) throws InitializationError
    {
        super( testClass );
    }

    @Override
    public void prepareTest ( Object test )
    {
        RoboGuice.setBaseApplicationInjector( Robolectric.application,
                RoboGuice.DEFAULT_STAGE,
                Modules.override( RoboGuice.newDefaultRoboModule( Robolectric.application ) )
                        .with( ( (InjectedTest) test ).getTestModule() ) );

        Injector injector = RoboGuice.getBaseApplicationInjector( Robolectric.application );
        ContextScope scope = injector.getInstance( ContextScope.class );
        scope.enter( Robolectric.application );

        injector.injectMembers( test );
    }
}