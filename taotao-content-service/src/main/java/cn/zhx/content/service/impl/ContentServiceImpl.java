package cn.zhx.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.zhx.common.utils.JsonUtils;
import cn.zhx.mapper.TbContentMapper;
import cn.zhx.pojo.Ad1Node;
import cn.zhx.pojo.MybatiesPageHelperRusult;
import cn.zhx.pojo.TaotaoResult;
import cn.zhx.pojo.TbContent;
import cn.zhx.pojo.TbContentExample;
import cn.zhx.pojo.TbContentExample.Criteria;
import cn.zhx.service.ContentSerivce;
import cn.zhx.service.JedisClient;
@Service
public class ContentServiceImpl implements ContentSerivce {
	
	@Autowired
	private TbContentMapper contentMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${REDIS_CONTENT_HASH_KEY}")
	private String contentKey;

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
		try {
			String result = jedisClient.hget(contentKey, categoryId+"");
			if(StringUtils.isNotBlank(result)){
				List<Ad1Node> jsonToList = JsonUtils.jsonToList(result, Ad1Node.class);
				return jsonToList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
		
		try {
			String objectToJson = JsonUtils.objectToJson(result);
			jedisClient.hset(contentKey, categoryId+"", objectToJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
