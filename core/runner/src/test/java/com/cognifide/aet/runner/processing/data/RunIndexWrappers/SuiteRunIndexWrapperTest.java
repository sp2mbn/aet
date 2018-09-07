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
package com.cognifide.aet.runner.processing.data.RunIndexWrappers;

import com.cognifide.aet.communication.api.wrappers.Run;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SuiteRunIndexWrapperTest {

  private SuiteRunIndexWrapper suiteRunIndexWrapper;

  @Mock
  Run objectToRun;

  @Before
  public void setUp() throws Exception {
    suiteRunIndexWrapper = new SuiteRunIndexWrapper(objectToRun);
  }

  @Test
  public void getUrls() {
  }

  @Test
  public void countUrls() {
  }

}