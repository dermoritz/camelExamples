## camelExamples

Code related to a "dialogue" "Enterprise integration with Apache Camel". 

### build

```
mvn clean package
```

### prerequisites

the program relies on a oracle database: ```jdbc:oracle:thin:camel/camel@localhost:1521:xe``` with one table with 4 columns, all varchar2(100): UUID (primary key), COL0, COL1, COL2, COL3

### run

Uncompress ...distribution.zip and run

```
java -jar camelExamples-<version>.jar <source> <folder>
```

The programm expects a specific file type in source. One example is contained in the sources see src/test/resources. The program will change some values in data from source and will save the date in target. This data is then written to data base.



