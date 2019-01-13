#!/bin/bash

bench_cmd="java -jar target/benchmarks.jar -f 3 -wi 3 -i 3"

(cd justify-benchmark-everit && $bench_cmd)
(cd justify-benchmark-justify && $bench_cmd)
