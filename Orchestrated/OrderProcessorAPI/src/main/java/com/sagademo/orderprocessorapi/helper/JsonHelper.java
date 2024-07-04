package com.sagademo.orderprocessorapi.helper;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

public final class JsonHelper {

    private static final Jsonb jsonb = JsonbBuilder.create();

    public static String toJson(Object object) {
        return jsonb.toJson(object);
    }

}
