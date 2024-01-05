package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}
	
	@Test
	public void testAddRemoveTimedPayment()
	{
		assertFalse(testAccount.timedPaymentExists("payment1")); // czy płatność nie istnieje na początku

		//dodanie płatności cyklicznej
		testAccount.addTimedPayment("payment1", 5, 3, new Money(100, SEK), SweBank, "Alice");
		assertTrue(testAccount.timedPaymentExists("payment1")); // czy płatność została dodana

		//usunięcie płatności cyklicznej
		testAccount.removeTimedPayment("payment1");
		assertFalse(testAccount.timedPaymentExists("payment1")); // czy płatność została usunięta
	}

	// do roboty
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException
	{
		testAccount.addTimedPayment("payment2", 2, 1, new Money(500, SEK), SweBank, "Alice");

		//Symulacja upływu czasu, powinna nastąpić jedna płatność
		testAccount.tick();

		//sprawdzenie, czy konto Alice otrzymało płatność
		assertEquals(1000500, SweBank.getBalance("Alice"));

	}

	@Test
	public void testAddWithdraw()
	{
		int initialBalance = testAccount.getBalance().getAmount();

		//wypłata i wpłata pieniędzy na koncie
		testAccount.withdraw(new Money(initialBalance, SEK));
		testAccount.deposit(new Money(initialBalance, SEK));

		int finalBalance = testAccount.getBalance().getAmount();

		assertEquals(finalBalance, initialBalance);
	}
	
	@Test
	public void testGetBalance()
	{
		Money expectedBalance = new Money(10000000, SEK); // Oczekiwane saldo to początkowa wartość
		assertEquals(expectedBalance.getAmount(), testAccount.getBalance().getAmount());
	}
}
