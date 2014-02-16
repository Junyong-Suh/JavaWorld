/**
 * Credit Card Class
 */
public class CreditCard extends Card {

	CreditCard(String cardNumber, String userName) throws CardIssueException {
		super(cardNumber, userName, CardType.CREDIT);

		if (!validate(cardNumber)) {
			throw new CardIssueException("Invalid credit card number.");
		}
	}

    /**
     * Luhn 10 formula validation for credit cards
     * (http://en.wikipedia.org/wiki/Luhn_algorithm)
     *
     * @param cardNumber - card number
     * @return boolean - true if valid, false otherwise
     */
	private boolean luhn_10_validation(String cardNumber) {
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

    /**
     * Credit Card specific validation
     *
     * @param cardNumber - card number
     * @return boolean - true if valid, false otherwise
     */
	@Override
	protected boolean validate(String cardNumber) {
		return this.luhn_10_validation(cardNumber);
	}
}