package com.yj.navigation.network;

import android.util.Log;

import com.google.gson.Gson;
import com.mining.app.zxing.decoding.Intents;
import com.yj.navigation.util.Base64Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

//

public class Caller {

    /**
     * http发送post请求
     *

     * @return
     */
    public static String sendPost(String urlStr, Map<String, Object> params) {
        String result = null;

        HttpURLConnection urlConnection = null;

        try {



            // Thread.sleep(5000);
            URL url = new URL(urlStr);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(15000);
            urlConnection.setReadTimeout(15000);
//            if(!TextUtils.isEmpty(type)&&!TextUtils.isEmpty(step)&&type.equals("czgl")&&step.equals("save")) {//需要post

                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            urlConnection.setRequestProperty("Accept-Charset", "utf-8");
//            urlConnection.setRequestProperty("contentType", "utf-8");

            urlConnection.setInstanceFollowRedirects(false);





            urlConnection.setUseCaches(false);


            urlConnection.connect();




            List<Map<String, String>> mapList = null;


            StringBuffer stringBuffer=new StringBuffer();

            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey().toString();


                if ("MAP".equals(key)) {
                    mapList = (List<Map<String, String>>) params.get("MAP");

                    int i = 0;
                    String idOrQuestion=null;
                    if(urlStr.contains("check")){
                        idOrQuestion="id";
                    }else{
                        idOrQuestion="question";

                    }

                    for (Map<String, String> map : mapList) {

//                        NameValuePair pair = new BasicNameValuePair("items[" + i + "]."+idOrQuestion, map.get(idOrQuestion));

                        stringBuffer.append("items[" + i + "]."+idOrQuestion + "=" + map.get(idOrQuestion) + "&");
//                        list.add(pair);
//                        NameValuePair pair2 = new BasicNameValuePair("items[" + i + "].answer", map.get("answer"));

                        stringBuffer.append("items[" + i + "].answer" + "=" + map.get("answer") + "&");
//                        list.add(pair2);
                        i++;
                    }


                } else {
                    String value = entry.getValue().toString();
//                    if(key.contains("takeDt")){
//                        value = value.replace(" ","+");
//                    }


                    stringBuffer.append(key + "=" + value + "&");



//                    NameValuePair pair = new BasicNameValuePair(key, value);
//                    list.add(pair);
                }

            }



String content= stringBuffer.toString().substring(0, stringBuffer.toString().length() - 1);

//                    content = Base64Util.encode(content);

//            Log.d("输入post参数", content);
//            DataOutputStream out = new DataOutputStream(urlConnection
//                    .getOutputStream());
//            // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写到流里面
////            out.writeBytes(content);
//            out.write(content.getBytes());

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getBytes());
            //发送请求数据

            DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());
            long total = content.getBytes().length;
            Log.i("request url：", ""+ urlStr);

            Log.i("request param：", ""+ content);

            Log.i("文件大小total：", ""+ total);
            // 设置每次写入1024bytes
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = -1;
            long progressBarLength = 0;
            //从文件读取数据至缓冲区
            while ((length = byteArrayInputStream.read(buffer)) != -1) {
                //将请求体写入DataOutputStream中
                out.write(buffer, 0, length);
                //获取进度
                progressBarLength += length;
            }




            out.flush();
            out.close();







            int code = urlConnection.getResponseCode();
            Log.d("STATUS code", String.format("%d", code));
            // String string = urlConnection.getResponseMessage();
//            System.out.println(urlConnection.toString());
            InputStream in = new BufferedInputStream(
                    urlConnection.getInputStream());
            result = convertStreamToString(in);
            Log.i("result ：", "" + result);

//            Log.d("INFO", "**RESULT** " + result);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("tag", "exception >>>>>>>" + e.getLocalizedMessage());
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }


        return result;
    }


    /**
     * http发送post请求
     *
     * @param url
     * @param maps
     * @return
     */
//    public static String sendPost0(String url, Map<String, Object> params) {
//        DefaultHttpClient client = new DefaultHttpClient();
//        /**NameValuePair是传送给服务器的请求参数    param.get("name") **/
//        List<NameValuePair> list = new ArrayList<NameValuePair>();
//
//        StringBuffer sb = new StringBuffer();
//        sb.append(url + "?");
//
//        List<Map<String, String>> mapList = null;
//
//
//
//        for (Map.Entry<String, Object> entry : params.entrySet()) {
//            String key = entry.getKey().toString();
//
//
//            if ("MAP".equals(key)) {
//                mapList = (List<Map<String, String>>) params.get("MAP");
//
//                int i = 0;
//                String idOrQuestion=null;
//                if(url.contains("check")){
//                    idOrQuestion="id";
//                }else{
//                    idOrQuestion="question";
//
//                }
//
//                for (Map<String, String> map : mapList) {
//
//                    NameValuePair pair = new BasicNameValuePair("items[" + i + "]."+idOrQuestion, map.get(idOrQuestion));
//
//                    sb.append("items[" + i + "]."+idOrQuestion + "=" + map.get(idOrQuestion) + "&");
//                    list.add(pair);
//                    NameValuePair pair2 = new BasicNameValuePair("items[" + i + "].answer", map.get("answer"));
//
//                    sb.append("items[" + i + "].answer" + "=" + map.get("answer") + "&");
//                    list.add(pair2);
//                    i++;
//                }
//
//
//            } else {
//                String value = entry.getValue().toString();
//                sb.append(key + "=" + value + "&");
//                NameValuePair pair = new BasicNameValuePair(key, value);
//                list.add(pair);
//            }
//
//        }
//
//        Log.d("输入参数", sb.toString().substring(0, sb.toString().length() - 1));
//
//
//        UrlEncodedFormEntity entity = null;
//        try {
//            /**设置编码 **/
//            entity = new UrlEncodedFormEntity(list, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        /**新建一个post请求**/
//        HttpPost post = new HttpPost(url);
//        post.setEntity(entity);
//        HttpResponse response = null;
//        try {
//            /**客服端向服务器发送请求**/
//            response = client.execute(post);
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        /**请求发送成功，并得到响应**/
//        String result = null;
//        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//            HttpEntity httpEntity = response.getEntity();
//            try {
//                result = EntityUtils.toString(httpEntity);
//                Log.d("输出参数：", result == null ? "" : result);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }// 返回json格式：
////			jsonObject = new Gson().toJson(result);
//        }
//        return result;
//    }


    /**
     * 功能：https post请求
     *
     * @param urlStr
     * @return
     */
//    public static String doHttpsPost(String urlStr, Map<String, Object> params) {
//
//        String data = null;
//        URL url = null;
//        HttpsURLConnection conn = null;
//        DataOutputStream out = null;
//
//        String postOrGet = (String) params.get("POST_OR_GET");
//        postOrGet = postOrGet == null ? "POST" : postOrGet;
//
//        try {
//
//            if (postOrGet.equalsIgnoreCase("get")) {// 拼接参数
//                StringBuffer sb = new StringBuffer();
//                Set<String> keys = params.keySet();
//                for (Iterator<String> i = keys.iterator(); i.hasNext(); ) {
//                    String key = i.next();
//                    if (!key.equals("Cookie") && !key.equals("POST_OR_GET")) {
//                        sb.append(key + "=" + params.get(key) + "&");
//
//                    }
//                }
//                if (sb.length() > 0) {
//                    urlStr = urlStr + "?"
//                            + sb.toString().substring(0, sb.length() - 1);
//                }
//            }
//
//            SSLSocketFactory sslSocketFactory = getSSLSocketFactory();
//
//            if (sslSocketFactory != null) {
//                Log.d("URL", urlStr);
//
//                url = new URL(urlStr);
//                conn = (HttpsURLConnection) url.openConnection();
//                conn.setSSLSocketFactory(sslSocketFactory);
//                conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
//            } else {
//                url = new URL(urlStr.replaceFirst("https", "http"));
//                Log.d("URL", url.toString());
//                conn = (HttpsURLConnection) url.openConnection();
//            }
//
//            conn.setConnectTimeout(30000);
//            conn.setRequestProperty("Accept-Charset", "utf-8");
//            conn.setRequestProperty("contentType", "utf-8");
//            conn.setReadTimeout(30000);
//            if (postOrGet.equalsIgnoreCase("POST"))
//                conn.setDoOutput(true);
//            else
//                conn.setDoOutput(false);
//
//            conn.setRequestMethod(postOrGet);
//
//            if (params.get("Cookie") != null) {
//                conn.addRequestProperty("Cookie", (String) params.get("Cookie"));
//            }
//
//            if (postOrGet.equalsIgnoreCase("post")) {
//
//                out = new DataOutputStream(conn.getOutputStream());
//                StringBuffer sb = new StringBuffer();
//                ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
//                if (params != null) {
//                    Set<String> keys = params.keySet();
//                    for (Iterator<String> i = keys.iterator(); i.hasNext(); ) {
//                        String key = i.next();
//                        if (!key.equals("Cookie") && !key.equals("POST_OR_GET")) {
//                            sb.append(key + "=" + params.get(key)
//                                    + "&");
//
//                        }
//                    }
//                }
//                Log.e(url + "*****params=***********", sb.toString().substring(0,
//                        sb.toString().length() - 1));
//                out.writeBytes(sb.toString().substring(0,
//                        sb.toString().length() - 1));
//                out.flush();
//            }
//
//            conn.connect();
//            int responseCode = conn.getResponseCode();
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                data = convertStreamToString(conn.getInputStream());
//                // if (urlStr.contains("passport.dodomoney.com")){
//                // data = data.substring(data.indexOf(">{") + 1,
//                // data.lastIndexOf("}<") + 1);//去除
//                // }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (conn != null)
//                conn.disconnect();
//            try {
//                if (out != null) {
//                    out.close();
//                }
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                return null;
//            }
//        }
//        Log.e(url + "*****result=***********", data == null ? "" : data);
//        return data;
//
//    }

    public static String doLogin(String urlStr) {
        String data = null;
        URL url = null;
        try {

            SSLSocketFactory sslSocketFactory = getSSLSocketFactory();
            HttpsURLConnection conn = null;
            if (sslSocketFactory != null) {
                Log.d("URL", urlStr);
                url = new URL(urlStr);
                conn = (HttpsURLConnection) url.openConnection();
                conn.setSSLSocketFactory(sslSocketFactory);
                conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            } else {
                url = new URL(urlStr.replaceFirst("https", "http"));
                Log.d("URL", url.toString());
                conn = (HttpsURLConnection) url.openConnection();
            }
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.connect();
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                data = convertStreamToString(conn.getInputStream());
                // if (urlStr.contains("passport.dodomoney.com")){
                // data = data.substring(data.indexOf(">{") + 1,
                // data.lastIndexOf("}<") + 1);//去除
                // }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    private static SSLSocketFactory getSSLSocketFactory() {
        try {
            initSSLSocketFactory();
            return socketFactory;
        } catch (Exception e) {
            return null;
        }
    }

    private static SSLSocketFactory socketFactory;

    private static void initSSLSocketFactory() {
        if (socketFactory != null) {
            if (HttpsURLConnection.getDefaultSSLSocketFactory().equals(
                    socketFactory)) {
                return;
            } else {
                HttpsURLConnection.setDefaultSSLSocketFactory(socketFactory);
                return;
            }
        }
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            HostnameVerifier hnv = new HostnameVerifier() {

                @Override
                public boolean verify(String hostname, SSLSession session) {
                    // TODO Auto-generated method stub
                    return false;
                }
            };
            TrustManager trustManager = new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                @Override
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }
            };
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            socketFactory = sslContext.getSocketFactory();
            HttpsURLConnection.setDefaultSSLSocketFactory(socketFactory);
            HttpsURLConnection.setDefaultHostnameVerifier(hnv);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

//    public static String doPost(String protocol, Map<String, String> params) {
//        String result = null;
//        DefaultHttpClient client = new DefaultHttpClient();// http客户�?
//        HttpPost httpPost = new HttpPost(protocol);
//        // httpPost.addHeader("Content-Type", "text/plain");
//        ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
//        if (params != null) {
//            Set<String> keys = params.keySet();
//            for (Iterator<String> i = keys.iterator(); i.hasNext(); ) {
//                String key = i.next();
//                pairs.add(new BasicNameValuePair(key, params.get(key)));
//            }
//        }
//
//        try {
//            UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(pairs,
//                    "utf-8");
//            /*
//			 * 将POST数据放入HTTP请求
//			 */
//            httpPost.setEntity(p_entity);
//			/*
//			 * 发出实际的HTTP POST请求
//			 */
//            HttpResponse response = client.execute(httpPost);
//            HttpEntity entity = response.getEntity();
//            InputStream content = entity.getContent();
//            result = convertStreamToString(content);
//
//        } catch (IllegalStateException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }

    // do get if param is null
    public static String doPost(String protocol, String param) {
        String result = null;
        boolean bPost = param == null ? false : true;

        Log.d(bPost ? "POST" : "GET", bPost ? param : protocol);
        HttpURLConnection urlConnection = null;

        try {
            // Thread.sleep(5000);
            URL url = new URL(protocol);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(20000);
            urlConnection.setReadTimeout(20000);
            if (bPost)
                urlConnection.setRequestMethod("POST");
            else
                urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type",
                    "text/plain; charset=utf-8"); // "application/json"
            // urlConnection.setRequestProperty("Content-Type",
            // "text/xml; charset=utf-8"); //"application/json"

            // urlConnection.setRequestProperty("SOAPAction",
            // "\"http://MoaService/GetDocListCount\"");
            // urlConnection.setRequestProperty("Content-Length",
            // String.format("%d", param.length())); //"application/json"
            urlConnection.setDoInput(true);
            if (bPost)
                urlConnection.setDoOutput(true);
            else
                urlConnection.setDoOutput(false);
            urlConnection.setUseCaches(false);
            if (bPost) {
                DataOutputStream outputStream = new DataOutputStream(
                        urlConnection.getOutputStream());
                byte[] paramBytes = param.getBytes("UTF-8");
                outputStream.write(paramBytes, 0, paramBytes.length);
                // outputStream.writeChars(param);
                outputStream.flush();
                outputStream.close();
            }

            urlConnection.connect();
            int code = urlConnection.getResponseCode();
            Log.d("STATUS code", String.format("%d", code));
            // String string = urlConnection.getResponseMessage();
            System.out.println(urlConnection.toString());
            InputStream in = new BufferedInputStream(
                    urlConnection.getInputStream());
            result = convertStreamToString(in);
            Log.d("INFO", "**RESULT** " + result);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("tag", "exception >>>>>>>" + e.getLocalizedMessage());
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }

        return result;
    }

    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    public static String dPost(String ADD_URL, Map<String, String> params,
                               Map<String, Integer> param) {
        StringBuffer sb = null;
        try {
            // 创建连接
            URL url = new URL(ADD_URL);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.connect();
            // POST请求
            DataOutputStream out = new DataOutputStream(
                    connection.getOutputStream());
            JSONObject obj = new JSONObject();
            try {
                if (params != null) {
                    Set<String> keys = params.keySet();
                    for (Iterator<String> i = keys.iterator(); i.hasNext(); ) {
                        String key = i.next();
                        obj.put(key, params.get(key));
                    }
                }
                if (param != null) {
                    Set<String> keys = param.keySet();
                    for (Iterator<String> i = keys.iterator(); i.hasNext(); ) {
                        String key = i.next();
                        obj.put(key, param.get(key));
                    }
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            out.writeBytes(obj.toString());
            out.flush();
            out.close();
            // 读取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String lines;
            sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            System.out.println(sb);
            reader.close();
            // 断开连接
            connection.disconnect();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOException : " + e);
            e.printStackTrace();
        }
        return sb.toString();

    }

    public static String dPosta(String ADD_URL, Map<String, Object> params) {
        String responseText = null;
        String lines = null;
        try {
            // 创建连接
            URL url = new URL(ADD_URL);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
//			connection.setRequestProperty("Content-Type",
//					"text/plain; charset=utf-8");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setConnectTimeout(5000);
            connection.setRequestProperty("Content-Type",
                    "application/json; charset=utf-8");
            connection.connect();
            // POST请求
            String obj = new Gson().toJson(params);
            Log.w("输入参数：", obj);
            // 读取响应
            // String objStr= URLEncoder.encode(obj.toString(), "utf-8");
            connection.getOutputStream().write(obj.getBytes());
            InputStream in = connection.getInputStream();
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            try {
                int b;
                while ((b = in.read()) != -1) {
                    byteOut.write(b);
                }
                responseText = new String(byteOut.toByteArray());
            } finally {
                byteOut.close();
            }
            lines = JsonFilter(responseText);
            // 断开连接
            connection.disconnect();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOException : " + e);
        }
        Log.w("输出参数：", lines == null ? "" : lines);

        return lines;

    }

    /*
     * 对json字符串进行过滤,防止乱码和黑框
     */
    public static String JsonFilter(String jsonstr) {
        if (jsonstr != null && jsonstr.indexOf("{") > -1) {
            return jsonstr.substring(jsonstr.indexOf("{"))
                    .replace("\r\n", "\n");
        } else {
            return jsonstr;
        }
    }

    /**
     * 功能：直接传json数据参数
     *
     * @param ADD_URL
     * @param jsonParams
     * @return
     */
    public static String dPostaJsonParam(String ADD_URL, String jsonParams) {
        String lines = null;
        try {
            // 创建连接
            URL url = new URL(ADD_URL);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestProperty("Content-Type",
                    "text/plain; charset=utf-8");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            // connection.setRequestProperty("Content-Type",
            // "application/json");
            connection.connect();
            // POST请求
            JSONObject obj = null;
            try {
                obj = new JSONObject(jsonParams);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // 读取响应

            // String objStr= URLEncoder.encode(obj.toString(), "utf-8");
            connection.getOutputStream().write(obj.toString().getBytes());
            byte[] buffer = new byte[connection.getContentLength()];
            connection.getInputStream().read(buffer, 0, buffer.length);
            lines = new String(buffer, "utf-8");
            // 断开连接
            connection.disconnect();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOException : " + e);
            e.printStackTrace();
        }
        return lines;

    }

    public static String dGet(String ADD_URL, Map<String, String> params,
                              Map<String, Integer> param) {
        StringBuffer sb = null;
        URL getUrl = null;
        try {
            getUrl = new URL(ADD_URL);

        // 根据拼凑的URL，打开连接，URL.openConnection函数会根据 URL的类型，
        // 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
        HttpURLConnection connection = (HttpURLConnection) getUrl
                .openConnection();
        // 进行连接，但是实际上get request要在下一句的 connection.getInputStream()函数中才会真正发到
        // 服务器
        connection.connect();
        // 取得输入流，并使用Reader读取
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        System.out.println(" ============================= ");
        System.out.println(" Contents of get request ");
        System.out.println(" ============================= ");
        String lines;
            sb=new StringBuffer();
        while ((lines = reader.readLine()) != null) {
//            System.out.println(lines);
            sb.append(lines);
        }
        reader.close();
        // 断开连接
        connection.disconnect();
//        System.out.println(" ============================= ");
//        System.out.println(" Contents of get request ends ");
//        System.out.println(" ============================= ");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();

    }

    @SuppressWarnings("unused")
    private static class TrustAnyTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

//    public static String doGet(String url) {
//
//        String data = null;
//        URI encodedUri = null;
//        HttpGet httpGet = null;
//        Log.d("URL", url);
//        try {
//            encodedUri = new URI(url);
//            httpGet = new HttpGet(encodedUri);
//        } catch (URISyntaxException e1) {
//            e1.printStackTrace();
//        }
//
//        HttpClient httpClient = new DefaultHttpClient();
//        HttpResponse httpResponse = null;
//
//        try {
//            // execute request
//            try {
//                httpResponse = httpClient.execute(httpGet);
//            } catch (UnknownHostException e) {
//                e.printStackTrace();
//            } catch (SocketException e) {
//                e.printStackTrace();
//            }
//
//            // request data
//            HttpEntity httpEntity = httpResponse.getEntity();
//
//            if (httpEntity != null) {
//                InputStream inputStream = httpEntity.getContent();
//                data = convertStreamToString(inputStream);
//            }
//
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return data;
//    }
}
