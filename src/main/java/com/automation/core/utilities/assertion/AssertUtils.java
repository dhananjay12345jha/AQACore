package com.automation.core.utilities.assertion;

import com.automation.core.utilities.string.StrUtils;
import org.junit.Assert;
import org.junit.ComparisonFailure;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AssertUtils
{
	public static void assertListEquals(final String failMessage, final List expected, final List actual)
	{
		Assert.assertArrayEquals(failMessage, expected.toArray(), actual.toArray());
	}

	public static void assertListEqualsIgnoreOrder(final String failMessage, final List expected, final List actual)
	{
		final List notInA = new ArrayList<>();
		final List notInB = new ArrayList<>();

		for (final Object x : expected)
		{
			if (!actual.contains(x))
			{
				notInB.add(x);
			}
		}

		for (final Object x : actual)
		{
			if (!expected.contains(x))
			{
				notInA.add(x);
			}
		}

		if (!notInA.isEmpty() || !notInB.isEmpty())
		{
			final String message = String.format("%s" +
												 "\nExpected elements that are not present: \n%s, " +
												 "\nUnexpected elements that are present: \n%s",
												 failMessage,
												 StrUtils.prettyList(notInB),
												 StrUtils.prettyList(notInA));
			Assert.fail(message);
		}

	}

	public static void assertArrayEqualsIgnoreOrder(final String failMessage, final Object[] expected, final Object[] actual)
	{
		assertListEquals(failMessage, Arrays.asList(expected), Arrays.asList(actual));
	}

	public static void assertEqualsIgnoreCase(final String errorMessage, final String expected, final String actual)
	{
		if (!expected.equalsIgnoreCase(actual))
		{
			throw new ComparisonFailure(errorMessage, expected, actual);
		}
	}

	public static void assertEqualsIgnoreCase(final String expected, final String actual)
	{
		assertEqualsIgnoreCase("The expected result did not match the actual value.", expected, actual);
	}

	public static void assertEqualsIgnoreCase(final SoftAssert softAssert,
											  final String errorMessage,
											  final String expected,
											  final String actual)
	{
		if (!expected.equalsIgnoreCase(actual))
		{
			softAssert.fail(String.format("%s Expected: %s, Actual: %s", errorMessage, expected, actual));
		}
	}

	public static void assertContainsAll(final List fullList, final List expectedValues)
	{
		final List notInList = new ArrayList<>();

		for (final Object x : expectedValues)
		{
			if (!fullList.contains(x))
			{
				notInList.add(x);
			}
		}

		if (!notInList.isEmpty())
		{
			final String message = String.format("\nExpected elements that are not present: \n%s",
												 StrUtils.prettyList(notInList));
			Assert.fail(message);
		}
	}

	public static void assertMatches(final String regex, final String text)
	{
		if (!text.matches(regex))
		{
			Assert.fail(String.format("Text does not match regex\nExpected format: %s\nActual text:%s", regex, text));
		}
	}

	public static void assertContainsIgnoreCase(final List<String> list, final String expected)
	{
		if (!list.stream()
				 .map(String::toUpperCase)
				 .collect(Collectors.toList())
				 .contains(expected.toUpperCase()))
		{
			Assert.fail(String.format("List does not contain expected value.\nList:\n%s\nExpected value:\n%s",
									  StrUtils.prettyList(list),
									  expected.toUpperCase()));
		}
	}

	public static void assertContains(final String actual, final String expected)
	{
		Assert.assertTrue(String.format("String does not contain expected value.\nExpected value: %s \nActual value: %s.",
										expected,
										actual),
						  actual.contains(expected));
	}
}
