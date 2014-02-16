/**
 * CardFactory Class
 */
public class CardFactory {

    /**
     * Validate the card before issue one
     *
     * @param cardNumber - card number
     * @param userName - user name that the card will belong to
     * @param type - type of card defined in CardType
     * @return Card - a card object
     * @throws CardIssueException - any card issue exceptions
     */
	public static Card issueCard(String cardNumber, String userName, CardType type) throws CardIssueException {
		Card card = null;

		if (hasValidCardNumberLength(cardNumber)) {
			switch (type) {
				case CREDIT:
					card = new CreditCard(cardNumber, userName);
					break;
				default:
					throw new CardIssueException("Invalid card type: "+ type);
			}
		} else {
			throw new CardIssueException("Card number too short: "+ cardNumber);
		}

		return card;
	}

    /**
     * Card number length validation for any card types
     *
     * Credit card numbers will always be numeric.
     * Credit card numbers may vary in length, up to 19 characters.
     *
     * @param cardNumber - Card number
     * @return boolean - true if valid, false otherwise
     */
	private static boolean hasValidCardNumberLength(String cardNumber) {
		return (cardNumber.length() < 20);
	}
}