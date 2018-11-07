package cs601.project3.httpserver;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
		try {
			if(request == null || request.trim().isEmpty()) {
				isValid = false;
				return;
			}
			String tokens[] = request.split("\n")[0].split("\\s+");
			params = new HashMap<String, String>();
			if(tokens.length == 3) {
				method = tokens[0];
				String[] temp = tokens[1].split("\\?");
				path = temp[0];
				if(temp.length > 1) {
					addUrlParamsToMap(URLDecoder.decode(temp[1], "UTF-8"));
				}
				protocol = tokens[2];
				if(!(method.equals("GET") || method.equals("POST")) || !protocol.startsWith("HTTP")) {
					isValid = false;
				}
				else {
					isValid = true;
				}
			}
			else {
				isValid = false;
			}
			this.request = request;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void addUrlParamsToMap(String paramString) {
		String[] paramList = paramString.split("\\&");
		for(String i : paramList) {
			addParamsToMap(i);
		}
	}
	
	public void addParamsToMap(String param) {
		String[] paramMap = param.split("=");
		if(paramMap.length > 1) {
			params.put(paramMap[0], paramMap[1]);
		}
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

	public void setParams(String parameterBody) {
		if(!parameterBody.trim().isEmpty() && parameterBody != null) {
			String[] paramList = parameterBody.split("\\&");
			for(String i : paramList) {
				if(!i.isEmpty()) {
					addParamsToMap(i);
				}
			}
		}
	}
}
