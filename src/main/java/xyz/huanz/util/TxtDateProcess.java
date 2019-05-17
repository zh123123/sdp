package xyz.huanz.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.n3r.idworker.Sid;

import lombok.RequiredArgsConstructor;
import xyz.huanz.config.ConstConfig;
import xyz.huanz.pojo.Training;

/**
 * @Description
 * @author H
 * @date 2019年5月9日
 */

@RequiredArgsConstructor
public class TxtDateProcess {
	
	public static void initTestTxt() throws IOException {
		File file = new File(ConstConfig.ROOT_PATH);
		if(!file.exists())
			file.mkdirs();
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
		for (int i = 0; i < 100; i++) {
			final int index = i;
			cachedThreadPool.execute(new Runnable() {
				public void run() {
					String filePath = ConstConfig.ROOT_PATH + index + ".txt";
					try (Writer writer = new BufferedWriter(new FileWriter(filePath));) {
						long time = new Date().getTime();
						String eno = UUID.randomUUID().toString();
						BigDecimal distance = new BigDecimal(0.0).setScale(2, BigDecimal.ROUND_HALF_UP);
						for (int i = 0; i < 60*60*3.5; i++) {
							time += 1000;
							BigDecimal speed = new BigDecimal(Math.random() * 5 + 1).setScale(2, BigDecimal.ROUND_HALF_UP);
							int heartRate = (int) Math.floor((Math.random() * 60)) + 60;
							distance = distance.add(speed);
							Training training = new Training("", eno, new Date(time), "1", "1", speed, distance, heartRate);
							System.out.print(index + ": " + training.toString(1));
							IOUtils.write(training.toString(1), writer);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			
		}
	}
	public static List<Training> txtToTrainingList(String filePath) throws NumberFormatException, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Training> list = new ArrayList<Training>();
		try (Reader reader = new FileReader(filePath); ){
			LineIterator lineIterator = IOUtils.lineIterator(reader);
			while (lineIterator.hasNext()) {
				//获取一行数据
				String next = lineIterator.nextLine();
				//切割一行数据存入pojo
				String[] split = next.split("[\\s]+");
				Training training = new Training("", split[0], sdf.parse(split[1] + " " + split[2]), split[3], split[4], new BigDecimal(split[5]), new BigDecimal(split[6]), Integer.parseInt(split[7]));
				list.add(training);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	
	public static void main(String[] args) throws IOException, NumberFormatException, ParseException {
		//TxtDateProcess.initTestTxt();
		String path = ConstConfig.ROOT_PATH + "/0.txt";
		List<Training> list = TxtDateProcess.txtToTrainingList(path);
		Training training = list.get(9);
		System.out.println(training.toString());
	}
}
