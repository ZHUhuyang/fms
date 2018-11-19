package com.fms.domain.filemanage;
import com.handu.apollo.base.TreeVo;
import lombok.Data;

@Data
public class Picture {
	private Long id;
	private String name;
	private String json;
	private int userId;
	private String time;

}
