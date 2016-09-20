package cn.zhx.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.zhx.mapper.TbContentMapper;
import cn.zhx.pojo.Ad1Node;
import cn.zhx.pojo.MybatiesPageHelperRusult;
import cn.zhx.pojo.TaotaoResult;
import cn.zhx.pojo.TbContent;
import cn.zhx.pojo.TbContentExample;
import cn.zhx.pojo.TbContentExample.Criteria;
import cn.zhx.service.ContentSerivce;
@Service
public class ContentServiceImpl implements ContentSerivce {
	
	@Autowired
	private TbContentMapper contentMapper;

	@Override
	public MybatiesPageHelperRusult getContentList(long categoryId, int page,
			int rows) {
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		PageHelper.startPage(page, rows);
		List<TbContent> list = contentMapper.selectByExample(example);
		PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(list);
		MybatiesPageHelperRusult result = new MybatiesPageHelperRusult();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public TaotaoResult saveContent(TbContent content) {
		content.setCreated( new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		return TaotaoResult.ok();
	}

	@Override
	public List<Ad1Node> getContentListForPortal(long categoryId) {
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> contentList = contentMapper.selectByExample(example);
		
		List<Ad1Node> result = new ArrayList<Ad1Node>();
		Ad1Node node = null;
		for (TbContent tbContent : contentList) {
			node = new Ad1Node();
			node.setAlt(tbContent.getSubTitle());
			node.setSrc(tbContent.getPic());
			node.setSrcB(tbContent.getPic2());
			node.setHref(tbContent.getUrl());
			result.add(node);
		}
		return result;
	}

}
