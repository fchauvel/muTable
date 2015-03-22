
# µTable

Copyright (C) 2015 - [Franck CHAUVEL](mailto:franck.chauvel@gmail.com)

![Build Status](https://drone.io/github.com/fchauvel/muTable/status.png)](https://drone.io/github.com/fchauvel/muTable/latest)

µTable is a tiny Java library to ease the manipulation of data
table. Languages like R for instance provides very convenient data
structure, which fasten the assembly small numerical experiments. The
aims of µTable is to bring the same in Java. See the example below:

```java
Table table = storage().fetch("http://samples.com/employee.csv");
for(Row eachRow: table.where(field("isMarried").is(value(false)))) {
	System.out.println(eachRow.getField("name"));
}
```

## Contact

Please report any bugs, issues or feature request using the [issue tracker](https://github.com/fchauvel/trio/issues).
Should you need any further information, feel free to email [me](mailto:franck.chauvel@gmail.com)

