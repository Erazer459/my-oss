package io.github.franzli347.foss.asyncTask;

import io.github.franzli347.foss.exception.AsyncException;
import lombok.*;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;

/**
 * @ClassName MyAsyncTask
 * @Author AlanC
 * @Date 2022/12/31 19:05
 **/
@Async
public abstract class MyAsyncTask {
    public abstract void run() throws AsyncException;
}
