package xyz.huanz.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import xyz.huanz.config.ConstConfig;
import xyz.huanz.pojo.Training;
import xyz.huanz.pojo.common.JSONResult;
import xyz.huanz.service.TrainingService;
import xyz.huanz.util.TxtDateProcess;

/**
* @Description 
* @author H
* @date 2019年5月10日 
*/

@Controller
@RequestMapping("/sdp")
@RequiredArgsConstructor
@Slf4j
public class TxtDataProcessController {
	
	private final TrainingService trainingService;
	
	@RequestMapping("/index")
	public String index() {
		
		return "index";
	}
	
	@PostMapping("/txtprocess/getEnoList")
	@ResponseBody
	public JSONResult getEnoList() {
		List<String> list = trainingService.selectEnoList();
		System.out.println(list);
		return JSONResult.ok(list);
	}
	
	@PostMapping("/txtprocess/getCheckedList")
	@ResponseBody
	public JSONResult getCheckedList(String enos) {
		System.out.println(enos);
		String[] split = enos.split(",");
		List<List> list = new ArrayList<>(); 
		for (String eno : split) {
			List<Training> data = trainingService.selectByEno(eno);
			list.add(data);
		}
		return JSONResult.ok(list);
	}
	
	@PostMapping("/txtprocess/txtToDb")
	@ResponseBody
	public JSONResult txtToDb(@RequestParam("file") MultipartFile file) {
		
		if(file == null || file.isEmpty()) {
			return JSONResult.errorMsg("文件为空！");
		}
		
		//将file上传至服务器中
		String uploadPath = ConstConfig.ROOT_PATH_UPLOAD;
		String filePath = uploadPath + file.getOriginalFilename();
		File outFile = new File(filePath);
		try (FileOutputStream fileOutputStream = new FileOutputStream(outFile);InputStream inputStream = file.getInputStream();){
			// 创建父文件夹
			if(outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
				outFile.mkdirs();
			}
			IOUtils.copy(inputStream, fileOutputStream);
			fileOutputStream.flush();
		} catch (IOException e) {
			JSONResult.errorMsg("上传出错！");
		}
		//将内容存入数据库
		try {
			List<Training> list = TxtDateProcess.txtToTrainingList(filePath);
			trainingService.insertListBatch(list);
		} catch (NumberFormatException | ParseException e) {
			e.printStackTrace();
		}
		return JSONResult.ok();
	}
	
}
