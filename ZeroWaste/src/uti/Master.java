package uti;

public class Master extends User{

	public Master(int result) {
		super(result);
		super.buttonCount = 1;
		super.userName = "master";
	}

	@Override
	public String print() {
		return "You are a MASTER environmentalist! Caring for the environment is an inseperable part of your lifestyle. "
					+ "We tried to offer you even more advanced ideas on how to protect the environment. "
					+ "We hope motivate you to maintain your level of concern for the nature";
		
	}
}
