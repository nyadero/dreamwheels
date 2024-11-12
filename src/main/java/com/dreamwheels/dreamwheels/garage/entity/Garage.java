package com.dreamwheels.dreamwheels.garage.entity;

import com.dreamwheels.dreamwheels.comments.entity.Comment;
import com.dreamwheels.dreamwheels.garage.enums.EngineAspiration;
import com.dreamwheels.dreamwheels.garage.enums.FuelType;
import com.dreamwheels.dreamwheels.garage.enums.GarageCategory;
import com.dreamwheels.dreamwheels.garage.enums.TransmissionType;
import com.dreamwheels.dreamwheels.uploaded_files.entity.UploadedFile;
import com.dreamwheels.dreamwheels.users.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "garages")
@Inheritance(strategy = InheritanceType.JOINED)
public class Garage{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private double buyingPrice;

    private int previousOwnersCount;

    private int mileage;

    private double acceleration;

    private int topSpeed;

    private int enginePower;

    private int torque;

    private int commentsCount;

    private int likesCount;

    @Enumerated(EnumType.STRING)
    private TransmissionType transmissionType;

    @Enumerated(EnumType.STRING)
    private GarageCategory category;

    @Enumerated(value = EnumType.STRING)
    private FuelType fuelType;

    @Enumerated(value = EnumType.STRING)
    private EngineAspiration engineAspiration;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "userId", foreignKey = @ForeignKey(name = "FK_USER_ID"))
    @JsonManagedReference
    private User user;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "garage")
    private List<UploadedFile> garageFiles = new ArrayList<>(0);

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "garage")
    @OrderBy(value = "createdAt")
    private List<Comment> comments = new ArrayList<>();

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;


}
