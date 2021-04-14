package com.learning.client;

import com.learning.controller.SwaggerApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "test-client", url="${feign.api.coffee-machine}")
public interface TestClient extends SwaggerApi {
}
