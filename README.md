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
