package cs601.project3.config;

public class Config {
	private String apiToken;
	private String apiChannel;
	private String qaFilePath;
	private String reviewFilePath;
	private int chatAppPort;
	private int searchAppPort;
	
	public String getApiToken() {
		return apiToken;
	}
	public void setApiToken(String apiToken) {
		this.apiToken = apiToken;
	}
	public String getApiChannel() {
		return apiChannel;
	}
	public void setApiChannel(String apiChannel) {
		this.apiChannel = apiChannel;
	}
	public String getQaFilePath() {
		return qaFilePath;
	}
	public void setQaFilePath(String qaFilePath) {
		this.qaFilePath = qaFilePath;
	}
	public String getReviewFilePath() {
		return reviewFilePath;
	}
	public void setReviewFilePath(String reviewFilePath) {
		this.reviewFilePath = reviewFilePath;
	}
	public int getChatAppPort() {
		return chatAppPort;
	}
	public void setChatAppPort(int chatAppPort) {
		this.chatAppPort = chatAppPort;
	}
	public int getSearchAppPort() {
		return searchAppPort;
	}
	public void setSearchAppPort(int searchAppPort) {
		this.searchAppPort = searchAppPort;
	}
}
