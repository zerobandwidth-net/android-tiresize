package net.zerobandwidth.android.apps.tiresize.database;

import net.zerobandwidth.android.apps.tiresize.model.TireSizeSpec;
import net.zerobandwidth.android.lib.database.sqlitehouse.SQLiteHouse;
import net.zerobandwidth.android.lib.database.sqlitehouse.annotations.SQLiteDatabaseSpec;

/**
 * Defines the database for the app, which holds saved tire size specs.
 * @since zerobandwidth-net/android-tiresize 0.0.1 (#1)
 */
@SQLiteDatabaseSpec(
		database_name = "tiresize",
		schema_version = 1,
		classes = { TireSizeSpec.class }
)
public class TireSizeDatabase
extends SQLiteHouse<TireSizeDatabase>
{
	public TireSizeDatabase( SQLiteHouse.Factory factory )
	{ super(factory) ; }
}
