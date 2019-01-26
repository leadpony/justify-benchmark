# Justify Benchmark

This project provides benchmarks based on [Java Microbenchmark Harness (JMH)].

## How to Build

The following tools are required to build and run the benchmarks.

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

## Benchmark Results

## Justify 0.11.0 - 2019-01-27

```bash
# Run complete. Total time: 00:41:55

REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
experiments, perform baseline and negative tests that provide experimental control, make sure
the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
Do not assume the numbers tell you what you want them to tell.

Benchmark                                        (name)   Mode  Cnt       Score      Error  Units
JustifyBenchmark.parseAndValidate          product.json  thrpt   25  139406.510 ± 3037.361  ops/s
JustifyBenchmark.parseAndValidate  product-invalid.json  thrpt   25  136613.763 ± 2140.119  ops/s
JustifyBenchmark.parseAndValidate            fstab.json  thrpt   25   20145.141 ±  215.785  ops/s
JustifyBenchmark.parseAndValidate    fstab-invalid.json  thrpt   25   23716.190 ±  207.411  ops/s
JustifyBenchmark.parseAndValidate        countries.json  thrpt   25      81.769 ±    1.947  ops/s
```

### Justify 0.10.0 - 2019-01-19

```bash
# Run complete. Total time: 00:41:55

REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
experiments, perform baseline and negative tests that provide experimental control, make sure
the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
Do not assume the numbers tell you what you want them to tell.

Benchmark                                        (name)   Mode  Cnt       Score      Error  Units
JustifyBenchmark.parseAndValidate          product.json  thrpt   25  137399.197 ± 2353.472  ops/s
JustifyBenchmark.parseAndValidate  product-invalid.json  thrpt   25  125964.076 ± 3037.078  ops/s
JustifyBenchmark.parseAndValidate            fstab.json  thrpt   25   17933.087 ±  256.719  ops/s
JustifyBenchmark.parseAndValidate    fstab-invalid.json  thrpt   25   22411.869 ±  267.345  ops/s
JustifyBenchmark.parseAndValidate        countries.json  thrpt   25      64.580 ±    0.464  ops/s
```

[Java Microbenchmark Harness (JMH)]: https://openjdk.java.net/projects/code-tools/jmh/
[Apache Maven]: https://maven.apache.org/
