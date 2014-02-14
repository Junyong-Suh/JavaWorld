import java.io.*;
import java.util.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class VenmoShell {

	private final int INTERACTIVE_MODE = 0;
	private final int BATCH_MODE = 1;

	// commands avaiable on Venmo Shell
	private List<String> venmoCommands = Arrays.asList("help", "exit", "quit", "user", "add", "pay", "feed", "balance");

	// user data on Venmo Shell
	private HashMap<String, User> venmoUsers = new HashMap<String, User>();
	private HashMap<String, Card> venmoCards = new HashMap<String, Card>();

	public VenmoShell() {
		// anything we need to do in the constructor? 
	}

	/*
		main method to run the Venmo Shell
	*/
	public void run(String[] args) {
		int argsLength = args.length;

		switch (argsLength) {
			case INTERACTIVE_MODE:
				interactiveShell();
				break;
			case BATCH_MODE:
				batchShell(args[0]);
				break;
			default:
				System.out.println("\nERROR: VenmoShell only supports two modes.\n\t1. interactively (from stdin), when run with no arguments. (e.g., java miniVenmo)\n\t2. from a file of newline-delimited commands, when provided with one argument. (e.g., java miniVenmo input)\n");
				break;
		}

		System.out.println("Thanks for using VenmoShell. Bye!");
	}

	// interactively (from stdin), when run with no arguments
	private void interactiveShell() {
		System.out.println("Welcome to VenmoShell. Type 'help' to see instructions, 'quit' or 'exit' to end VenmoShell.");

		// open up standard input
      	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String userCommand = "";
		while (true) {
			System.out.print("VenmoShell> ");
			userCommand = readCommand(br);

			// interpret commands
			if (userCommand.equalsIgnoreCase("exit") || userCommand.equalsIgnoreCase("quit")) {
				break;
			} else {
				interpretCommand(userCommand);
			}
		}
	}

	private void interpretCommand(String userCommand) {
		String[] userCommands = userCommand.split(" ");	// All input is space delimited.
		// filter empty commands
		if (userCommands.length == 0) {
			displayCommandError(userCommand);
			return ;
		} 
		
		String command = userCommands[0];
		// filter invalid commands
		if (!venmoCommands.contains(command)) {
			displayCommandError(userCommand);
			return ;
		}

		// a command that doesn't have any arguments
		if (userCommand.equals("help")) {
			commandHelp();
			return ;
		}

		// commands with arguments
		if (command.startsWith("feed")) {
			commandFeed(userCommand);
		} else if (command.startsWith("balance")) {
			commandBalance(userCommand);
		} else if (command.startsWith("user")) {
			commandUser(userCommand);
		} else if (command.startsWith("add")) {
			commandAdd(userCommand);
		} else if (command.startsWith("pay")) {
			commandPay(userCommand);
		} else {
			// shouldn't be here but just in case
			displayCommandError(userCommand);
		}
	}

	/*
		"user" will create a new user with a given name.
		e.g., user <user>

		User names should be alphanumeric but also allow underscores and dashes.
		User names should be no shorter than 4 characters but no longer than 15.
		Users start with a balance of $0.
	*/
	private void commandUser(String userCommand) {
		String[] userCommands = userCommand.split(" ");	// All input is space delimited.
		if (userCommands.length != 2) {
			displayInvalidArgumentsError();
			return ;
		}

		String userName = userCommands[1];

		// check if the user exists
		if (venmoUsers.containsKey(userName)) {
			System.out.println("ERROR: User with name \""+ userName +"\" already exist. Please choose another name.");
			return ;
		}

		if (User.isValidUserName(userName)) {
			// create user with the name
			venmoUsers.put(userName, new User(userName));
		}
	}

	/*
		"add" will create a new credit card for a given name, card number
		e.g., add <user> <card_number>

		Card numbers should be validated using Luhn-10.
		Cards that fail Luhn-10 will display an error.
		Cards that have already been added will display an error.
		Users can only have one card. Attempting to add a second valid card will display an error.
	*/
	private void commandAdd(String userCommand) {
		String[] userCommands = userCommand.split(" ");	// All input is space delimited.
		if (userCommands.length != 3) {
			displayInvalidArgumentsError();
			return ;
		}

		// validate user name and the card
		String userName = userCommands[1];
		String cardNumber = userCommands[2];

		// is the card already in use?
		if (venmoCards.containsKey(cardNumber)) {
			System.out.println("ERROR: Card number \""+ cardNumber +"\" is already belong to someone. Please choose other card numbers.");
			return ;
		}

		// is the card number valid?
		if (!Card.isCardNumberValid(cardNumber)) {
			return ;
		}

		// is the user exist?
		if (!venmoUsers.containsKey(userName)) {
			System.out.println("ERROR: User with name \""+ userName +"\" does NOT exist.");
			return ;
		}

		User u = venmoUsers.get(userName);
		// the user already has a card?
		if (u.hasCard()) {
			System.out.println("ERROR: User \""+ userName +"\" already has a card. Users can only have one card.");
			return ;
		}

		// create the card and assign to the user
		Card c = new Card(cardNumber);
		venmoCards.put(cardNumber, c);
		u.addCard(c);
		System.out.println("Card \""+ cardNumber +"\" is added to user \""+ userName +"\".");
	}

	/*
		"pay" will create a payment between two users.
		e.g., pay <actor> <target> <amount> <note>

		<actor> and <target> are usernames that were created in #1
		Users cannot pay themselves.
		Payments will always charge the actor's credit card (not decrement their balance).
		Payments will always increase the target's balance.
		If the actor user has no credit card, display an error.
	*/
	private void commandPay(String userCommand) {
		String[] userCommands = userCommand.split(" ");	// All input is space delimited.
		if (userCommands.length < 5) {
			displayInvalidArgumentsError();
			return ;
		}

		// get the users if exist
		String actor = userCommands[1];
		if (!venmoUsers.containsKey(actor)) {
			System.out.println("ERROR: User with name \""+ actor +"\" does NOT exist.");
			return ;
		}

		String target = userCommands[2];
		if (actor.equals(target)) {
			System.out.println("ERROR: users cannot pay themselves.");
			return ;
		}
		if (!venmoUsers.containsKey(target)) {
			System.out.println("ERROR: User with name \""+ target +"\" does NOT exist.");
			return ;
		}

		// the actor has a card?
		User a = venmoUsers.get(actor);
		if (!a.hasCard()) {
			System.out.println("ERROR: User \""+ actor +"\" does NOT have a card.");
			return ;
		}

		User t = venmoUsers.get(target);

		// Amounts will be prefixed with "$" and will be dollars and cents
		String amounts = userCommands[3];
		String[] notes = Arrays.copyOfRange(userCommands, 4, userCommands.length);

		a.chargeCreditCard(amounts);
		a.appendToFeed("You", target, amounts, notes);
		t.addBalance(amounts);
		t.appendToFeed(actor, "You", amounts, notes);

		System.out.println("User \""+ actor +"\" paid \""+ amounts +"\" to user \""+ target +"\" successfully.");
	}

	/*
		"feed" will display a feed of the respective user's payments.
		e.g., feed <user>
	*/
	private void commandFeed(String userCommand) {
		String[] userCommands = userCommand.split(" ");	// All input is space delimited.
		if (userCommands.length != 2) {
			displayInvalidArgumentsError();
			return ;
		}

		// get the user if exists then prints the feed
		String userName = userCommands[1];
		if (!venmoUsers.containsKey(userName)) {
			System.out.println("ERROR: User with name \""+ userName +"\" does NOT exist.");
			return ;
		}

		User u = venmoUsers.get(userName);
		System.out.print(u.getFeed());
	}

	/*
		"balance" will display a user's balance
		e.g. balance <user>
	*/
	private void commandBalance(String userCommand) {
		String[] userCommands = userCommand.split(" ");	// All input is space delimited.
		if (userCommands.length != 2) {
			displayInvalidArgumentsError();
			return ;
		}

		// get the user if exists then prints the balance
		String userName = userCommands[1];
		if (!venmoUsers.containsKey(userName)) {
			System.out.println("ERROR: User with name \""+ userName +"\" does NOT exist.");
			return ;
		}

		User u = venmoUsers.get(userName);
		System.out.println(u.getBalance());
	}

	private void commandHelp() {
		String usage = "\n1. \"user\" will create a new user with a given name. (e.g., user <user>)\n\tUser names should be alphanumeric but also allow underscores and dashes.\n\tUser names should be no shorter than 4 characters but no longer than 15.\n\tUsers start with a balance of $0.\n\t\n2. \"add\" will create a new credit card for a given name, card number (e.g., add <user> <card_number>)\n\tCard numbers should be validated using Luhn-10.\n\tCards that fail Luhn-10 will display an error.\n\tCards that have already been added will display an error.\n\tUsers can only have one card. Attempting to add a second valid card will display an error.\n\t\n3. \"pay\" will create a payment between two users. (e.g., pay <actor> <target> <amount> <note>)\n\t<actor> and <target> are usernames that were created in #1\n\tUsers cannot pay themselves.\n\tPayments will always charge the actor's credit card (not decrement their balance).\n\tPayments will always increase the target's balance.\n\tIf the actor user has no credit card, display an error.\n\t\n4. \"feed\" will display a feed of the respective user's payments. (e.g., feed <user>)\n\t\n5. \"balance\" will display a user's balance (e.g., balance <user>)\n\t\n6. \"help\" will show commands and instructions. (e.g., help)\n\t\n7. \"exit\" or \"quit\" (e.g., exit) will terminate this shell.\n";
		
		System.out.println(usage);
	}

	private String readCommand(BufferedReader br) {
		String command = "";

		try {
			command = br.readLine();
		} catch (IOException ioe) {
			System.out.println("ERROR: I/O error while reading your command.");
			System.exit(1);
		}

		return command;
	}

	private void displayInvalidArgumentsError() {
		System.out.println("ERROR: invalid arguments. Type 'help' for more information.");
	}

	private void displayCommandError(String command) {
		System.out.println("ERROR: command \""+ command +"\" not recognized. Type 'help' for more commands.");
	}

	// from a file of newline-delimited commands, when provided with one argument 
	// assuming the argument to be the filename to read
	private void batchShell(String argument) {
		BufferedReader br = null;

		try {
			String currentLine;
			br = new BufferedReader(new FileReader(argument));
			while ((currentLine = br.readLine()) != null) {
				System.out.println("VenmoShell> "+ currentLine);

				// interpret commands
				if (currentLine.equalsIgnoreCase("exit") || currentLine.equalsIgnoreCase("quit")) {
					break;
				} else {
					interpretCommand(currentLine);
				}
			}
		} catch (IOException e) {
			System.out.println("ERROR: input file \""+ argument +"\" does NOT exist.");
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException ex) {
				System.out.println("ERROR: failed to close BufferedReader.");
				ex.printStackTrace();
			}
		}
	}
}
