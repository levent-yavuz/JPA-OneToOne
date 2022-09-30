package com.levent.onetoone.repository;

import java.util.List;

import com.levent.onetoone.entity.Stadium;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

/**
 * Implements persistence methods for stadium.
 */
public class StadiumRepositoryImpl implements StadiumRepository{

private EntityManager entityManager;
	
	public StadiumRepositoryImpl(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	 /**
     * Saves the specified stadium to the database.
     *
     * @param stadium The Stadium to save to the database.
     */
	@Override
	public void saveStadium(Stadium stadium) {
		
		entityManager.getTransaction().begin();
		entityManager.persist(stadium);
		entityManager.getTransaction().commit();
	}

	/**
     * Returns a list of all stadiums in the database.
     *
     * @return A list of all stadiums in the database.
     */
	@Override
	public List<Stadium> getAllStadiums() {
		
		List<Stadium> stadiums = entityManager.createQuery("Select s From Stadium s", Stadium.class).getResultList();
		
		return stadiums;
	}

	/**
     * Finds the stadium with the specified ID.
     *
     * @param id The ID of the stadium to find.
     * @return The requested stadium
     */
	@Override
	public Stadium getStadiumById(int id) {
		
		Stadium stadium = entityManager.find(Stadium.class, id);
		
		return stadium;
	}

	/**
     * Updates the stadium with the specified ID. This method updates the stadium's name and all references to its team,
     *
     * @param id The ID of the stadium to update.
     * @param name The name of the stadium's updated new name.
     */
	@Override
	public void updateStadiumName(int id, String name) {
		
		entityManager.getTransaction().begin();
		
		Query updateQuery = entityManager.createQuery("Update Stadium Set name=:p1 where id=:p2", Stadium.class);
		updateQuery.setParameter("p1", name).setParameter("p2", id).executeUpdate();
		
		Stadium stadium = entityManager.find(Stadium.class, id);
		stadium.setName(name);
		
		entityManager.getTransaction().commit();	
	}
	
	/**
     * Deletes the stadium with the specified ID.
     *
     * @param id The ID of the stadium to delete.
     */
	@Override
	public void deleteStadium(int id) {
		
		entityManager.getTransaction().begin();
		entityManager.remove(getStadiumById(id));
		entityManager.getTransaction().commit();
	}
}
