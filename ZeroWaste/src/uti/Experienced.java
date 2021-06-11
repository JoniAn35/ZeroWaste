package uti;

public class Experienced extends User{

	public Experienced(int result) {
		super(result);
		super.buttonCount = 2;
		super.userName = "expirienced";
	}

	@Override
	public String print() {
		return "You are an EXPERIENCED environmentalist! You have some experience with some environmental practices. "
				+ "However, in order to make your lifestyle even more eco-friendly we have prepared some tasks for you.";
		
	}
}
