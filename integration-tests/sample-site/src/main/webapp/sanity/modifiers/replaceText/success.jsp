<%--

    AET

    Copyright (C) 2013 Cognifide Limited

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

--%>
<%
Long millis = System.currentTimeMillis();
%>
<p id="demo1">Replace me by Xpath 1: <%=millis%></p>
<p id="demo2" class="replaceMe">Replace me by css Selector 2: <%=2*millis%></p>
<input id="demo3" type="text" name="Value shouldn't be a number" value="<%=millis%>">