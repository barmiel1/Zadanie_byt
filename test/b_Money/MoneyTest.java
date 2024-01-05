package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLOutput;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount()
	{
		//sprawdzenie czy metoda getAmount zwraca podana w konstruktorze kwote
		assertEquals(SEK100.getAmount(), 10000);
	}

	@Test
	public void testGetCurrency()
	{
		//sprawdzenie czy przypisany jest odpowiedni obiekt waluty
		assertSame(SEK100.getCurrency(), SEK);
	}

	@Test
	public void testToString()
	{
		//sprawdzenie czy money jest poprawnie wyswietlane
		assertEquals("100.0 SEK", SEK100.toString());
	}

	@Test
	public void testGlobalValue()
	{
		//sprawdzenie czy kwota poprawnie konwertuje sie z danej waluty do unwiersalnej waluty
		assertEquals(SEK100.universalValue(), 1500);
	}

	@Test
	public void testEqualsMoney()
	{
		assertFalse(SEK100.equals(EUR20));
		assertTrue(SEK100.equals(EUR10));
	}

	@Test
	public void testAdd()
	{
		assertEquals(20000, SEK100.add(EUR10).getAmount());
	}

	@Test
	public void testSub()
	{
		assertEquals(0, SEK100.sub(EUR10).getAmount());
	}

	@Test
	public void testIsZero()
	{
		assertFalse(SEK100.isZero());
		assertTrue(SEK0.isZero());
	}

	@Test
	public void testNegate()
	{
		assertEquals(SEKn100.getAmount(), SEK100.negate().getAmount());
	}

	@Test
	public void testCompareTo()
	{
		assertEquals(-1, SEKn100.compareTo(SEK100));
		assertEquals(0, SEK100.compareTo(SEK100));
		assertEquals(1, SEK100.compareTo(SEKn100));
	}
}
