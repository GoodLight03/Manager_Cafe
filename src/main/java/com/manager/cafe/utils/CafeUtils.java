package com.manager.cafe.utils;


import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.util.Strings;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public class CafeUtils {
    private CafeUtils(){

    }

    public static ResponseEntity<String> getResponseEntity(String responseMessage,HttpStatus httpStatus){
        return new ResponseEntity<String>("{\"message\":\""+ responseMessage +"\"}", httpStatus);
    }

    public static String getUUID(){
        Date date =new Date();
        long time=date.getTime();
        return "Bill"+time;
    }

    public static JSONArray getJsonArrayfromString(String data) throws JSONException{
        JSONArray jsonArray=new JSONArray(data);
        return jsonArray;
    }

    public static Map<String,Object> getMapFormJson(String data){
        if(!Strings.isEmpty(data))
            return new Gson().fromJson(data, new TypeToken<Map<String,Object>>(){}.getType());
        return new HashMap<>();
    }

    public static Boolean isFileExist(String path){
        try {
            File file=new File(path);
            return (file !=null && file.exists()) ? Boolean.TRUE :Boolean.FALSE;
        } catch (Exception e) {
           e.printStackTrace();
        }
        return false;
    }
}
