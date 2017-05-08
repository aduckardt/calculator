# Calculator

To build the application run the following command after you clone repository:

_mvn clean install_

To run the application go to the target folder and run, for example:

_java -jar calculator.jar "add(2, 2)"_

The output will be:

_4_

By default the application uses INFO log level. You can also change it by providing a command line argument:

_**--log-level**_

Example:

_java -jar calculator.jar --log-level=DEBUG "add(2, 2)"_

In this case application will start using DEBUG log level. You can check that by looking at the log file called: **calculator.log**

The application supports the following log levels: INFO, DEBUG, ERROR

Other examples of using this application:

| **Command:**                                                                   | **Output:** |
| -------------------------------------------------------------------------------|-------------|
| java -jar calculator.jar "add(1, mult(2, 3))"                                  | 7           |
| java -jar calculator.jar "mult(add(2, 2), div(9, 3))"                          | 12          |
| java -jar calculator.jar "let(a, 5, add(a, a))"                                | 10          |
| java -jar calculator.jar "let(a, 5, let(b, mult(a, 10), add(b, a)))"           | 55          |
| java -jar calculator.jar "let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b))" | 40          |


This application evaluates the value of expression that is one of the following:

* Numbers: integers between Integer.MIN_VALUE and Integer.MAX_VALUE
* Variables: strings of characters, where each character is one of a-z, A-Z
* Arithmetic functions: add, sub, mult, div, each taking two arbitrary expressions as arguments.  In other words, each argument may be any of the expressions on this list.
* A “let” operator for assigning values to variables:
	**_let(variable name, value expression, expression where variable is used)_**
	
As with arithmetic functions, the value expression and the expression where the variable is used may be an arbitrary expression from this list. 

