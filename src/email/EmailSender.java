package email;


public class EmailSender {

	public String host ;
	public String from ;
	public String psw;
	
	public EmailSender(String host, String from, String psw){
		this.host = host;
		this.from = from;
		this.psw = psw;
	}
}
