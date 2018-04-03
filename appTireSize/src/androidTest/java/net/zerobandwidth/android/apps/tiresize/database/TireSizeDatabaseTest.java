package net.zerobandwidth.android.apps.tiresize.database;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import net.zerobandwidth.android.apps.tiresize.model.TireSizeSpec;
import net.zerobandwidth.android.lib.database.sqlitehouse.SQLiteHouse;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Exercises {@link TireSizeDatabase}.
 * @since zerobandwidth-net/android-tiresize 0.0.1 (#1)
 */
@RunWith(AndroidJUnit4.class)
public class TireSizeDatabaseTest
{
	protected static final long CONNECTION_TIMEOUT = 1000L ;

	/**
	 * Ensures that, once created/connected, the database contains the specs
	 * table that we expect.
	 */
	@Test
	public void testDatabaseHasSpecTable()
	{
		TireSizeDatabase dbh = SQLiteHouse.Factory.init().getInstance(
					TireSizeDatabase.class,
					InstrumentationRegistry.getTargetContext(),
					null
			);
		dbh.openDB() ;
		long tsGiveUp = (new Date()).getTime() + CONNECTION_TIMEOUT ;
		//noinspection StatementWithEmptyBody - This is an intentional wait.
		while( ! dbh.isConnected() && (new Date()).getTime() < tsGiveUp ) ;
		if( ! dbh.isConnected() )
			fail( "Couldn't connect to database!" ) ;
		assertNotNull( dbh.describe( TireSizeSpec.class ) ) ;
	}
}
