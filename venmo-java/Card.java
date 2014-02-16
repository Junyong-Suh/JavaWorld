/**
 * Abstract class Card
 */
public abstract class Card {
	private String cardNumber;
	private String userName;
	private CardType cardType;

	public Card(String cardNumber, String userName, CardType cardType) {
		this.cardNumber = cardNumber;
		this.userName = userName;
		this.cardType = cardType;
	}

	public String getCardNumber() {
        return this.cardNumber;
	}

	public String getUserName() {
		return this.userName;
	}

	public CardType getCardType() {
        return this.cardType;
	}

    /**
     * Any card-specific validation should be in this method
     *
     * @param cardNumber - card number
     * @return boolean true if valid, false otherwise
     */
	protected abstract boolean validate(String cardNumber);
}