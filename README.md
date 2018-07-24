# LeanixMetricsFailuresPoster
Program to query factsheets from LeanIX, save how many factsheets each filter removed, and that post information to LeanIX metrics. Please be sure to only use Java 8 (1.8) when running the program as LeanIX's Java SDK doesn't work with the latest version of Java as of writing this.  
## How To Use  
To use the program, you will need your Workspace ID and an API Token. Both can be found in the API Tokens section on the Administrator Page. You will need to set these equal to the corresponding variables at the beginning of the main function in LeanixMetricFailures.java. Once that's done, everything should be taken care of and you can run LeanixMetricFailures.java to run the program.

##Testing  
To test the program, you will need to set the API token and Workspace ID equal to their corresponding variables in QueryTests.java and LeanixMetricFailuresTests.java. You can then run the tests.