# Implementation of external mergesort algorithm in java

## Repo Structure

**external_mergesort/src/** source code directory

 `Main.java` - Main class used to run the code
 
 `GenerateFile.java` - Class used to generate a test file

`ReaderStream.java` -  Factory class for different objects of type InStream

`InStream.java` - Abstract class for input streams

` InputStream1.java` - Read Implementation 1

`InputStream2.java` - Read Implementation 2 (default size buffer)

`InputStream3.java` - Read Implementation 3 (variable size buffer)

`InputStream4.java` - Read Implementation 4 (memory map)

`WriterStream.java` - Factory class for different objects of type OutStream

`OutStream.java` - Abstract class for output streams

`OutputStream1.java` - Write Implementation 1

`OutputStream2.java` - Write Implementation 2 (default size buffer)

`OutputStream3.java` - Write Implementation 3 (variable size buffer)

`OutputStream4.java` - Write Implementation 4 (memory map)

`ExternalMergesort.java` - Class implementing the external mergesort algorithm

`MultiwayMerge.java` - Class implementing the d-way merge algorithm

`QueueItem.java` - Class for objects that will be inserted in stream queue

**scripts/** - Shell scripts used to run the tests

`run_IO.sh` - runs the Input/Output tests
`runD.sh` - runs the external mergesort tests with d variable
`runM.sh` - runs the external mergesort tests with M variable
`runN.sh` - runs the external mergesort tests with N variable

**results/** - Results as obtained by the execution of the scripts

`IO/` directory with results of all the Input/Output tests

`DVAR/` directory with external mergesort tests with d variable

`MVAR/` directory with external mergesort tests with M variable

`NVAR/` directory with external mergesort tests with N variable

**temp/** - Intermediate results directory
`merged` - Intermediate(before merge) results directory
`mwmerged` - Intermediate(after merge) results directory
`final_output/` Final results directory

# Master Branch

This branch holds the code, which was used for the development. You can run it directly importing the code into an IDE, by executing the provided jar file or by recompiling and running the Main.java.

Any changes in the parameters should be done inside the code in the Main class (external_mergesort/src/Main.java).

For ease of use, we have created a jar file which you can run alone, without any arguments to produce a simple run of External mergesort.

`java -jar project-abmartin-iprapas-sopapado.jar`

## Benchmark_run Branch

This branch holds the code, with which we run the benchmark tests. It can be run from the command line with arguments. However, it is dependent on paths of the machine that we used for testing and therefore we recommend you don't try to run it without modifying these paths in the code and ensuring that you have all the directories needed in place.


If you want, you can provide the relevant arguments to run the algorithms as explained below.

There is a total of 6 parameters that you can (but not mandatorily - depending on execution) pass into the program.

*<bench 0,1,2,3>*

0 = Generate files, 1 = Input/Output streams testing, 2 = External Mergesort, 3 = In-memory sort

* <IO implementation>*
1 = data input stream, 2 = buffered input stream with default size, 3 = buffered input stream with specified size, 4 = memory mapping

* <buffersize>*
how many bytes to set as buffer

* <N numbers>*
the number of integers to generate and then sort

* <M memory> *
how many 32-bit integers the memory can fit

* <Dway merge> *
the number of streams that can be sorted in one pass

* <K Streams> *
the number of streams to be created (for Read/Write experiments)


## EXAMPLES 

Example of File Generator:
`java -jar project-abmartin-iprapas-sopapado.jar 0`

> 8 input files will be generated under inputs/ folder ranging from 10 to 10m integers

[You will need benchmark branch to run this, as you need to generate test files into "input_io" and "output_io" folders]

Example of Read/Write implementations benchmark with buffered stream (3), of buffer size 4096, for 1000 numbers, 5 streams
`java -jar project-abmartin-iprapas-sopapado.jar 1 3 4096 1000 5`


Example of external mergesort execution with memory mapping, 4096 buffer size, 100000 numbers, 1000 numbers fit in memory, 5 streams can merge in one pass

`java -jar project-abmartin-iprapas-sopapado.jar 2 4 4096 100000 1000 5`


Example of internal sort with buffered stream, 4096 buffer size and 100000 numbers 

`java -jar project-abmartin-iprapas-sopapado.jar 3 3 4096 100000`

