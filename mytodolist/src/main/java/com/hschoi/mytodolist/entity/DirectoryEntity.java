package com.hschoi.mytodolist.entity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@NoArgsConstructor
@Entity
@Table( name="directory", uniqueConstraints = { @UniqueConstraint(columnNames = {"parents", "children"}) } )
public class DirectoryEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "parents")
    private Integer parents;

    @Column(name = "children")
    private Integer children;
}
