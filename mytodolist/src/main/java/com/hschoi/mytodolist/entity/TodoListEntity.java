package com.hschoi.mytodolist.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="todo")
public class TodoListEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "content")
    private String content;

    @Column(name = "upper_todo")
    private String upperTodo;

    @Column(name = "reg_dt")
    private Date regDt;

    @Column(name = "mod_dt")
    private Date modDt;

    @Column(name = "compl_dt")
    private Date complDt;

    @OneToMany(mappedBy = "children", targetEntity = DirectoryEntity.class, fetch = FetchType.LAZY)
    private List<DirectoryEntity> parents;

    @OneToMany(mappedBy = "parents", targetEntity = DirectoryEntity.class, fetch = FetchType.LAZY)
    private List<DirectoryEntity> children;

    public boolean isCompleted () {
        return (complDt != null);
    }

}
