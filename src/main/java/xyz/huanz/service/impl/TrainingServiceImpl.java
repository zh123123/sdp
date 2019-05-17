package xyz.huanz.service.impl;

import java.util.List;

import org.n3r.idworker.Sid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import xyz.huanz.mapper.TrainingMapper;
import xyz.huanz.mapper.TrainingMapperCustom;
import xyz.huanz.pojo.Training;
import xyz.huanz.service.TrainingService;

/**
* @Description 
* @author H
* @date 2019年5月10日 
*/
@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingServiceImpl implements TrainingService {
	
	private final Sid sid;
	
	private final TrainingMapper trainingMapper;

	private final TrainingMapperCustom trainingMapperCustom;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void insertTraining(Training training) {
		if(training.getId() == null || training.getId().equals(""))
			training.setId(sid.nextShort());
		trainingMapper.insertSelective(training);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void insertList(List<Training> list) {
		for (Training training : list) {
			insertTraining(training);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void insertListBatch(List<Training> list) {
		for (Training training : list) {
			training.setId(sid.nextShort());
		}
		trainingMapperCustom.insertListBatch(list);
		
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> selectEnoList() {

		return trainingMapperCustom.selectEnoList();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Training> selectByEno(String eno) {
		Example example = new Example(Training.class);
		example.setOrderByClause("time asc");
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("eno", eno);
		return trainingMapper.selectByExample(example);
	}
}
