package com.automation.core.driver;

import org.openqa.selenium.*;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v91.network.Network;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class CoreDriver implements WrapsDriver, WebDriver
{
	protected final WebDriver wrappedDriver;

	public CoreDriver(final WebDriver wrappedDriver)
	{
		this.wrappedDriver = wrappedDriver;
	}

	@Override
	public WebDriver getWrappedDriver()
	{
		return wrappedDriver;
	}

	@Override
	public void get(final String url)
	{
		wrappedDriver.get(url);
	}

	@Override
	public String getCurrentUrl()
	{
		return wrappedDriver.getCurrentUrl();
	}

	@Override
	public String getTitle()
	{
		return wrappedDriver.getTitle();
	}

	@Override
	public List<WebElement> findElements(final By by)
	{
		return wrappedDriver.findElements(by);
	}

	@Override
	public WebElement findElement(final By by)
	{
		return wrappedDriver.findElement(by);
	}

	@Override
	public String getPageSource()
	{
		return wrappedDriver.getPageSource();
	}

	@Override
	public void close()
	{
		wrappedDriver.close();
	}

	@Override
	public void quit()
	{
		wrappedDriver.quit();
	}

	@Override
	public Set<String> getWindowHandles()
	{
		return wrappedDriver.getWindowHandles();
	}

	@Override
	public String getWindowHandle()
	{
		return wrappedDriver.getWindowHandle();
	}

	@Override
	public TargetLocator switchTo()
	{
		return wrappedDriver.switchTo();
	}

	@Override
	public Navigation navigate()
	{
		return wrappedDriver.navigate();
	}

	@Override
	public Options manage()
	{
		return wrappedDriver.manage();
	}

	public DevTools getDevtools()
	{
		if (getWrappedDriver() instanceof HasDevTools)
		{
			final HasDevTools driver = (HasDevTools) getWrappedDriver();
			final DevTools devTools = driver.getDevTools();
			devTools.createSession();
			devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

			return devTools;
		}
		else
		{
			throw new IllegalStateException("Given WebDriver subclass does not support dev tools: " + getWrappedDriver().getClass().getName());
		}
    }

    public Object executeScript(final String script, final Object ... args)
	{
		return ((JavascriptExecutor) wrappedDriver).executeScript(script, args);
	}
}
