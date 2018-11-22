package com.paramsTest.asynctest.test;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class ResponseWrapper extends HttpServletResponseWrapper{
    private PrintWriter cachedWriter;
    private CharArrayWriter bufferedWriter;
    public ResponseWrapper(HttpServletResponse response) {
        super(response);
        bufferedWriter = new CharArrayWriter();
        cachedWriter = new PrintWriter(bufferedWriter);
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return cachedWriter;
    }

    /**
     * 获取原始HTML
     *
     * @return
     */
    public String getResult() {
        byte[] bytes = bufferedWriter.toString().getBytes();
        try {
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
