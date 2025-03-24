package com.everyplaceinkorea.epik_boot3_api.entity.musical;

import com.everyplaceinkorea.epik_boot3_api.admin.contents.musical.dto.MusicalTicketPriceDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MusicalTicketPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String seat;
    private String price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="musical_id")
    private Musical musical;

    public void updateMusicalTicketPrice(MusicalTicketPriceDto musicalTicketPriceDto) {
        this.setSeat(musicalTicketPriceDto.getSeat());
        this.setPrice(musicalTicketPriceDto.getPrice());
    }
}
