package com.example.benchmark;

import org.openjdk.jmh.annotations.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class StreamBenchmark {

    private List<Integer> numbers;

    @Setup(Level.Trial)
    public void setUp() {
        Random random = new Random();
        numbers = random.ints(10_000_000, 1, 101)
                .boxed()
                .collect(Collectors.toList());
    }

    @Benchmark
    public long sequentialSum() {
        return numbers.stream()
                .mapToLong(Integer::longValue)
                .sum();
    }

    @Benchmark
    public double sequentialAverage() {
        return numbers.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
    }

    @Benchmark
    public double sequentialStandardDeviation() {
        double mean = numbers.stream()
                .mapToDouble(Integer::doubleValue)
                .average()
                .orElse(0.0);
        double variance = numbers.stream()
                .mapToDouble(num -> Math.pow(num - mean, 2))
                .average()
                .orElse(0.0);
        return Math.sqrt(variance);
    }

    @Benchmark
    public List<Integer> sequentialMultiplyByTwo() {
        return numbers.stream()
                .map(num -> num * 2)
                .collect(Collectors.toList());
    }

    @Benchmark
    public List<Integer> sequentialFilter() {
        return numbers.stream()
                .filter(num -> num % 2 == 0 && num % 3 == 0)
                .collect(Collectors.toList());
    }


    @Benchmark
    public long parallelSum() {
        return numbers.parallelStream()
                .mapToLong(Integer::longValue)
                .sum();
    }

    @Benchmark
    public double parallelAverage() {
        return numbers.parallelStream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
    }

    @Benchmark
    public double parallelStandardDeviation() {
        double mean = numbers.parallelStream()
                .mapToDouble(Integer::doubleValue)
                .average()
                .orElse(0.0);
        double variance = numbers.parallelStream()
                .mapToDouble(num -> Math.pow(num - mean, 2))
                .average()
                .orElse(0.0);
        return Math.sqrt(variance);
    }

    @Benchmark
    public List<Integer> parallelMultiplyByTwo() {
        return numbers.parallelStream()
                .map(num -> num * 2)
                .collect(Collectors.toList());
    }

    @Benchmark
    public List<Integer> parallelFilter() {
        return numbers.parallelStream()
                .filter(num -> num % 2 == 0 && num % 3 == 0)
                .collect(Collectors.toList());
    }
}

