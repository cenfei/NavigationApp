package com.yj.navigation.network;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import com.google.gson.Gson;
import com.yj.navigation.util.Base64Util;
import com.yj.navigation.util.Md5Util;

public class DESedeUtil {

	// 签名key  
    private final static String signKey = "21Vw72FJK2Fitl61vEVul5eFc8ftgdef" ;  
	// 密钥  
    private final static String secretKey = "l4Vw72FJK2Fitl61vEVul5eFc8ftghdh" ;  
    // 向量  
    private final static String iv = "01234567" ;  
    // 加解密统一使用的编码方式  
    private final static String encoding = "utf-8" ;  
       
    /** 
     * 3DES加密 
     *  
     * @param plainText 普通文本 
     * @return 
     * @throws Exception  
     */ 
    public static String encode(String plainText) throws Exception {  
        Key deskey = null ;  
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());  
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance( "desede" );  
        deskey = keyfactory.generateSecret(spec);  
       
        Cipher cipher = Cipher.getInstance( "desede/CBC/PKCS5Padding" );  
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());  
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);  
        byte [] encryptData = cipher.doFinal(plainText.getBytes(encoding));  
        return Base64Util.encode(encryptData);
    }  
       
    /** 
     * 3DES解密 
     *  
     * @param encryptText 加密文本 
     * @return 
     * @throws Exception 
     */ 
    public static String decode(String encryptText) throws Exception {  
        Key deskey = null ;  
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());  
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance( "desede" );  
        deskey = keyfactory.generateSecret(spec);  
        Cipher cipher = Cipher.getInstance( "desede/CBC/PKCS5Padding" );  
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());  
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
        byte [] decryptData = cipher.doFinal(Base64Util.decode(encryptText));
        return new String(decryptData, encoding);  
    }
    
    
    /**
     * 签名
     * @param data
     * @param sign
     * @return
     * @throws Exception
     */
    public static String sign(String data) throws Exception {
        String mysign = Md5Util.MD5(data + signKey);
        return mysign;
    }
    
    /**
     * 验证签名
     * @param data
     * @param sign
     * @return
     * @throws Exception
     */
    public static boolean verifySign(String data, String sign) throws Exception {
        String mysign = sign(data);
        if(sign.equalsIgnoreCase(mysign)){
        	return true;
        }else{
        	return false;
        }
    }
    
    public static void main(String[] args) throws Exception{
    	Map<String, Object> ss = new HashMap<String, Object>();
    	ss.put("appName", "carhelper");
    	ss.put("platform", "app");
    	ss.put("version", "1.0.0");
    	ss.put("deviceNo", "123456");
    	ss.put("mobile", "13800000000");
    	
    	
    	String json = new Gson().toJson(ss);
    	System.out.println("请求数据："+json);
		System.out.println("请求数据签名："+sign(json));
		System.out.println("请求数据加密："+encode(json));
		System.out.println("请求数据加密后解密："+decode(encode(json)));
		
	}

}
