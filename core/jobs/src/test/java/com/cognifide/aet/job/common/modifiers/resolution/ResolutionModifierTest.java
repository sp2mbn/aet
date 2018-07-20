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
package com.cognifide.aet.job.common.modifiers.resolution;

import com.cognifide.aet.job.api.exceptions.ParametersException;
import com.cognifide.aet.job.api.exceptions.ProcessingException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ResolutionModifierTest {

  private static final String PARAM_MAXIMIZE = "maximize";

  private static final String WIDTH_PARAM = "width";

  private static final String HEIGHT_PARAM = "height";

  private static final String NOT_A_NUMBER = "NaN";

  private static final int WINDOW_WIDTH = 1024;

  private static final int WINDOW_HEIGHT = 768;

  private static final int CUSTOM_WIDTH = 800;

  private static final int CUSTOM_HEIGHT = 600;

  private static final int CHROME_LIMIT = 15000;

  @Mock
  private RemoteWebDriver webDriver;

  @Mock
  private Capabilities capabilities;

  @Mock
  private Map<String, String> params;

  @Mock
  private WebDriver.Options options;

  @Mock
  private WebDriver.Window window;

  @Mock
  private Dimension windowDimension;

  @InjectMocks
  private ResolutionModifier tested;

  @Before
  public void setUp() {
    when(webDriver.manage()).thenReturn(options);
    when(webDriver.getCapabilities()).thenReturn(capabilities);
    when(options.window()).thenReturn(window);
    when(window.getSize()).thenReturn(windowDimension);
    when(windowDimension.getWidth()).thenReturn(WINDOW_WIDTH);
    when(windowDimension.getHeight()).thenReturn(WINDOW_HEIGHT);
  }

  @Test(expected = ParametersException.class)
  public void setParametersTest_widthNotANumber() throws ParametersException {
    when(params.containsKey(WIDTH_PARAM)).thenReturn(true);
    when(params.get(WIDTH_PARAM)).thenReturn(NOT_A_NUMBER);

    tested.setParameters(params);
  }

  @Test(expected = ParametersException.class)
  public void setParametersTest_heightNotANumber() throws ParametersException {
    when(params.containsKey(HEIGHT_PARAM)).thenReturn(true);
    when(params.get(HEIGHT_PARAM)).thenReturn(NOT_A_NUMBER);

    tested.setParameters(params);
  }

  @Test(expected = ParametersException.class)
  public void setParametersTest_width0() throws ParametersException {
    when(params.containsKey(WIDTH_PARAM)).thenReturn(true);
    when(params.get(WIDTH_PARAM)).thenReturn("0");

    tested.setParameters(params);
  }

  @Test(expected = ParametersException.class)
  public void setParametersTest_height0() throws ParametersException {
    when(params.containsKey(HEIGHT_PARAM)).thenReturn(true);
    when(params.get(HEIGHT_PARAM)).thenReturn("0");

    tested.setParameters(params);
  }

  @Test
  public void collectTest_setWidthHeight() throws ParametersException, ProcessingException {
    when(params.containsKey(HEIGHT_PARAM)).thenReturn(true);
    when(params.containsKey(WIDTH_PARAM)).thenReturn(true);
    when(params.get(HEIGHT_PARAM)).thenReturn("" + CUSTOM_HEIGHT);
    when(params.get(WIDTH_PARAM)).thenReturn("" + CUSTOM_WIDTH);

    tested.setParameters(params);
    tested.collect();

    verify(windowDimension, never()).getWidth();
    verify(windowDimension, never()).getHeight();
    verify(window, times(1)).setSize(new Dimension(CUSTOM_WIDTH, CUSTOM_HEIGHT));
  }

  @Test
  public void collectTest_setOnlyWidth() throws ParametersException, ProcessingException {
    when(params.containsKey(WIDTH_PARAM)).thenReturn(true);
    when(params.containsKey(HEIGHT_PARAM)).thenReturn(false);
    when(params.get(WIDTH_PARAM)).thenReturn("" + CUSTOM_WIDTH);

    when(capabilities.getBrowserName()).thenReturn("chrome");
    when(webDriver.executeScript("return document.body.scrollHeight")).thenReturn(Long.parseLong( "" + CHROME_LIMIT + 5000));

    tested.setParameters(params);
    tested.collect();

    verify(windowDimension, never()).getWidth();
    verify(windowDimension, never()).getHeight();
    verify(window, atLeastOnce()).setSize(new Dimension(CUSTOM_WIDTH, CHROME_LIMIT));
  }
}
