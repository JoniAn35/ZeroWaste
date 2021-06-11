package uti;

public class Newbie extends User{
	
	public Newbie(int result) {
		super(result);
		super.buttonCount = 3;
		super.userName = "newbie";
	}

	@Override
	public String print() {
		return "You are a NEWBIE environmentalist! You have little or no experience with saving the environment. "
				+ "But don't worry because we have prepared a lot of tasks for you in order to help you be more eco-friendly.";
		
	}

}
