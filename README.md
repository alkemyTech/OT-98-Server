# README

### CODE STANDARDS

We follow the rules
from [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html) and:

- The interfaces should start with prefix "I". Example: IUserRepository.
- The names of the attributes for Java code use camel case, but the name for SQL uses underscore and
  uppercase.
- The name of the tables should be in plural, but the entity name should be in singular.
- Exceptions should be handled by ErrorHandler class. This class should be an implementation of
  ControllerAdvice.
- All the configuration stuffs must go in the config package.
- The name of abstract classes should start with prefix "Abstract". Example: "AbstractFile".
- The integration test should go into the integration package.
- The validation constrains messages must go inside the ValidationMessages class.
- If you add a new endpoint, make sure to set the role access for it in the SecurityConfig class.

The code style for this repository is the used by [Google](https://github.com/google/styleguide).
So, make sure to set up your IDE with the right code style format file.

### KEEP IN MIND FOR PULL REQUEST AND CODE REVIEW

- The name of the branch should be equal to the {ticket#}.
- The rule for the title is: "{ticket#}: {jiraTitle}".
- The commits should be: "{ticket#}: {commitDescription}". Small commits are a nice to have.
- If you don’t add unit test or integration test as part of your code changes, you should add at
  least the request and response as evidence that the code is working as expected. Also, you could
  add any other steps that are needed for execution.
- Once you finish to addressing all the comments, leave a comment on the PR to the reviewer asking
  to re-review the PR.

### SWAGGER DOCUMENTATION

If you want to display the API documentation, run the below command and then go
to: http://localhost:8080/api/docs/swagger-ui/

```
mvn spring-boot:run
```

### USERS SEED

| id  | email				  |  first_name	   | last_name    | 
|-----|-----------------------|----------------| -------------|
| 1   | imontovio@alkemy.com  | 	Ignacio	   | 	Montovio  | 
| 2   | abahi@alkemy.com	  | 	Alexis	   |    Bahi      | 
| 3   | jaman@alkemy.com	  | 	Joaquin	   | 	Aman      | 
| 4   | klugo@alkemy.com	  | 	Kevin	   | 	Lugo      |  
| 5   | lscaceres@alkemy.com  | 	Lucio	   | 	Scaceres  |   
| 6   | mcevini@alkemy.com	  | 	Matias	   | 	Cevini    |   
| 7   | oruina@alkemy.com	  | 	Oscar	   | 	Ruina     |  
| 8   | mkain@alkemy.com	  | 	Magali	   | 	Kain      | 
| 9   | psamid@alkemy.com	  | 	Pablo	   | 	Samid     |  
| 10  | aruiz@alkemy.com	  | 	Alejandro  | 	Ruiz      | 
| 11  | mruiz@alkemy.com	  | 	Mario	   |    Ruiz      |  
| 12  | llopez@alkemy.com	  | 	Lucas	   |  	Lopez     |  
| 13  | stierno@alkemy.com	  | 	Santiago   |	Tierno    |  
| 14  | jtierno@alkemy.com	  | 	Julieta	   |	Tierno    | 
| 15  | dtierno@alkemy.com	  | 	Daniela	   |	Tierno    |   
| 16  | npaez@alkemy.com	  | 	Naiara     |    Paez      |   
| 17  | jpaez@alkemy.com	  | 	Julian	   |	Paez      |  
| 18  | jsantoro@alkemy.com	  | 	Josias	   |	Santoro   | 
| 19  | fsantoro@alkemy.com	  | 	Federico   |	Santoro   | 
| 20  | esantoro@alkemy.com	  | 	Ezequiel   |	Santoro   | 

All the users have "foo12345" as password.
	
### ROLES SEED

| id | 	description	| 	name   |
|----|--------------|----------| 
| 1	 |  ROLE_USER   | 	USER   |
| 2	 | 	ROLE_ADMIN  | 	ADMIN  | 
