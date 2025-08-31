Java version: 17.0.14
Maven version: Apache Maven 3.9.9

---Запуск
Для запуска в директории target ввести команду из примеров ниже:
java -jar SigmaBankApplication-1.0-SPABSHOT.jar --stat --sort=salary
java -jar SigmaBankApplication-1.0-SPABSHOT.jar --stat --sort=name --order=asc --output=console
java -jar SigmaBankApplication-1.0-SPABSHOT.jar --stat --sort=name --order=asc --output=file --path=output/outputfile.txt
java -jar SigmaBankApplication-1.0-SPABSHOT.jar --stat --order=asc


---Библиотеки и их зависимости
-Spring boot
    <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter</artifactId>
    </dependency>
-lombok version: 1.18.30
    <dependency>
          <groupId>org.projectlombok</groupId>
          <artifactId>lombok</artifactId>
          <version>1.18.30</version>
          <scope>provided</scope>
    </dependency>
-opencsv version: 5.12.0
    <dependency>
          <groupId>com.opencsv</groupId>
          <artifactId>opencsv</artifactId>
          <version>5.12.0</version>
    </dependency>

---Плагины
-spotless-maven-plugin version 2.43.0
      <groupId>com.diffplug.spotless</groupId>
      <artifactId>spotless-maven-plugin</artifactId>
      <version>2.43.0</version>