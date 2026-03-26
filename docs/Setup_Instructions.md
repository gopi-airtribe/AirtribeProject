# Setup Instructions

## JDK Version Used
This project is configured with **JDK 17** in `pom.xml`.

## Basic Setup Steps
1. Install JDK 17.
2. Verify installation:
   ```bash
   java -version
   javac -version
   ```
3. Open the project in IntelliJ IDEA or any Java IDE.
4. Let Maven import the project dependencies.
5. Compile the project:
   ```bash
   mvn clean compile
   ```
6. Run the console application:
   ```bash
   java -cp target/classes com.airtribe.learntrack.Main
   ```

## Hello World Run Explanation
A basic Java program contains a `main` method. When you run it:
- the Java compiler (`javac`) converts `.java` source code into `.class` files
- those `.class` files contain **bytecode**
- the JVM reads the bytecode and executes it on your machine

Example Hello World source:
```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

Compile and run:
```bash
javac HelloWorld.java
java HelloWorld
```

Expected output:
```text
Hello, World!
```

> Note: This repository is text-based, so a brief explanation is included here in place of screenshots.

