package com.fms.utils;

import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropertieUtil{
	
  private static Properties properties = null;

  static { 
	  
	properties = new Properties();
    try {
      ClassLoader cl = PropertieUtil.class.getClassLoader();
      InputStream is = cl.getResourceAsStream("kafka.properties");
      properties.load(is);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String getValue(String key){
    return properties.getProperty(key);
  }

  public static void setValue(String key, String value) {
    try {
      OutputStream fos = new FileOutputStream(PropertieUtil.class.getClassLoader().getResource("kafka.properties").getPath());
      properties.setProperty(key, value);
      properties.store(fos, "");
      fos.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void test(){
    System.out.println(PropertieUtil.getValue("kafka.producer.bootstrap.servers"));
  }
}