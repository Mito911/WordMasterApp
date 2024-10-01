package pl.maciejtuznik.WordMasterApp.word;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import pl.maciejtuznik.WordMasterApp.categories.Categories;

import java.time.LocalDateTime;

@Entity
public class Words {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String originalWord;
    private String translatedWord;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference // Ignoruj serializację kategorii w słowach, aby przerwać rekursję
    private Categories category;

    private LocalDateTime createdAt;

    // Konstruktor z kategorią
    public Words(String originalWord, String translatedWord, Categories category) {
        this.originalWord = originalWord;
        this.translatedWord = translatedWord;
        this.category = category;
        this.createdAt = LocalDateTime.now();
    }

    // Domyślny konstruktor wymagany przez JPA
    public Words() {
        this.createdAt = LocalDateTime.now();
    }

    // Gettery i settery
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalWord() {
        return originalWord;
    }

    public void setOriginalWord(String originalWord) {
        this.originalWord = originalWord;
    }

    public String getTranslatedWord() {
        return translatedWord;
    }

    public void setTranslatedWord(String translatedWord) {
        this.translatedWord = translatedWord;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
