SQL Builder
===========

A dynamic SQL builder for Java language.

[![Build Status](https://secure.travis-ci.org/jonathanhds/sql-builder.png?branch=master)](http://travis-ci.org/jonathanhds/sql-builder)

Examples
=======
Some usage examples:
Select
-------
```java
QueryBuilder query = new QueryBuilder().select()
                                       .column("s.name")
                                       .column("count(s.impediments) AS total_impediments")
                                       .from()
                                       .table("sprint s")
                                       .groupBy()
                                       .column("s.name")
                                       .having()
                                       .column("total_impediments > 5")
```

The output is:

```sql
SELECT
    s.name,
    count(s.impediments) AS total_impediemnts
FROM
    sprint s
GROUP BY
    s.name
HAVING
    total_impediemnts > 5
```
Delete
-------
```java
DeleteQuery query = new DeleteQuery("account a").addWhere("a.id > 666")
                                                .addWhere("a.creation_date > '2013-01-01'");
```

The output is:

```sql
DELETE
FROM
    account a
WHERE
    a.id > 666
    AND a.creation_date > '2013-01-01'
```
Update
---------
```java
UpdateQuery query = new UpdateQuery("employee e").set("e.salary", "50000")
                                                 .addWhere("e.age > 40")
                                                 .addWhere("e.genre = 'female'");
```

The output is:

```sql
UPDATE
    employee e
SET
    e.salary = '50000'
WHERE
    e.age > 40
    AND e.genre = 'female'
```

Insert
-------
```java
InsertQuery query = new InsertQuery("persons").columns("id", "name", "age")
                                              .values(1, "foo", 30)
                                              .values(2, "bar", 23)
                                              .values(3, "hello", 54)
                                              .values(4, "world", 19);
```

The output is:

```sql
INSERT INTO
    persons (id, name, age)
VALUES
    (1, 'foo', 30),
    (2, 'bar', 23),
    (3, 'hello', 54),
    (4, 'world', 19)
```

Usage
=======
To use this library, add this dependency on your pom.xml:

```xml
<dependency>
    <groupId>com.github.jonathanhds</groupId>
    <artifactId>sql-builder</artifactId>
    <version>1.1</version>
</dependency>
```
