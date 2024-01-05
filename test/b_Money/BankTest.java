package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName()
	{
		assertEquals("SweBank", SweBank.getName());
	}

	@Test
	public void testGetCurrency()
	{
		assertEquals(SEK, SweBank.getCurrency());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException
	{
		SweBank.openAccount("John");	//stara wersja tej metody nie dodawala nowego konta
		assertEquals(0, SweBank.getBalance("John"));
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException
	{
		//metoda podnosila wyjatek AccountDoesNotExistException dla konta ktore istnije
		Money depositMoney = new Money(100, SEK);
		SweBank.deposit("Ulrika", depositMoney);
		assertEquals(100, SweBank.getBalance("Ulrika"));
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException
	{
		Money depositMoney = new Money(50, SEK);
		SweBank.deposit("Bob", depositMoney);

		Money withdrawalMoney = new Money(50, SEK);
		SweBank.withdraw("Bob", withdrawalMoney);	//po wywolanuj starej wersji metody saldo powinien wynosic 0, a wynosi nadal 50
		assertEquals(0, SweBank.getBalance("Bob"));

	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException
	{
		assertEquals(0, SweBank.getBalance("Ulrika"));
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException
	{
		Money transferMoney = new Money(50, SEK);
		SweBank.transfer("Ulrika", Nordea, "Bob", transferMoney);
		assertEquals(50, Nordea.getBalance("Bob"));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException
	{
		Money paymentMoney = new Money(20, SEK);
		SweBank.addTimedPayment("Ulrika", "payment1", 1, 1, paymentMoney, Nordea, "Bob");
		SweBank.tick();
		assertEquals(20, Nordea.getBalance("Bob"));

	}
}
