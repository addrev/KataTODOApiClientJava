package com.karumi.postapiclient;

import com.karumi.todoapiclient.MockWebServerTest;
import com.karumi.todoapiclient.TodoApiClient;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PostsApiClientTest extends MockWebServerTest{
    private PostsApiClient apiClient;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        String mockWebServerEndpoint = getBaseEndpoint();
        apiClient = new PostsApiClient(mockWebServerEndpoint);
    }


    @Test
    public void getAllPosts_sendsHeadersProperly() throws Exception {
        enqueueMockResponse();

        apiClient.getAllPost();

        assertRequestContainsHeader("Accept", "application/json");
    }
}