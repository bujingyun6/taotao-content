package cn.zhx.content.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zhx.mapper.TbContentCategoryMapper;
import cn.zhx.pojo.EasyUITreeNode;
import cn.zhx.pojo.TbContentCategory;
import cn.zhx.pojo.TbContentCategoryExample;
import cn.zhx.pojo.TbContentCategoryExample.Criteria;
import cn.zhx.service.ContentCatgoryService;

@Service
public class ContentCatgoryServiceImpl implements ContentCatgoryService{

	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;
	
	@Override
	public List<EasyUITreeNode> getContentCatgoryList(long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		
		List<TbContentCategory> selectByExample = tbContentCategoryMapper.selectByExample(example);
		List<EasyUITreeNode> result = new ArrayList<EasyUITreeNode>();
		EasyUITreeNode easyUITreeNode = null;
		for (TbContentCategory tbContentCategory : selectByExample) {
			easyUITreeNode = new EasyUITreeNode();
			easyUITreeNode.setId(tbContentCategory.getId());
			easyUITreeNode.setText(tbContentCategory.getName());
			easyUITreeNode.setState(tbContentCategory.getIsParent()?"closed":"open");
			result.add(easyUITreeNode);
		}
		return result;
	}

}
