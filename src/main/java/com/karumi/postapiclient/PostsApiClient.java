package com.karumi.postapiclient;


import com.karumi.postapiclient.dto.PostDto;
import com.karumi.todoapiclient.dto.TaskDto;
import com.karumi.todoapiclient.exception.ItemNotFoundException;
import com.karumi.todoapiclient.exception.NetworkErrorException;
import com.karumi.todoapiclient.exception.TodoApiClientException;
import com.karumi.todoapiclient.exception.UnknownErrorException;
import com.karumi.todoapiclient.interceptor.DefaultHeadersInterceptor;

import java.io.IOException;
import java.util.List;

import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import static com.karumi.postapiclient.PostsApiClientConfig.BASE_ENDPOINT;

public class PostsApiClient {

    private final PostsService postsService;

    public PostsApiClient() {
        this(BASE_ENDPOINT);
    }

    public PostsApiClient(String baseEndpoint) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseEndpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofit.client().interceptors().add(new DefaultHeadersInterceptor());
        this.postsService = retrofit.create(PostsService.class);
    }


    public List<PostDto> getAllPost() throws TodoApiClientException {
        try {
            Response<List<PostDto>> response = postsService.getAll().execute();
            inspectResponseForErrors(response);
            return response.body();
        } catch (IOException e) {
            throw new NetworkErrorException();
        }
    }
    private void inspectResponseForErrors(Response response) throws TodoApiClientException {
        int code = response.code();
        if (code == 404) {
            throw new ItemNotFoundException();
        } else if (code >= 400) {
            throw new UnknownErrorException(code);
        }
    }
}
