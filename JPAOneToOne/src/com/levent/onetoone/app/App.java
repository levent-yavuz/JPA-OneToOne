package com.levent.onetoone.app;

import com.levent.onetoone.repository.StadiumRepository;
import com.levent.onetoone.repository.StadiumRepositoryImpl;
import com.levent.onetoone.repository.TeamRepository;
import com.levent.onetoone.repository.TeamRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class App {

	public static void main(String[] args) {
		//Entity manager
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPAOneToOneUnit");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		TeamRepository teamRepository = new TeamRepositoryImpl(entityManager);
		teamRepository.prepareData();
		
		StadiumRepository stadiumRepository = new StadiumRepositoryImpl(entityManager);
		
		System.out.println("Teams");
		teamRepository.getAllTeams().stream().forEach(System.out::println);
		
		System.out.println("Stadiums");
		stadiumRepository.getAllStadiums().stream().forEach(System.out::println);
		
		System.out.println("\nThe team with the specified (ID = 1) is deleting..");
		teamRepository.deleteTeam(1);
		
		System.out.println("The team's name (the specified ID = 3) is updating to 'Test Team'..");
		teamRepository.updateTeamName(3,"Test");
		
		System.out.println("The stadium with the specified (ID = 4) is deleting..");
		stadiumRepository.deleteStadium(4);
		
		System.out.println("The stadium's name (the specified ID = 2) is updating to 'Test Stadium'..");
		stadiumRepository.updateStadiumName(2, "Test");
		
		System.out.println("\nAll Teams After Delete and Update Operations");
		teamRepository.getAllTeams().stream().forEach(System.out::println);
		
		System.out.println("\nAll Stadiums After Delete and Update Operations");
		stadiumRepository.getAllStadiums().stream().forEach(System.out::println);
		
		//Close the entity manager and associated factory
        entityManager.close();
        entityManagerFactory.close();
	}
}
