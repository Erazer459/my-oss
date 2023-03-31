package io.github.franzli347.foss.web.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface FileDownloadService {

    void download(String id, String bid, Boolean inline, HttpServletRequest request, HttpServletResponse response);
}
