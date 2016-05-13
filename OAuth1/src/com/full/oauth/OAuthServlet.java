package com.full.oauth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class OAuthServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, NoClassDefFoundError {
		String outputString, line;

		resp.setContentType("text/plain");

		String code = req.getParameter("code");
		String urlParameters = "code=" + code
				+ "&client_id=551280924469-fq1n13js6kdbaku1pc52reo6cn0v42mm.apps.googleusercontent.com"
				+ "&client_secret=PXmekmb0EvYl25e4uSVE9GCj"
				+ "&redirect_uri=http://5-dot-oauthdemo-1310.appspot.com/oauth"
				+ "&grant_type=authorization_code";
		URL url = new URL("https://accounts.google.com/o/oauth2/token");
		URLConnection urlConn = url.openConnection();
		urlConn.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(urlConn.getOutputStream());
		writer.write(urlParameters);
		writer.flush();

		outputString = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
		while ((line = reader.readLine()) != null) {
			outputString += line;
		}
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Login abc = new Login();
		abc.setLine(outputString.substring(21,94));
		pm.makePersistent(abc);
		try {
			long t = 6000;
			req.wait(t);
		} catch (Exception e) {
		}

		URL url1 = new URL(
				"https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + outputString.substring(21,94));
		URLConnection conn1 = url1.openConnection();
		String outputString1 = "", line1;
		BufferedReader reader1 = new BufferedReader(new InputStreamReader(conn1.getInputStream()));
		while ((line1 = reader1.readLine()) != null) {
			outputString1 += line1;
		}
		resp.getWriter().print(outputString1);
		writer.close();
		reader.close();
	}
}