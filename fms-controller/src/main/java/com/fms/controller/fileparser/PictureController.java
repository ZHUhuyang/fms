package com.fms.controller.fileparser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fms.domain.filemanage.NetProperty;
import com.fms.domain.filemanage.Picture;
import com.fms.service.filemanage.NetPropertyService;
import com.fms.service.filemanage.PictureService;
import com.fms.utils.ParamUtil;
import com.handu.apollo.base.Page;
import com.handu.apollo.utils.ExtUtil;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/picture")
public class PictureController {
	@Autowired
	private PictureService pictureService;
	@Autowired
	private NetPropertyService netPropertyService;

	@RequestMapping(value = "testPic", method = RequestMethod.GET)
	public void testPic() {
		System.out.println("test");
	}

	/**
	 * 查询拓扑图列表.
	 * @param params
	 * @return
	 */
	@RequestMapping("getPictureList")
	public Object getPictureList(Map<String, Object> params) {
		return pictureService.getList(params);
	}

	@RequestMapping("delete")
	public void delete(String id) {
		pictureService.delete(Long.parseLong(id));
	}


	/**
	 * 分页查询
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping("page")
	public Object page(@RequestParam Map<String, Object> params, HttpServletRequest request) {
		Page page = ParamUtil.getPager(request);
		return pictureService.page(params, page);
	}


	@ResponseBody
	@RequestMapping("insertData")
	public void insertData(HttpServletRequest request) throws IOException {
		//	System.out.println(jsonData);
		String idStr = request.getParameter("id");
		Long id=null;
		if(null!=idStr){
			id= Long.parseLong(idStr);
		}
		String jsonData = request.getParameter("json");
		String name = request.getParameter("name");

		// 将读取的数据转换为JSONObject
		JSONObject jsonObject = JSONObject.parseObject(jsonData);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String date = df.format(new Date());
		Picture picture;
		if (jsonObject != null) {
			// 取出所有控件读取属性
			if (null == idStr || idStr!="") {
				picture = new Picture();
				picture.setJson(jsonData);
				picture.setName(name);
				picture.setTime(date);
				pictureService.add(picture);
				id = picture.getId();
			} else {
				picture = pictureService.get(id);
				picture.setJson(jsonData);
				picture.setName(name);
				picture.setTime(date);
				pictureService.update(picture);

				JSONArray btnArray = jsonObject.getJSONArray("datas");
				//String datas = jsonObject.getString("datas");
				// 循环获取控件
				for (int i = 0; i < btnArray.size(); i++) {

					JSONObject kongjianObj = btnArray.getJSONObject(i);

					// 获取控件各个属性, 先判断属性是否存在，然后获取
					String className = kongjianObj.getString("_className");// "_className":
					// "Q.Node",

					String refId = "";
					if (kongjianObj.containsKey("_refId")) {
						refId = kongjianObj.getString("_refId");// "_refId": "359"
					}

					JSONObject json = kongjianObj.getJSONObject("json");// "json": {
					// }
					NetProperty netProperty = new NetProperty();

					// 获取json里面的各个属性 ， 先判断属性是否存在，然后获取
					String from = "";
					String to = "";
					if (json.containsKey("from")) {
						from = json.getString("from");
					}
					if (json.containsKey("to")) {
						to = json.getString("to");
					}

					String controlName = "";
					String image = "";
					if (json.containsKey("name")) {
						controlName = json.getString("name");// "name": "光纤交换机",
					}

					if (json.containsKey("image")) {
						image = json.getString("image");// "image":
						// "data/images/server.png"
					}
					String parentId = "";
					if (json.containsKey("parent")) {
						parentId = json.getString("parent");// 获取到{"_ref": 343}
						// 或者
						parentId = json.getJSONObject("parent").getString("_ref");// 获取到343
					}
					String location = "";
			/*	String location_x = "";
				String location_y = "";*/
					if (json.containsKey("location")) {
						location = json.getString("location");
						String styles = "";
				/*String edge_line_dash = "";
				String edge_width = "";
				String edge_color = "";
				String arrow_to = "";*/
						if (json.containsKey("styles")) {
							styles = json.getString("styles");// {"arrow.to":false,"edge.color":"#F00","edge.line.dash":[8,5],"edge.width":3}
						}
						String background = "";
						if (json.containsKey("background")) {
							background = json.getString("background");// "name":
							// "光纤交换机",
						}
						String border = "";
						if (json.containsKey("border")) {
							border = json.getString("border");// "name": "光纤交换机",
						}
						String borderColor = "";
						if (json.containsKey("borderColor")) {
							border = json.getString("borderColor");// "name":
							// "光纤交换机",
						}
						String fontsize = "";
						if (json.containsKey("fontsize")) {
							border = json.getString("fontsize");// "name": "光纤交换机",
						}
						String labelAnchorPosition = "";
						if (json.containsKey("labelAnchorPosition")) {
							border = json.getString("labelAnchorPosition");// "name":
							// "光纤交换机",
						}
						String labelPosition = "";
						if (json.containsKey("labelPosition")) {
							border = json.getString("labelPosition");// "name":
							// "光纤交换机",
						}
//					netProperty.setId(new Date().getTime() + "0001");
						netProperty.setId(id);
						netProperty.setBackground(background);
						netProperty.setStyles(styles);
						netProperty.setImage(image);
						netProperty.setBorder(border);
						netProperty.setBorderColor(borderColor);
						netProperty.setLabelAnchorPosition(labelAnchorPosition);
						netProperty.setLabelPosition(labelPosition);
						netProperty.setLocation(location);
						netProperty.setFontsize(fontsize);
						netProperty.setParentId(parentId);
						netProperty.setPictureId(id);
						netProperty.setControlName(controlName);
						netProperty.setFrom(from);
						netProperty.setTo(to);
						netProperty.setClassname(className);
						netProperty.setRefId(refId);
						netPropertyService.add(netProperty);

					}
				}
			}
		}

	}

	@RequestMapping("showPicture")
	public Object showPicture(Long id) {
		String json = pictureService.queryJson(id);
		return JSONObject.parse(json);
	}


}