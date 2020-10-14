/**
 * 
 */
package com.woshidaniu.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.woshidaniu.basicutils.StringUtils;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：web工具类
 * <p>
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年7月19日下午5:25:24
 */
public class WebUtils {

	public static final String DEFAULT_CHARACTER_ENCODING = "ISO-8859-1";
	
	/**
     * Standard Servlet 2.3+ spec request attributes for include URI and paths.
     * <p>If included via a RequestDispatcher, the current resource will see the
     * originating request. Its own URI and paths are exposed as request attributes.
     */
    public static final String INCLUDE_REQUEST_URI_ATTRIBUTE = "javax.servlet.include.request_uri";
    public static final String INCLUDE_CONTEXT_PATH_ATTRIBUTE = "javax.servlet.include.context_path";
    public static final String INCLUDE_SERVLET_PATH_ATTRIBUTE = "javax.servlet.include.servlet_path";
    public static final String INCLUDE_PATH_INFO_ATTRIBUTE = "javax.servlet.include.path_info";
    public static final String INCLUDE_QUERY_STRING_ATTRIBUTE = "javax.servlet.include.query_string";
	
    /**
     * 
     * <p>方法说明：获取参数<p>
     * <p>作者：a href="#">xiaokang[1036]<a><p>
     * <p>时间：2016年10月15日下午2:57:05<p>
     */
    public static String cleanRequestParam(HttpServletRequest request, String parameterName){
    	String out = null;
    	String in = request.getParameter(parameterName);
        if (in != null) {
            out = in.trim();
            if (out.equals("")) {
                out = null;
            }
        }
        return out;
    }
    
    public static boolean isTrue(HttpServletRequest request, String paramName) {
        String value = cleanRequestParam(request, paramName);
        return value != null &&
                (value.equalsIgnoreCase("true") ||
                        value.equalsIgnoreCase("t") ||
                        value.equalsIgnoreCase("1") ||
                        value.equalsIgnoreCase("enabled") ||
                        value.equalsIgnoreCase("y") ||
                        value.equalsIgnoreCase("yes") ||
                        value.equalsIgnoreCase("on"));
    }
    
    /**
     * A convenience method that merely casts the incoming <code>ServletRequest</code> to an
     * <code>HttpServletRequest</code>:
     * <p/>
     * <code>return (HttpServletRequest)request;</code>
     * <p/>
     * Logic could be changed in the future for logging or throwing an meaningful exception in
     * non HTTP request environments (e.g. Portlet API).
     *
     * @param request the incoming ServletRequest
     * @return the <code>request</code> argument casted to an <code>HttpServletRequest</code>.
     */
    public static HttpServletRequest toHttp(ServletRequest request) {
        return (HttpServletRequest) request;
    }

    /**
     * A convenience method that merely casts the incoming <code>ServletResponse</code> to an
     * <code>HttpServletResponse</code>:
     * <p/>
     * <code>return (HttpServletResponse)response;</code>
     * <p/>
     * Logic could be changed in the future for logging or throwing an meaningful exception in
     * non HTTP request environments (e.g. Portlet API).
     *
     * @param response the outgoing ServletResponse
     * @return the <code>response</code> argument casted to an <code>HttpServletResponse</code>.
     */
    public static HttpServletResponse toHttp(ServletResponse response) {
        return (HttpServletResponse) response;
    }
    
	/**
     * Return the path within the web application for the given request.
     * Detects include request URL if called within a RequestDispatcher include.
     * <p/>
     * For example, for a request to URL
     * <p/>
     * <code>http://www.somehost.com/myapp/my/url.jsp</code>,
     * <p/>
     * for an application deployed to <code>/mayapp</code> (the application's context path), this method would return
     * <p/>
     * <code>/my/url.jsp</code>.
     *
     * @param request current HTTP request
     * @return the path within the web application
     */
    public static String getPathWithinApplication(HttpServletRequest request) {
        String contextPath = getContextPath(request);
        String requestUri = getRequestUri(request);
        if (StringUtils.startsWithIgnoreCase(requestUri, contextPath)) {
            // Normal case: URI contains context path.
            String path = requestUri.substring(contextPath.length());
            return (StringUtils.isNoneBlank(path) ? path : "/");
        } else {
            // Special case: rather unusual.
            return requestUri;
        }
    }

    /**
     * Return the request URI for the given request, detecting an include request
     * URL if called within a RequestDispatcher include.
     * <p>As the value returned by <code>request.getRequestURI()</code> is <i>not</i>
     * decoded by the servlet container, this method will decode it.
     * <p>The URI that the web container resolves <i>should</i> be correct, but some
     * containers like JBoss/Jetty incorrectly include ";" strings like ";jsessionid"
     * in the URI. This method cuts off such incorrect appendices.
     *
     * @param request current HTTP request
     * @return the request URI
     */
    public static String getRequestUri(HttpServletRequest request) {
        String uri = (String) request.getAttribute(INCLUDE_REQUEST_URI_ATTRIBUTE);
        if (uri == null) {
            uri = request.getRequestURI();
        }
        return normalize(decodeAndCleanUriString(request, uri));
    }

    /**
     * Normalize a relative URI path that may have relative values ("/./",
     * "/../", and so on ) it it.  <strong>WARNING</strong> - This method is
     * useful only for normalizing application-generated paths.  It does not
     * try to perform security checks for malicious input.
     * Normalize operations were was happily taken from org.apache.catalina.util.RequestUtil in
     * Tomcat trunk, r939305
     *
     * @param path Relative path to be normalized
     * @return normalized path
     */
    public static String normalize(String path) {
        return normalize(path, true);
    }

    /**
     * Normalize a relative URI path that may have relative values ("/./",
     * "/../", and so on ) it it.  <strong>WARNING</strong> - This method is
     * useful only for normalizing application-generated paths.  It does not
     * try to perform security checks for malicious input.
     * Normalize operations were was happily taken from org.apache.catalina.util.RequestUtil in
     * Tomcat trunk, r939305
     *
     * @param path             Relative path to be normalized
     * @param replaceBackSlash Should '\\' be replaced with '/'
     * @return normalized path
     */
    private static String normalize(String path, boolean replaceBackSlash) {

        if (path == null)
            return null;

        // Create a place for the normalized path
        String normalized = path;

        if (replaceBackSlash && normalized.indexOf('\\') >= 0)
            normalized = normalized.replace('\\', '/');

        if (normalized.equals("/."))
            return "/";

        // Add a leading "/" if necessary
        if (!normalized.startsWith("/"))
            normalized = "/" + normalized;

        // Resolve occurrences of "//" in the normalized path
        while (true) {
            int index = normalized.indexOf("//");
            if (index < 0)
                break;
            normalized = normalized.substring(0, index) +
                    normalized.substring(index + 1);
        }

        // Resolve occurrences of "/./" in the normalized path
        while (true) {
            int index = normalized.indexOf("/./");
            if (index < 0)
                break;
            normalized = normalized.substring(0, index) +
                    normalized.substring(index + 2);
        }

        // Resolve occurrences of "/../" in the normalized path
        while (true) {
            int index = normalized.indexOf("/../");
            if (index < 0)
                break;
            if (index == 0)
                return (null);  // Trying to go outside our context
            int index2 = normalized.lastIndexOf('/', index - 1);
            normalized = normalized.substring(0, index2) +
                    normalized.substring(index + 3);
        }

        // Return the normalized path that we have completed
        return (normalized);

    }


    /**
     * Decode the supplied URI string and strips any extraneous portion after a ';'.
     *
     * @param request the incoming HttpServletRequest
     * @param uri     the application's URI string
     * @return the supplied URI string stripped of any extraneous portion after a ';'.
     */
    private static String decodeAndCleanUriString(HttpServletRequest request, String uri) {
        uri = decodeRequestString(request, uri);
        int semicolonIndex = uri.indexOf(';');
        return (semicolonIndex != -1 ? uri.substring(0, semicolonIndex) : uri);
    }

    /**
     * Return the context path for the given request, detecting an include request
     * URL if called within a RequestDispatcher include.
     * <p>As the value returned by <code>request.getContextPath()</code> is <i>not</i>
     * decoded by the servlet container, this method will decode it.
     *
     * @param request current HTTP request
     * @return the context path
     */
    public static String getContextPath(HttpServletRequest request) {
        String contextPath = (String) request.getAttribute(INCLUDE_CONTEXT_PATH_ATTRIBUTE);
        if (contextPath == null) {
            contextPath = request.getContextPath();
        }
        if ("/".equals(contextPath)) {
            // Invalid case, but happens for includes on Jetty: silently adapt it.
            contextPath = "";
        }
        return decodeRequestString(request, contextPath);
    }
	
    /**
     * Decode the given source string with a URLDecoder. The encoding will be taken
     * from the request, falling back to the default "ISO-8859-1".
     * <p>The default implementation uses <code>URLDecoder.decode(input, enc)</code>.
     *
     * @param request current HTTP request
     * @param source  the String to decode
     * @return the decoded String
     * @see #DEFAULT_CHARACTER_ENCODING
     * @see javax.servlet.ServletRequest#getCharacterEncoding
     * @see java.net.URLDecoder#decode(String, String)
     * @see java.net.URLDecoder#decode(String)
     */
    @SuppressWarnings({"deprecation"})
    public static String decodeRequestString(HttpServletRequest request, String source) {
        String enc = determineEncoding(request);
        try {
            return URLDecoder.decode(source, enc);
        } catch (UnsupportedEncodingException ex) {
            return URLDecoder.decode(source);
        }
    }
    
    /**
     * 
     * <p>方法说明：获取请求主机信息<p>
     * <p>作者：a href="#">xiaokang[1036]<a><p>
     * <p>时间：2016年8月8日下午9:09:26<p>
     */
    public static String getDomainName(String requestURL){
    	String token = "://";
		int index = requestURL.indexOf(token);
		String part = requestURL.substring(index + token.length());
		StringBuilder domain = new StringBuilder();

		for (int i = 0; i < part.length(); i++) {
			char character = part.charAt(i);

			if (character == '/' || character == ':') {
				break;
			}

			domain.append(character);
		}

		return domain.toString();
    }
    
    /**
     * 
     * <p>方法说明：获取请求主机信息<p>
     * <p>作者：a href="#">xiaokang[1036]<a><p>
     * <p>时间：2016年8月8日下午9:09:26<p>
     */
    public static String getDomainName(HttpServletRequest request){
    	String url = request.getRequestURL().toString();
    	String token = "://";
		int index = url.indexOf(token);
		String part = url.substring(index + token.length());
		StringBuilder domain = new StringBuilder();

		for (int i = 0; i < part.length(); i++) {
			char character = part.charAt(i);

			if (character == '/' || character == ':') {
				break;
			}

			domain.append(character);
		}

		return domain.toString();
    }
    
    /**
     * 
     * <p>方法说明：判断是否是ajax 请求<p>
     * <p>作者：a href="#">xiaokang[1036]<a><p>
     * <p>时间：2016年8月9日下午2:23:00<p>
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
		return (request.getHeader("X-Requested-With") != null  && "XMLHttpRequest".equals( request.getHeader("X-Requested-With").toString())) ;
	}
    
    /**
     * Determine the encoding for the given request.
     * Can be overridden in subclasses.
     * <p>The default implementation checks the request's
     * {@link ServletRequest#getCharacterEncoding() character encoding}, and if that
     * <code>null</code>, falls back to the {@link #DEFAULT_CHARACTER_ENCODING}.
     *
     * @param request current HTTP request
     * @return the encoding for the request (never <code>null</code>)
     * @see javax.servlet.ServletRequest#getCharacterEncoding()
     */
    protected static String determineEncoding(HttpServletRequest request) {
        String enc = request.getCharacterEncoding();
        if (enc == null) {
            enc = DEFAULT_CHARACTER_ENCODING;
        }
        return enc;
    }
    
    /**
     * Redirects the current request to a new URL based on the given parameters.
     *
     * @param request          the servlet request.
     * @param response         the servlet response.
     * @param url              the URL to redirect the user to.
     * @param queryParams      a map of parameters that should be set as request parameters for the new request.
     * @param contextRelative  true if the URL is relative to the servlet context path, or false if the URL is absolute.
     * @param http10Compatible whether to stay compatible with HTTP 1.0 clients.
     * @throws java.io.IOException if thrown by response methods.
     */
    @SuppressWarnings("rawtypes")
    public static void issueRedirect(ServletRequest request, ServletResponse response, String url, Map queryParams, boolean contextRelative, boolean http10Compatible) throws IOException {
        RedirectView view = new RedirectView(url, contextRelative, http10Compatible);
        view.renderMergedOutputModel(queryParams, toHttp(request), toHttp(response));
    }

    /**
     * Redirects the current request to a new URL based on the given parameters and default values
     * for unspecified parameters.
     *
     * @param request  the servlet request.
     * @param response the servlet response.
     * @param url      the URL to redirect the user to.
     * @throws java.io.IOException if thrown by response methods.
     */
    public static void issueRedirect(ServletRequest request, ServletResponse response, String url) throws IOException {
        issueRedirect(request, response, url, null, true, true);
    }

    /**
     * Redirects the current request to a new URL based on the given parameters and default values
     * for unspecified parameters.
     *
     * @param request     the servlet request.
     * @param response    the servlet response.
     * @param url         the URL to redirect the user to.
     * @param queryParams a map of parameters that should be set as request parameters for the new request.
     * @throws java.io.IOException if thrown by response methods.
     */
    @SuppressWarnings("rawtypes")
	public static void issueRedirect(ServletRequest request, ServletResponse response, String url, Map queryParams) throws IOException {
        issueRedirect(request, response, url, queryParams, true, true);
    }

    /**
     * Redirects the current request to a new URL based on the given parameters and default values
     * for unspecified parameters.
     *
     * @param request         the servlet request.
     * @param response        the servlet response.
     * @param url             the URL to redirect the user to.
     * @param queryParams     a map of parameters that should be set as request parameters for the new request.
     * @param contextRelative true if the URL is relative to the servlet context path, or false if the URL is absolute.
     * @throws java.io.IOException if thrown by response methods.
     */
    @SuppressWarnings("rawtypes")
    public static void issueRedirect(ServletRequest request, ServletResponse response, String url, Map queryParams, boolean contextRelative) throws IOException {
        issueRedirect(request, response, url, queryParams, contextRelative, true);
    }
    
    /**
     * 
     * <p>方法说明：判断是否是ajax请求<p>
     * <p>作者：a href="#">xiaokang[1036]<a><p>
     * <p>时间：2016年8月5日下午8:27:38<p>
     */
    public static boolean isAjaxRequest(ServletRequest request){
    	if(! (request instanceof HttpServletRequest))
    		return false;
    	HttpServletRequest httpRequest = (HttpServletRequest)request;
    	return httpRequest.getHeader("X-Requested-With") != null;
    }
    
    //
    private static class RedirectView {

        /**
         * The default encoding scheme: UTF-8
         */
        public static final String DEFAULT_ENCODING_SCHEME = "UTF-8";

        private String url;

        private boolean contextRelative = false;

        private boolean http10Compatible = true;

        private String encodingScheme = DEFAULT_ENCODING_SCHEME;

        /**
         * Constructor for use as a bean.
         */
        
        @SuppressWarnings("unused")
		public RedirectView() {
        }

        /**
         * Create a new RedirectView with the given URL.
         * <p>The given URL will be considered as relative to the web server,
         * not as relative to the current ServletContext.
         *
         * @param url the URL to redirect to
         * @see #RedirectView(String, boolean)
         */
        public RedirectView(String url) {
            setUrl(url);
        }

        /**
         * Create a new RedirectView with the given URL.
         *
         * @param url             the URL to redirect to
         * @param contextRelative whether to interpret the given URL as
         *                        relative to the current ServletContext
         */
        @SuppressWarnings("unused")
		public RedirectView(String url, boolean contextRelative) {
            this(url);
            this.contextRelative = contextRelative;
        }

        /**
         * Create a new RedirectView with the given URL.
         *
         * @param url              the URL to redirect to
         * @param contextRelative  whether to interpret the given URL as
         *                         relative to the current ServletContext
         * @param http10Compatible whether to stay compatible with HTTP 1.0 clients
         */
        public RedirectView(String url, boolean contextRelative, boolean http10Compatible) {
            this(url);
            this.contextRelative = contextRelative;
            this.http10Compatible = http10Compatible;
        }


        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        /**
         * Set whether to interpret a given URL that starts with a slash ("/")
         * as relative to the current ServletContext, i.e. as relative to the
         * web application root.
         * <p/>
         * Default is "false": A URL that starts with a slash will be interpreted
         * as absolute, i.e. taken as-is. If true, the context path will be
         * prepended to the URL in such a case.
         *
         * @param contextRelative whether to interpret a given URL that starts with a slash ("/")
         *                        as relative to the current ServletContext, i.e. as relative to the
         *                        web application root.
         * @see javax.servlet.http.HttpServletRequest#getContextPath
         */
        @SuppressWarnings("unused")
		public void setContextRelative(boolean contextRelative) {
            this.contextRelative = contextRelative;
        }

        /**
         * Set whether to stay compatible with HTTP 1.0 clients.
         * <p>In the default implementation, this will enforce HTTP status code 302
         * in any case, i.e. delegate to <code>HttpServletResponse.sendRedirect</code>.
         * Turning this off will send HTTP status code 303, which is the correct
         * code for HTTP 1.1 clients, but not understood by HTTP 1.0 clients.
         * <p>Many HTTP 1.1 clients treat 302 just like 303, not making any
         * difference. However, some clients depend on 303 when redirecting
         * after a POST request; turn this flag off in such a scenario.
         *
         * @param http10Compatible whether to stay compatible with HTTP 1.0 clients.
         * @see javax.servlet.http.HttpServletResponse#sendRedirect
         */
        @SuppressWarnings("unused")
		public void setHttp10Compatible(boolean http10Compatible) {
            this.http10Compatible = http10Compatible;
        }

        /**
         * Set the encoding scheme for this view. Default is UTF-8.
         *
         * @param encodingScheme the encoding scheme for this view. Default is UTF-8.
         */
        @SuppressWarnings("unused")
		public void setEncodingScheme(String encodingScheme) {
            this.encodingScheme = encodingScheme;
        }


        /**
         * Convert model to request parameters and redirect to the given URL.
         *
         * @param model    the model to convert
         * @param request  the incoming HttpServletRequest
         * @param response the outgoing HttpServletResponse
         * @throws java.io.IOException if there is a problem issuing the redirect
         * @see #appendQueryProperties
         * @see #sendRedirect
         */
        @SuppressWarnings("rawtypes")
		public final void renderMergedOutputModel(
                Map model, HttpServletRequest request, HttpServletResponse response) throws IOException {

            // Prepare name URL.
            StringBuilder targetUrl = new StringBuilder();
            if (this.contextRelative && getUrl().startsWith("/")) {
                // Do not apply context path to relative URLs.
                targetUrl.append(request.getContextPath());
            }
            targetUrl.append(getUrl());
            //change the following method to accept a StringBuilder instead of a StringBuilder for Shiro 2.x:
            appendQueryProperties(targetUrl, model, this.encodingScheme);

            sendRedirect(request, response, targetUrl.toString(), this.http10Compatible);
        }

        /**
         * Append query properties to the redirect URL.
         * Stringifies, URL-encodes and formats model attributes as query properties.
         *
         * @param targetUrl      the StringBuffer to append the properties to
         * @param model          Map that contains model attributes
         * @param encodingScheme the encoding scheme to use
         * @throws java.io.UnsupportedEncodingException if string encoding failed
         * @see #urlEncode
         * @see #queryProperties
         * @see #urlEncode(String, String)
         */
        @SuppressWarnings("rawtypes")
		protected void appendQueryProperties(StringBuilder targetUrl, Map model, String encodingScheme)
                throws UnsupportedEncodingException {

            // Extract anchor fragment, if any.
            // The following code does not use JDK 1.4's StringBuffer.indexOf(String)
            // method to retain JDK 1.3 compatibility.
            String fragment = null;
            int anchorIndex = targetUrl.toString().indexOf('#');
            if (anchorIndex > -1) {
                fragment = targetUrl.substring(anchorIndex);
                targetUrl.delete(anchorIndex, targetUrl.length());
            }

            // If there aren't already some parameters, we need a "?".
            boolean first = (getUrl().indexOf('?') < 0);
            Map queryProps = queryProperties(model);

            if (queryProps != null) {
                for (Object o : queryProps.entrySet()) {
                    if (first) {
                        targetUrl.append('?');
                        first = false;
                    } else {
                        targetUrl.append('&');
                    }
                    Map.Entry entry = (Map.Entry) o;
                    String encodedKey = urlEncode(entry.getKey().toString(), encodingScheme);
                    String encodedValue =
                            (entry.getValue() != null ? urlEncode(entry.getValue().toString(), encodingScheme) : "");
                    targetUrl.append(encodedKey).append('=').append(encodedValue);
                }
            }

            // Append anchor fragment, if any, to end of URL.
            if (fragment != null) {
                targetUrl.append(fragment);
            }
        }

        /**
         * URL-encode the given input String with the given encoding scheme, using
         * {@link URLEncoder#encode(String, String) URLEncoder.encode(input, enc)}.
         *
         * @param input          the unencoded input String
         * @param encodingScheme the encoding scheme
         * @return the encoded output String
         * @throws UnsupportedEncodingException if thrown by the JDK URLEncoder
         * @see java.net.URLEncoder#encode(String, String)
         * @see java.net.URLEncoder#encode(String)
         */
        protected String urlEncode(String input, String encodingScheme) throws UnsupportedEncodingException {
            return URLEncoder.encode(input, encodingScheme);
        }

        /**
         * Determine name-value pairs for query strings, which will be stringified,
         * URL-encoded and formatted by appendQueryProperties.
         * <p/>
         * This implementation returns all model elements as-is.
         *
         * @param model the model elements for which to determine name-value pairs.
         * @return the name-value pairs for query strings.
         * @see #appendQueryProperties
         */
        @SuppressWarnings("rawtypes")
		protected Map queryProperties(Map model) {
            return model;
        }

        /**
         * Send a redirect back to the HTTP client
         *
         * @param request          current HTTP request (allows for reacting to request method)
         * @param response         current HTTP response (for sending response headers)
         * @param targetUrl        the name URL to redirect to
         * @param http10Compatible whether to stay compatible with HTTP 1.0 clients
         * @throws IOException if thrown by response methods
         */
        protected void sendRedirect(HttpServletRequest request, HttpServletResponse response,
                                    String targetUrl, boolean http10Compatible) throws IOException {
            if (http10Compatible) {
                // Always send status code 302.
                response.sendRedirect(response.encodeRedirectURL(targetUrl));
            } else {
                // Correct HTTP status code is 303, in particular for POST requests.
                response.setStatus(303);
                response.setHeader("Location", response.encodeRedirectURL(targetUrl));
            }
        }

    }
}
