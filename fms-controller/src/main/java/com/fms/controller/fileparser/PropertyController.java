package com.fms.controller.fileparser;


import java.util.Map;

import com.fms.service.filemanage.NetPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/propertyController")
public class PropertyController {
	@Autowired
	NetPropertyService netPropertyService;

	@RequestMapping("getList")
	public Object getList(Map<String, Object> params) {
		return netPropertyService.getList(params);
	}

/*
	@ResponseBody
	@RequestMapping("/hardwarePropertyDisplay")
    public String hardwarePropertyDisplay(int controlId,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			BaseService service=App.getService("t_hardware_property");
			HashMap<String, Object> map = service.setData(controlId).where("id=#{controlId}").selectMap();
			String info=new String();
			info=(String)(map.get("property1")+"_"+map.get("property2")+"_"+map.get("property3"));
			return info;
	
	}
	
	

	@ResponseBody
	@RequestMapping("/blockPropertyDisplay")
    public String blockPropertyDisplay(int controlId,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			BaseService service=App.getService("t_block_property");
			HashMap<String, Object> map = service.setData(controlId).where("id=#{controlId}").selectMap();
			String info=new String();
			info=(String)(map.get("property1")+"_"+map.get("property2")+"_"+map.get("property3"));
			return info;
	}*/
	
}

