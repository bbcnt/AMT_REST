# Other infos not related to the functionning of the program #

In this part, we are going to list a few other things, like individual tests and dependencies used in the project.
## Dependencies ##

There are a few libs we used (use Maven to get them of course). This is an extract of the pom file.

	<dependencies>
		...
    	<dependency>
            <groupId>org.json</groupId>
            <artifactId>json</artifactId>
            <version>20141113</version>
        </dependency>
        <dependency>
            <groupId>com.sun.jersey</groupId>
            <artifactId>jersey-client</artifactId>
            <version>1.18.2</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>joda-time</groupId>
            <artifactId>joda-time</artifactId>
            <version>2.6</version>
        </dependency>
		...
	<dependencies>
		

As you can see, 3 different dependencies, 

[org.json](http://mvnrepository.com/artifact/org.json/json/20141113) a simple JSON parser.

[Jersey Client](http://mvnrepository.com/artifact/com.sun.jersey/jersey-client) A Jersey client to test REST API. In the end, we decided to use a WebServlet instead, but this can still be useful in the future.

[Joda time](http://mvnrepository.com/artifact/joda-time/joda-time) for managing dates in Java.

That's all for this part, both the dependencies that we are actually using, are pretty straighforward and easy to understand.

## Tests ##

A WebServlet has been created to test the working infrastructure. To be able to use it and test the application, once it has been deployed on GlassFish (obviously), one only needs to type in the following URL:

    http://localhost:8080/AMT_REST/test

**/test** will be treated by the servlet, and it will create a few users, organizations, sensors and observations for us. With that, we can then see if the facts are correctly working.

We will now start doing some basic GET tests with [POSTMAN](https://chrome.google.com/webstore/detail/postman-rest-client/fdmmgilgnpjigdojojpjoooidkmcomcm). For example GET on all users :

    
![GET USERS](./images/GET_Users.png "GET USERS")

As you can see, the way to use the API is to access : 

	http://localhost:8080/AMT_REST/api/

And then complete it with the values you want, for instance:

	http://localhost:8080/AMT_REST/api/users
	http://localhost:8080/AMT_REST/api/facts
	http://localhost:8080/AMT_REST/api/users?organizationid=1

For the last example, we are using query params, to be able to make our research more precise. In this case, the content we get is : 

![GET USERS](./images/GET_Users_ORG1.png "GET USERS")

Marie, which is part of Org2, is not shown because we want all users from the organization of id "1", which is Org1. Pretty easy, no? To access the full list of these query params and all the different HTTP methodes available, consult our official API documentation.

Now we will only put the results of our tests so far, using POSTMAN and PHPMyAdmin to verify that our system works.