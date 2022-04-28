package eu.palantir.portal.dto.page;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "content",
        "contentSize",
        "total",
        "index",
        "size",
})
public class PageDto<T> implements Serializable {

    private List<T> content;
    // Total size of the query.
    private Long total;

    // Input Parameters for Pagination.
    // If size is zero, no pagination is done, these values are null.
    // If these values are null, total and contentSize are equal.
    private Integer index;
    private Integer size;

    public PageDto() {
    }

    public PageDto(List<T> content) {
        this(content, 0, 0);
    }

    public PageDto(List<T> content, Integer index, Integer size) {
        this(content, Long.valueOf(Integer.MAX_VALUE), index, size);
    }

    public PageDto(List<T> content, Long total, Integer index, Integer size) {
        this.content = content;
        if (size > 0) {
            this.index = index;
            this.size = size;
            this.total = total;
        } else {
            this.total = Long.valueOf(this.getContentSize());
            this.size = null;
            this.index = null;
        }
    }

    @JsonProperty("contentSize")
    public Integer getContentSize() {
        return content == null ? 0 : content.size();
    }

    public List<T> getContent() {
        return this.content;
    }

    public Long getTotal() {
        return this.total;
    }

    public Integer getIndex() {
        return this.index;
    }

    public Integer getSize() {
        return this.size;
    }

}
