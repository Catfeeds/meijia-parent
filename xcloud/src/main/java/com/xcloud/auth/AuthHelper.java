package com.xcloud.auth;

import javax.servlet.http.HttpServletRequest;

public class AuthHelper {

	public static void setSessionAccountAuth(HttpServletRequest request, AccountAuth accountAuth){
		request.getSession().setAttribute("accountAuth", accountAuth);
	}

	public static AccountAuth getSessionAccountAuth(HttpServletRequest request){
		return (AccountAuth)request.getSession().getAttribute("accountAuth");
	}

	public static void  removeSessionAccountAuth(HttpServletRequest request,String accountAuth){
		 request.getSession().removeAttribute(accountAuth);
	}
}
