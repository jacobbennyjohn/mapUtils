package com.jbjohn;

import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.Assert;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.HashMap;

/**
 */
public class MapUtilGetTest {

    String json;
    HashMap map;

    @Before
    public void setUp() throws Exception {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("xml/test.json");
        StringWriter writer = new StringWriter();
        IOUtils.copy(stream, writer, String.valueOf(Charset.defaultCharset()));
        json = writer.toString();
        map = new ObjectMapper().readValue(json, HashMap.class);
    }

    @Test
    public void testGet() throws Exception {
        String expected = "event-stats";
        String result = (String) MapUtil.get(map, "$.sports-content.sports-metadata.@fixture-key");
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testGetPredicate() throws Exception {
        String expected = "129";
        String result = (String) MapUtil.get(map, "$.sports-content.sports-metadata.sports-content-codes.sports-content-code.[?@code-type==tournament].@code-key.[0]");
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testGetArrayKey() throws Exception {
        String expected = "129";
        String result = (String) MapUtil.get(map, "$.sports-content.sports-metadata.sports-content-codes.sports-content-code.[3].@code-key");
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testGetWildCard() throws Exception {
        String expected = "129";
        String result = (String) MapUtil.get(map, "$.sports-content.sports-metadata.sports-content-codes.sports-content-code.[*].@code-key.[3]");
        Assert.assertEquals(expected, result);
    }
}