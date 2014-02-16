/*
	miniVenmo.java

	author
		Junyong Suh (junyongsuh@gmail.com)

	last updated
		2/16/2014

	Java Environment Info
		java version "1.6.0_65"
		Java(TM) SE Runtime Environment (build 1.6.0_65-b14-462-11M4609)
		Java HotSpot(TM) 64-Bit Server VM (build 20.65-b04-462, mixed mode)
*/
public class miniVenmo {
	public static void main(String[] args) {
		VenmoShell v = new VenmoShell();
		v.run(args);
	}
}