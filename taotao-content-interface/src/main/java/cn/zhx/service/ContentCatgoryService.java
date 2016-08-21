package cn.zhx.service;

import java.util.List;

import cn.zhx.pojo.EasyUITreeNode;
import cn.zhx.pojo.TaotaoResult;

public interface ContentCatgoryService {

	List<EasyUITreeNode> getContentCatgoryList(long parentId);
	
	TaotaoResult createTreeNode(long parentId,String name);
}
