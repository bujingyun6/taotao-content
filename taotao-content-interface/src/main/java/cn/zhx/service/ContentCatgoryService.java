package cn.zhx.service;

import java.util.List;

import cn.zhx.pojo.EasyUITreeNode;

public interface ContentCatgoryService {

	List<EasyUITreeNode> getContentCatgoryList(long parentId);
}
