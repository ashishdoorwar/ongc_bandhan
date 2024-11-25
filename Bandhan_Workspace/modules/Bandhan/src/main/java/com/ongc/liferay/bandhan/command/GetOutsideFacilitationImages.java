package com.ongc.liferay.bandhan.command;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

@Component(
	    immediate = true,
	    property = {
	        "osgi.http.whiteboard.context.path=/facilitation",
	        "osgi.http.whiteboard.servlet.pattern=/blade/facilationServlet/*"
	    },
	    service = Servlet.class
	)

public class GetOutsideFacilitationImages extends HttpServlet{
	

	@Override
    protected void doGet(
            HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
		
		String imgID = request.getParameter("imgID");
		ResourceBundle szLable = ResourceBundle.getBundle("/content/bandhan");
		
		response.setContentType("image/jpeg");
		File file = new File(szLable.getString("facilitationImagePath").toString().trim()+imgID+".jpg");
		if(file.exists()){}else {file = new File(szLable.getString("facilitationImagePath").toString().trim()+"default.jpg");}
		BufferedImage image = ImageIO.read(file);
		ImageIO.write(image, "jpg", response.getOutputStream());
		
		
        
    }
	
	

}
