package com.fanplayground.fanplayground.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BoardColumn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "column_id")
    private Long ColumnId;

    @Column(name = "column_name", nullable = false)
    private String ColumnName;

    @Column(name = "column_no", nullable = false)
    private String ColumnNo;
//
//    /**
//     * BoardColumn : Board = n : 1
//     */
//    @ManyToOne
//    @JoinColumn(name = "board_id")
//    private Board board;

//    /**
//     * BoardColumn : Card = 1 : n
//     */
//    @OneToMany(mappedBy = "boardColumn",orphanRemoval = true)
//    @JoinColumn(name = "column_id")
//    @JsonBackReference
//    private List<Card> cards = new ArrayList<>();





}
