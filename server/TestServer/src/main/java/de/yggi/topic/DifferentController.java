package de.yggi.topic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DifferentController {
	
	@Autowired
	private TopicService topicService;
	
	@RequestMapping("/test")
	public List<Topic> test() {
		return topicService.getAll();
	}
	

}
