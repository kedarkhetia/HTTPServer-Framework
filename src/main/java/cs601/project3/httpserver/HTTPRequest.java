package cs601.project3.httpserver;

import java.util.HashMap;
import java.util.Map;

public class HTTPRequest {
	private String request;
	private String method;
	private String path;
	private String protocol;
	private Map<String, String> params;
	private boolean isValid;
	
	public HTTPRequest(String request) {
		String tokens[] = request.split("\n")[0].split("\\s+");
		if(tokens.length == 3) {
			this.method = tokens[0];
			String[] temp = tokens[1].split("\\?");
			this.path = temp[0];
			if(temp.length > 1) {
				this.params = addParamsToMap(temp[1]);
			}
			this.protocol = tokens[2];
		}
		else {
			isValid = false;
		}
		if(!(this.method.equals("GET") || this.method.equals("POST")) || !this.protocol.startsWith("HTTP")) {
			isValid = false;
		}
		else {
			isValid = true;
		}
		this.request = request;
	}
	
	private Map<String, String> addParamsToMap(String paramString) {
		Map<String, String> params = new HashMap<String, String>();
		String[] paramList = paramString.split("\\&");
		for(String i : paramList) {
			String[] temp = i.split("=");
			params.put(temp[0], temp[1]);
		}
		return params;
	}

	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	
	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
}
