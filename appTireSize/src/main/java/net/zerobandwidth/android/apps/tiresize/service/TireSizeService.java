package net.zerobandwidth.android.apps.tiresize.service;

import android.app.Service;
import android.content.Intent;

import net.zerobandwidth.android.apps.tiresize.database.TireSizeDatabase;
import net.zerobandwidth.android.lib.database.sqlitehouse.SQLiteHouse;
import net.zerobandwidth.android.lib.services.SimpleServiceConnection;

/**
 * Service that maintains a persistent connection to the SQLite DB.
 * @since zerobandwidth-net/android-tiresize 0.0.1 (#1)
 */
public class TireSizeService
extends Service
{
/// Service Binding Management /////////////////////////////////////////////////

	public class Binder
	extends android.os.Binder
	implements SimpleServiceConnection.InstanceBinder<TireSizeService>
	{
		@Override
		public TireSizeService getServiceInstance()
		{ return TireSizeService.this ; }
	}

	protected final TireSizeService.Binder m_bind =
			new TireSizeService.Binder() ;

	@Override
	public TireSizeService.Binder onBind( Intent sig )
	{ return m_bind ; }

/// Instance Members ///////////////////////////////////////////////////////////

	protected TireSizeDatabase m_dbh = null ;

/// Service Lifecycle //////////////////////////////////////////////////////////

	@Override
	public void onCreate()
	{
		super.onCreate() ;
		m_dbh = SQLiteHouse.Factory.init().getInstance(
				TireSizeDatabase.class, this, null ) ;
	}

	@Override
	public int onStartCommand( Intent sig, int bmFlags, int nStartID )
	{
		super.onStartCommand( sig, bmFlags, nStartID ) ;
		return START_STICKY ;
	}

	@Override
	public void onDestroy()
	{
		if( m_dbh != null ) m_dbh.close() ;
		super.onDestroy();
	}

/// Instance Methods ///////////////////////////////////////////////////////////

	public TireSizeDatabase getDB()
	{ return m_dbh ; }
}
