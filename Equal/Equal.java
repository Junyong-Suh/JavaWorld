public class Equal {
	public static void main(String[] args) {
		String s = new String("new");
		String v = new String("new");
		System.out.println(s == v); 	 // false
		System.out.println(s.equals(v)); // true
		String u = "new";
		String w = "new";
		System.out.println(u == w);	 // true
		System.out.println(u.equals(w)); // true
		System.out.println(s == u);	 // false
		System.out.println(s.equals(u)); // true
	}
}
