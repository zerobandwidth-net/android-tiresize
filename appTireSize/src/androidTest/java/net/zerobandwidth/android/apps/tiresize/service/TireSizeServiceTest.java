package net.zerobandwidth.android.apps.tiresize.service;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ServiceTestRule;
import android.support.test.runner.AndroidJUnit4;

import net.zerobandwidth.android.lib.services.SimpleServiceConnection;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertNotNull;

/**
 * Exercises {@link TireSizeService}.
 * @since zerobandwidth-net/android-tiresize 0.0.1 (#1)
 */
@RunWith(AndroidJUnit4.class)
public class TireSizeServiceTest
{
	@Test
	public void testStartService()
	throws TimeoutException, InterruptedException
	{
		final ServiceTestRule rule = new ServiceTestRule() ;
		final Context ctx = InstrumentationRegistry.getTargetContext() ;
		Intent sigStart = new Intent( ctx, TireSizeService.class ) ;
		rule.startService(sigStart) ;
		// Duplicate SimpleTestServiceConnection since it's not available T_T
		IBinder bind = null ;
		final int MAX_RETRIES = 10 ;
		int nAttempt = 0 ;
		SimpleServiceConnection<TireSizeService> conn = new
				SimpleServiceConnection<>( TireSizeService.class ) ;
		while( bind == null && nAttempt++ < MAX_RETRIES )
		{
			Intent sigBind = new Intent( ctx, TireSizeService.class ) ;
			bind = rule.bindService( sigBind, conn, Context.BIND_AUTO_CREATE );
		}
		assertNotNull(bind) ;
		// TODO Why doesn't this work???
	}
}
