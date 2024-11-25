package com.ongc.liferay.bandhan.util;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.ongc.liferay.bandhan.portlet.FeedbackPortlet;

import java.util.Enumeration;
import java.util.regex.Pattern;

import javax.portlet.PortletRequest;

public class CommonUtil {
	private static final Log LOGGER = LogFactoryUtil.getLog(CommonUtil.class);
	
	public static boolean checkParameter(PortletRequest request) {
		Enumeration<String> parameterNames = request.getParameterNames();
		boolean parameterCheck = false;
		 String regex="[^<>]*";
		while(parameterNames.hasMoreElements()) {
			String parameterName = parameterNames.nextElement();
			if (!Pattern.matches("[^<>]*", (ParamUtil.getString(request, parameterName)))) {
				
				LOGGER.info(ParamUtil.getString(request, parameterName)+"========================>"+Pattern.matches("[^<>]*", (ParamUtil.getString(request, parameterName))));
				parameterCheck = true;
				break;
			}
		}
		return parameterCheck;
	}
	
}
