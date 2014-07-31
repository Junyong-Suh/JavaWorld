public class Card {
	private String cardNumber;
	private String userName;

	public Card(String cardNumber, String userName) {
		this.cardNumber = cardNumber;
		this.userName = userName;
	}

	static private boolean luhn_10_validation(String cardNumber) {
		int n = 0;
		int sum = 0;

		// LUHN-10 Error Dection Algorithm (http://www.ee.unb.ca/cgi-bin/tervo/luhn.pl)
		for (int i = 0; i < cardNumber.length(); i++) {
			n = Character.getNumericValue(cardNumber.charAt(i));
			if (i % 2 == 0) {
				n = n * 2;
				if (n > 9) {
					n = 1 + (n % 10);
				}
			}
			sum = sum + n;
		}

		return (sum % 10 == 0);
	}

	static public boolean isCardNumberValid(String cardNumber) {
		// Assumption: Credit card numbers will always be numeric.
		// Credit card numbers may vary in length, up to 19 characters.

		if (19 < cardNumber.length()) {
			System.out.println("ERROR: credit card numbers should be less than 19 characters.");
			return false;
		}

		if (!luhn_10_validation(cardNumber)) {
			System.out.println("ERROR: this card is invalid.");
			return false;
		}

		return true;
	}	
}