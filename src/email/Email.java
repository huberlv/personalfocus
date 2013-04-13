package email;

import java.util.ArrayList;

public class Email {

	public String receiver;
	public ArrayList <String> content = null;
	public ArrayList<OtherStruct> other;
	
	
	public Email(String receiver, ArrayList<String> ec, ArrayList<OtherStruct> other){
		this.receiver = receiver;
		this.content = ec;
		this.other = other;
	}

}
