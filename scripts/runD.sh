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
	rm ${MERGED}/*
        find ${MERGED}/. -maxdepth 1 -name '*.txt' -delete 

	rm ${MWMERGED}/*
	rm ${FINAL_OUT}/*
}

# N, M, d - variables

N=1000000
M=1000
rm -rf ${OUTPUT_DIR}/DVAR/*
mkdir -p ${OUTPUT_DIR}/DVAR
for d in 2 4 10 20 50 100 1000;
do
        BUFFERSIZE=$((4*N))
        BUFFERSIZE=4000
        inputfile=${INPUT_DIR}/input_${N}.txt
        javaoutfile=${FINAL_OUT}/final_output.txt
        echo "Run ${N} ${BUFFERSIZE}"
        perf stat -d -d -d -r 5 --append -o ${OUTPUT_DIR}/DVAR/outD${d}.txt java Main 2 4 ${BUFFERSIZE} ${N} ${M} ${d} ${inputfile} ${javaoutfile}
        clean
done

