#!/bin/bash
HOME="/home/jp/Projects/project-abmartin-iprapas-sopapado"
MERGED="${HOME}/temp/merged"
MWMERGED="${HOME}/temp/mwmerged"
FINAL_OUT="${HOME}/temp/final_output"
JAVA_MAIN_DIR="${HOME}/external_mergesort/src/"
INPUT_DIR="${HOME}/inputs"
OUTPUT_DIR="${HOME}/outputs"

cd $JAVA_MAIN_DIR

function clean {
        find ${MERGED}/. -maxdepth 1 -name '*.txt' -delete 
	rm ${MWMERGED}/*
	rm ${FINAL_OUT}/*
}

# N, M, d - variables

# N=10,000,000, d= 100, M=10, 100, 1.000, 10.000, 100.000, 1.000.000

N=1000000
d=100
rm -rf ${OUTPUT_DIR}/MVAR/*
mkdir -p ${OUTPUT_DIR}/MVAR
for M in 10 50 100 500 1000 10000 100000;
do
	BUFFERSIZE=$((4*N))
        BUFFERSIZE=4000
	inputfile=${INPUT_DIR}/input_${N}.txt
	javaoutfile=${FINAL_OUT}/final_output.txt
	echo "Run ${N} ${BUFFERSIZE}"
	perf stat -d -d -d -r 5 --append -o ${OUTPUT_DIR}/MVAR/outM${M}.txt java Main 2 4 ${BUFFERSIZE} ${N} ${M} ${d} ${inputfile} ${javaoutfile}
	clean
done

