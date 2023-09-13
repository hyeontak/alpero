package com.dev.cTak.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.cTak.mapper.AlperoMapper;
import com.dev.cTak.vo.ItemVo;

@Service
public class AlperoService {

	private final AlperoMapper alperoMapper;
	
	@Autowired
	public AlperoService(AlperoMapper alperoMapper) {
		this.alperoMapper = alperoMapper;
	}
	
	public List<ItemVo> selectItemList(ItemVo value){
		return alperoMapper.selectItemList(value);
	}
	public List<ItemVo> compareList(ItemVo value){
		return alperoMapper.compareList(value);
	}
	
	public int insertItems(ItemVo value) {
		return alperoMapper.insertItems(value);
	}

	public int insertItemsTemp(ItemVo value) {
		return alperoMapper.insertItemsTemp(value);
	}

	public int curDataUpdate(ItemVo value) {
		return alperoMapper.curDataUpdate(value);
	}
}