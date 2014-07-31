import java.text.NumberFormat;
import java.util.Locale;
import java.util.Currency;
import java.math.BigDecimal;

public class Balance {
	private BigDecimal balance;
	private Locale locale; 

	public Balance() {
		balance = new BigDecimal("0.0");
		locale = new Locale("en", "US");
	}

	public void deposit(String amounts) {
		Currency currencyInstance = Currency.getInstance(locale);
		String currencySymbol = currencyInstance.getSymbol();
		String value = amounts.replace(currencySymbol, "");

		// BigDecimal is immutable so cant set values except in constructor
		BigDecimal result = balance.add(new BigDecimal(value));
		balance = result;
	}

	// public void withdraw(String amounts) {
	// 	// BigDecimal is immutable so cant set values except in constructor
	// 	BigDecimal result = balance.subtract(new BigDecimal(amounts));
	// 	balance = result;
	// }

	public String getBalance() {
		// Get a currency formatter for the current locale.
		NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
		return fmt.format(balance);
	}
}