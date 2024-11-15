# Application MutantOrHuman

This project is an API that determines whether a given DNA sequence belongs to a mutant or a human based on the amount of 4-letter sequence that makes up its DNA table (vertical, horizontal, diagonal).

## Installation
(You must have Maven and Java installed)

Follow these steps to install and run the project:

1. Clone the repository: `git clone https://github.com/aleWidom/mutantsorhumans`
2. Navigate to the project directory: `cd your-repo`
3. Build the project using Maven: `mvn clean install`
4. Run the project: `mvn spring-boot:run`
5. Test the API Rest using Postman or another application:
   GET: http://mutantsorhumans.us-east-1.elasticbeanstalk.com/stats
  -Returns stats on the number of mutants and humans.

  POST: http://mutantsorhumans.us-east-1.elasticbeanstalk.com/mutant
  -Submit a DNA sequence to check if it's mutant or human.

## Deployment to  AWS

The project is deployed to Elastic Beanstalk