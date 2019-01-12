/*
 * Copyright 2018-2019 the Justify authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.leadpony.justify.benchmark.everit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

/**
 * Benchmarks for JSON Schema Validator developed by everit.org.
 *
 * @author leadpony
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
public class EveritJsonSchemaValidatorBenchmark {

    @State(Scope.Benchmark)
    public static class ValidationState {

        @Param({ "product.json", "product-invalid.json", "fstab.json", "fstab-invalid.json" })
        private String name;

        private Schema schema;
        private String instance;
        private boolean valid;

        @Setup
        public void setUp() throws IOException {
            this.schema = readSchemaFromResource(getSchemaNameFor(name));
            this.instance = readInstanceFromResource(name);
            this.valid = !name.endsWith("-invalid.json");
        }

        private static String getSchemaNameFor(String name) {
            String[] tokens = name.split("\\.|-", 2);
            return tokens[0] + ".schema.json";
        }

        private Schema readSchemaFromResource(String name) throws IOException {
            try (InputStream in = getClass().getClassLoader().getResourceAsStream(name)) {
                JSONObject object = new JSONObject(new JSONTokener(in));
                return SchemaLoader.load(object);
            }
        }

        private String readInstanceFromResource(String name) throws IOException {
            InputStream in = getClass().getClassLoader().getResourceAsStream(name);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                return reader.lines().collect(Collectors.joining("\n"));
            }
        }
    }

    public JSONObject parseOnly(ValidationState state) {
        return new JSONObject(state.instance);
    }

    @Benchmark
    public void parseAndValidate(ValidationState state) {
        try {
            state.schema.validate(new JSONObject(state.instance));
            assert state.valid;
        } catch (ValidationException  e) {
            assert !state.valid;
        }
    }
}
