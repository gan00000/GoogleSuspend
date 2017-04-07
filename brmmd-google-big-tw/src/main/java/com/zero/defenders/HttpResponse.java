package com.zero.defenders;

/**
 * Http ��Ӧ���� Response
 * 
 * @author w00171845
 * @version [�汾��, 2012-8-7]
 * @see [�����/����]
 * @since [��Ʒ/ģ��汾]
 */
public class HttpResponse
{
    public final static int OK = 200;
    
    protected int defaultPort;
    
    protected String file;
    
    protected String host;
    
    protected String path;
    
    protected int port;
    
    protected String protocol;
    
    protected String query;
    
    protected String ref;
    
    protected String userInfo;
    
    protected String contentEncoding;
    
    protected String content;
    
    protected String contentType;
    
    protected int code;
    
    protected String message;
    
    protected String method;
    
    protected int connectTimeout;
    
    protected int readTimeout;
    
    public String getContent()
    {
        return content;
    }
    
    public String getContentType()
    {
        return contentType;
    }
    
    public int getCode()
    {
        return code;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public String getContentEncoding()
    {
        return contentEncoding;
    }
    
    public String getMethod()
    {
        return method;
    }
    
    public int getConnectTimeout()
    {
        return connectTimeout;
    }
    
    public int getReadTimeout()
    {
        return readTimeout;
    }
    
    public int getDefaultPort()
    {
        return defaultPort;
    }
    
    public String getFile()
    {
        return file;
    }
    
    public String getHost()
    {
        return host;
    }
    
    public String getPath()
    {
        return path;
    }
    
    public int getPort()
    {
        return port;
    }
    
    public String getProtocol()
    {
        return protocol;
    }
    
    public String getQuery()
    {
        return query;
    }
    
    public String getRef()
    {
        return ref;
    }
    
    public String getUserInfo()
    {
        return userInfo;
    }
}

