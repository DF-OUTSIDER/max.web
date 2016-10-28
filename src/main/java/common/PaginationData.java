package common;

import java.io.Serializable;
import java.util.List;

public class PaginationData<T> implements Serializable {
    private int pageSize;
    private int pageIndex;
    private int pageCount;
    private int resultCount;
    private List<T> result;

    /**
     * default constructor pageSize=10 and currentIndex=1
     *
     * @param result
     */
    public PaginationData(List<T> result, int resultCount) {
        this.pageSize = 10;
        this.pageIndex = 1;
        this.result = result;
        this.resultCount = resultCount;
        this.pageCount = (resultCount + pageSize - 1) / pageSize;
    }

    public PaginationData(int pageSize, int pageIndex, List<T> result, int resultCount) {
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
        this.result = result;
        this.pageCount = (resultCount + pageSize - 1) / pageSize;
        this.resultCount = result.size();
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getResultCount() {
        return resultCount;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
