<!-- setear para que la cmd use el path a jdk 8 -->
set JAVA_HOME="C:\Program Files\Eclipse Adoptium\jdk-8.0.472.8-hotspot"
set PATH=%JAVA_HOME%\bin;%PATH%

<!-- ejecutar robocode desde la consola usando gradlew  -->
.\gradlew build
cd .sandbox
.\robocode.bat