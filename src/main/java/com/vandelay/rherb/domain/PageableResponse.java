package com.vandelay.rherb.domain;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Data
public class PageableResponse {
    @JsonIgnore
    private String type;
    private Meta meta;
    @JsonIgnore
    private List<Object> data;

    public PageableResponse(String type, Page p, String link) {
        this.type = type;
        this.meta = new Meta(p);
        setLinks(link);
        this.data = p.getContent();
    }

    private void setLinks(String link) {
        int selfPage = this.meta.getPage();
        int prevPage = this.meta.getPage() >= 1 ? this.meta.getPage() - 1 : this.meta.getPage();
        int nextPage = this.meta.getPage() < (this.meta.getTotalPages() - 1) ? this.meta.getPage() + 1 : this.meta.getPage();
        this.meta.setSelf(link + "?page=" + this.meta.getPage() + "&pageSize=" + this.meta.getPageSize());
        if (prevPage != selfPage)
            this.meta.setPrev(link + "?page=" + prevPage + "&pageSize=" + this.meta.getPageSize());
        if (nextPage != selfPage)
            this.meta.setNext(link + "?page=" + nextPage + "&pageSize=" + this.meta.getPageSize());
    }

    @JsonAnyGetter
    public Map<String, Object> any() {
        return Collections.singletonMap(type, data);
    }
}

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
class Meta {
    private int page;
    private int pageSize;
    private long totalItems;
    private int totalPages;
    private String self;
    private String prev;
    private String next;

    public Meta(Page p) {
        this.page = p.getPageable().getPageNumber();
        this.pageSize = p.getPageable().getPageSize();
        this.totalItems = p.getTotalElements();
        this.totalPages = p.getTotalPages();
    }
}