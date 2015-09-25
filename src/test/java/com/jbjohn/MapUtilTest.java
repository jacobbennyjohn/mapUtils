package com.jbjohn;

import com.jbjohn.model.Caster;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

/**
 */
public class MapUtilTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGet() throws Exception {
        MapUtil.get(new HashMap(), "");
    }

    @Test
    public void testSet() throws Exception {
        MapUtil.set(new HashMap(), "", "");
    }

    @Test
    public void testParse() throws Exception {
        MapUtil.parse(new HashMap(), "", Caster.Type.STRING);
    }
}