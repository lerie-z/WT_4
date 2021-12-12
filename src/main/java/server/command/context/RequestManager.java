package server.command.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class RequestManager {
    private final HttpServletRequest request;

    public RequestManager(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public RequestContext createContext() {
        Enumeration<String> requestParameterNames = this.request.getParameterNames();
        Enumeration<String> requestAttributesNames = this.request.getAttributeNames();
        HttpSession session = this.request.getSession();
        Enumeration<String> sessionAttributesNames = session.getAttributeNames();
        Map<String, String> requestParameters = this.createRequestParameters(requestParameterNames);
        Map<String, Object> requestAttributes = this.createRequestAttributes(requestAttributesNames);
        Map<String, Object> sessionAttributes = this.createSessionAttributes(sessionAttributesNames, session);
        return new RequestContext(requestParameters, requestAttributes, sessionAttributes);
    }

    public void updateRequest(RequestContext context) {
        Map<String, Object> requestAttributes = context.getAllRequestAttributes();
        Map<String, Object> sessionAttributes = context.getAllSessionAttributes();
        HttpSession session = this.request.getSession();
        this.setSessionAttributes(session, sessionAttributes);
        this.setRequestAttributes(requestAttributes);
    }

    private void setRequestAttributes(Map<String, Object> attributes) {
        for (Map.Entry<String, Object> attribute : attributes.entrySet()) {
            this.request.setAttribute(attribute.getKey(), attribute.getValue());
        }
    }

    private void setSessionAttributes(HttpSession session, Map<String, Object> attributes) {
        for (Map.Entry<String, Object> attribute : attributes.entrySet()) {
            session.setAttribute(attribute.getKey(), attribute.getValue());
        }
    }

    private Map<String, String> createRequestParameters(Enumeration<String> names) {
        Map<String, String> result = new HashMap<>();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            result.put(name, this.request.getParameter(name));
        }

        return result;
    }

    private Map<String, Object> createRequestAttributes(Enumeration<String> names) {
        Map<String, Object> result = new HashMap<>();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            result.put(name, this.request.getAttribute(name));
        }

        return result;
    }

    private Map<String, Object> createSessionAttributes(Enumeration<String> names, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            result.put(name, session.getAttribute(name));
        }

        return result;
    }
}
