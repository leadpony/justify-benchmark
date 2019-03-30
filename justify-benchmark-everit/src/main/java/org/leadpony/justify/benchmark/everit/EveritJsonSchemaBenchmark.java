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

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.leadpony.justify.benchmark.common.Fixture;
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
@State(Scope.Benchmark)
public class EveritJsonSchemaBenchmark {

    @Param({
        "product.json",
        "product-invalid.json",
        "fstab.json",
        "fstab-invalid.json",
        "countries.json",
        "schema.json"
        })
    private String name;

    private Fixture fixture;
    private Schema schema;
    private String instance;
    private boolean isArray;

    @Setup
    public void setUp() throws IOException {
        fixture = Fixture.byName(name);
        schema = readSchemaFromResource();
        instance = fixture.getInstanceAsString();
        isArray = fixture.isArray();
    }

    private Schema readSchemaFromResource() throws IOException {
        try (InputStream in = fixture.openSchemaStream()) {
            JSONObject object = new JSONObject(new JSONTokener(in));
            return SchemaLoader.load(object);
        }
    }

    public Object parseOnly() {
        return isArray ? new JSONArray(this.instance) : new JSONObject(this.instance);
    }

    @Benchmark
    public Object parseAndValidate() {
        Object value = isArray ? new JSONArray(this.instance) : new JSONObject(this.instance);
        try {
            this.schema.validate(value);
            assert fixture.isValid();
        } catch (ValidationException e) {
            assert !fixture.isValid();
        }
        return value;
    }
}
