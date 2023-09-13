package com.dev.cTak.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.dev.cTak.vo.ItemVo;

@Mapper
public interface AlperoMapper {
	List<ItemVo> selectItemList(ItemVo value);
	List<ItemVo> compareList(ItemVo value);
	int insertItems(ItemVo value);
	int insertItemsTemp(ItemVo value);
	int curDataUpdate(ItemVo value);
}
