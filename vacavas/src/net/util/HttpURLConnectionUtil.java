package net.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;




import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;


public class HttpURLConnectionUtil {

	private static final Logger logger = Logger.getLogger(HttpURLConnectionUtil.class);

	// private final String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) throws Exception {

		HttpURLConnectionUtil http = new HttpURLConnectionUtil();
		String url = "http://www.google.com/search?q=mkyong";
		System.out.println("Testing 1 - Send Http GET request");
		//http.sendGet(url, "test");


		//System.out.println("httpResponse::::"+httpResponse);
	}

	// HTTP GET request
	public HTTPResponse sendGet(String url) {

		HTTPResponse httpResponse = new HTTPResponse();
		int responseCode = -1;
		StringBuffer response = new StringBuffer();
		String error = "";
		InputStream in = null;
		URL obj = null;
		HttpURLConnection con = null;
		try {
			obj = new URL(url);

			con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");
			con.setReadTimeout(30000);
			con.setConnectTimeout(30000);
			// add request header
			// con.setRequestProperty("User-Agent", userAgent);
			// con.setRequestProperty("Content-Type",
			// "text/plain; charset=utf-8");
			// con.setRequestProperty("Accept", "/");
			con.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
			con.setRequestProperty("Accept-Language",
					"en-US,en;q=0.8,hi;q=0.6,it;q=0.4");

			responseCode = con.getResponseCode();
			in = con.getInputStream();

			int  i=-1;	
			while ((i = in.read()) != -1) {
				response.append((char)i);
			}
			logger.info("\nSending 'GET' request to URL : " + url
					+ ", Response Code : " + responseCode + ", response: "
					+ response.toString());

		} catch (Exception e) {
			System.out.println("Sending 'GET' request to URL : " + url
					+ ", Response Code : " + responseCode + ", response: "
					+ response.toString() + ", Excption: " + e);
			logger.error("Sending 'GET' request to URL : " + url
					+ ", Response Code : " + responseCode + ", response: "
					+ response.toString() + ", Excption: " + e);
		} finally {

			try {
				if (in != null)
					in.close();
				if (con != null)
					con.disconnect();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("Error in closing http connection for url:" + url
						+ " " + e);
			}
		}

		httpResponse.setError(error);
		httpResponse.setResponseCode(responseCode);
		httpResponse.setResponseStr(response.toString());
		return httpResponse;
	}

	// HTTP GET request
	public static InputStream getHttpInpuStream(String url) {
		InputStream in = null;
		URL obj = null;
		HttpURLConnection con = null;
		try {
			obj = new URL(url);
			con = (HttpURLConnection) obj.openConnection();
			// optional default is GET
			con.setRequestMethod("GET");
			con.setReadTimeout(30000);
			con.setConnectTimeout(30000);
			in = con.getInputStream();
		} catch (Exception e) {
			logger.error("getHttpInpuStream:: exception " + e);
		}

		return in;
	}



	public HTTPResponse sendPostRequest(String url,Map<String,String> map){
		String result ="";
		HTTPResponse httpResponse=new HTTPResponse(); 
		try{


			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(url);

			post.setHeader("Content-type","application/x-www-form-urlencoded");

			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			Iterator<String> itr=map.keySet().iterator();
			while(itr.hasNext()){
				String key=itr.next();	
				urlParameters.add(new BasicNameValuePair(key,map.get(key)));
			}

			post.setEntity(new UrlEncodedFormEntity(urlParameters));

			HttpResponse response = client.execute(post);
			httpResponse.setResponseCode(response.getStatusLine().getStatusCode());
			BufferedReader rd = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent()));


			String line = "";
			while ((line = rd.readLine()) != null) {
				result+=line;
			}
			httpResponse.setResponseStr(result);
			rd.close();
		}catch(Exception ex){
			logger.error("sendPostRequest:: ",ex);
			result=null;
		}finally{

		}
		return httpResponse;
	}


	public String makeHTTPPOSTRequest(String url,String json,String authStringEnc) {
		String result ="";   
		try {
			HttpClient c = new DefaultHttpClient();        
			HttpPost post = new HttpPost(url); 	            
			post.setHeader("Content-type","application/json");
			if(authStringEnc!=null){
				post.setHeader("Authorization", "Basic " + authStringEnc);
			}
			post.setEntity(new StringEntity(json));	           
			HttpResponse r = c.execute(post);	 
			BufferedReader rd = new BufferedReader(new InputStreamReader(r.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				result+=line;
			}

			rd.close();
		}
		catch(Exception ex) {
			result=ex.toString();
			logger.error("makeHTTPPOSTRequest:: ",ex);
		}  
		return result;
	}    



	public HTTPResponse makeHTTPSRequest(String url) {
		String result ="";   
		HTTPResponse httpResponse = new HTTPResponse();
		try {
			HttpClient c = new DefaultHttpClient();        
			HttpGet post = new HttpGet(url); 	            
			//post.setHeader("Content-type","application/json");

			//post.setEntity(new StringEntity(json));	           
			HttpResponse r = c.execute(post);	
			httpResponse.setResponseCode(r.getStatusLine().getStatusCode());
			BufferedReader rd = new BufferedReader(new InputStreamReader(r.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				result+=line;
			}
			httpResponse.setResponseStr(result);
			rd.close();
		}
		catch(Exception ex) {
			result=ex.toString();
			httpResponse.setResponseStr(result);
			logger.error("makeHTTPSRequest:: ",ex);
		}  
		return httpResponse;
	} 

	public HTTPResponse makeHTTPSRequestWithSelfSigned(String url) {
		String result ="";   
		HTTPResponse httpResponse = new HTTPResponse();
		try {

			SSLContextBuilder builder = new SSLContextBuilder();
			builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					builder.build());

			//////////////

			SSLContext sslContext = SSLContext.getInstance("SSL");
			// set up a TrustManager that trusts everything
			sslContext.init(null, new TrustManager[] { new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					System.out.println("getAcceptedIssuers =============");
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs,
						String authType) {
					System.out.println("checkClientTrusted =============");
				}

				public void checkServerTrusted(X509Certificate[] certs,
						String authType) {
					System.out.println("checkServerTrusted =============");
				}
			} }, new SecureRandom());

			SSLSocketFactory sf = new SSLSocketFactory(sslContext);
			Scheme httpsScheme = new Scheme("https", 443, sf);
			SchemeRegistry schemeRegistry = new SchemeRegistry();
			schemeRegistry.register(httpsScheme);

			// apache HttpClient version >4.2 should use BasicClientConnectionManager
			ClientConnectionManager cm = new BasicClientConnectionManager(schemeRegistry);

			HttpClient httpClient = new DefaultHttpClient(cm);

			///////////////


			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpGet);
			// HttpClient c = new DefaultHttpClient();        
			// HttpGet post = new HttpGet(url); 	            

			// HttpResponse r = c.execute(post);	
			httpResponse.setResponseCode(response.getStatusLine().getStatusCode());
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				result+=line;
			}
			httpResponse.setResponseStr(result);
			rd.close();
		}
		catch(Exception ex) {
			result=ex.toString();
			logger.error("makeHTTPSRequest:: ",ex);
		}  
		return httpResponse;
	} 




	public String makeHTTPPOSTRequestWithSoapXML(String url,String xml) {
		String result ="";   
		try {
			HttpClient httpClient= new DefaultHttpClient();        
			HttpPost post = new HttpPost(url); 	            
			post.setHeader("Content-type","text/xml");
			//post.setHeader("Authorization", "Basic " + authStringEnc);
			post.setEntity(new StringEntity(xml));	           
			HttpResponse r = httpClient.execute(post);	 
			BufferedReader rd = new BufferedReader(new InputStreamReader(r.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				result+=line;
			}
			rd.close();

		}
		catch(Exception ex) {
			result=ex.toString();
			logger.error("makeHTTPPOSTRequest:: "+ex+", url: "+url+" , xml: "+xml);
		}  
		return result;
	}

	public String invokePostURL(String url,String xml){
		String urlResp = "";

		try{

			java.net.URL urlObject =  new java.net.URL(url);
			java.net.HttpURLConnection urlConn = (java.net.HttpURLConnection)urlObject.openConnection();
			urlConn.setUseCaches(false);
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			urlConn.setRequestMethod("POST");
			urlConn.connect();
			OutputStream out=urlConn.getOutputStream();
			out.write(xml.getBytes());

			InputStream in=urlConn.getInputStream();
			int i=-1;
			while((i=in.read())!=-1){
				urlResp+=(char)i;
			}
			out.close();
			in.close();
		} catch (Exception e) {
			urlResp= e.getMessage();
		}
		return urlResp;

	}

	public HTTPResponse invokeGetURL(String url){
		String urlResp = "";
		HTTPResponse httpResponse = new HTTPResponse();
		try{

			java.net.URL urlObject =  new java.net.URL(url);
			java.net.HttpURLConnection urlConn = (java.net.HttpURLConnection)urlObject.openConnection();
			urlConn.setUseCaches(false);
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			urlConn.setRequestMethod("GET");
			urlConn.connect();

			httpResponse.setResponseCode(urlConn.getResponseCode());
			InputStream in=urlConn.getInputStream();
			int i=-1;
			while((i=in.read())!=-1){
				urlResp+=(char)i;
			}
			httpResponse.setResponseStr(urlResp);
			in.close();
		} catch (Exception e) {
			httpResponse.setResponseCode(-1);
			logger.error("Exception:: ",e);
		}
		logger.info("invokeGetURL::: "+url+" , response: "+httpResponse);
		return httpResponse;
	}


	public HTTPResponse sendHttpsGet(String url) {

		HTTPResponse httpResponse = new HTTPResponse();
		int responseCode = -1;
		StringBuffer response = new StringBuffer();
		String error = "";
		BufferedReader in = null;
		URL obj = null;
		HttpsURLConnection con = null;
		try {
			obj = new URL(url);

			con = (HttpsURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");
			con.setReadTimeout(30000);
			con.setConnectTimeout(30000);

			responseCode = con.getResponseCode();
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			logger.info("\nSending 'GET' request to URL : " + url
					+ ", Response Code : " + responseCode + ", response: "
					+ response.toString());

		} catch (Exception e) {

			logger.error("Sending 'GET' request to URL : " + url
					+ ", Response Code : " + responseCode + ", response: "
					+ response.toString() + ", Excption: " + e);
		} finally {

			try {
				if (in != null)
					in.close();
				if (con != null)
					con.disconnect();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("Error in closing http connection for url:" + url
						+ " " + e);
			}
		}

		httpResponse.setError(error);
		httpResponse.setResponseCode(responseCode);
		httpResponse.setResponseStr(response.toString());
		return httpResponse;
	}



	public HTTPResponse sendHttpGet(String url,Map<String,String> headerMap) {

		HTTPResponse httpResponse = new HTTPResponse();
		int responseCode = -1;
		StringBuffer response = new StringBuffer();
		String error = "";
		BufferedReader in = null;
		URL obj = null;
		HttpURLConnection con = null;
		try {
			obj = new URL(url);

			con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");
			con.setReadTimeout(30000);
			con.setConnectTimeout(30000);
			if(headerMap!=null){
				Iterator<String> itr=headerMap.keySet().iterator();
				while(itr.hasNext()){
					String key=itr.next();
					con.setRequestProperty(key,headerMap.get(key));
				}
			}

			responseCode = con.getResponseCode();
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			logger.info("\nSending 'GET' request to URL : " + url
					+ ", Response Code : " + responseCode + ", response: "
					+ response.toString());

		} catch (Exception e) {

			logger.error("Sending 'GET' request to URL : " + url
					+ ", Response Code : " + responseCode + ", response: "
					+ response.toString() + ", Excption: " + e);
		} finally {

			try {
				if (in != null)
					in.close();
				if (con != null)
					con.disconnect();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("Error in closing http connection for url:" + url
						+ " " + e);
			}
		}

		httpResponse.setError(error);
		httpResponse.setResponseCode(responseCode);
		httpResponse.setResponseStr(response.toString());
		return httpResponse;
	}



	public HTTPResponse sendHttpsPost(String url,String json) {

		HTTPResponse httpResponse = new HTTPResponse();
		int responseCode = -1;
		StringBuffer response = new StringBuffer();
		String error = "";
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		BufferedReader in =null;
		try {


			post.addHeader("Accept","application/json");
			post.addHeader("Content-Type","application/json");

			if(!StringUtils.isEmpty(json)){
				StringEntity stringEntity=new StringEntity(json);
				stringEntity.getContent();
				logger.debug("stringEntity::::::"+stringEntity);

				post.setEntity(stringEntity);					
			}

			HttpResponse res = client.execute(post);
			logger.debug("HttpResponse:::::::::::"+res.getStatusLine());
			responseCode=res.getStatusLine().getStatusCode();
			if (res != null) {
				InputStream inputStream = res.getEntity().getContent(); //Get the data in the entity
				in = new BufferedReader(new InputStreamReader(inputStream));
				String inputLine;

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);

				}
			}

		} catch (Exception e) {
			error=e.toString();
			logger.error("\nSending 'GET' request to URL : " + url
					+ ", Response Code : " + responseCode + ", response: "
					+ response.toString() + ", Excption: " + e);
		} finally {

			try {
				if (in != null)
					in.close();
				if (post != null)
					post.releaseConnection();
			} catch (Exception e) {

				logger.error("Error in closing http connection for url:" + url
						+ " " + e);
			}
		}

		httpResponse.setError(error);
		httpResponse.setResponseCode(responseCode);
		httpResponse.setResponseStr(response.toString());
		return httpResponse;
	}


	public HTTPResponse sendHttpsGet(String url,Map<String,String> headerMap) {

		HTTPResponse httpResponse = new HTTPResponse();
		int responseCode = -1;
		StringBuffer response = new StringBuffer();
		String error = "";
		BufferedReader in = null;
		URL obj = null;
		HttpsURLConnection con = null;
		try {
			obj = new URL(url);

			con = (HttpsURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");
			con.setReadTimeout(30000);
			con.setConnectTimeout(30000);
			if(headerMap!=null){
				Iterator<String> itr=headerMap.keySet().iterator();
				while(itr.hasNext()){
					String key=itr.next();
					con.setRequestProperty(key,headerMap.get(key));
				}
			}

			responseCode = con.getResponseCode();
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			logger.info("\nSending 'GET' request to URL : " + url
					+ ", Response Code : " + responseCode + ", response: "
					+ response.toString());

		} catch (Exception e) {

			logger.error("Sending 'GET' request to URL : " + url
					+ ", Response Code : " + responseCode + ", response: "
					+ response.toString() + ", Excption: " + e);
		} finally {

			try {
				if (in != null)
					in.close();
				if (con != null)
					con.disconnect();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("Error in closing http connection for url:" + url
						+ " " + e);
			}
		}

		httpResponse.setError(error);
		httpResponse.setResponseCode(responseCode);
		httpResponse.setResponseStr(response.toString());
		return httpResponse;
	}



	public HTTPResponse makeHTTPPOSTRequest(String url,String json,Map<String,String> headerMap) {
		HTTPResponse httpResponse =new HTTPResponse();   
		String result="";
		try {
			HttpClient c = new DefaultHttpClient();        
			HttpPost post = new HttpPost(url); 	            

			if(headerMap!=null){
				Iterator<String> itr=headerMap.keySet().iterator();
				while(itr.hasNext()){
					String key=itr.next();
					post.setHeader(key,headerMap.get(key));
				}
			}
			if(json!=null){
				post.setEntity(new StringEntity(json));	        
			}
			HttpResponse r = c.execute(post);	
			httpResponse.setResponseCode(r.getStatusLine().getStatusCode());
			BufferedReader rd = new BufferedReader(new InputStreamReader(r.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				result+=line;
			}
			httpResponse.setResponseStr(result);
			rd.close();
		}
		catch(Exception ex) {
			result=ex.toString();
			httpResponse.setResponseStr(result);		        	
			logger.error("makeHTTPPOSTRequest:: ",ex);
		}  
		return httpResponse;
	}


	public HTTPResponse sendPostRequest(String url,Map<String,String> data,Map<String,String> header){
		String result ="";
		HTTPResponse httpResponse=new HTTPResponse(); 
		try{


			//HttpClient client = HttpClientBuilder.create().build();
			HttpClient client = new DefaultHttpClient(); 
			HttpPost post = new HttpPost(url);
			Iterator<String> itr=header.keySet().iterator();
			while(itr.hasNext()){
				String key=itr.next();	
				post.setHeader(key,header.get(key));
			}


			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			itr=data.keySet().iterator();
			while(itr.hasNext()){
				String key=itr.next();	
				urlParameters.add(new BasicNameValuePair(key,data.get(key)));
			}

			post.setEntity(new UrlEncodedFormEntity(urlParameters));

			HttpResponse response = client.execute(post);
			httpResponse.setResponseCode(response.getStatusLine().getStatusCode());
			BufferedReader rd = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent()));


			String line = "";
			while ((line = rd.readLine()) != null) {
				result+=line;
			}
			httpResponse.setResponseStr(result);
			rd.close();
		}catch(Exception ex){
			logger.error("sendPostRequest:: ",ex);
			result=null;
		}finally{

		}
		return httpResponse;
	}


	public HTTPResponse makeHTTPPOSTRequestWithXML(String url,String xml) {
		String result ="";   
		HTTPResponse httpResponse=new HTTPResponse();
		try {

			DefaultHttpClient  httpClient= new DefaultHttpClient();  



			HttpPost post = new HttpPost(url); 	            
			post.setHeader("Content-type","application/xml");
			//post.setHeader("Content-type","application/text");
			//post.setHeader("Authorization", "Basic " + authStringEnc);
			post.setEntity(new StringEntity(xml));	

			HttpResponse r = httpClient.execute(post);	
			httpResponse.setResponseCode(r.getStatusLine().getStatusCode());
			Header []location=r.getAllHeaders();
			String header="";
			for(Header h:location ){
				header+=h.getName()+"="+h.getValue();
			}
			httpResponse.setErrorMessage(header);
			BufferedReader rd = new BufferedReader(new InputStreamReader(r.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				result+=line;
			}
			httpResponse.setResponseStr(result);
			rd.close();

		}
		catch(Exception ex) {
			result=ex.toString();
			httpResponse.setResponseStr(ex.toString());
			logger.error("makeHTTPPOSTRequest:: "+", url: "+url+" , xml: "+xml,ex);
		}  
		return httpResponse;
	}    

	// HTTP GET request
	//	public HTTPResponse makeHTTPPOSTRequestWithXML(String url,String xml) {
	//
	//				HTTPResponse httpResponse = new HTTPResponse();
	//				int responseCode = -1;
	//				StringBuffer response = new StringBuffer();
	//				String error = "";
	//				InputStream in = null;
	//				OutputStream out=null;
	//				URL obj = null;
	//				HttpsURLConnection con = null;
	//				try {
	//					obj = new URL(url);
	//
	//					con = (HttpsURLConnection) obj.openConnection();
	//					con.setInstanceFollowRedirects(true);
	//					
	//					// optional default is GET
	//					con.setRequestMethod("POST");
	//					con.setReadTimeout(30000);
	//					con.setConnectTimeout(30000);
	//					con.setDoOutput(true);
	//					con.setDoInput(true);
	//					// add request header
	//					// con.setRequestProperty("User-Agent", userAgent);
	//					// con.setRequestProperty("Content-Type",
	//					// "text/plain; charset=utf-8");
	//					// con.setRequestProperty("Accept", "/");
	//					con.setRequestProperty("Content-type","application/xml");
	//					
	//
	//					responseCode = con.getResponseCode();
	//					 out=con.getOutputStream();
	//					out.write(xml.getBytes());
	//					out.flush();
	//					in = con.getInputStream();
	//					
	//					int  i=-1;	
	//					while ((i = in.read()) != -1) {
	//						response.append((char)i);
	//					}
	//					logger.info("makeHTTPPOSTRequestWithXML " + url
	//							+ ", Response Code : " + responseCode + ", response: "
	//							+ response.toString());
	//
	//				} catch (Exception e) {
	//										logger.error("makeHTTPPOSTRequestWithXML " + url
	//							+ ", Response Code : " + responseCode + ", response: "
	//							+ response.toString() + ", Excption: " + e);
	//				} finally {
	//
	//					try {
	//						if(out!=null){out.close();}
	//						if (in != null)
	//							in.close();
	//						if (con != null)
	//							con.disconnect();
	//					} catch (Exception e) {
	//						// TODO Auto-generated catch block
	//						logger.error("Error in closing http connection for url:" + url
	//								+ " " + e);
	//					}
	//				}
	//
	//				httpResponse.setError(error);
	//				httpResponse.setResponseCode(responseCode);
	//				httpResponse.setResponseStr(response.toString());
	//				return httpResponse;
	//			}


	public HTTPResponse makeHTTPGETRequest(String url,Map<String,String> headerMap) {
		String result ="";   
		HTTPResponse httpResponse=new HTTPResponse(); 
		try {
			HttpClient c = new DefaultHttpClient();        
			HttpGet get = new HttpGet(url); 	            

			if(headerMap!=null){
				Iterator<String> itr=headerMap.keySet().iterator();
				while(itr.hasNext()){
					String key=itr.next();
					get.setHeader(key,headerMap.get(key));
				}
			}



			HttpResponse r = c.execute(get);	 
			httpResponse.setResponseCode(r.getStatusLine().getStatusCode());
			Header[] responseHeader=  r.getAllHeaders();

			BufferedReader rd = new BufferedReader(new InputStreamReader(r.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				result+=line;
			}
			httpResponse.setResponseStr(result);
			rd.close();
		}
		catch(Exception ex) {
			result=ex.toString();
			httpResponse.setResponseStr(ex.toString());
			logger.error("makeHTTPPOSTRequest:: ",ex);
		}  
		return httpResponse;
	}



	public HTTPResponse makeHTTPDeleteRequest(String url,Map<String,String> headerMap) {
		String result ="";   
		HTTPResponse httpResponse=new HTTPResponse(); 
		try {
			HttpClient c = new DefaultHttpClient();        
			HttpDelete get = new HttpDelete(url); 	            

			if(headerMap!=null){
				Iterator<String> itr=headerMap.keySet().iterator();
				while(itr.hasNext()){
					String key=itr.next();
					get.setHeader(key,headerMap.get(key));
				}
			}
			HttpResponse r = c.execute(get);	 
			httpResponse.setResponseCode(r.getStatusLine().getStatusCode());
			Header[] responseHeader=  r.getAllHeaders();

			BufferedReader rd = new BufferedReader(new InputStreamReader(r.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				result+=line;
			}
			httpResponse.setResponseStr(result);
			rd.close();
		}
		catch(Exception ex) {
			result=ex.toString();
			httpResponse.setResponseStr(ex.toString());
			logger.error("makeHTTPDELETERequest:: ",ex);
		}  
		return httpResponse;
	}


}