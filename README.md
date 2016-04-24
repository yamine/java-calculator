# java-calculator

This program calculates arithmetic expressions based on multiplication, addition or division.
#### Command arguments:
**Command:**  <command> [-log_level [debug|info|warn|error]]*

- The <command>: could be “let(…)”, “add(…)”, “div(…)” and “mult(…)”. Each cof these  expressions can have sub-expressions.
- -log-level: the logging level to be set. The log level can be modified through the command line. If not provided, the default logging level is ERROR.

The logging is done through the console. One can add other appenders in the file log4j2.xml located under resource directory.

This is a java program. Therefore it can be run directly through "java" program or through the provided shell script file calc.sh.

#### Example of inputs/outputs:

| INPUT  | OUTPUT |
| ------ | ------ |
|"add(1, 2)" |3 |
|"add(1, mult(2, 3))"|7|
|"mult(add(2, 2), div(9, 3))"|12|
|"let(a, 5, add(a, a))"|10|
|"let(a, 5, let(b, mult(a, 10), add(b, a)))"|55|
|"let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b))"|40|


**Note:** you will need to have the following jar files in the class path: log4j-api-2.5.jar and log4j-core-2.5.jar.