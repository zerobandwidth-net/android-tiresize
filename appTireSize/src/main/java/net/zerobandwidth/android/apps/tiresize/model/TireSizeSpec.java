package net.zerobandwidth.android.apps.tiresize.model;

import net.zerobandwidth.android.lib.database.sqlitehouse.SQLightable;
import net.zerobandwidth.android.lib.database.sqlitehouse.annotations.SQLiteColumn;
import net.zerobandwidth.android.lib.database.sqlitehouse.annotations.SQLitePrimaryKey;
import net.zerobandwidth.android.lib.database.sqlitehouse.annotations.SQLiteTable;

import java.util.UUID;

/**
 * Contains saved data for a tire size specification.
 * @since 0.0.1 (#1)
 */
@SQLiteTable("specs")
public class TireSizeSpec
implements SQLightable
{
	/** Magic value to indicate that a field has not been initialized */
	public static final int NOT_SET = -1 ;

	/**
	 * Placeholder when rendering the spec as text, but a field has not been
	 * initialized.
	 * @see #toString()
	 */
	public static final String NOT_SET_DISPLAY = "?" ;

	/**
	 * A static constant for the number of inches in a millimeter.
	 * Used to calculate the height of the sidewall in inches.
	 */
	public static final double INCHES_PER_MILLIMETER = 0.03937007874d ;

	/**
	 * A unique ID for this specification if it is stored in the app's database.
	 */
	@SQLiteColumn( name="spec_id", index=0 )
	@SQLitePrimaryKey
	protected String m_sID = null ;

	/** A display name for the specification. */
	@SQLiteColumn( name="name", index=1 )
	protected String m_sName = null ;

	/** The width of the tire, in <b>millimeters</b>. */
	@SQLiteColumn( name="width", index=2 )
	protected int m_nWidth = NOT_SET ;

	/** The "ratio" (percentage) of tire width to sidewall height. */
	@SQLiteColumn( name="ratio", index=3 )
	protected int m_nRatio = NOT_SET ;

	/** The diameter of the wheel, in <b>inches</b>. */
	@SQLiteColumn( name="wheel", index=4 )
	protected int m_nWheel = NOT_SET ;

	/**
	 * Empty constructor. Required by certain features of {@link SQLightable}.
	 */
	public TireSizeSpec() {}

	/**
	 * A "full" constructor, allowing definition of the spec, but not ID or
	 * name.
	 * @param nWidth the width of the tire, in <b>millimeters</b>
	 * @param nRatio the "ratio" (percentage) of tire width to sidewall height
	 * @param nWheel the diameter of the wheel, in <b>inches</b>
	 * @throws IllegalArgumentException if any of the values are non-positive
	 */
	public TireSizeSpec( int nWidth, int nRatio, int nWheel )
	throws IllegalArgumentException
	{ this.setWidth(nWidth).setRatio(nRatio).setWheel(nWheel) ; }

	/**
	 * Accesses the spec's database ID, if any.
	 * @return the spec's unique ID
	 */
	public String getID()
	{ return m_sID ; }

	/**
	 * Mutates the spec's database ID.
	 * Should not be used generally; the database framework will manage this.
	 * @param s the spec's unique ID
	 * @return (fluid)
	 */
	public TireSizeSpec setID( String s )
	{ m_sID = s ; return this ; }

	/**
	 * Sets a random UUID for the instance, if it has not been set already.
	 * @return (fluid)
	 */
	public TireSizeSpec identify()
	{ return this.identify(false) ; }

	/**
	 * Sets a random UUID for the instance.
	 * If an ID has already been set, it will be overridden only if requested by
	 * setting the {@code bForce} argument to {@code true}.
	 * @param bForce specifies whether to overwrite an existing ID
	 * @return (fluid)
	 */
	public TireSizeSpec identify( boolean bForce )
	{
		if( m_sID == null || bForce )
			m_sID = UUID.randomUUID().toString() ;
		return this ;
	}

	/**
	 * Accesses the spec's user-assigned name.
	 * @return the spec's user-assigned name
	 */
	public String getName()
	{ return m_sName ; }

	/**
	 * Mutates the spec's user-assigned name.
	 * @param s the spec's user-assigned name
	 * @return (fluid)
	 */
	public TireSizeSpec setName( String s )
	{ m_sName = s ; return this ; }

	/**
	 * Gets a "display name" for the spec.
	 * This is the user-assigned name if set, or the specification itself
	 * otherwise.
	 * @return the spec's user-assigned name, or its dimensions
	 * @see #toString()
	 */
	public String getDisplayName()
	{
		if( m_sName == null || m_sName.isEmpty() )
			return this.toString() ;
		else return m_sName ;
	}

	/**
	 * Accesses the tire width.
	 * @return the width of the tire, in <b>millimeters</b>
	 */
	public int getWidth()
	{ return m_nWidth ; }

	/**
	 * Mutates the tire width.
	 * @param n the width of the tire, in <b>millimeters</b>
	 * @return (fluid)
	 * @throws IllegalArgumentException if the value is non-positive
	 */
	public TireSizeSpec setWidth( int n )
	throws IllegalArgumentException
	{
		if( n <= 0 && n != NOT_SET )
			throw new IllegalArgumentException( "Width must be positive." ) ;
		m_nWidth = n ;
		return this ;
	}

	/**
	 * Accesses the tire's width/sidewall ratio.
	 * @return the "ratio" (percentage) of tire width to sidewall height
	 */
	public int getRatio()
	{ return m_nRatio ; }

	/**
	 * Mutates the tire's width/sidewall ratio.
	 * @param n the "ratio" (percentage) of tire width to sidewall height
	 * @return (fluid)
	 */
	public TireSizeSpec setRatio( int n )
	{
		if( n <= 0 && n != NOT_SET )
			throw new IllegalArgumentException( "Ratio must be positive." ) ;
		m_nRatio = n ;
		return this ;
	}

	/**
	 * Accesses the wheel diameter.
	 * @return the diameter of the wheel, in <b>inches</b>
	 */
	public int getWheel()
	{ return m_nWheel ; }

	/**
	 * Mutates the wheel diameter.
	 * @param n the diameter of the wheel, in <b>inches</b>
	 * @return (fluid)
	 */
	public TireSizeSpec setWheel( int n )
	{
		if( n <= 0 && n != NOT_SET )
			throw new IllegalArgumentException("Wheel size must be positive.") ;
		m_nWheel = n ;
		return this ;
	}

	/**
	 * Renders the tire size specification in the canonical format:
	 * <code><i>www</i><b>/</b><i>ss</i><b>R</b><i>ll</i></code>, where
	 * {@code www} is the width, {@code ss} is the sidewall ratio, and
	 * {@code ll} is the wheel diameter. For example, {@code 205/50R17}.
	 * @return a string representation of the tire size spec
	 */
	@Override
	public String toString()
	{
		return (new StringBuilder())
			.append(( m_nWidth != NOT_SET ? m_nWidth : NOT_SET_DISPLAY ))
			.append( "/" )
			.append(( m_nRatio != NOT_SET ? m_nRatio : NOT_SET_DISPLAY ))
			.append( "R" )
			.append(( m_nWheel != NOT_SET ? m_nWheel : NOT_SET_DISPLAY ))
			.toString()
			;
	}

	/**
	 * Converts the width measurement (if set) from millimeters to inches.
	 * @return the width of the tire, in <b>inches</b>, or {@link Double#NaN} if
	 *  the width is not set
	 */
	public double getWidthInches()
	{
		if( m_nWidth == NOT_SET ) return Double.NaN ;
		return ((double)m_nWidth) * INCHES_PER_MILLIMETER ;
	}

	/**
	 * Calculates the actual height of the sidewall.
	 * @return the height of the sidewall, in <b>millimeters</b>, or
	 * {@link Double#NaN} if either the width or ratio is not set
	 */
	public double getSidewallMillimeters()
	{
		if( m_nWidth == NOT_SET || m_nRatio == NOT_SET ) return Double.NaN ;
		return ((double)m_nWidth) * ((double)m_nRatio) / 100.0d ;
	}

	/**
	 * Calculates the actual height of the sidewall.
	 * @return the height of the sidewall, in <b>inches</b>, or
	 *  {@link Double#NaN} if either the width or ratio is not set
	 */
	public double getSidewallInches()
	{
		if( m_nRatio == NOT_SET ) return Double.NaN ;
		return this.getWidthInches() * m_nRatio / 100.0d ;
	}

	/**
	 * Calculates the true diameter of the tire (not wheel).
	 * @return the diameter of the tire, in <b>inches</b>, or {@link Double#NaN}
	 *  if the width, sidewall ratio, or wheel diameter is not set
	 */
	public double getFullDiameterInches()
	{
		if( m_nWheel == NOT_SET ) return Double.NaN ;
		return this.getSidewallInches() * 2 + ((double)m_nWheel) ;
	}

	/**
	 * Calculates the circumference of the tire.
	 * @return the circumference of the tire, in <b>inches</b>, or
	 *  {@link Double#NaN} if any basic measurement is not set
	 */
	public double getCircumferenceInches()
	{ return this.getFullDiameterInches() * Math.PI ; }

	/**
	 * Calculates the number of times that the tire will turn completely over in
	 * one mile. The circumference is multiplied by the constant 63,360 &mdash;
	 * the number of inches in one mile.
	 * @return the number of times that the tire will turn in one mile, or
	 *  {@link Double#NaN} if any basic measurement is not set
	 */
	public double getRevolutionsPerMile()
	{ return ( 63360.0d / this.getCircumferenceInches() ) ; }
}
