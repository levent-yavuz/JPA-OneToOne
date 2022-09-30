package com.levent.onetoone.repository;

import java.util.List;

import com.levent.onetoone.entity.Stadium;
import com.levent.onetoone.entity.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

/**
 * Implements persistence methods for team.
 */
public class TeamRepositoryImpl implements TeamRepository{

	private EntityManager entityManager;
	
	public TeamRepositoryImpl(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	 /**
     * Saves the specified team to the database.
     *
     * @param team The Team to save to the database.
     */
	@Override
	public void saveTeam(Team team) {
		
		entityManager.getTransaction().begin();
		entityManager.persist(team);
		entityManager.getTransaction().commit();
	}

	/**
     * Returns a list of all teams in the database.
     *
     * @return A list of all teams in the database.
     */
	@Override
	public List<Team> getAllTeams() {
		
		List<Team> teams = entityManager.createQuery("Select t From Team t", Team.class).getResultList();
		
		return teams;
	}
	
	/**
     * Finds the team with the specified ID.
     *
     * @param id The ID of the team to find.
     * @return The requested team
     */
	@Override
	public Team getTeamById(int id) {

		Team team = entityManager.find(Team.class, id);
		
		return team;
	}
	
	/**
     * Updates the team with the specified ID. This method updates the team's name and all references to its stadium,
     *
     * @param id The ID of the team to update.
     * @param name The name of the team's updated new name.
     */
	@Override
	public void updateTeamName(int id, String name) {
		
		entityManager.getTransaction().begin();
		Query updateNameQuery = entityManager.createQuery("Update Team Set name=:p1 where id=:p2", Team.class);
		updateNameQuery.setParameter("p1", name).setParameter("p2", id).executeUpdate();
		
		Team team = entityManager.find(Team.class, id);
		team.setName(name);
		
		entityManager.getTransaction().commit();
	}
	
	/**
     * Deletes the team with the specified ID.
     *
     * @param id The ID of the team to delete.
     */
	@Override
	public void deleteTeam(int id) {
		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.remove(getTeamById(id));
		transaction.commit();
	}

	public void prepareData() {
		
		Team mCity = new Team("Manchester City", "The Sky Blues", "Blue and White", 6);
		Team mUtd = new Team("Liverpool", "The Reds", "Red", 19);
		Team lpool = new Team("Manchester United", "The Red Devils ", "Red and White", 20);
		Team che = new Team("Chelsea", "The Blues", "White and Blue", 6);
		
		Stadium etihad = new Stadium("Etihad Stadium", 2020 , 55097, mCity);
		Stadium oldTrafford = new Stadium("Old Trafford", 1910, 74310, mUtd);
		Stadium anfieldRoad = new Stadium("Anfield Road", 1884, 53394, lpool);
		Stadium stamfordBridge = new Stadium("Stamford Bridge", 1877, 41837, che);
		
		mCity.setStadium(etihad);
		mUtd.setStadium(oldTrafford);
		lpool.setStadium(anfieldRoad);
		che.setStadium(stamfordBridge);
		
		saveTeam(mCity);
		saveTeam(mUtd);
		saveTeam(lpool);
		saveTeam(che);
	}
}
