# Movie Information Validation
## How to run:
- Import/clone the project into IntelliJ or any other IDE.
- Open [POM.xml](pom.xml) and make sure that all the dependencies are downloaded.
- Run [testng.xml](testng.xml) file. (Right click on testng.xml -> Run).
- You can also add a run configuration if you don't want to run the testng.xml file everytime.

## How to modify the program to validate any movie details:
- Go to [framework.properties](src/test/resources/framework.properties) file and modify the movieName.

## Explanation of various classes:

- [ReadProperty](src/test/java/utilities/ReadProperty.java) to read framework.properties file.
- [DriverFactory](src/test/java/utilities/DriverFactory.java) to create/initiate the driver based on the browser name give in [framework.properties](src/test/resources/framework.properties) file.
- [DataStore](src/test/java/utilities/DataStore.java) to store various data during runtime
  1. <b>putData</b> method is used to insert <b>thread specific</b> data into the map.
  2. <b>getData</b> method is used to get <b>thread specific</b> data from the map.
  3. <b>shareData</b> method is used to insert <b>thread independent</b> data into a static map so that the data can be shared throughout the execution.
  4. <b>getSharedData</b> method is used to get <b>thread independent</b> data from the static map.