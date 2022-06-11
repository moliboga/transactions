package com.example.transactions.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "log")
public class Log extends BaseModel{
    @Column
    private String fromWarehouse;

    @Column
    @NonNull
    private String toWarehouse;

    @Column
    @NonNull
    private String productName;

    @Column
    @NonNull
    private Integer amount;
}
