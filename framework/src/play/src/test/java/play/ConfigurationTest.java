/*
 * Copyright (C) 2009-2016 Lightbend Inc. <https://www.lightbend.com>
 */
package play;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.MapAssert.entry;

public class ConfigurationTest {

    @Test
    public void beAccessibleAsAMap() {
        assertThat(exampleConfig().asMap()).hasSize(2).includes(
                entry("foo", ImmutableMap.of("bar1", "value1", "bar2", "value2")),
                entry("blah", "value3"));
    }

    @Test
    public void beAccessibleAsAnEntrySet() {
        Set<Map.Entry<String, ConfigValue>> entrySet = exampleConfig().entrySet();
        assertThat(entrySet).hasSize(3);
        List<String> keys = Lists.transform(Lists.newArrayList(entrySet), Map.Entry::getKey);
        assertThat(keys).containsOnly("foo.bar1", "foo.bar2", "blah");
    }

    @Test
    public void makesUnderlyingAccessible() {
        Config underlying
            = ConfigFactory.parseMap(ImmutableMap.of("foo.bar1", "value1"));
        Configuration config = new Configuration(underlying);
        Assert.assertEquals(underlying, config.underlying());
    }

    @Test
    public void stringConstructor() {
        Configuration config = new Configuration("a = 1");
        assertThat(config.getInt("a")).isEqualTo(1);
    }

    public Configuration exampleConfig() {
        return new Configuration(ImmutableMap.of("foo.bar1", "value1",
                "foo.bar2", "value2",
                "blah", "value3"));
    }
}
