package br.com.nex.util;

import java.security.NoSuchAlgorithmException;

public class Hash {

	public static String md5Convert(String senha)
			throws NoSuchAlgorithmException {
		 try {
		        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
		        byte[] array = md.digest(senha.getBytes());
		        StringBuffer sb = new StringBuffer();
		        for (int i = 0; i < array.length; ++i) {
		          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
		       }
		        return sb.toString();
		    } catch (java.security.NoSuchAlgorithmException e) {
		    	e.printStackTrace();
		    }
		    return null;
	}
}
