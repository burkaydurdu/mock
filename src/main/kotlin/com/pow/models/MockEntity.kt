package com.pow.models

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import java.util.*
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class MockEntity {
        @Id
        @GeneratedValue(generator = "uuid2")
        @GenericGenerator(name = "uuid2", strategy = "uuid2")
        lateinit var id: UUID

        @Column(columnDefinition = "timestamp with time zone not null")
        var createdAt: LocalDateTime = LocalDateTime.now()

        @Column(columnDefinition = "timestamp with time zone not null")
        var updatedAt: LocalDateTime = LocalDateTime.now()

        @Column(columnDefinition = "timestamp with time zone")
        @JsonSerialize(using = LocalDateTimeSerializer::class)
        @JsonDeserialize(using = LocalDateTimeDeserializer::class)
        var deletedAt: LocalDateTime? = null
    }