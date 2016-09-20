package cn.zhx.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zhx.mapper.TbContentCategoryMapper;
import cn.zhx.pojo.EasyUITreeNode;
import cn.zhx.pojo.TaotaoResult;
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

	

	

	@Override
	public TaotaoResult createTreeNode(long parentId, String name) {
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setParentId(parentId);
		contentCategory.setName(name);
		contentCategory.setCreated(new Date());
		//新建的节点都是叶子节点，所以是false
		contentCategory.setIsParent(false);
		 //默认的排序是1
		contentCategory.setSortOrder(1);
		//可选值:1(正常),2(删除)
		contentCategory.setStatus(1);
		tbContentCategoryMapper.insert(contentCategory);
		
		TbContentCategory parentNode = tbContentCategoryMapper.selectByPrimaryKey(parentId);
		//如果当前节点的父节点添加之前是叶子节点,现在有了子节点，所以要改成父节点
		if (!parentNode.getIsParent()){
			parentNode.setIsParent(true);
			tbContentCategoryMapper.updateByPrimaryKey(parentNode);
		}
		return TaotaoResult.ok(contentCategory);
	}

	/* (non-Javadoc)修改结点类型
	 * @see cn.zhx.service.ContentCatgoryService#update(long, java.lang.String)
	 */
	@Override
	public TaotaoResult update(long id, String text) {
		TbContentCategory category = tbContentCategoryMapper.selectByPrimaryKey(id);
		category.setUpdated(new Date());
		category.setName(text);
		tbContentCategoryMapper.updateByPrimaryKey(category);
		return TaotaoResult.ok();
	}





	@Override
	public TaotaoResult del(long id) {
		tbContentCategoryMapper.deleteByPrimaryKey(id);
		return TaotaoResult.ok();
	}

}
