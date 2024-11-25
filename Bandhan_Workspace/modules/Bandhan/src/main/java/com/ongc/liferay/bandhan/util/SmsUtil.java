package com.ongc.liferay.bandhan.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

public class SmsUtil {
	
	final static Log log = LogFactoryUtil.getLog(SmsUtil.class.getName());
	public static void sendSMStoEnabler(String MSISDN, String msg){
		log.info("*************** In sendSMStoEnabler *************");
		String response = "";
		BufferedReader in = null;
		
		//String sUserMsg="";
		
		try
		{
			System.getProperties().put("http.nonProxyHosts",RTConstant.SMS_HOST);
			String strEncUser = RTConstant.SMS_USER; 
			String strEncPass= RTConstant.SMS_PASS;
			StringBuffer smsUrl = new StringBuffer();
			log.info("Mobile No:::"+URLEncoder.encode(MSISDN , "UTF-8"));
			/*smsUrl.append("http://").append(RTConstant.SMS_HOST).append(":").append(RTConstant.SMSC_PORT);
			smsUrl.append("/cgi-bin/sendsms?username=").append(strEncUser).append("&password=").append(strEncPass).append("&from=ONGC&to=");
			smsUrl.append(URLEncoder.encode(MSISDN , "UTF-8")).append("&text=").append(URLEncoder.encode(msg , "UTF-8"));*/
			/*smsUrl.append("http://").append("10.205.48.187").append(":").append("13013");
			smsUrl.append("/cgi-bin/sendsms?username=").append(strEncUser).append("&password=").append(strEncPass).append("&from=ONGC&to=");
			smsUrl.append(URLEncoder.encode(MSISDN , "UTF-8")).append("&text=").append(URLEncoder.encode(msg , "UTF-8")).append("&meta-data=%3Fsmpp%3FEntityID%3D1001186049255234740");
*/			
			
			smsUrl.append("http://").append("10.205.48.187").append(":").append("13013");
			smsUrl.append("/cgi-bin/sendsms?username=").append(strEncUser).append("&password=").append(strEncPass).append("&from=ONGC&to=");
			//smsUrl.append(URLEncoder.encode(MSISDN , "UTF-8")).append("&text=").append("ONGCIN").append(URLEncoder.encode(msg , "UTF-8")).
			//append("&meta-data=%3Fsmpp%3FEntityID%3D1001186049255234740%26ContentID%3D1407161760950943662");
			smsUrl.append(URLEncoder.encode(MSISDN , "UTF-8")).append("&text=").append(URLEncoder.encode(msg , "UTF-8")).append("&meta-data=%3Fsmpp%3FEntityID%3D1001186049255234740%26ContentID%3D1407163783657563320");
			
			
			log.info("url:::"+smsUrl);
			URL url = new URL(removeWhiteSpaces(smsUrl.toString()));
			in = new BufferedReader(new InputStreamReader(url.openStream()));
			String tempStr = null;
			while ((tempStr = in.readLine()) != null) 
			{
				response = response + tempStr;
			}
			
			log.info("Status - Message send To User: "+response);
			
		}
		catch (Exception e) 
		{
			log.info("Problem in sending enabler sms : "+e.toString());
			System.out.println("Problem in sending enabler sms  : "+e.toString());
		}
		finally 
		{
			try 
			{
				if(in!=null)
					in.close();
			}
			catch (IOException e1) 
			{
				log.info(e1.toString());
			}
		}
	
	} 
	private static String removeWhiteSpaces(String smsText)
	{
		int i;
		do 
		{
			i = smsText.indexOf(" ");
			if (i > 0)
			{
				smsText =smsText.substring(0, i) +"%20"+smsText.substring(i + 1);
			}
		} 
		while (i > 0);
		return smsText;
	}
}
