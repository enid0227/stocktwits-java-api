package com.stocktwitlist.api.helper;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public final class ObjectMappers {
  public static final ObjectMapper getDefaultTestObjectMapper() {
    return JsonMapper.builder()
        .addModule(new JavaTimeModule())
        .addModule(new GuavaModule())
        // hide null value per google json guide
        // https://google.github.io/styleguide/jsoncstyleguide.xml?showone=Empty/Null_Property_Values#Empty/Null_Property_Values
        .serializationInclusion(Include.NON_NULL)
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        // sort for testing consistency
        .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
        .build();
  }
}
