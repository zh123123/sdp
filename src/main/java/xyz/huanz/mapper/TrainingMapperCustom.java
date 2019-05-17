package xyz.huanz.mapper;

import java.util.List;

import xyz.huanz.pojo.Training;

/**
* @Description 
* @author H
* @date 2019年5月12日 
*/
public interface TrainingMapperCustom {
	
	void insertListBatch(List<Training> list);
	
	List<String> selectEnoList();
}
