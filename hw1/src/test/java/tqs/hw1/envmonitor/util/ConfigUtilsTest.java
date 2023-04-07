package tqs.hw1.envmonitor.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigUtilsTest {

    @Test
    void whenGetExistingProperty_thenValue() {
        String value = ConfigUtils.getProperty("key");

        assertThat(value).isEqualTo("value");
    }

    @Test
    void whenGetNonExistingProperty_thenNull() {
        String value = ConfigUtils.getProperty("key2");

        assertThat(value).isNull();
    }
}
