package server.command;

public class CommandResult {
    private final String page;
    private final ResponseType type;

    public CommandResult(String page, ResponseType type) {
        this.page = page;
        this.type = type;
    }

    public boolean isRedirect() {
        return type == ResponseType.REDIRECT;
    }

    public boolean isForward() {
        return type == ResponseType.FORWARD;
    }

    public String getPage() {
        return page;
    }
}
