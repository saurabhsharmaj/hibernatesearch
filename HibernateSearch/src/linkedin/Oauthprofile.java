package linkedin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

public class Oauthprofile {
	/**
	 * * @param args * @throws GeneralSecurityException * @throws IOException * @throws
	 * HttpException
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws GeneralSecurityException,
			HttpException, IOException {
		String baseUrl = "http://api.linkedin.com/v1/people/~";
		String Consumer_key = "0pg326br705f"; 
		// add one "&" after the
		// access_token_secret;
		String access_token_secret = "wn7058w0OVadqW8W&";
		String access_token = "access_token";
		long time = System.currentTimeMillis() / 1000;
		String oauth_nonce = Md5(Long.toString(time));
		String oauth_timestamp = Long.toString(time / 100);
		String base = "GET&" + URLEncoder.encode(baseUrl).replace("%7E", "~")
				+ "&";
		String ParamerterString = "";
		ParamerterString += "oauth_consumer_key=" + Consumer_key + "&";
		ParamerterString += "oauth_nonce=" + oauth_nonce + "&";
		ParamerterString += "oauth_signature_method=HMAC-SHA1&";
		ParamerterString += "oauth_timestamp=" + oauth_timestamp + "&";
		ParamerterString += "oauth_token=" + access_token + "&";
		ParamerterString += "oauth_version=1.0";
		base = base + URLEncoder.encode(ParamerterString).replace("%7E", "~");
		String signature = URLEncoder.encode(computeSignature(base,
				access_token_secret));
		ParamerterString += "&oauth_signature=" + signature;
		String[] parameters = ParamerterString.split("&");
		StringBuilder sb = new StringBuilder(
				"OAuth realm=\"http://api.linkedin.com/\",");
		for (int i = 0; i < parameters.length; i++) {
			sb.append(parameters[i]);
			if (i < parameters.length - 1) {
				sb.append(",");
			}
		}
		System.out.println("Signature : " + signature);
		System.out.println("Base String : " + base);
		System.out.println("Parameter String : " + ParamerterString);
		System.out.println("Autheration header : " + sb.toString());
		System.out.println("url : " + baseUrl + "?" + ParamerterString);
		Header authorization = new Header("Authorization", sb.toString());
		HttpClient client = new HttpClient();
		HttpMethod getMethod = new GetMethod(baseUrl + "?" + ParamerterString);
		getMethod.setRequestHeader(authorization);
		getMethod.setFollowRedirects(false);
		client.executeMethod(getMethod);
		System.out.println(getMethod.getResponseBodyAsString());
		Header[] hh = getMethod.getRequestHeaders();
		System.out.println("Header Start ---------------------");
		for (Header header : hh) {
			System.out.println(header.getName() + " " + header.getValue());
		}
		System.out.println("Header End ---------------------");
	}

	private static String computeSignature(String baseString, String keyString)
			throws GeneralSecurityException, UnsupportedEncodingException {
		SecretKey secretKey = null;
		byte[] keyBytes = keyString.getBytes();
		secretKey = new SecretKeySpec(keyBytes, "HmacSHA1");
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(secretKey);
		byte[] text = baseString.getBytes();
		return new String(Base64.encodeBase64(mac.doFinal(text)));
	}

	private static String Md5(String pass) {
		byte[] defaultBytes = pass.getBytes();
		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(defaultBytes);
			byte messageDigest[] = algorithm.digest();
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				String hex = Integer.toHexString(0xFF & messageDigest[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			// *** Testausgabe
			System.out.println("pass " + pass + "   md5 version is "
					+ hexString.toString());
			pass = hexString + "";
		} catch (NoSuchAlgorithmException nsae) {

		}
		return pass;
	}
}
