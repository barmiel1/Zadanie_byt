package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5 );
	}

	@Test
	public void testGetName()
	{
		//sprawdzenie czy wartosc 'name' podana w konstruktorze zgadza sie z tym co zwraca metoda getName()
		assertEquals(SEK.getName(), "SEK");
		assertEquals(DKK.getName(), "DKK");
		assertEquals(EUR.getName(), "EUR");
	}
	
	@Test
	public void testGetRate()
	{
		//sprawdzenie czy wartosc 'rate' podana w konstruktorze zgadza sie z tym co zwraca metoda getRate()
		assertEquals(SEK.getRate(),0.15, .001);
		assertEquals(DKK.getRate(),0.20, .001);
		assertEquals(EUR.getRate(),1.5, .001);
	}
	
	@Test
	public void testSetRate()
	{
		double newRate = 0.5;

		//sprawdzenie czy dla kazej waluty ustawiana jest poprawna wartosc 'rate'
		SEK.setRate(newRate);
		assertEquals(SEK.getRate(), newRate, .001);

		DKK.setRate(newRate);
		assertEquals(DKK.getRate(), newRate, .001);

		EUR.setRate(newRate);
		assertEquals(EUR.getRate(), newRate, .001);
	}
	
	@Test
	public void testGlobalValue()
	{
		int testAmount = 100;

		//sprawdzenie czy ta dana kwota poprawnie konwertuje sie z danej waluty do unwiersalnej waluty
		assertEquals(SEK.universalValue(100), 15d, .001);
		assertEquals(DKK.universalValue(100), 20d, .001);
		assertEquals(EUR.universalValue(100), 150d, .001);
	}
	
	@Test
	public void testValueInThisCurrency()
	{
		int amount = 100;

		//sprawdzenie czy ta dana kwota poprawnie konwertuje sie z jednej waluty do drugiej
		assertEquals(SEK.valueInThisCurrency(amount, DKK), 133);
		assertEquals(SEK.valueInThisCurrency(amount, EUR), 1000);
		assertEquals(DKK.valueInThisCurrency(amount, EUR), 750);
	}

}
