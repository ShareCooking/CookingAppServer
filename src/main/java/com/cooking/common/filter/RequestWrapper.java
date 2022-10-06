package com.cooking.common.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class RequestWrapper extends HttpServletRequestWrapper {
	
	public RequestWrapper(HttpServletRequest servletRequest) {         
		super(servletRequest);     
	}
	
	public String[] getParameterValues(String parameter) {  
		String[] values = super.getParameterValues(parameter);       
		  
		if (values==null)  {                  
		return null;          
		  
		}      
  
		int count = values.length;      
  
		String[] encodedValues = new String[count];      
  
		for (int i = 0; i < count; i++) {                
			encodedValues[i] = cleanXSS(values[i]);        
		}       
  
		return encodedValues;    
	}     
  
	public String getParameter(String parameter) {           
	
		String value = super.getParameter(parameter);           
		  
		if (value == null) {                  
		   
			return null;                   
		
		}           
		  
		return cleanXSS(value);     
	  
	}     
 
	public String getHeader(String name) {         
	 
		String value = super.getHeader(name);         
		  
		if (value == null){             
			return null;         
		}
		   
		return cleanXSS(value);     
	}     
 
	private String cleanXSS(String value) {     
	  
		String retValue = value;
		retValue = retValue.replaceAll("<", "&lt;").replaceAll(">", "&gt;"); 
		  
		retValue = retValue.replaceAll("'", "&#39;");        
		
		retValue = retValue.replaceAll("eval\\((.*)\\)", "");         
		  
		retValue = retValue.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");         
		  
		retValue = retValue.replaceAll("script", "");
		  
		retValue = retValue.replaceAll("iframe", "");
		  
//		retValue = retValue.replaceAll("src", "");
	  
		return retValue;
	  
	}	
 
}