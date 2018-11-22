package com;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class Test {
    public static List mem(){
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List list=new ArrayList();
        for(int j=0; j<10000; j++){
            List list2=new ArrayList();
            list2.add("aaaaaaaaaaaaaaaaaaaaaaaaa");
            list.add(list2);
        }
        return list;
    }
    public static String usedmem(){
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int freemem= (int) (Runtime.getRuntime().freeMemory()/1024/1024);
        int totalmem= (int) (Runtime.getRuntime().totalMemory()/1024/1024);
        int maxmem= (int) (Runtime.getRuntime().maxMemory()/1024/1024);
        int usedMemory=totalmem-freemem;

        float result= (float) (usedMemory*1.0/maxmem*100);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS").format(Calendar.getInstance().getTime())+"-------used Memory:"+usedMemory+"-------freepercent:"+(100-result)+"%";
    }

    public static String getparam(HttpServletRequest request,HttpServletResponse response) throws IOException {
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("get result:---");
        Map<String, String[]> params = request.getParameterMap();
        if (params == null || params.isEmpty()) {
            response.sendError(500, "params is empty!!!!!!");
//            throw  new IOException("param is empty");
        } else {
            for (Iterator iter = params.entrySet().iterator(); iter.hasNext(); ) {
                Map.Entry element = (Map.Entry) iter.next();
                Object strKey = element.getKey();
                String[] value = (String[]) element.getValue();
                stringBuffer.append(strKey.toString() + "===");
                for (int i = 0; i < value.length; i++) {
                    if (value[i].length() != 0) {
                        stringBuffer.append(value[i] + ",,,");
                    } else {
                        response.sendError(500, strKey.toString()+" is empty");
//                        throw  new IOException(strKey.toString()+" is empty");
                    }
                }
            }
        }
        return stringBuffer.toString();
    }
    public static String getValue(String key){
        Properties prop = new Properties();
        String string="";
        try {
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("jvm.properties");
            prop.load(in);
            string=prop.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }
}
