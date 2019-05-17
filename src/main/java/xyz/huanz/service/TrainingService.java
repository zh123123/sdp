package xyz.huanz.service;

import java.util.List;

import xyz.huanz.pojo.Training;

/**
* @Description 与训练数据相关的业务类
* @author H
* @date 2019年5月10日 
*/

public interface TrainingService {
	
	void insertTraining(Training training);
	
	/**
	 * @Description 将列表添加到数据库中
	 * @param list
	 */
	void insertList(List<Training> list);
	
	/**
	 * @Description 将列表批量添加到数据库中	*推荐使用
	 * @param list
	 */
	void insertListBatch(List<Training> list);
	
	/**
	 * @Description 返回设备号列表
	 * @return
	 */
	List<String> selectEnoList();
	
	/**
	 * @Description 根据设备号查找
	 * @param eno
	 * @return
	 */
	List<Training> selectByEno(String eno);
	
}
