package com.swithun.backend.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "tag", schema = "scoring_system", catalog = "")
public class TagEntity {
    private Integer id;
    private String name;
    private Collection<TagCommentEntity> tagCommentsById;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagEntity tagEntity = (TagEntity) o;
        return Objects.equals(id, tagEntity.id) && Objects.equals(name, tagEntity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @OneToMany(mappedBy = "tagByTagId")
    public Collection<TagCommentEntity> getTagCommentsById() {
        return tagCommentsById;
    }

    public void setTagCommentsById(Collection<TagCommentEntity> tagCommentsById) {
        this.tagCommentsById = tagCommentsById;
    }
}
