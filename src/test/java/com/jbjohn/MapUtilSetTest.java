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
public class MapUtilSetTest {

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
    public void testSet() throws Exception {
        String expected = "Document title";
        MapUtil.set(map, "$.sports-content.sports-metadata.sports-title", expected);
        String result = (String) MapUtil.get(map, "$.sports-content.sports-metadata.sports-title");
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testSetType() throws Exception {
        int expected = 809979;
        MapUtil.set(map, "$.sports-content.sports-event.event-metadata.@event-key", expected);
        int result = Integer.parseInt(MapUtil.get(map, "$.sports-content.sports-event.event-metadata.@event-key").toString());
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testSetPredicate() throws Exception {
        Integer expected = 300;
        MapUtil.set(map, "$.sports-content.sports-metadata.sports-content-codes.sports-content-code.[?@code-type==team].[?@code-key==271]", 300);
        Integer result = Integer.parseInt(MapUtil.get(map, "$.sports-content.sports-metadata.sports-content-codes.sports-content-code.[?@code-type==team].@code-key.[1]").toString());
        Assert.assertEquals(expected, result);
    }
}