package hu.szaniszlaid.webdemo.utils;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class MatcherUtils {

    /**
     * isLongEqual compares any {@link Number} object as long
     * */
    public static Matcher<Number> isLongEqual(final long value) {
        return new TypeSafeMatcher<Number>() {

            @Override
            public void describeTo(Description description) {
                description
                        .appendText("isLongEqual should return ")
                        .appendValue(value);
            }

            @Override
            protected boolean matchesSafely(Number item) {
                return item.longValue() == value;
            }

        };
    }
}
