package com.example.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/5/27.
 */
public class ResponseUtils {

    public static void write(HttpServletResponse response, String resultBody) {
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        int length = 0;
        try {
            byte[] bytes = resultBody.getBytes("utf-8");
            length = bytes.length;
            response.setHeader("Content-Length", length + "");
            response.setDateHeader("Expires", 0);
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(resultBody);
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
