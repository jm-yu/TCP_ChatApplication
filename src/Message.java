import java.io.Serializable;

public class Message implements Serializable  {

	private String message;
	private int type;
	private byte[] b;
	
	Message(String message, int type, byte[] b){
		this.message = message;
		this.type = type;
		this.b = b;
	}
	public String getmsg(){
		return message;
	}
	public byte[] getbytes(){
		return b;
	}
}
