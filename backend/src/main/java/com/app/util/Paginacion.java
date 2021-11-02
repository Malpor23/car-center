package com.app.util;

import java.util.Collection;

/**
 *
 * @author Manuel Gomez
 */
public class Paginacion {

    private Collection<?> content;
    private Long totalPages;
    private Integer size;
    private Integer number;
    private Integer totalRegistros;

    public Paginacion() {
    }

    public Collection<?> getContent() {
        return content;
    }

    public void setContent(Collection<?> content) {
        this.content = content;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getTotalRegistros() {
        return totalRegistros;
    }

    public void setTotalRegistros(Integer totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

}
