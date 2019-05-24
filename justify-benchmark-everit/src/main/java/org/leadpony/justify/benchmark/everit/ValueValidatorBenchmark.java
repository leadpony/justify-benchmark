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
import org.leadpony.justify.benchmark.common.JsonResource;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

/**
 * @author leadpony
 *
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class ValueValidatorBenchmark {

    @Param({
        "PRODUCT",
        "PRODUCT_INVALID",
        "FSTAB",
        "FSTAB_INVALID",
        "COUNTRIES",
        "SCHEMA"
        })
    private JsonResource resource;

    private Schema schema;
    private Object instance;

    @Setup
    public void setUp() throws IOException {
        schema = readSchemaFromResource();
        instance = readInstanceFromResource();
    }

    private Schema readSchemaFromResource() throws IOException {
        try (InputStream in = resource.openSchemaStream()) {
            JSONObject object = new JSONObject(new JSONTokener(in));
            return SchemaLoader.load(object);
        }
    }

    private Object readInstanceFromResource() {
        String string = resource.getInstanceAsString();
        return resource.isArray()
                ? new JSONArray(string) : new JSONObject(string);
    }

    @Benchmark
    public boolean validateValue() {
        try {
            this.schema.validate(instance);
            assert resource.isValid();
            return true;
        } catch (ValidationException e) {
            assert !resource.isValid();
            return false;
        }
    }
}
