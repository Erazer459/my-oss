package io.github.franzli347.foss.service;

import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface FileDownloadService {
    DeferredResult<ResponseEntity<StreamingResponseBody>> download(String id);

    void download(String id, HttpServletRequest request, HttpServletResponse response);

    void player(String id, HttpServletRequest request, HttpServletResponse response);
}
