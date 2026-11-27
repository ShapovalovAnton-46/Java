Lab10 — Reflection, Log4j 2 and localization

Requirements:
- JDK 17 or newer
- Maven

Open the project as a Maven project in IntelliJ IDEA.

Important for Task 1:
Modern Java versions encapsulate private fields in java.lang.String.
To allow the educational reflection demonstration, add this VM option
in Run -> Edit Configurations -> VM options:

--add-opens java.base/java.lang=ALL-UNNAMED

Then run Main.

Menu:
1-9: functions from Lab5
10: choose Ukrainian or English interface
11: reflection demonstration
0: exit

Log levels:
- DEBUG and INFO are written to logs/application.log
- WARN and ERROR are written to console and logs/application.log

The reflection task intentionally changes the internal contents of String.
This is for demonstration only; String objects are immutable by Java's
language contract and should never be modified this way in real applications.

Replacement strings must have exactly the same length as the original.
