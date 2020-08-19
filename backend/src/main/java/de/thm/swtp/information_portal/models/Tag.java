package de.thm.swtp.information_portal.models;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import lombok.Data;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Data
@Getter
@Setter
@AllArgsConstructor

//Name of collection in MongoDB
@Document(collection = "tag")
public class Tag {

    private String id;

    // Name of Tag
    private String name;

    // Creation Date as Unix-Timestamp
    private Long timeStamp;

    public Tag(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name.toUpperCase();
        this.timeStamp = Instant.now().getEpochSecond() * 1000;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Tag tag = (Tag) o;
        return Objects.equals(this.name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }
}
