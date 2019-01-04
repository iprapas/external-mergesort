#!/bin/bash
HOME="/home/jp/Projects/project-abmartin-iprapas-sopapado"
MERGED="${HOME}/temp/merged"
MWMERGED="${HOME}/temp/mwmerged"
FINAL_OUT="${HOME}/temp/final_output"
JAVA_MAIN_DIR="${HOME}/external_mergesort/src/"
INPUT_DIR="${HOME}/inputs"
OUTPUT_DIR="${HOME}/outputs/IO"



cd $JAVA_MAIN_DIR

function clean {
	rm ${HOME}/output_io/*
}

#
#rm -rf ${OUTPUT_DIR}/KVAR/*
#mkdir -p ${OUTPUT_DIR}/KVAR
#N=100000
#BUFFERSIZE=$((4*4096))
#for I in  1 2 3 4;
#do
#for k in 2 4 8 16 30;
#do
#	echo "Run Implementation ${I} with buffersize ${BUFFERSIZE} and ${k} streams with ${N} elements"
#	perf stat -d -d -d -r 5 --append -o ${OUTPUT_DIR}/KVAR/outK${k}_Implementation${I}.txt java Main 1 ${I} ${BUFFERSIZE} ${N} ${k}
#	clean
#done
#done
#
#k=30
#
#rm -rf ${OUTPUT_DIR}/NVAR/*
#mkdir -p ${OUTPUT_DIR}/NVAR
#for I in 1;
#do
#    for N in 1000 10000 100000;
#    do
#	echo "Run Implementation ${I} with buffersize ${BUFFERSIZE} and ${k} streams with ${N} elements"
#	perf stat -d -d -d -r 5 --append -o ${OUTPUT_DIR}/NVAR/out_Implementation${I}_N${N}.txt java Main 1 ${I} ${BUFFERSIZE} ${N} ${k}
#    done
#done
#
#
#k=30
#for I in 2 3 4;
#do
#    for N in 1000 10000 100000 1000000 10000000;
#    do
#	echo "Run Implementation ${I} with buffersize ${BUFFERSIZE} and ${k} streams with ${N} elements"
#	perf stat -d -d -d -r 5 --append -o ${OUTPUT_DIR}/NVAR/out_Implementation${I}_N${N}.txt java Main 1 ${I} ${BUFFERSIZE} ${N} ${k}
#    done
#done
#


k=30
N=10000000
rm -rf ${OUTPUT_DIR}/BVAR/*
mkdir -p ${OUTPUT_DIR}/BVAR
for I in 3 4;
do
    for B in 10 100 1000 10000 100000 1000000 10000000;
    do
	BUFFERSIZE=$((4*B))
	echo "Run Implementation ${I} with buffersize ${BUFFERSIZE} and ${k} streams with ${N} elements"
	perf stat -d -d -d -r 5 --append -o ${OUTPUT_DIR}/BVAR/out_Implementation${I}_BUFFER${BUFFERSIZE}.txt java Main 1 ${I} ${BUFFERSIZE} ${N} ${k}
    done
done
