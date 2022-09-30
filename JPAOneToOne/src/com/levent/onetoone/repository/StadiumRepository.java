package com.levent.onetoone.repository;

import java.util.List;
import com.levent.onetoone.entity.Stadium;

public interface StadiumRepository {
	
	void saveStadium(Stadium stadium);
	List<Stadium> getAllStadiums();
	Stadium getStadiumById(int id);
	void updateStadiumName(int id, String name);
	void deleteStadium(int id);
}
