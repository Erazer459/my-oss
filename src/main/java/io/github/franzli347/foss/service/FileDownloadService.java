package io.github.franzli347.foss.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

public interface FileDownloadService {
    DeferredResult<ResponseEntity<StreamingResponseBody>> download(String id);
}
