package cs601.project3.slack;

public class Message {
	private String text;
	private String username;
	private String bot_id;
	private String type;
	private String subtype;
	private String ts;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getBot_id() {
		return bot_id;
	}
	public void setBot_id(String bot_id) {
		this.bot_id = bot_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSubtype() {
		return subtype;
	}
	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}
	public String getTs() {
		return ts;
	}
	public void setTs(String ts) {
		this.ts = ts;
	}
}
