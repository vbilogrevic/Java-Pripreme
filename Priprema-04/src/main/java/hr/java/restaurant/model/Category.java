package hr.java.restaurant.model;

public class Category extends Entity {

    private String name;
    private String description;

    public Category(Long id, String name, String description) {
        super(id);
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class CategoryBuilder {
        long id;
        String name;
        String description;

        public CategoryBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public CategoryBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public CategoryBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Category build() {
            return new Category(id, name, description);
        }
    }
}
