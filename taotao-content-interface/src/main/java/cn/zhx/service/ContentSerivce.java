package cn.zhx.service;

import java.util.List;

import cn.zhx.pojo.Ad1Node;
import cn.zhx.pojo.MybatiesPageHelperRusult;
import cn.zhx.pojo.TaotaoResult;
import cn.zhx.pojo.TbContent;

public interface ContentSerivce {

	MybatiesPageHelperRusult getContentList(long categoryId ,int page,int rows);
	TaotaoResult saveContent(TbContent content);
	List<Ad1Node> getContentListForPortal(long categoryId);
}
