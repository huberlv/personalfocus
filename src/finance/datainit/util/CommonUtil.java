package finance.datainit.util;

import java.io.IOException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;


/**
 * @version:	  
 */
public class CommonUtil{
	
	public static int getMaxPage(int total,int pageSize){
		return total/pageSize+((total%pageSize)>0?1:0);
	}
	
	public static String doPost(String url, List<NameValuePair> params){
		String response = null;
		HttpPost post = new HttpPost(url);
		ResponseHandler<String> handler = new BasicResponseHandler();  
		HttpClient client = new DefaultHttpClient();
		try {
			post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			response = client.execute(post, handler);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			post.abort();
			client.getConnectionManager().shutdown();
		}
		return response;
	}
	
	public static String doGet(String url){
		String response = null;
		HttpGet get = new HttpGet(url);
		ResponseHandler<String> handler = new BasicResponseHandler();  
		HttpClient client = new DefaultHttpClient();
		try {
			response = client.execute(get, handler);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			get.abort();
			client.getConnectionManager().shutdown();
		}
		return response;
	}
		

}
