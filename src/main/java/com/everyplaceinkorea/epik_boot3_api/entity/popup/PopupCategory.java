package com.everyplaceinkorea.epik_boot3_api.entity.popup;

import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PopupCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String content;

}
