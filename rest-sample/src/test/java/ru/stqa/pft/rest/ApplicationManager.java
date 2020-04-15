package ru.stqa.pft.rest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;

public class ApplicationManager {

  private WebDriver wd;
  private String browser;

  public ApplicationManager(String browser) {
    this.browser = browser;
    }

  public void init() {
    if (browser.equals(BrowserType.FIREFOX)) {
      wd = new FirefoxDriver();
    } else if (browser.equals(BrowserType.CHROME)) {
      wd = new ChromeDriver();
    }
  }
  public void stop() {
    if (wd != null) {
      wd.quit();
    }
  }
}
