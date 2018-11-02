package cs601.project3.httpserver;

public class HTTPResponse {
	private String protocol;
	private int statusCode;
	private String status;
	private String response;
	
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String method) {
		this.protocol = method;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	
	public String getResponseHeader() {
		return protocol + " " + statusCode + " " +
				status + "\n\r\n";
	}
}
