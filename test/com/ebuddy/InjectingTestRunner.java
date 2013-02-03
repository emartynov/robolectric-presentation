package com.ebuddy;

import android.app.Application;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import org.junit.runners.model.InitializationError;
import roboguice.RoboGuice;
import roboguice.inject.ContextScope;
import roboguice.test.RobolectricRoboTestRunner;

public class InjectingTestRunner extends RobolectricRoboTestRunner
{
    private Application application;

    public InjectingTestRunner ( Class<?> testClass ) throws InitializationError
    {
        super( testClass );
    }

    @Override
    protected android.app.Application createApplication ()
    {
        application = super.createApplication();

        return application;
    }

    @Override
    public void prepareTest ( Object test )
    {
        RoboGuice.setBaseApplicationInjector( application,
                RoboGuice.DEFAULT_STAGE,
                Modules.override( RoboGuice.newDefaultRoboModule( application ) )
                        .with( ( (InjectedTest) test ).getTestModule() ) );

        Injector injector = RoboGuice.getBaseApplicationInjector( application );
        ContextScope scope = injector.getInstance( ContextScope.class );
        scope.enter( application );

        injector.injectMembers( test );
    }
}