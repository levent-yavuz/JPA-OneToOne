### Other JPA Projects 
- JPA-OneToMany (https://github.com/levent-yavuz/JPA-OneToMany)
- JPA-ManyToMany (https://github.com/levent-yavuz/JPA-ManyToMany)
# JPA - OneToOne Bidirectional Relationship

For this relationship type, the default data loading method is EAGER: every time you ask for A, the B will also be retrieved.

OneToMany: LAZY

ManyToOne: EAGER

ManyToMany: LAZY

OneToOne: EAGER

## orphanRemoval
If orphanRemoval=true is specified the disconnected  instance is automatically removed. This is useful for cleaning up dependent objects that should not exist without a reference from an owner object.
To avoid dangling references as a result of orphan removal this feature should only be enabled for fields that hold private non shared dependent objects.

## mappedBy
```java
@Entity
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String nickName;
	private String shirtColor;
	private int championshipsWon;
	
	@OneToOne( cascade = CascadeType.PERSIST, orphanRemoval = true)
	private Stadium stadium;
}
```
```java
@Entity
public class Stadium {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private int yearOfOpening;
	private int capacity;
	
	@OneToOne(mappedBy = "stadium", orphanRemoval = true)
	private Team team; 
}
```

The mappedBy parameter exists on the inverse side of a OneToOne bidirectional relationship.The class with the mappedBy parameter is inverse side.
If mappedBy is not used for one side then there will be 2 unidirectional OneToOne relationships and in this case, both tables will have a foreign key mutually and this would not be desirable.

In this project, the Team Entity owns this relationship(owning side) and Foreign Key information is also included in the Team table. Thus, foreign key information is not stored in the inverse side (Stadium).

## App Class and Output
```java
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
```
```
Teams
Team [id =1, name = Manchester City, Stadium = Etihad Stadium]
Team [id =2, name = Liverpool, Stadium = Old Trafford]
Team [id =3, name = Manchester United, Stadium = Anfield Road]
Team [id =4, name = Chelsea, Stadium = Stamford Bridge]
Stadiums
Stadium [id = 1, name =Etihad Stadium, Team = Manchester City]
Stadium [id = 2, name =Old Trafford, Team = Liverpool]
Stadium [id = 3, name =Anfield Road, Team = Manchester United]
Stadium [id = 4, name =Stamford Bridge, Team = Chelsea]

The team with the specified (ID = 1) is deleting..
The team's name (the specified ID = 3) is updating to 'Test Team'..
The stadium with the specified (ID = 4) is deleting..
The stadium's name (the specified ID = 2) is updating to 'Test Stadium'..

All Teams After Delete and Update Operations
Team [id =2, name = Liverpool, Stadium = Test]
Team [id =3, name = Test, Stadium = Anfield Road]

All Stadiums After Delete and Update Operations
Stadium [id = 2, name =Test, Team = Liverpool]
Stadium [id = 3, name =Anfield Road, Team = Test]
```
