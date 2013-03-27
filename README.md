SQL Builder
===========

A dynamic SQL builder for Java language.

[![Build Status](https://secure.travis-ci.org/jonathanhds/sql-builder.png?branch=master)](http://travis-ci.org/jonathanhds/sql-builder)

Examples
--------

To build a SELECT query, create a SelectBuilder object like this:

```java
SelectBuilder select = new SelectBuilder();
select.addColumns("p.name", "p.lastname", "p.birthday");
      .addFrom("persons p");
      .addWhere("p.genre = 'male'");
      
System.out.println(select.toString());
```

The output is:

```sql
SELECT p.name, p.lastname, p.birthday FROM persons p WHERE p.genre = 'male'
```
