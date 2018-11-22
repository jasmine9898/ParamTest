package com.paramsTest;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Util {

    public static String oracletest() {
        String result = "Database test";
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin://@192.168.2.61:1521/javatest";
        String username = "javatest";
        String password = "nbs2o13";
        String sql = "create table test_user(id INT,name varchar2(25),birthday varchar2(255),longcontent varchar2(255));";
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
           /* Statement stat = conn.createStatement();
            stat.execute(sql);
            */
           /*
            String sql4 = "delete from test_user ";
            PreparedStatement preparedStatement4 = conn.prepareStatement(sql4);
            // preparedStatement4.setString(1, "%PreparedStatement%");
            preparedStatement4.execute();

            String sql2 = "insert into test_user(id,name,birthday) values (1,?,to_char(sysdate,'YYYY-MM-DD HH24:MI:SS'))";
            PreparedStatement preparedStatement = conn.prepareStatement(sql2);
            preparedStatement.setString(1,"DB ok "+test);
            preparedStatement.execute();*/

            String sql3 = "select * from test_user ";
            //  String sql2 = "SELECT * FROM test_user WHERE name like ? and rownum<=?";
            PreparedStatement preparedStatement3 = null;

            preparedStatement3 = conn.prepareStatement(sql3);
            ResultSet rs = preparedStatement3.executeQuery();
            while (rs.next()) {
                result = rs.getString("name");
            }
        } catch (SQLException e) {
            result = e.getMessage();
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result + "<br>";
    }

    public static String httpclient(String url) {
        StringBuffer stringBuffer = new StringBuffer();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        httpget.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        CloseableHttpResponse res = null;
        try {
            res = httpclient.execute(httpget);
            HttpEntity entity = res.getEntity();
            stringBuffer.append("<br>" + url + "<br>" + EntityUtils.toString(entity) + "<br>");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

    public static String urlconnection(String urlstring) {
        URL url = null;
        try {
            url = new URL(urlstring.replace(" ", ""));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Charsert", "UTF-8");
        conn.setConnectTimeout(100000);
        conn.setReadTimeout(1000000);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestProperty("content-type", "application/octet-stream;charset=UTF-8");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<br><br>");
        try {
            stringBuffer.append(String.valueOf(conn.getResponseCode()));

       /* Map<String, List<String>> header = conn.getHeaderFields();
        for (String key : header.keySet()) {
            stringBuffer.append(key + ":" + header.get(key) + "<br>");
        }*/

            /*if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new InputStreamReader(is, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String readLine = null;
                while ((readLine = br.readLine()) != null) {
                    stringBuffer.append(readLine);
                }
                is.close();
                br.close();
            }*/
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

    public static String getStackTrace() {
        StackTraceElement[] traces = Thread.currentThread().getStackTrace();
        StringBuilder sb = new StringBuilder();
        if (traces != null) {
            for (StackTraceElement ele : traces) {
                sb.append(ele.getLineNumber()).append("\t|").append(ele.getClassName()).append(".").append(ele.getMethodName()).append("\r\n<br>");
            }
        }
        return sb.toString();
    }

    public static String printTrack() {
        StackTraceElement[] st = Thread.currentThread().getStackTrace();
        if (st == null) {
            System.out.println("无堆栈...");
            return "";
        }
        StringBuffer sbf = new StringBuffer();
        for (StackTraceElement e : st) {
            if (sbf.length() > 0) {
                sbf.append(" <- ");
                sbf.append(System.getProperty("line.separator"));
            }
            sbf.append(java.text.MessageFormat.format("{0}.{1}() {2}"
                    , e.getClassName()
                    , e.getMethodName()
                    , e.getLineNumber()));
        }
        return sbf.toString();
    }

    public static void writeToResponse(ServletResponse response, String message) {
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.write(message);
        writer.flush();
    }

    public static String paramOption(ServletRequest request) {
        StringBuffer stringBuffer = new StringBuffer();
        String params = getParamstr(request);
        stringBuffer.append(params);
        if (params.contains("db===1")) {
            stringBuffer.append(oracletest());
        }
        if (params.contains("urlparam===http://")) {
            String[] substr = params.split("<br>");
            for (String s : substr) {
                if (s.contains("urlparam=")) {
                    String urlResult = urlconnection(s.substring(s.indexOf("http")));
                    stringBuffer.append(urlResult);
                }
            }
        }

        return stringBuffer.toString() + "<br>";
    }

    public static String getParamstr(ServletRequest request) {
        StringBuffer stringBuffer = new StringBuffer();
        Map<String, String[]> params = request.getParameterMap();
        if (params == null || params.isEmpty()) {
            stringBuffer.append("Error-500,params is empty!!!!!!");
        } else {
            for (Iterator iter = params.entrySet().iterator(); iter.hasNext(); ) {
                Map.Entry element = (Map.Entry) iter.next();
                Object strKey = element.getKey();
                String[] value = (String[]) element.getValue();
                for (int i = 0; i < value.length; i++) {
                    if (value[i].length() == 0) {
                        stringBuffer.append("Error-500," + strKey.toString() + " is empty");
                    } else {
                        stringBuffer.append(strKey.toString() + "===" + value[i] + "<br>");
                    }
                }
            }
        }
        return stringBuffer.toString() + "<br>";
    }

    public static String longProcessing(int secs) {
        // wait for given time before finishing
        try {
            Thread.sleep(secs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "<br>线程" + Thread.currentThread().getId() + " sleep " + secs;
    }

    public static String nowtime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS").format(Calendar.getInstance().getTime());
    }

}
