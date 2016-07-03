/**
 * 
 */
package client.view;

import java.io.Serializable;

/**
 * 
 * represent the chat message
 * @author Massimiliano Ventura
 *
 */
public class MessaggioChat implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6735858160492701007L;
	private String autore;
	private String msg;
	/**
	 * builds an object MessaggioChat
	 * @param autore
	 * @param msg
	 */
	public MessaggioChat(String autore, String msg) {
		this.autore = autore;
		this.msg = msg;
	}
	/**
	 * @return the autore
	 */
	public String getAutore() {
		return autore;
	}
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	
	
}
