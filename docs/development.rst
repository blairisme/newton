.. _development:

Development
--------

The Newton system can be developed using the [IntelliJ IDE](https://www.jetbrains.com/idea/). The Newton project can be added to IntelliJ either via the command line or using the import wizard. To get started please follow the instructions outlined below.

## Via the Command Line
1. [Download](https://www.jetbrains.com/idea/download/) and install IntelliJ.
2. Run the following commands from a command prompt or terminal.
```
cd <newton repository>
gradlew cleanIdea idea
```
3. Open IntelliJ and select the open option.
4. Select the location of the Newton repository.

## Via the import wizard
1. [Download](https://www.jetbrains.com/idea/download/) and install IntelliJ.
2. Open IntelliJ and select the "Import Project" option.
3. Select the location of the Newton repository.
4. Newtons build system is based on Gradle, so select the Gradle external model option.
5. Select the "Auto-import" option.
6. Deselect the "create separate modules per resource set option"
7. Select finish.
