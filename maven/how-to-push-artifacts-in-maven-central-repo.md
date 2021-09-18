## How to push artifacts in maven central repo

### Requesting Access to Maven Central
You need to register an account over at [Sonatype JIRA](https://issues.sonatype.org/secure/Dashboard.jspa) and create a [new issue](https://issues.sonatype.org/secure/CreateIssue.jspa?issuetype=21&pid=10134) for this.

Feel free to use the issue I opened as a [template](https://issues.sonatype.org/browse/OSSRH-73233), but make sure to fill-in all the required fields with your data.

### Step 01 Add required tags inside *(&lt;project&gt;)* your pom.xml

#### Project Name, Description and URL
For some human readable information about your project and a pointer to your project website for more, we require the presence of `name`, `description` and `url`.
```
<name>Jaxer core library</name>
<description>Simple and light weight library to overcome complex task</description>
<url>https://github.com/jaxer-in/jaxer-core</url>
```

#### Supply Javadoc and Sources
```
<groupId>io.github.jaxer-in</groupId>
<artifactId>jaxer-core</artifactId>
<version>0.0.3</version>
<packaging>jar</packaging>
```



#### License Information
```
<licenses>
	<license>
		<name>GNU General Public License v3.0</name>
		<url>https://github.com/jaxer-in/jaxer-core/raw/main/LICENSE</url>
	</license>
</licenses>
```
[More you can find here](https://central.sonatype.org/publish/requirements/#license-information)



#### Distribution Informations
```
<distributionManagement>
	<snapshotRepository>
		<id>ossrh</id>
		<url>https://s01.oss.sonatype.org/content/repositories/snapshots</url>
	</snapshotRepository>
	<repository>
		<id>ossrh</id>
		<url>https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/</url>
	</repository>
</distributionManagement>
```



#### Developer Informations
```
<developers>
	<developer>
		<name>Shakir Ansari</name>
		<email>ershakiransari@outlook.com</email>
	</developer>
</developers>
```


#### SCM Information
The connection to your source control system is another required element. The syntax used depends on the version control system used. Connection details the read only connection, while developerConnection details read and write access connection details.
The url contains the URL for a web front end to your SCM system.
```
<scm>
	<connection>scm:git:git://github.com:jaxer-in/jaxer-core.git</connection>
	<developerConnection>scm:git:ssh://github.com:jaxer-in/jaxer-core.git</developerConnection>
	<url>https://github.com/jaxer-in/jaxer-core.git/tree/main</url>
</scm>
```
[More you can find here](https://central.sonatype.org/publish/requirements/#scm-information)



### Step 02 - Plugins for
Add below listes pluging inside &lt; build &gt; inside &lt; plugins &gt;

1) maven-deploy-plugin
```
<plugin>
	<artifactId>maven-deploy-plugin</artifactId>
	<version>2.8.2</version>
	<executions>
		<execution>
			<id>default-deploy</id>
			<phase>deploy</phase>
			<goals>
				<goal>deploy</goal>
			</goals>
		</execution>
	</executions>
</plugin>
```
2) maven-source-plugin
```
<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-source-plugin</artifactId>
	<version>3.2.1</version>
	<executions>
		<execution>
			<id>attach-sources</id>
			<goals>
				<goal>jar-no-fork</goal>
			</goals>
		</execution>
	</executions>
</plugin>
```

3) maven-javadoc-plugin
```
<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-javadoc-plugin</artifactId>
	<version>3.2.0</version>
	<executions>
		<execution>
			<id>attach-javadocs</id>
			<goals>
				<goal>jar</goal>
			</goals>
		</execution>
	</executions>
</plugin>
```

4) maven-surefire-plugin
```
<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-surefire-plugin</artifactId>
	<version>2.22.2</version>
</plugin>
```

5) nexus-staging-maven-plugin
```
<plugin>
	<groupId>org.sonatype.plugins</groupId>
	<artifactId>nexus-staging-maven-plugin</artifactId>
	<version>1.6.7</version>
	<extensions>true</extensions>
	<configuration>
		<serverId>ossrh</serverId>
		<nexusUrl>https://s01.oss.sonatype.org/</nexusUrl>
		<autoReleaseAfterClose>true</autoReleaseAfterClose>
	</configuration>
</plugin>
```

6) maven-release-plugin

You'll learn about ${gpg.passphrase} in following step
```
<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-release-plugin</artifactId>
	<version>2.5.3</version>
	<configuration>
		<localCheckout>true</localCheckout>
		<pushChanges>false</pushChanges>
		<mavenExecutorId>forked-path</mavenExecutorId>
		<arguments>-Dgpg.passphrase=${gpg.passphrase}</arguments>
	</configuration>
	<dependencies>
		<dependency>
			<groupId>org.apache.maven.scm</groupId>
			<artifactId>maven-scm-provider-gitexe</artifactId>
			<version>1.9.5</version>
		</dependency>
	</dependencies>
</plugin>
```

### Step 02 - Profile with gpg plugin
You need to sign your code with gpg generated keys

```
<profile>
	<id>ci-cd</id>
	<build>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-gpg-plugin</artifactId>
				<version>1.6</version>
				<executions>
					<execution>
						<id>sign-artifacts</id>
						<phase>verify</phase>
						<goals>
							<goal>sign</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
		</plugins>
	</build>
</profile>
```


In case if I missed anything ot there is any typo kindly refer the below link

[https://central.sonatype.org/publish/requirements/#sufficient-metadata](https://central.sonatype.org/publish/requirements/#sufficient-metadata)

