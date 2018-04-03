package net.zerobandwidth.android.apps.tiresize.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static net.zerobandwidth.android.apps.tiresize.model.TireSizeSpec.NOT_SET;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Exercises {@link TireSizeSpec}.
 * @since zerobandwidth-net/android-tiresize 0.0.1 (#1)
 */
@RunWith(JUnit4.class)
public class TireSizeSpecTest
{
	private static final double EPSILON = 0.00001d ;

	/** A reused instance. */
	private TireSizeSpec m_spec ;

	/** Initializes the reusable test data instances. */
	@Before
	public void setup()
	{
		m_spec = new TireSizeSpec() ;
	}

	/**
	 * Proves that the default constructor initializes an empty object, and the
	 * full constructor sets all the proper values.
	 */
	@Test
	public void testConstruction()
	{
		assertEquals( null, m_spec.m_sID ) ;
		assertEquals( null, m_spec.m_sName ) ;
		assertEquals( NOT_SET, m_spec.m_nWidth ) ;
		assertEquals( NOT_SET, m_spec.m_nRatio ) ;
		assertEquals( NOT_SET, m_spec.m_nWheel ) ;

		TireSizeSpec spec = new TireSizeSpec( 195, 60, 15 ) ;
		assertEquals( null, spec.m_sID ) ;
		assertEquals( null, spec.m_sName ) ;
		assertEquals( 195, spec.m_nWidth ) ;
		assertEquals( 60, spec.m_nRatio ) ;
		assertEquals( 15, spec.m_nWheel ) ;
	}

	/** Exercises basic accessors and mutators. */
	@Test
	public void testBasicProperties()
	{
		assertEquals( "foo", m_spec.setID("foo").getID() ) ;
		assertEquals( "bar", m_spec.setName("bar").getName() ) ;
		assertEquals( 1, m_spec.setWidth(1).getWidth() ) ;
		assertEquals( 2, m_spec.setRatio(2).getRatio() ) ;
		assertEquals( 3, m_spec.setWheel(3).getWheel() ) ;
	}

	/** Ensures that bad inputs to the setter methods evoke exceptions. */
	@Test
	public void testBasicPropertyExceptions()
	{
		IllegalArgumentException xArg = null ;
		try { m_spec.setWidth(0) ; }
		catch( IllegalArgumentException x ) { xArg = x ; }
		assertNotNull(xArg) ;

		xArg = null ;
		try { m_spec.setRatio(0) ; }
		catch( IllegalArgumentException x ) { xArg = x ; }
		assertNotNull(xArg) ;

		xArg = null ;
		try { m_spec.setWheel(0) ; }
		catch( IllegalArgumentException x ) { xArg = x ; }
		assertNotNull(xArg) ;
	}

	/**
	 * Exercises {@link TireSizeSpec#identify()} and
	 * {@link TireSizeSpec#identify(boolean)}.
	 */
	@Test
	public void testIdentify()
	{
		String sID1 = m_spec.identify().getID() ;
		assertNotNull(sID1) ;
		String sID2 = m_spec.identify().getID() ;
		assertEquals( sID1, sID2 ) ;
		String sID3 = m_spec.identify(true).getID() ;
		assertNotEquals( sID1, sID3 ) ;
	}

	/** Exercises {@link TireSizeSpec#getDisplayName()}. */
	@Test
	public void testGetDisplayName()
	{
		m_spec.setWidth(215).setRatio(45).setWheel(17) ;
		assertEquals( m_spec.toString(), m_spec.getDisplayName() ) ;
		m_spec.setName( "flargle" ) ;
		assertEquals( "flargle", m_spec.getDisplayName() ) ;
		m_spec.setName( "" ) ;
		assertEquals( m_spec.toString(), m_spec.getDisplayName() ) ;
	}

	/** Exercises {@link TireSizeSpec#toString}. */
	@Test
	public void testCustomToString()
	{
		assertEquals( "?/?R?", m_spec.toString() ) ;
		m_spec.setWidth(195).setRatio(50).setWheel(17) ;
		assertEquals( "195/50R17", m_spec.toString() ) ;
	}

	/** Exercises {@link TireSizeSpec#getWidthInches}. */
	@Test
	public void testGetWidthInches()
	{
		assertEquals( Double.NaN, m_spec.getWidthInches(), EPSILON ) ;
		m_spec.setWidth(195) ;
		assertEquals( 7.67717d, m_spec.getWidthInches(), EPSILON ) ;
	}

	/** Exercises {@link TireSizeSpec#getSidewallMillimeters}. */
	@Test
	public void testGetSidewallMillimeters()
	{
		assertEquals( Double.NaN, m_spec.getSidewallMillimeters(), EPSILON ) ;
		m_spec.setWidth(235) ;
		assertEquals( Double.NaN, m_spec.getSidewallMillimeters(), EPSILON ) ;
		m_spec.setRatio(45) ;
		assertEquals( 105.75d, m_spec.getSidewallMillimeters(), EPSILON ) ;
		m_spec.setWidth( NOT_SET ) ;
		assertEquals( Double.NaN, m_spec.getSidewallMillimeters(), EPSILON ) ;
	}

	/** Exercises {@link TireSizeSpec#getSidewallInches} */
	@Test
	public void testGetSidewallInches()
	{
		assertEquals( Double.NaN, m_spec.getSidewallInches(), EPSILON ) ;
		m_spec.setWidth(205) ;
		assertEquals( Double.NaN, m_spec.getSidewallInches(), EPSILON ) ;
		m_spec.setRatio(50) ;
		assertEquals( 4.035433071d, m_spec.getSidewallInches(), EPSILON ) ;
		m_spec.setWidth(NOT_SET) ;
		assertEquals( Double.NaN, m_spec.getSidewallInches(), EPSILON ) ;
	}

	/** Exercises {@link TireSizeSpec#getFullDiameterInches} */
	@Test
	public void testGetFullDiameterInches()
	{
		assertEquals( Double.NaN, m_spec.getFullDiameterInches(), EPSILON ) ;
		m_spec.setWidth(245) ;
		assertEquals( Double.NaN, m_spec.getFullDiameterInches(), EPSILON ) ;
		m_spec.setRatio(35) ;
		assertEquals( Double.NaN, m_spec.getFullDiameterInches(), EPSILON ) ;
		m_spec.setWheel(18) ;
		assertEquals( 24.751968504d, m_spec.getFullDiameterInches(), EPSILON ) ;
	}

	/** Exercises {@link TireSizeSpec#getCircumferenceInches}*/
	@Test
	public void testGetCircumferenceInches()
	{
		assertEquals( Double.NaN, m_spec.getCircumferenceInches(), EPSILON ) ;
		m_spec.setWidth(245).setRatio(35).setWheel(18) ;
		assertEquals( 77.760602414d, m_spec.getCircumferenceInches(), EPSILON ) ;
	}

	/** Exercises {@link TireSizeSpec#getRevolutionsPerMile} */
	@Test
	public void testGetRevolutionsPerMile()
	{
		assertEquals( Double.NaN, m_spec.getRevolutionsPerMile(), EPSILON ) ;
		m_spec.setWidth(245).setRatio(35).setWheel(18) ;
		assertEquals( 814.808502417d, m_spec.getRevolutionsPerMile(), EPSILON ) ;
	}
}
