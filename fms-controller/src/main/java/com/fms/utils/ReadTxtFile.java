package com.fms.utils;
        import java.io.BufferedReader;
        import java.io.File;
        import java.io.FileInputStream;
        import java.io.InputStreamReader;
        import java.util.ArrayList;
        import java.util.List;
        import com.alibaba.fastjson.JSONObject;


public class ReadTxtFile {
    public static void main(String[] args) {
        try {
            String path = "";
            String pathName = path + ".txt";
            File file = new File(pathName);
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "GBK");
            BufferedReader br = new BufferedReader(isr);
            String content = br.readLine();
            br.close();
            isr.close();
            content = content.substring(2, content.length() - 2);
            content = content.replace("},{", ";");
            String[] arrContent = content.split(";");

            //设置列头名称和表体数据
            String[] rowsName = new String[]{"code_type", "code", "name"};
            List<Object[]> dataList = new ArrayList<Object[]>();

            for (String arrc : arrContent) {
                JSONObject jsonObj = JSONObject.parseObject("{" + arrc + "}");
//				String code = jsonObj.getString("COUNTRY_CODE"); 
//				String name = jsonObj.getString("COUNTRY_NAME"); 
//				String code = jsonObj.getString("code"); 
//				String name = jsonObj.getString("name"); 
                String code = jsonObj.getString("code");
                String name = jsonObj.getString("name");
                Object[] obj = new Object[rowsName.length];
                obj[0] = "HY_TRANS_CONDITION";
                obj[1] = code;
                obj[2] = name;
                dataList.add(obj);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}