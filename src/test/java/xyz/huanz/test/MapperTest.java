package xyz.huanz.test;

import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import xyz.huanz.config.ConstConfig;
import xyz.huanz.mapper.TrainingMapper;
import xyz.huanz.pojo.Training;
import xyz.huanz.service.TrainingService;
import xyz.huanz.util.TxtDateProcess;

/**
* @Description 
* @author H
* @date 2019年5月12日 
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTest {
	@Autowired
	private TrainingService trainingService;
	
	@Test
	public void insertTest() throws NumberFormatException, ParseException {
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		
		for (int i = 0; i < 100; i++) {
			final int index = i;
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					String path = ConstConfig.ROOT_PATH + index + ".txt";		
					try {
						List<Training> list = TxtDateProcess.txtToTrainingList(path);
						trainingService.insertListBatch(list);
					} catch (NumberFormatException | ParseException e) {
						e.printStackTrace();
					}
				}
			});
		}	
	}
	
	@Test
	public void test1() throws NumberFormatException, ParseException {
		String path = ConstConfig.ROOT_PATH + "/0.txt";
		List<Training> list = TxtDateProcess.txtToTrainingList(path);
		long start = System.currentTimeMillis();
		trainingService.insertList(list);
		long end = System.currentTimeMillis();
		System.out.println("花费时间:" + (end - start));
	}
	
	@Test
	public void test2() throws NumberFormatException, ParseException {
		String path = ConstConfig.ROOT_PATH + "/0.txt";
		List<Training> list = TxtDateProcess.txtToTrainingList(path);
		long start = System.currentTimeMillis();
		trainingService.insertListBatch(list);
		long end = System.currentTimeMillis();
		System.out.println("花费时间:" + (end - start));
	}
	
}
