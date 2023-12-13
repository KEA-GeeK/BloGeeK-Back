package Geek.Blog.entity;

import lombok.Getter;

@Getter
public enum Category {
    CULTURAL_LIFE(1),
    EDUCATION(2),
    FASHION_BEAUTY(3),
    HEALTH(4),
    HOBBIES(5),
    LIFESTYLE(6),
    SOCIETY(7),
    ETC(8);

    private final int id;

    Category(int id){
        this.id = id;
    }

    public static Category getNameById(int id) {
        for (Category category : Category.values()) {
            if (category.getId() == id) {
                return category;
            }
        }
        return null;
    }
}