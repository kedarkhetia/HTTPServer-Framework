package cs601.project3.slack;

/**
 * This is the model class to represent the SlackResponse 
 * received from the slack api.
 * 
 * @author kmkhetia
 *
 */
public class SlackResponse {
	private boolean ok;
	private String channel;
	private String ts;
	private Message message;
	
	public boolean getOk() {
		return ok;
	}
	public void setOk(boolean ok) {
		this.ok = ok;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getTs() {
		return ts;
	}
	public void setTs(String ts) {
		this.ts = ts;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
}
