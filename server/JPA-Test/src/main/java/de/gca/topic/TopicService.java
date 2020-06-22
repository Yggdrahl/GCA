package de.gca.topic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {
	
	@Autowired
	private TopicRepository topicRepository;
		
	
	
	
	
	
	public List<Topic> getAll() {
		//return (ArrayList<Topic>) topicRepository.findAll();
		List<Topic> list = new ArrayList<Topic>();
		topicRepository.findAll().forEach(list::add); //Lamda
		
		return list;
	}
	
	public Topic getTopic(int id) {
		
		
		return null;
	}
	
	public Topic getTopic(String name) {
		
		return null;
		
	}
	
	public void add(Topic topic) {
		//this.list.add(topic);
		topicRepository.save(topic);
	}
	

}
