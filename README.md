# Justify Benchmark

This project provides benchmarks based on [JMH].

## How to Build

The following tools are required to build and run the examples.

* JDK 8 or higher
* [Apache Maven] 3.6.0 or higher

The commands below build all the benchmarks in this repository.

```bash
$ git clone https://github.com/leadpony/justify-benchmark.git
$ cd justify-benchmark
$ mvn package
```

## How To Run

The command below runs all the benchmarks.

```bash
$ ./run-all.sh
```

[JMH]: https://openjdk.java.net/projects/code-tools/jmh/
[Apache Maven]: https://maven.apache.org/
