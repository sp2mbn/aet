/**
 * AET
 *
 * Copyright (C) 2013 Cognifide Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.cognifide.aet.worker.drivers.firefox;

import static com.cognifide.aet.worker.drivers.WebDriverHelper.NAME;
import static com.cognifide.aet.worker.drivers.WebDriverHelper.NAME_LABEL;
import static com.cognifide.aet.worker.drivers.WebDriverHelper.PATH;

import com.cognifide.aet.job.api.collector.HttpRequestExecutorFactory;
import com.cognifide.aet.job.api.collector.ProxyServerWrapper;
import com.cognifide.aet.job.api.collector.WebCommunicationWrapper;
import com.cognifide.aet.worker.api.WebDriverFactory;
import com.cognifide.aet.worker.exceptions.WorkerException;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.osgi.framework.Constants;

@Service
@Component(immediate = false, description = "AET Firefox WebDriver Factory", label = "AET Firefox WebDriver Factory", metatype = true)
@Properties({@Property(name = Constants.SERVICE_VENDOR, value = "Cognifide Ltd")})
public class FirefoxWebDriverFactory implements WebDriverFactory {

  private static final String DEFAULT_FIREFOX_BINARY_PATH = "/usr/bin/firefox";

  private static final String DEFAULT_FIREFOX_ERROR_LOG_FILE_PATH = "/opt/aet/firefox/log/stderr.log";

  private static final String LOG_FILE_PATH = "logFilePath";

  private static final String DEFAULT_FF_NAME = "ff";

  @Reference
  private HttpRequestExecutorFactory requestExecutorFactory;

  @Property(name = NAME, label = NAME_LABEL, value = DEFAULT_FF_NAME)
  private String name;

  @Property(name = PATH, label = "Custom path to Firefox binary", value = DEFAULT_FIREFOX_BINARY_PATH)
  private String path;

  @Property(name = LOG_FILE_PATH, label = "Path to firefox error log", value = DEFAULT_FIREFOX_ERROR_LOG_FILE_PATH)
  private String logFilePath;

  @Override
  public String getName() {
    return name;
  }

  @Override
  public WebCommunicationWrapper createWebDriver(ProxyServerWrapper proxyServer)
      throws WorkerException {
    try {
      Proxy proxy = proxyServer.seleniumProxy();
      proxyServer.setCaptureContent(true);
      proxyServer.setCaptureHeaders(true);

      DesiredCapabilities capabilities = new DesiredCapabilities();
      capabilities.setCapability(CapabilityType.PROXY, proxy);
      capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

      FirefoxProfile fp = getFirefoxProfile();
      fp.setAcceptUntrustedCertificates(true);
      fp.setAssumeUntrustedCertificateIssuer(false);
      setCommonCapabilities(capabilities, fp);

      return new FirefoxCommunicationWrapperImpl(getFirefoxDriver(capabilities), proxyServer,
          requestExecutorFactory
              .createInstance());
    } catch (Exception e) {
      throw new WorkerException(e.getMessage(), e);
    }
  }

  @Override
  public WebCommunicationWrapper createWebDriver() throws WorkerException {
    try {
      DesiredCapabilities capabilities = new DesiredCapabilities();

      FirefoxProfile fp = getFirefoxProfile();
      setCommonCapabilities(capabilities, fp);

      return new FirefoxCommunicationWrapperImpl(getFirefoxDriver(capabilities), null,
          requestExecutorFactory
              .createInstance());
    } catch (Exception e) {
      throw new WorkerException(e.getMessage(), e);
    }
  }

  private FirefoxProfile getFirefoxProfile() throws IOException {
    final FirefoxProfile firefoxProfile = FirefoxProfileBuilder.newInstance()
        .withUnstableAndFastLoadStrategy()
        .withLogfilePath(logFilePath)
        .withFlashSwitchedOff()
        .withForcedAliasing()
        .withJavaScriptErrorCollectorPlugin()
        .withDevtoolsStorageEnabled()
        .withAllCookiesAccepted()
        .withRandomPort()
        .withUpdateDisabled()
        .build();
    System.setProperty("webdriver.firefox.logfile", logFilePath);
    System.setProperty("webdriver.load.strategy", "unstable");
    return firefoxProfile;
  }

  private void setCommonCapabilities(DesiredCapabilities capabilities, FirefoxProfile fp) {
    capabilities.setCapability(FirefoxDriver.PROFILE, fp);
    capabilities.setCapability("marionette", false);
    capabilities.setCapability("firefox_binary", new File(path).getAbsolutePath());
  }

  private WebDriver getFirefoxDriver(DesiredCapabilities capabilities) {
    WebDriver driver = new FirefoxDriver(capabilities);
    driver.manage().timeouts().pageLoadTimeout(5L, TimeUnit.MINUTES);
    return driver;
  }

  @Activate
  public void activate(Map<String, String> properties) {
    this.name = PropertiesUtil.toString(properties.get(NAME), DEFAULT_FF_NAME);
    this.path = PropertiesUtil.toString(properties.get(PATH), DEFAULT_FIREFOX_BINARY_PATH);
    this.logFilePath = PropertiesUtil
        .toString(properties.get(LOG_FILE_PATH), DEFAULT_FIREFOX_ERROR_LOG_FILE_PATH);

  }

}