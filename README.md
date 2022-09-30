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
```
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
```
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

mappedBy can only be used for one side of a OneToOne relationship.This means that Team Entity owns this relationship(owning side) and Foreign Key information is also included in the Team table. Thus, foreign key information is not stored in the inverse side (Stadium).

## App Class and Output
```
public class App {

	public static void main(String[] args) {
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TeamManagementUnit");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		TeamRepository repository = new TeamRepositoryImpl(entityManager);
		repository.prepareData();
		
		System.out.println("All Stadiums");
		repository.allStadiums().stream().forEach(System.out::println);
		
		System.out.println("All Teams with Their Stadiums");
		repository.allTeams().stream().forEach(System.out::println);
		
		//Deleting Team(id = 1)and related Stadium => (orphanRemoval = true) 
		repository.deleteTeam(1);
		
		//Updating Team's name which id is 7
		repository.updateTeamName(3,"Test");
		
		//Deleting Stadium(id = 4) and related Team => (orphanRemoval = true)
		repository.deleteStadium(4);
		
		//Updating Stadium's name which id is 8
		repository.updateStadiumName(2, "Test");
		
		System.out.println("All Teams with Their Stadiums After Delete and Update Operations");
		repository.allTeams().stream().forEach(System.out::println);
		
		System.out.println("All Stadiums After Delete and Update Operations");
		repository.allStadiums().stream().forEach(System.out::println);
	}
}
```
```
All Stadiums
Stadium [id=1, name=Etihad Stadium, yearOfOpening=2020, capacity=55097]
Stadium [id=2, name=Old Trafford, yearOfOpening=1910, capacity=74310]
Stadium [id=3, name=Anfield Road, yearOfOpening=1884, capacity=53394]
Stadium [id=4, name=Stamford Bridge, yearOfOpening=1877, capacity=41837]
All Teams with Their Stadiums
Team [id=1, name=Manchester City, nickName=The Sky Blues, shirtColor=Blue and White, championshipsWon=6, stadium=Stadium [id=1, name=Etihad Stadium, yearOfOpening=2020, capacity=55097]]
Team [id=2, name=Liverpool, nickName=The Reds, shirtColor=Red, championshipsWon=19, stadium=Stadium [id=2, name=Old Trafford, yearOfOpening=1910, capacity=74310]]
Team [id=3, name=Manchester United, nickName=The Red Devils , shirtColor=Red and White, championshipsWon=20, stadium=Stadium [id=3, name=Anfield Road, yearOfOpening=1884, capacity=53394]]
Team [id=4, name=Chelsea, nickName=The Blues, shirtColor=White and Blue, championshipsWon=6, stadium=Stadium [id=4, name=Stamford Bridge, yearOfOpening=1877, capacity=41837]]
All Teams with Their Stadiums After Delete and Update Operations
Team [id=2, name=Liverpool, nickName=The Reds, shirtColor=Red, championshipsWon=19, stadium=Stadium [id=2, name=Test, yearOfOpening=1910, capacity=74310]]
Team [id=3, name=Test, nickName=The Red Devils , shirtColor=Red and White, championshipsWon=20, stadium=Stadium [id=3, name=Anfield Road, yearOfOpening=1884, capacity=53394]]
All Stadiums After Delete and Update Operations
Stadium [id=2, name=Test, yearOfOpening=1910, capacity=74310]
Stadium [id=3, name=Anfield Road, yearOfOpening=1884, capacity=53394]
```
# StackOverflowError Due to Using of toString Method

## toString Method of Team Class

```
@Override
	public String toString() {
		return "Team [id=" + id + ", name=" + name + ", nickName=" + nickName + ", shirtColor=" + shirtColor
				+ ", championshipsWon=" + championshipsWon + ", stadium=" + stadium + "]";
	}
```

## toString Method of Stadium Class

```
@Override
	public String toString() {
		return "Stadium [id=" + id + ", name=" + name + ", yearOfOpening=" + yearOfOpening + ", capacity=" + capacity
				+  "]";
	}
```

In the toString methods, there will be a continuous call to each other. The Team object calls the Stadium object, and the Stadium object calls the Team object. In this case, it causes an error(StackOverflowError).

I solved this problem by removing Team object from toString method in Stadium class. 
