# JVM Basics

## What is JDK?
The **JDK (Java Development Kit)** is the full toolkit used to create Java programs. It includes the compiler (`javac`), the JVM, and other development tools.

## What is JRE?
The **JRE (Java Runtime Environment)** provides what is needed to run Java programs. It includes the JVM and supporting libraries, but not the full set of development tools.

## What is JVM?
The **JVM (Java Virtual Machine)** is the engine that runs Java bytecode. It acts like a virtual computer that understands compiled Java code and executes it on the actual operating system.

## What is Bytecode?
When Java source code is compiled, it is converted into **bytecode** inside `.class` files. Bytecode is not specific to one operating system. That is why the same compiled Java program can run on different systems that have a compatible JVM.

## What does “Write Once, Run Anywhere” mean?
Java source code is compiled into bytecode instead of machine code for only one platform. Because bytecode runs on the JVM, the same Java program can be used on Windows, macOS, or Linux without changing the source code.

In simple words, you write the program once, and as long as the target machine has a JVM, the program can run there. The operating-system-specific part is handled by the JVM implementation, not by rewriting your Java code.

