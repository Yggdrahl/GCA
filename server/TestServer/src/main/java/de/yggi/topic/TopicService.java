package de.yggi.topic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TopicService {
	
	private List<Topic> list = new ArrayList<>(Arrays.asList(
			new Topic("Säugetiere", 1),
			new Topic("Vögel", 2),
			new Topic("Meeresbewoner", 3),
			new Topic("Autos", 4),
			new Topic("Länder", 5),
			new Topic("Historie", 6)
			));
	
	
	
	public List<Topic> getAll() {
		return this.list;
	}
	
	public Topic getTopic(int id) {
		
		if(id >= this.list.size()) {
			return null;
		}
		
		return this.list.get(id);
	}
	
	public Topic getTopic(String name) {
		
		Topic result = null;
		
		for(int i = 0; i < this.list.size(); i++) {
			if(this.list.get(i).getName().equals(name)) {
				result = this.list.get(i);
			}
		}
		
		
		return result;
		
	}
	
	public void add(Topic topic) {
		this.list.add(topic);
	}
	

}
