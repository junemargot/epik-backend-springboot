package com.everyplaceinkorea.epik_boot3_api.entity.popup;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PopupImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "img_order", nullable = false)
    private Integer imgOrder;

    @Column(name = "img_saved_name", nullable = false)
    private String imgSavedName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "popup_id")
    private Popup popup;

//    public void addPopup(Popup popup) {
//        this.popup = popup;
//    }
}
