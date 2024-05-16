package server.model;


public class ExitCommand implements Command {

	public ExitCommand() {}

	@Override
	public Response execute() {
		return new Response(ReturnEnum.OK);
	}
}
