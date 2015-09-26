package com.jbjohn;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbjohn.model.Caster;
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
public class MapUtilParseTest {

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
    public void testParse() throws Exception {
        int expected = 809979;
        MapUtil.parse(map, "$.sports-content.sports-event.event-metadata.@event-key", Caster.Type.INTEGER);
        int result = Integer.parseInt(MapUtil.get(map, "$.sports-content.sports-event.event-metadata.@event-key").toString());
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testParseArrayKey() throws Exception {
        int expected = 129;
        MapUtil.parse(map, "$.sports-content.sports-metadata.sports-content-codes.sports-content-code.[3].@code-key", Caster.Type.INTEGER);
        int result = Integer.parseInt(MapUtil.get(map, "$.sports-content.sports-metadata.sports-content-codes.sports-content-code.[3].@code-key").toString());
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testParseWildCard() throws Exception {
        int expected = 2;
        MapUtil.parse(map, "$.sports-content.sports-event.team.[*].team-stats.@score", Caster.Type.INTEGER);
        int result = Integer.parseInt(MapUtil.get(map, "$.sports-content.sports-event.team.[0].team-stats.@score").toString());
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testParseWildCardMultiple() throws Exception {
        int expected = 2;
        MapUtil.parse(map, "$.sports-content.sports-event.team.[*].[*].[*]", Caster.Type.INTEGER);
        int result = Integer.parseInt(MapUtil.get(map, "$.sports-content.sports-event.team.[0].team-stats.@score").toString());
        Assert.assertEquals(expected, result);
    }
}