HTTPServer-Framework
=======================================

For this project, I have implemented an HTTP server, two web applications, and a full suite of tests. I have used following concepts:

- Concurrency and threads
- Sockets
- JUnit and testing
- Good design practices

## Part 1 - HTTP Server

For Part 1 of this Project I have implemented a HTTP server. 

### Requirements

1. I have used raw sockets for this Project. I have *not* useed Tomcat, Jetty, or any other existing server. I have also *not* used any external libraries for request parsing, etc.
2. My server supports `GET` and `POST` requests. Any other HTTP method will result in a `405 Method Not Allowed` status code. See [HTTP Status Code Definitions](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html). 
3. My server is flexible enough to support different web applications. 
4. My server is multithreaded. Each incoming request will be handled by a different threads.

### Recommended Design

Here the design is similar to the design supported by [Jetty](https://www.eclipse.org/jetty/documentation/9.4.12.v20180830/). My design does not follow the exact API nor does it is as extensive, however I have borrow similar ideas. Here are a few items to consider:

- Create a `Handler` interface with a `handle` method. Each web application will implement a different set of handlers, for example a search application will support a `SearchHandler` and a chat application would have a `ChatHandler`. 
- Support an `addMapping` method that will map a URI path to a specific `Handler` instance. When a new request is made to the server the server will retrieve from the mapping the `Handler` appropriate for the path in the request URI.

The examples below show how my API is used for the required applications below.


```java
public class SearchApplication {
	public static void main(String[] args) {
		int port = 1024;
		HTTPServer server = new HTTPServer(port);
		//The request GET /reviewsearch will be dispatched to the 
		//handle method of the ReviewSearchHandler.
		server.addMapping("/reviewsearch", new ReviewSearchHandler());
		//The request GET /find will be dispatched to the 
		//handle method of the FindHandler.
		server.addMapping("/find", new FindHandler());
		server.startup();
	}
}

```

```java
public class ChatApplication {
	public static void main(String[] args) {
		int port = 1024;
		HTTPServer server = new HTTPServer(port);
		server.addMapping("/slackbot", new ChatHandler());
		server.startup();
	}
}

```

## Part 2 - Search Application

For Part 2 of this Project I have implemented an application that will allow users to search the `InvertedIndex` that was built for [InvertedIndex](https://github.com/kedarkhetia/InvertedIndex) project. 

Below are the list of supported APIs:

#### `GET /reviewsearch`

This request will return a web page containing, at minimum, a text box and button. When the user enters a query in the text box and clicks the button the `POST` method described below will be called.

#### `POST /reviewsearch`

The body of this request will look as follows: `query=term` where the value is a URL-encoded string.

It will return a web page listing all of the review search results.

#### `GET /find`

This request will return a web page containing, at minimum, a text box and button. When the user enters a message in the text box and clicks the button the `POST` method described below will be called.

#### `POST /find`

The body of this request will look as follows: `asin=123456789` where the value is a URL-encoded string.

It will return a web page listing all of the review and qa documents with the provided ASIN.

## Part 3 - Chat Application

For Part 3 of this Project I have implemented an application that will allow a user to anonymously post a message to the Slack team. Refer to the [Accessing the Slack API](slack.md) instructions for pointers.

Below are the list of supported APIs:

#### `GET /slackbot`

This request will return a web page containing, at minimum, a text box and button. When the user enters a message in the text box and clicks the button the `POST` method described below will be called.

#### `POST /slackbot`

The body of this request will look as follows: `message=test+message` where the value is a URL-encoded string.

This request will trigger a post of the message specified by the body to the Slack channel `#project3`. Regardless of the user who types the message it will always appear to have come from your application.

## Part 4 - Tests

For Part 4 I have written test cases to test my solution. I have demonstrated unit tests, integration tests, and system tests.

### Unit Tests

Unit tests will test one method. It is not required that you have unit tests for every method you implement, however methods that perform complex processing or logic should have one or more associated unit tests. As an example, you may have a method that parses an HTTP request to validate that it has a correct format, valid method, etc. You should implement several unit tests to confirm that the method works correctly when the request is valid and in all scenarios when it is invalid.

### Integration Tests

Integration tests generally test a path of execution through your program. You may, for example, have a handler that takes as input a request and then calls another method to post to to Slack. In this case, you could use a *mock request object* and test that passing that mock object to your handler results in a correct post to Slack.

### System Tests

System tests will test the end-to-end execution of your program. A system test would use a client program to make a request (valid or invalid) of your deployed server and verify that the response is correct.

## External Libraries

The only external libraries I am using for this project are [GSON](https://github.com/google/gson) 2.8.5 and JUnit.
