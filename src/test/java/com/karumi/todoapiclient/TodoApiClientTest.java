/*
 *   Copyright (C) 2016 Karumi.
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.karumi.todoapiclient;

import com.karumi.todoapiclient.dto.TaskDto;
import com.karumi.todoapiclient.exception.ItemNotFoundException;

import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TodoApiClientTest extends MockWebServerTest {

  private TodoApiClient apiClient;

  @Before public void setUp() throws Exception {
    super.setUp();
    String mockWebServerEndpoint = getBaseEndpoint();
    apiClient = new TodoApiClient(mockWebServerEndpoint);
  }

  @Test public void sendsAcceptAndContentTypeHeaders() throws Exception {
    enqueueMockResponse();

    apiClient.getAllTasks();

    assertRequestContainsHeader("Accept", "application/json");
  }

  @Test public void sendsAcceptLanguageHeaders() throws Exception {
    enqueueMockResponse();

    apiClient.getAllTasks();

    assertRequestContainsHeader("Accept-Language", "es");
  }

  @Test public void sendsGetAllTaskRequestToTheCorrectEndpoint() throws Exception {
    enqueueMockResponse();

    apiClient.getAllTasks();

    assertGetRequestSentTo("/todos");
  }

  @Test public void parsesTasksProperlyGettingAllTheTasks() throws Exception {
    enqueueMockResponse(200, "getTasksResponse.json");

    List<TaskDto> tasks = apiClient.getAllTasks();

    assertEquals(tasks.size(), 200);
    assertTaskContainsExpectedValues(tasks.get(0));
  }

    @Test
    public void parsesPropertlyGettingAnEmptyTaskList() throws Exception {
        enqueueMockResponse(200, "emptyListResponse.json");

        List<TaskDto> tasks = apiClient.getAllTasks();

        assertEquals(0, tasks.size());
    }

    @Test(expected = ItemNotFoundException.class)
    public void parsesPropertlyGettingAn404() throws Exception {
        enqueueMockResponse(404);

        apiClient.getAllTasks();
    }


    @Test
    public void parsesPropertlyGettingAnEmptyResponse() throws Exception {
        enqueueMockResponse(200, "emptyResponse.json");

        List<TaskDto> tasks = apiClient.getAllTasks();

        assertNull( tasks);
    }


    private void assertTaskContainsExpectedValues(TaskDto task) {
    assertEquals(task.getId(), "1");
    assertEquals(task.getUserId(), "1");
    assertEquals(task.getTitle(), "delectus aut autem");
    assertFalse(task.isFinished());
  }
}
