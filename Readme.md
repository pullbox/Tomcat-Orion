To execute this Orion from command prompt, you need a Tomcat environment, JDK and Maven

1.  Go to Orion folder and execute

mvn install:install-file -Dfile=takipi-sdk-0.2.0.jar -DgroupId=com.takipi -DartifactId=takipi-sdk -Dversion=0.2.0 -Dpackaging=jar -DgeneratePom=true

2.  Go to the Orion Folder and execute
```
mvn clean compile package
```
3. Deploy the war file from the target directory (Orion-1.0.war) to Tomcat
4. Make sure to add Takipi to the tomcat instance.
	```
	-agentlib:TakipiAgent -Dtakipi.application.name=APPID12345 -Dtakipi.deployment.name=Orion:v3.7,Enceladus:v1.8
	```
5. Go to the root of the Orion Application
    ```
    http://<TomcatHostName>:<PortNumber>/Orion-1.0/
    ```
5. Generate errors in Billing and Common.

Good Luck.