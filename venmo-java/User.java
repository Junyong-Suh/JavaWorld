import java.util.regex.Pattern;

public class User {
	private Balance balance;
	private StringBuilder feed;
	private Card card;
	private String userName;

	public User(String userName) {
		this.userName = userName;
		balance = new Balance();
		feed = new StringBuilder("");
		card = null;
	}

	public String getUserName() {
		return userName;
	}

	public void addCard(Card c) {
		card = c;
	}

	public boolean hasCard() {
		return (card != null);
	}

	public void chargeCreditCard(String amounts) {
		if (this.hasCard()) {
			// charge the credit card
		} else {
			// roll back the transaction?
		}
	}

	public void appendToFeed(String actor, String target, String amounts, String[] notes) {
		feed.append(actor).append(" paid ").append(target).append(" "+ amounts).append(" for ");
		for (int i = 0; i < notes.length; i++) {
			feed.append(notes[i]).append(" ");
		}
		feed.append("\n");
	}

	public String getFeed() {
		return feed.toString();
	}

	public void addBalance(String amounts) {
		balance.deposit(amounts);
	}

	public String getBalance() {
		return balance.getBalance();
	}

	static public boolean isValidUserName(String userName) {
		// User names should be alphanumeric but also allow underscores and dashes.
		String USER_NAME_REGEX = "[\\w-]*";

		if (userName.length() < 4) {
			System.out.println("User names should be no shorter than 4 characters");
			return false;
		}

		if (15 < userName.length()) {
			System.out.println("User names should be no longer than 15.");
			return false;
		}
		
		if (!Pattern.matches(USER_NAME_REGEX, userName)) {
			System.out.println("User names should be alphanumeric but also allow underscores and dashes.");
			return false;
		}

		System.out.println("User "+ userName +" added.");
		return true;
	}
}