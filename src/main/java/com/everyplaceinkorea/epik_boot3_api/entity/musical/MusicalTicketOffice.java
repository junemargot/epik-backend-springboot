package com.everyplaceinkorea.epik_boot3_api.entity.musical;

import com.everyplaceinkorea.epik_boot3_api.admin.contents.musical.dto.MusicalTicketOfficeDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MusicalTicketOffice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String link;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "musical_id")
    private Musical musical;

    public void updateMusicalTicketOffice(MusicalTicketOfficeDto musicalTicketOfficeDto) {
        this.setName(musicalTicketOfficeDto.getName());
        this.setLink(musicalTicketOfficeDto.getLink());
    }
}
