package cs601.project3.httpserver;

/**
 * It stores the HTTP response status and status codes.
 * 
 * @author kmkhetia
 *
 */
public final class HTTPConstants {
	
	//OK
	public static final String STATUS_OK = "OK";
	public static final int STATUS_CODE_OK = 200;
	
	//Page Not Found
	public static final String STATUS_PNF = "error";
	public static final int STATUS_CODE_PNF = 404;
	
	//Method NOT Allowed
	public static final String STATUS_MNA = "Method Not Allowed";
	public static final int STATUS_CODE_MNA = 405;
}
