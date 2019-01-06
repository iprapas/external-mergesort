For ease of use, we have created a jar file which you can run alone, without any arguments to produce a simple run of External mergesort.
If wanted, you can provide the relevant arguments to run the algorithms as explained below.

=======================================================================================================

There is a total of 6 parameters that you can (but not mandatorily - depending on execution) pass into the program.

<bench 0,1,2,3>
0 = Generate files
1 = Input/Output streams testing
2 = External Mergesort
3 = In-memory sort

<IO implementation>
1 = data input stream
2 = buffered input stream with default size
3 = buffered input stream with specified size
4 = memory mapping

<buffersize>
how many bytes to set as buffer

<N numbers>
the number of integers to generate and then sort

<M memory> 
how many 32-bit integers the memory can fit

<Dway merge>
the number of streams that can be sorted in one pass

<K Streams>
the number of streams to be created (for Read/Write experiments)


==================== EXAMPLES =========================================================================

Example of File Generator:
java -jar project-abmartin-iprapas-sopapado.jar 0
>> 8 input files will be generated under inputs/ folder ranging from 10 to 10m integers

-------------------------------------------------------------------------------------------------------
[You will need benchmark branch to run this, as you need to generate test files into "input_io" and "output_io" folders]
Example of Read/Write implementations benchmark with buffered stream (3), of buffer size 4096, for 1000 numbers, 5 streams
java -jar project-abmartin-iprapas-sopapado.jar 1 3 4096 1000 5

-------------------------------------------------------------------------------------------------------

Example of external mergesort execution with memory mapping, 4096 buffer size, 100000 numbers, 1000 numbers fit in memory, 5 streams can merge in one pass
java -jar project-abmartin-iprapas-sopapado.jar 2 4 4096 100000 1000 5

-------------------------------------------------------------------------------------------------------

Example of internal sort with buffered stream, 4096 buffer size and 100000 numbers 
java -jar project-abmartin-iprapas-sopapado.jar 3 3 4096 100000

=======================================================================================================