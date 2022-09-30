package com.levent.onetoone.repository;

import java.util.List;
import com.levent.onetoone.entity.Team;

public interface TeamRepository {

	void saveTeam(Team team);
	List<Team> getAllTeams();
	Team getTeamById(int id);
	void updateTeamName(int id, String name);
	void deleteTeam(int id);
	void prepareData();
}
