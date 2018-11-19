package com.fms.service.filemanage;

import com.fms.domain.filemanage.NetProperty;
import com.fms.domain.filemanage.Picture;
import com.handu.apollo.data.mybatis.Dao;
import com.handu.apollo.utils.CharPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 控件属性服务实现类.
 *
 * @author drc
 */
@Service
@Transactional
public class NetPropertyService {
	public static final String CLASSNAME = NetPropertyService.class.getName() + CharPool.PERIOD;

	@Autowired
	private Dao dao;

	public void add(NetProperty netProperty){
		dao.insert(CLASSNAME,"add",netProperty);
	}

	public List<NetProperty> getList(Map<String, Object> params) {
		return dao.getList(CLASSNAME, "query", params);
	}

}
