package eu.palantir.portal.dto.page;

import java.io.Serializable;

public class PageListDto<T> implements Serializable {

    public java.util.List<T> content;
    // Total size of the query. It is not always equal to this.content.size() or
    // this.size
    public Long total;

    public PageListDto(java.util.List<T> content, Long total) {
        this.content = content;
        this.total = total;
    }

    public PageListDto(java.util.List<T> content, Integer total) {
        this.content = content;
        this.total = Long.valueOf(total);
    }

    public int getContentSize() {
        return content.size();
    }

}
