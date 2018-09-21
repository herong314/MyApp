package com.query;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import cn.vlabs.umt.oauth.AccessToken;
import cn.vlabs.umt.oauth.Oauth;
import cn.vlabs.umt.oauth.UMTOauthConnectException;
import cn.vlabs.umt.oauth.UserInfo;
import cn.vlabs.umt.oauth.common.exception.OAuthProblemException;
import net.sf.json.JSONObject;

@Path("/login")
public class VerificationLogin {
	@Context HttpServletRequest request;
	@Context HttpServletResponse response;
	
	@GET
	@Path("/authorization")
	public void UmtOAuthAddressServlet() throws ServletException, IOException {
		try {
			Oauth oauth = new Oauth("umtoauthconfig.properties");
			response.sendRedirect(oauth.getAuthorizeURL(request));
		} catch (UMTOauthConnectException e) {
			e.printStackTrace();
		}
	}	
	
	@GET
	@Path("/bindgrid")
	@Produces({"application/json"})
    //public  Response getUserName(@QueryParam("cstnetId") String cstnetId){
	public  Response getUserName(){
		JSONObject result = new JSONObject();		
		String userName=null;
		HttpSession session = request.getSession();
		Object cstnetId1=session.getAttribute("cstnetId");
		String cstnetId=null;
		if(cstnetId1!=null){
			cstnetId=session.getAttribute("cstnetId").toString();
		}
		if(cstnetId!=null&&cstnetId.length()>0){
			//userName=getUser(cstnetId);
			userName="herong";
		}
		if(userName!=null&&userName.length()>0){
			result.put("isbind", "true");
			result.put("userName", userName);
		}else{
			result.put("isbind", "false");
		}
		String resXml = result.toString(); 
		GenericEntity<String> entity = new GenericEntity<String>(resXml) {};
	   	ResponseBuilder builder = Response.ok(entity);
	   	return builder.build();	 
	}
}
