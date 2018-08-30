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
package com.cognifide.aet.communication.api.wrappers;

import com.cognifide.aet.communication.api.messages.MessageType;
import com.cognifide.aet.communication.api.metadata.Suite;
import java.io.Serializable;

public interface Run<T> extends Serializable {

  //TODO Change it to ENUM
  String getType();

  T getObjectToRun();

  default Suite getRealSuite(){
    return null;
  }

  default String getCorrelationId() {
    return null;
  }

  default String getProject() {
    return null;
  }

  default String getCompany() {
    return null;
  }

  default String getName() {
    return null;
  }

  default String getSuiteIdentifier() {
    return null;
  }
}