package nextstep.optional;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Java - Optional
 * https://www.baeldung.com/java-optional
 */
public class OptionalTest {

    @Test
    void create() {
        Optional<String> empty = Optional.empty();
        assertFalse(empty.isPresent());
    }

    @Test
    public void givenNonNull_whenCreatesNonNullable_thenCorrect() {
        String name = "baeldung";
        Optional<String> opt = Optional.of(name);
        assertTrue(opt.isPresent());
    }

    @Test()
    public void givenNull_whenThrowsErrorOnCreate_thenCorrect() {
        String name = null;
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> Optional.of(name));
    }

    @Test
    public void givenNonNull_whenCreatesNullable_thenCorrect() {
        String name = "baeldung";
        Optional<String> opt = Optional.ofNullable(name);
        assertTrue(opt.isPresent());
    }

    @Test
    public void givenOptional_whenIsPresentWorks_thenCorrect() {
        Optional<String> opt = Optional.of("Baeldung");
        assertTrue(opt.isPresent());

        opt = Optional.ofNullable(null);
        assertFalse(opt.isPresent());
    }

    @Test
    public void givenAnEmptyOptional_thenIsEmptyBehavesAsExpected() {
        Optional<String> opt = Optional.of("Baeldung");
        assertFalse(opt::isEmpty);

        opt = Optional.ofNullable(null);
        assertTrue(opt.isEmpty());
    }

    @Test
    public void givenOptional_whenIfPresentWorks_thenCorrect() {
        Optional<String> opt = Optional.of("baeldung");
        opt.ifPresent(System.out::println);
    }

    @Test
    public void whenOrElseWorks_thenCorrect() {
        String nullName = null;
        String name = Optional.ofNullable(nullName).orElse("john");
        assertEquals("john", name);
    }

    @Test
    public void whenOrElseGetAndOrElseOverlap_thenCorrect() {
        String text = null;

        String defaultText =
                Optional.ofNullable(text).orElseGet(this::getMyDefault);
        assertEquals("Default Value", defaultText);

        defaultText = Optional.ofNullable(text).orElse(getMyDefault());
        assertEquals("Default Value", defaultText);
    }

    @Test
    public void whenOrElseGetAndOrElseDiffer_thenCorrect() {
        String text = "Text present";

        System.out.println("Using orElseGet:");
        String defaultText =
                Optional.ofNullable(text).orElseGet(this::getMyDefault);
        assertEquals("Text present", defaultText);

        System.out.println("Using orElse:");
        defaultText = Optional.ofNullable(text).orElse(getMyDefault());
        assertEquals("Text present", defaultText);
    }

    @Test
    public void whenOrElseThrowWorks_thenCorrect() {
        String nullName = null;
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    Optional.ofNullable(nullName)
                            .orElseThrow(IllegalArgumentException::new);
                });
    }

    @Test
    public void whenNoArgOrElseThrowWorks_thenCorrect() {
        String nullName = null;
        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> {
                    Optional.ofNullable(nullName).orElseThrow();
                });
    }

    @Test
    public void givenOptionalWithNull_whenGetThrowsException_thenCorrect() {
        Optional<String> opt = Optional.ofNullable(null);
        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> {
                    String name = opt.get();
                });
    }

    @Test
    public void whenOptionalFilterWorks_thenCorrect() {
        Integer year = 2016;
        Optional<Integer> yearOptional = Optional.of(year);
        boolean is2016 = yearOptional.filter(y -> y == 2016).isPresent();
        assertTrue(is2016);

        boolean is2017 = yearOptional.filter(y -> y == 2017).isPresent();
        assertFalse(is2017);
    }

    @Test
    public void givenOptional_whenMapWorks_thenCorrect() {
        List<String> companyNames = Arrays.asList(
                "paypal", "oracle", "", "microsoft", "", "apple");
        Optional<List<String>> listOptional = Optional.of(companyNames);

        int size = listOptional.map(List::size).orElse(0);
        assertEquals(6, size);
    }

    @Test
    public void givenOptional_whenMapWorks_thenCorrect2() {
        String name = "baeldung";
        Optional<String> nameOptional = Optional.of(name);

        int len = nameOptional.map(String::length)
                .orElse(0);

        assertEquals(8, len);
    }

    @Test
    public void givenOptional_whenMapWorks_thenCorrect3() {
        String name = null;
        Optional<String> nameOptional = Optional.ofNullable(name);

        int len = nameOptional.map(String::length)
                .orElse(0);

        assertEquals(0, len);
    }

    @Test
    public void givenOptional_whenMapWorksWithFilter_thenCorrect() {
        String password = " password ";
        Optional<String> passOpt = Optional.of(password);
        boolean correctPassword = passOpt.filter(
                pass -> pass.equals("password")).isPresent();
        assertFalse(correctPassword);

        correctPassword = passOpt.map(String::trim)
                .filter(pass -> pass.equals("password"))
                .isPresent();
        assertTrue(correctPassword);
    }


    private String getMyDefault() {
        System.out.println("Getting Default Value");
        return "Default Value";
    }
}