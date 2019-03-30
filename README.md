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

### Justify 0.15.0-SNAPSHOT - 2019-03-30

```
Benchmark                                        (name)   Mode  Cnt       Score      Error  Units
JustifyBenchmark.parseAndValidate          product.json  thrpt   25  173781.305 ± 3918.726  ops/s
JustifyBenchmark.parseAndValidate  product-invalid.json  thrpt   25  152951.641 ± 1092.811  ops/s
JustifyBenchmark.parseAndValidate            fstab.json  thrpt   25   20284.380 ±  256.720  ops/s
JustifyBenchmark.parseAndValidate    fstab-invalid.json  thrpt   25   24359.228 ±  221.039  ops/s
JustifyBenchmark.parseAndValidate        countries.json  thrpt   25     105.037 ±    1.255  ops/s
```

### Justify 0.14.0 - 2019-03-17

```
Benchmark                                        (name)   Mode  Cnt       Score      Error  Units
JustifyBenchmark.parseAndValidate          product.json  thrpt   25  178765.047 ± 5371.388  ops/s
JustifyBenchmark.parseAndValidate  product-invalid.json  thrpt   25  151768.189 ± 2779.978  ops/s
JustifyBenchmark.parseAndValidate            fstab.json  thrpt   25   20793.098 ±  234.020  ops/s
JustifyBenchmark.parseAndValidate    fstab-invalid.json  thrpt   25   24825.132 ±  292.183  ops/s
JustifyBenchmark.parseAndValidate        countries.json  thrpt   25     104.572 ±    1.054  ops/s
```

### Justify 0.14.0-SNAPSHOT - 2019-03-16

```
Benchmark                                        (name)   Mode  Cnt       Score      Error  Units
JustifyBenchmark.parseAndValidate          product.json  thrpt   25  176319.838 ± 2812.802  ops/s
JustifyBenchmark.parseAndValidate  product-invalid.json  thrpt   25  151570.404 ± 5302.539  ops/s
JustifyBenchmark.parseAndValidate            fstab.json  thrpt   25   21117.274 ±  402.102  ops/s
JustifyBenchmark.parseAndValidate    fstab-invalid.json  thrpt   25   23705.672 ±  292.519  ops/s
JustifyBenchmark.parseAndValidate        countries.json  thrpt   25     105.036 ±    1.136  ops/s
```

### Justify 0.13.0 - 2019-02-28

```
Benchmark                                        (name)   Mode  Cnt       Score      Error  Units
JustifyBenchmark.parseAndValidate          product.json  thrpt   25  143191.831 ± 4376.383  ops/s
JustifyBenchmark.parseAndValidate  product-invalid.json  thrpt   25  133888.632 ± 2718.851  ops/s
JustifyBenchmark.parseAndValidate            fstab.json  thrpt   25   18942.186 ±  377.493  ops/s
JustifyBenchmark.parseAndValidate    fstab-invalid.json  thrpt   25   22478.392 ±  423.481  ops/s
JustifyBenchmark.parseAndValidate        countries.json  thrpt   25      80.148 ±    0.520  ops/s
```

### Justify 0.12.0 - 2019-02-09

```
Benchmark                                        (name)   Mode  Cnt       Score      Error  Units
JustifyBenchmark.parseAndValidate          product.json  thrpt   25  117395.923 ± 9984.155  ops/s
JustifyBenchmark.parseAndValidate  product-invalid.json  thrpt   25  129534.518 ± 3788.073  ops/s
JustifyBenchmark.parseAndValidate            fstab.json  thrpt   25   19408.803 ±  628.075  ops/s
JustifyBenchmark.parseAndValidate    fstab-invalid.json  thrpt   25   23242.643 ±  544.392  ops/s
JustifyBenchmark.parseAndValidate        countries.json  thrpt   25      82.310 ±    1.067  ops/s
```

### Justify 0.11.0 - 2019-01-27

```
Benchmark                                        (name)   Mode  Cnt       Score      Error  Units
JustifyBenchmark.parseAndValidate          product.json  thrpt   25  139406.510 ± 3037.361  ops/s
JustifyBenchmark.parseAndValidate  product-invalid.json  thrpt   25  136613.763 ± 2140.119  ops/s
JustifyBenchmark.parseAndValidate            fstab.json  thrpt   25   20145.141 ±  215.785  ops/s
JustifyBenchmark.parseAndValidate    fstab-invalid.json  thrpt   25   23716.190 ±  207.411  ops/s
JustifyBenchmark.parseAndValidate        countries.json  thrpt   25      81.769 ±    1.947  ops/s
```

### Justify 0.10.0 - 2019-01-19

```
Benchmark                                        (name)   Mode  Cnt       Score      Error  Units
JustifyBenchmark.parseAndValidate          product.json  thrpt   25  137399.197 ± 2353.472  ops/s
JustifyBenchmark.parseAndValidate  product-invalid.json  thrpt   25  125964.076 ± 3037.078  ops/s
JustifyBenchmark.parseAndValidate            fstab.json  thrpt   25   17933.087 ±  256.719  ops/s
JustifyBenchmark.parseAndValidate    fstab-invalid.json  thrpt   25   22411.869 ±  267.345  ops/s
JustifyBenchmark.parseAndValidate        countries.json  thrpt   25      64.580 ±    0.464  ops/s
```

[Java Microbenchmark Harness (JMH)]: https://openjdk.java.net/projects/code-tools/jmh/
[Apache Maven]: https://maven.apache.org/
