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
package org.leadpony.justify.benchmark.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The defined fixtures.
 *
 * @author leadpony
 */
public enum Fixture {
    COUNTRIES("countries.json", "countries.schema.json", true),
    FSTAB("fstab.json", "fstab.schema.json", true),
    FSTAB_INVALID("fstab-invalid.json", "fstab.schema.json", false),
    PRODUCT("product.json", "product.schema.json", true),
    PRODUCT_INVALID("product-invalid.json", "product.schema.json", false),
    SCHEMA("schema.json", "schema.json", true)
    ;

    private final String name;
    private final String schemaName;
    private final boolean valid;

    private static final Map<String, Fixture> fixtures = new HashMap<>();

    static {
        for (Fixture fixture : values()) {
            fixtures.put(fixture.name, fixture);
        }
    }

    private Fixture(String name, String schemaName, boolean valid) {
        this.name = name;
        this.schemaName = schemaName;
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }

    public boolean isArray() {
        switch (this) {
        case COUNTRIES:
            return true;
        default:
            return false;
        }
    }

    public String getInstanceAsString() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(getResourceAsStream(name), StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            return null;
        }
    }

    public InputStream openSchemaStream() {
        return getResourceAsStream(schemaName);
    }

    public static Fixture byName(String name) {
        return fixtures.get(name);
    }

   private InputStream getResourceAsStream(String name) {
        return getClass().getClassLoader().getResourceAsStream(name);
    }
}
