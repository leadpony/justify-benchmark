#!/bin/bash

bench_cmd="java -jar target/benchmarks.jar -f 3 -wi 3 -i 3"

for dir in justify-benchmark-*
do
    (cd $dir && $bench_cmd)
done
