package simpleretrofit.andrewx.com.datalibrary;import android.content.Context;import android.text.TextUtils;import android.widget.Toast;import java.io.BufferedReader;import java.io.IOException;import java.io.InputStream;import java.io.InputStreamReader;import java.io.UnsupportedEncodingException;import java.net.URLEncoder;import java.security.MessageDigest;import java.security.NoSuchAlgorithmException;import java.util.List;public final class FormatUtil {	public static final int CHINESE = 0;	private FormatUtil() {	}	/** 获取系统换行符 */	public final static String LINE_SEPARATOR = System.getProperty("line.separator");	public static String convertStreamToString(InputStream is) {		/*		 * To convert the InputStream to String we use the		 * BufferedReader.readLine() method. We iterate until the BufferedReader		 * return null which means there's no more data to read. Each line will		 * appended to a StringBuilder and returned as String.		 */		final BufferedReader reader = new BufferedReader(new InputStreamReader(				is));		final StringBuilder sb = new StringBuilder();		String line = null;		try {			while ((line = reader.readLine()) != null) {				sb.append(line);				sb.append(LINE_SEPARATOR);			}		} catch (IOException e) {		} finally {			try {				is.close();			} catch (IOException e) {			}		}		return sb.toString();	}	public static String listToString(List<String> stringList){		if (stringList==null) {			return null;		}		StringBuilder result=new StringBuilder();		boolean flag=false;		for (String string : stringList) {			if (flag) {				result.append(",");			}else {				flag=true;			}			result.append(string);		}		return result.toString();	}	public static String formatTime(double s) {		try {			long msec = (long) s;			String minutes, seconds;			seconds = "00" + (msec) % 60;			minutes = "" + (msec / 60);			if (minutes.length() == 1)				minutes = "0" + minutes;			seconds = seconds.substring(seconds.length() - 2, seconds.length());			String times = minutes + ":" + seconds;			return times;		} catch (Exception e) {			return "";		}	}	public static String formatNumber(int number) {		try {			String str;			if (number < 10000) {				str = String.valueOf(number);			} else if (number < 100000000) {				str = String.valueOf(number / 10000) + "涓囨";			} else {				str = String.valueOf(number / 100000000) + "浜挎";			}			return str;		} catch (Exception e) {			// TODO: handle exception		}		return "";	}	public static String URLEncoder(String s) {		if (TextUtils.isEmpty(s))			return "";		try {			s = URLEncoder.encode(s, "UTF-8");		} catch (UnsupportedEncodingException e) {			return "";		} catch (NullPointerException e) {			return "";		}		return s;	}	public static boolean isMD5(String s) {		if (s != null && s.length() == 32)			return true;		return false;	}	public static String md5(final String s) {		try {			MessageDigest digest = MessageDigest.getInstance("MD5");			digest.update(s.getBytes());			byte messageDigest[] = digest.digest();			StringBuffer hexString = new StringBuffer();			for (int i = 0; i < messageDigest.length; i++) {				String h = Integer.toHexString(0xFF & messageDigest[i]);				while (h.length() < 2)					h = "0" + h;				hexString.append(h);			}			return hexString.toString();		} catch (NoSuchAlgorithmException e) {			return "";		}	}	public static void showToast(Context context, String text, int dur) {		Toast.makeText(context, text, dur).show();	}	public static String getLockEncyptPswd(final String s){		String str = s + "iktnI8?Eru:y,tk~2L3f<v{Snv$";		return md5(str).toUpperCase();	}}