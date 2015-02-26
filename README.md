
# µTable

Copyright (C) 2015 - [Franck CHAUVEL](franck.chauvel@gmail.com)

![License](https://img.shields.io/badge/License-LGPLv3-GREEN.svg)
[![Build Status](https://drone.io/github.com/fchauvel/muTable/status.png)](https://drone.io/github.com/fchauvel/muTable/latest)


µTable is a tiny Java library to ease the manipulation of data
table. Languages like R for instance provides very convenient data
structure, which fasten the assembly small numerical experiments. The
aims of µTable is to bring the same in Java. See the example below:

```java
Table table = Table.read("employee.csv");
for(Row eachRow: table.where(field("isMarried").is(value(false)))) {
	System.out.println(eachRow.getField("name"));
}
```




## 1. CONTACT

Please report any bugs, issues or feature request to:

       https://github.com/fchauvel/trio/issues	

