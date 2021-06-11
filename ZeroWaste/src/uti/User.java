package uti;

public abstract class User {
	int testResult;
	int buttonCount;
	String userName;
	
	public User(int result) {
		testResult = result;
	}
	
	public abstract String print();
	
}
