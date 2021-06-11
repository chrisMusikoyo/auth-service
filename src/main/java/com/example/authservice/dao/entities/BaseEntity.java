package com.example.authservice.dao.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/***
 *
 * @version 1.0
 */
@MappedSuperclass
@Getter
@Setter
public class BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "createdon")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    public LocalDateTime createdOn;
    @Column(name = "softDelete", columnDefinition = "boolean default false")
    public boolean softDelete;

    @PrePersist
    public void addData() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        ZoneId zoneId = ZoneId.of("Africa/Nairobi");
        ZonedDateTime kenya_zone = zonedDateTime.withZoneSameInstant(zoneId);
        this.createdOn = kenya_zone.toLocalDateTime();
        this.softDelete = false;
    }
}
