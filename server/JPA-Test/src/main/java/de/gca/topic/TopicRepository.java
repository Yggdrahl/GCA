package de.gca.topic;

import org.springframework.data.repository.CrudRepository;

public interface TopicRepository extends CrudRepository<Topic, Integer> {
	//Der Zugriff auf DB's ist fast immer gleich:
	// findAll
	// getById(innt id)
	//upgrade(int id)
	//usw.
	
	//Für die Standartfälle können wir von einem CrudInterface erben, welchen nur noch den Datentyp
	//des Entity und ds PrimaryKey braucht.
	
	//Obwohl das "nur" ein Interface ist, sind die Funktionen ausformuliert
}
