package de.yggi.topic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopicController {
	
	@Autowired
	private TopicService topicService; //Es wird nach dem Objekt "TopicService" gesucht und hier ein SIngelton davon erzeugt
	
		
	@RequestMapping("/topics")
	public List<Topic> sayHello() {
		return topicService.getAll(); 
	}
	
	@RequestMapping("/topic/id")
	public @ResponseBody Topic getTopic(@RequestParam("id") int idVar) //id -> topic/id?id=0
	{
		return (Topic) topicService.getTopic(idVar);
	}
	
	@RequestMapping("/topics/{name}")
	public Topic getTopic(@PathVariable("name") String name) {
		return topicService.getTopic(name);
	}
		
	@RequestMapping(method = RequestMethod.POST, value = "/topics")
	public void insertTopic(@RequestBody Topic topic) {
		topicService.add(topic);
	}
	
	
}
