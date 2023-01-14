package io.github.franzli347.foss.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface FileDownloadService {

    void download(String id, Boolean inline, HttpServletRequest request, HttpServletResponse response);
}
