package cs601.project3.httpserver;

/**
 * It is a Model class to represent HTTPResponse
 * 
 * @author kmkhetia
 *
 */
public class HTTPResponse {
	private String protocol;
	private int statusCode;
	private String status;
	private String response;
	
	public String getProtocol() {
		return protocol;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public String getStatus() {
		return status;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	
	public void setResponseHeader(String protocol, String status, int statusCode) {
		this.protocol = protocol;
		this.status = status;
		this.statusCode = statusCode;
	}
	
	public String getResponseHeader() {
		return protocol + " " + statusCode + " " +
				status + "\n\r\n";
	}
	
	public boolean isValid() {
		if(protocol != null && status != null && statusCode != 0 && response != null) {
			return true;
		}
		return false;
	}
}
