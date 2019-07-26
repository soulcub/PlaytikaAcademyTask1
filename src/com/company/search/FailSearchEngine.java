package com.company.search;

import com.company.servers.FallibleWithInners;

public class FailSearchEngine {

    public Result findFail(FallibleWithInners currentFallibleWithInners) {
        int start = 0;
        int end = currentFallibleWithInners.getSize();

        while (end - start > 1) {
            int current = (start + end) / 2;
            if (currentFallibleWithInners.getInnerFallible(current).isFailed()) {
                end = current;
            } else {
                start = current;
            }
        }
        return getResult(currentFallibleWithInners, start, end);
    }

    private Result getResult(FallibleWithInners currentFallibleWithInners, int start, int end) {
        FallibleWithInners innerFailed = currentFallibleWithInners.getInnerFallible(start);
        if (!innerFailed.isFailed()) {
            innerFailed = currentFallibleWithInners.getInnerFallible(end);
        }
        return getResult(innerFailed);
    }

    private Result getResult(FallibleWithInners failed) {
        if (failed.getSize() > 0) {
            Result result = findFail(failed);
            result.setFailedServer(failed.getId());
            return result;
        } else {
            return new Result(failed.getId());
        }
    }

}
