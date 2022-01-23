package alehad.messenger.ws.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/")
public class HelloServer {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String direBonjour() 
	{
		return "Bonjour, tout le monde! Ton Messenger WS.";
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHTMLHello() 
	{
	    return "<html><title>Hello</title><body><h1>Bonjour, tout le monde! Ton Messenger WS.</h1><body></html>";
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String sayJsonHello() 
	{
	    return "{\"name\":\"greeting\", \"message\":\"Bonjour tout le monde! Ton Messenger WS.\"}";
	}
}
